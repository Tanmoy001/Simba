// import { generateSummary as generateSummaryApi, getHistory as getHistoryApi } from '../../api/summaryApi';
import {
  GENERATE_SUMMARY_REQUEST,
  GENERATE_SUMMARY_SUCCESS,
  GENERATE_SUMMARY_FAILURE,
  GET_HISTORY_REQUEST,
  GET_HISTORY_SUCCESS,
  GET_HISTORY_FAILURE,
} from '../actionTypes';

import axios from "axios";

export const generateSummary = (url) => async (dispatch) => {
  console.log("URL:", url);
  
  const user = JSON.parse(localStorage.getItem("user"));
  console.log("User:", user?.user_data?.username); // Check if user is valid before parsing
  const userRaw = user?.user_data?.username;
  const token = user?.user_data?.token?.trim(); 

  console.log("Token:", token); // Check if token is valid before parsing
  try {
    dispatch({ type: GENERATE_SUMMARY_REQUEST });

    console.log("Username:", userRaw); // Check if userRaw is valid before parsing
    console.log("Token:", token); // Check if token is valid before parsing

    const res = await axios.post(
      'http://localhost:8080/api/summary',
      url, // plain string, not JSON
      {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'text/plain', // must match backend's expected content type
        },
        params: { username: userRaw }, // if backend expects this as a query param
      }
    );
    console.log("Response:", res.data); // Log the response data for debugging
    dispatch({
      type: GENERATE_SUMMARY_SUCCESS,
      payload: res.data, // assuming res.data is the Summary object
    });

  } catch (error) {
    dispatch({
      type: GENERATE_SUMMARY_FAILURE,
      payload: error.response?.data?.error || error.message,
    });
  }
};

export const getHistory = () => async (dispatch) => {
  const user = JSON.parse(localStorage.getItem("user"));
  const Id = user?.user_data?.Id;
  const token = user?.user_data?.token?.trim();

  try {
    dispatch({ type: GET_HISTORY_REQUEST });

    const res = await axios.get('http://localhost:8080/api/history', {
      params: { userId: Id }, // if backend expects this as a query param
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });
    console.log("History Response:", res.data); // Log the response data for debugging
    dispatch({
      type: GET_HISTORY_SUCCESS,
      payload: res.data,
    });
    
  } catch (error) {
    dispatch({
      type: GET_HISTORY_FAILURE,
      payload: error.response?.data?.message || error.message,
    });
  }
};