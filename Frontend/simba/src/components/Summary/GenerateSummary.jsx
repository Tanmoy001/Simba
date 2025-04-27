import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { generateSummary } from '../../store/actions/summaryActions';
import '../../App.css';

const GenerateSummary = () => {
  const [url, setUrl] = useState('');
  const dispatch = useDispatch();
  const { summary, loading, error } = useSelector((state) => state.summary);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await dispatch(generateSummary(url));
    } catch (err) {
      // Error is already handled in the action
    }
  };

  return (
    <div className="gradient-box">
      <h2>Generate New Summary</h2>
      <form>
        <input
          type="url"
          placeholder="Enter URL to summarize"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          required
        />
        <button type="submit" className="btn" disabled={loading} onClick={handleSubmit}>
          {loading ? 'Generating...' : 'Generate Summary'}
        </button>
      </form>
      
      {error && <p style={{ color: '#ff6b6b' }}>{error}</p>}
      
      {summary && (
        <div style={{ marginTop: '20px', padding: '15px', background: 'rgba(0, 0, 0, 0.3)', borderRadius: '8px' }}>
          <h3>Generated Summary:</h3>
          <p>{summary.summary}</p>
        </div>
      )}
    </div>
  );
};

export default GenerateSummary;