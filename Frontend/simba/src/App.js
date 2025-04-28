import React,{useEffect} from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import Home from './pages/Home';
import Navbar from './components/Navbar';
import './App.css';
// import store from './store/store';
import { loadUser } from './store/actions/authActions';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import GenerateSummary from './components/Summary/GenerateSummary';
import History from './components/Summary/History';

function App() {
  const {  isAuthenticated } = useSelector(state => state.user);
  
const dispatch = useDispatch();
  useEffect(() => {
    dispatch(loadUser());
  }, [dispatch]);
  return (
    <Router>
      <div className="app">
        <Navbar />
        
        <Routes>
          <Route path="/" element={<Home />} />
          {isAuthenticated?
          <>
          
          <Route path="/summary-gen" element={ <GenerateSummary />  } />
          <Route path="/history-log" element={<History />} />
          </>
          :
          <>
          <Route path="/login" element={<Login/> } />
          <Route path="/register" element={<Register/> } />
          </>
          }
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;