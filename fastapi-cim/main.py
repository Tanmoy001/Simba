from fastapi import FastAPI, Body, Request
import logging
from fastapi.middleware.cors import CORSMiddleware
from langchain.prompts import PromptTemplate
from langchain_groq import ChatGroq
from langchain_core.output_parsers import StrOutputParser
import re

app = FastAPI()

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

llm = ChatGroq(
    temperature=0,
    groq_api_key="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",
    model_name="deepseek-r1-distill-llama-70b"
)

def get_prompt_for_document(url):
    """Generate appropriate prompt based on URL type"""
    # Add your URL type detection logic here
    # Example pattern matching:
    if "youtube.com" in url:
        return "Briefly summarize the key points from this YouTube video at {url}"
    elif "arxiv.org" in url:
        return "Briefly provide a technical summary of this research paper at {url}"
    else:
        return "Briefly provide a concise summary of the content at this URL: {url}"

def clean_text(text):
    """Clean and format the LLM response"""
    text = re.sub(r'\*\*(.*?)\*\*', r'\1', text)  # Remove bold markers
    text = re.sub(r'\n+', '\n', text).strip()     # Clean extra newlines
    text = re.sub(r'</?think>', '', text)
    return text

def extract_data_with_llm(url):
    try:
        prompt_text = get_prompt_for_document(url)
        prompt = PromptTemplate(
            input_variables=["url"],
            template=prompt_text
        )
        
        chain = prompt | llm | StrOutputParser()
        
        response = chain.invoke({"url": url})
        cleaned_response = clean_text(response)
        return cleaned_response
        
    except Exception as e:
        logger.error(f"LLM Error: {str(e)}")
        return f"Error generating summary: {str(e)}"

@app.post("/process")
async def process_url(request: Request, url: str = Body(..., media_type="text/plain")):
    logger.info(f"Received request for URL: {url}")
    
    try:
        summary = extract_data_with_llm(url)
        return {
            "url": url,
            "summary": summary,
            "status": "success"
        }
    except Exception as e:
        logger.error(f"Processing failed: {str(e)}")
        return {
            "error": str(e),
            "status": "error"
        }