import React from 'react';
import '../App.css';

const Home = () => {
  return (
    <div className="container">
      <h1 className="page-title">Summary Generator</h1>
      
      <div className="gradient-box">
        <h2>Welcome to our AI-powered Summary Service</h2>
        <p>
          Generate concise summaries from any web content with our advanced AI technology. 
          Save time and get the key points from articles, blogs, and documents instantly.
        </p>
        
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '20px', marginTop: '40px' }}>
        <div className="gradient-box">
          <h3>Fast Summaries</h3>
          <p>Get summaries in seconds with our high-speed processing engine.</p>
        </div>
        <div className="gradient-box">
          <h3>Accurate Results</h3>
          <p>Our AI extracts the most important information with precision.</p>
        </div>
        <div className="gradient-box">
          <h3>History Tracking</h3>
          <p>Access your summary history anytime from any device.</p>
        </div>
      </div>
    </div>
  );
};

export default Home;