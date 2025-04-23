import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../App.css';
import { logout } from '../store/actions/authActions';
import { useDispatch, useSelector } from 'react-redux'; // useSelector to get authentication status from Redux store

const Navbar=()=> {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector(state => state.user.isAuthenticated); // Get authentication status from Redux

  const handleLogout = () => {
    try {
      dispatch(logout()); // Dispatch logout action to clear store state
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      navigate('/'); // Navigate to homepage after logout
    } catch (err) {
      console.error("Error during logout", err);
    }
  };

  return (
    <nav style={{
      display: 'flex',
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: '20px',
      background: 'rgba(255, 255, 255, 0.05)',
      backdropFilter: 'blur(10px)',
      position: 'sticky',
      top: 0,
      zIndex: 100,
    }}>
      <Link to="/" style={{ textDecoration: 'none', color: 'white', fontSize: '1.5rem', fontWeight: 'bold' }}>
        SummaryAI
      </Link>
      
      <div style={{ display: 'flex', gap: '20px' }}>
        {isAuthenticated ? (
          <>
            <Link to="/summary-gen" className="btn" style={{ padding: '10px 20px' }}>Generator</Link>
            <Link to="/history-log" className="btn" style={{ padding: '10px 20px' }}>History</Link>
            <button onClick={handleLogout} className="btn" style={{ padding: '10px 20px' }}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login" className="btn" style={{ padding: '10px 20px' }}>Login</Link>
            <Link to="/register" className="btn" style={{ padding: '10px 20px' }}>Register</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
