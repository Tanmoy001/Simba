import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getHistory } from '../../store/actions/summaryActions';
import '../../App.css';

const History = () => {
  const dispatch = useDispatch();
  const { history, loading, error } = useSelector((state) => state.summary);
  // const username = localStorage.getItem('username');

  useEffect(() => {
    dispatch(getHistory());
  }, [dispatch]);

  return (
    <div className="gradient-box">
      <h2>Your Summary History</h2>
      
      {loading ? (
        <p>Loading your history...</p>
      ) : error ? (
        <p style={{ color: '#ff6b6b' }}>{error.message}</p>
      ) : history.length === 0 ? (
        <p>No history found. Generate some summaries!</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>URL</th>
              <th>All Info</th>
            </tr>
          </thead>
          <tbody>
            {history.map((item, index) => (
              <tr key={index}>
                <td>{item.id}</td>
                <td>
                  <a href={item.url} target="_blank" rel="noopener noreferrer">
                    {item.url.length > 30 ? `${item.url.substring(0, 30)}...` : item.url}
                  </a>
                </td>
                <td>
                  {item.summary.length > 50 
                    ? `${item.summary.substring(0, 50)}...` 
                    : item.summary}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default History;