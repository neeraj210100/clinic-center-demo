import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  // Only include withCredentials if you're using cookies/auth tokens
  // If not using cookies, you can remove this and set allowCredentials(false) in backend
  // withCredentials: true,
});

// Request interceptor (optional - for logging/debugging)
api.interceptors.request.use(
  (config) => {
    // Log request in development
    if (process.env.NODE_ENV === 'development') {
      console.log('API Request:', config.method?.toUpperCase(), config.url);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for better error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle CORS errors specifically
    if (error.message === 'Network Error' || !error.response) {
      console.error('Network Error - Check if backend is running and CORS is configured correctly');
      return Promise.reject({
        ...error,
        message: 'Unable to connect to server. Please check your connection and ensure the backend is running.',
      });
    }

    // Handle CORS preflight errors
    if (error.code === 'ERR_NETWORK' || error.response?.status === 0) {
      console.error('CORS Error - Check backend CORS configuration');
      return Promise.reject({
        ...error,
        message: 'CORS error: Backend may not be allowing requests from this origin.',
      });
    }

    return Promise.reject(error);
  }
);

// Appointment API
export const appointmentAPI = {
  create: (data) => api.post('/appointments', data),
  getAll: () => api.get('/appointments'),
  getById: (id) => api.get(`/appointments/${id}`),
  updateStatus: (id, status) => api.put(`/appointments/${id}/status?status=${status}`),
  getByStatus: (status) => api.get(`/appointments/status/${status}`),
};

export default api;