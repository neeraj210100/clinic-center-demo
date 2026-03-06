import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  // Removed withCredentials - not needed since backend doesn't require credentials
});

// Request interceptor (optional - for logging/debugging)
api.interceptors.request.use(
  (config) => {
    // Log request in development
    if (process.env.NODE_ENV === 'development') {
      console.log('API Request:', config.method?.toUpperCase(), config.url);
      console.log('Full URL:', config.baseURL + config.url);
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
      console.error('Request URL:', error.config?.url);
      console.error('Base URL:', error.config?.baseURL);
      return Promise.reject({
        ...error,
        message: 'Unable to connect to server. Please check your connection and ensure the backend is running.',
      });
    }

    // Handle CORS preflight errors
    if (error.code === 'ERR_NETWORK' || error.response?.status === 0) {
      console.error('CORS Error - Check backend CORS configuration');
      console.error('Origin:', window.location.origin);
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