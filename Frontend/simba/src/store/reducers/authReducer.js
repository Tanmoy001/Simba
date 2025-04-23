import{LOGIN_REQUEST,LOGIN_SUCCESS,LOGIN_FAIL,REGISTER_SUCCESS,REGISTER_REQUEST,REGISTER_FAIL,
  LOAD_SUCCESS,LOAD_FAIL,LOAD_REQUEST,CLEAR_ERRORS,LOGOUT_FAIL,LOGOUT_SUCCESS} from "../actionTypes"
  const initialState = {
    loading: false,
    isAuthenticated: false,
    user: null,
    error: null
  };
  export const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_REQUEST:
          case REGISTER_REQUEST:
            case LOAD_REQUEST:
          return {
            loading:true,
            isAuthenticated:false,
        };
        case LOGIN_SUCCESS:
          return {
            ...state,
            loading:false,
            isAuthenticated: true ,
            //            token:action.payload.token,
            user:action.payload,
            userstatus:"Welcome"
                    };
          
          case REGISTER_SUCCESS:
          return {
            ...state,
            loading:false,
            isAuthenticated: true ,
            //            token:action.payload.token,
            user:action.payload,
            userstatus:"Register successfull"
                    };


            case LOAD_SUCCESS:
          return {
            ...state,
            loading:false,
            isAuthenticated: true ,
            //            token:action.payload.token,
            user:action.payload,
                    };
          
          case LOGIN_FAIL:
            case REGISTER_FAIL:
            return {
                ...state,
                loading: false,
                isAuthenticated: false,
                user: null,
                error: action.payload,
            };
            case LOAD_FAIL:
               return {
              loading: false,
              isAuthenticated: false,
              user: null,
              error: action.payload,
            };
            //FOR LOGOUT SUCCESS
            case LOGOUT_SUCCESS:
              return{
              loading:false,
              user:null,
              isAuthenticated:false,
              message : action.payload,
              
              }
              case LOGOUT_FAIL:
                return{
                  ...state,
                  loading:false,
                  error:action.payload
                }
            case CLEAR_ERRORS:
                return {
                   ...state,
                    error:null,
                };
        default:
          return state;
      }
}
