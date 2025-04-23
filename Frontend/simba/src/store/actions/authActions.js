
import{LOGIN_REQUEST,LOGIN_SUCCESS,LOGIN_FAIL,REGISTER_SUCCESS,REGISTER_REQUEST,REGISTER_FAIL,
  LOAD_SUCCESS,LOAD_FAIL,LOAD_REQUEST,LOGOUT_SUCCESS,LOGOUT_FAIL} from "../actionTypes"
import axios from "axios"
//LOGIN THE USE OR SIGNIN
export const login = (userData) => async (dispatch) => {
  try {
    dispatch({ type: LOGIN_REQUEST });

    const config = {
      headers: { 'Content-Type': 'application/json' }
    };

    const res = await axios.post('http://localhost:8080/api/login', userData, config);
    console.log("Response:", res.data);

    // Store in localStorage so loadUser can retrieve later
    localStorage.setItem("user", JSON.stringify(res.data));
    
    dispatch({ type: LOGIN_SUCCESS, payload: res.data });

  } catch (error) {
    console.error("Login error:", error);
    dispatch({
      type: LOGIN_FAIL,
      payload: error.response?.data?.error || error.message || "Login failed",
    });
  }
};

//SIGNUP OR REGISTER THE NEW USE 
export const register = (userData) => async (dispatch) => {
  try {
    dispatch({ type: REGISTER_REQUEST });

    const config = {
      headers: { 'Content-Type': 'application/json' },
      withCredentials: true // For sending cookies if needed
    };

    const res = await axios.post('http://localhost:8080/api/register', userData , config)
    console.log("Response:", res.data);

    localStorage.setItem("user", JSON.stringify(res.data));
    

    dispatch({ type: REGISTER_SUCCESS, payload: res.data });

  } catch (error) {
    console.error("Error:", error);
    const errorMessage = error.response?.data?.error || error.message || "Something went wrong";
    dispatch({ type: REGISTER_FAIL, payload: errorMessage });
  }
}
export const loadUser = () => async (dispatch) => {
  try {
    dispatch({ type: LOAD_REQUEST });

    const userRaw = localStorage.getItem("user");

    // Check if userRaw is valid before parsing
    if (userRaw) {
      const user = JSON.parse(userRaw);
      dispatch({ type: LOAD_SUCCESS, payload: user });
    } else {
      // If no valid localStorage data, treat as not logged in
      dispatch({ type: LOAD_FAIL, payload: "No valid user data found" });
    }

  } catch (error) {
    console.error("loadUser error:", error);
    dispatch({
      type: LOAD_FAIL,
      payload: error.message || "Failed to load user",
    });
  }
};



//LOADING THE USER FROM COOKIES TO STATE
// export const loadUser = () =>async(dispatch)=>{
//   try {
//     dispatch({ type: LOAD_REQUEST })
//     const res = await axios.get('/api/me')
//     console.log(res)
//     dispatch({ type: LOAD_SUCCESS, payload: res.data.user })
//   } catch (error) {
//     console.error("Error:", error);
//     dispatch({ type: LOAD_FAIL, payload:error.response.data.error })
//   }
// }

//for logout the user
export const logout = () => async (dispatch) => {
  try {
    const user = JSON.parse(localStorage.getItem("user"));
    console.log("User:", user?.user_data?.username); // Check if user is valid before parsing
    const userRaw = user?.user_data?.username;
    const token = user?.user_data?.token?.trim(); 
    console.log("Token:", token); // Check if token is valid before parsing

   const data = await axios.get('http://localhost:8080/api/logout', {
      headers: {
        Authorization: `Bearer ${token}`
      },
    });

    localStorage.removeItem("user");
    localStorage.removeItem("token");
    dispatch({ type: LOGOUT_SUCCESS, payload: data.message });
  } catch (error) {
    console.error("Error during logout:", error);
    dispatch({
      type: LOGOUT_FAIL,
      payload: error.response?.data?.error || "Logout failed"
    });
  }
};
