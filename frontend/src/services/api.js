import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Appointment API
export const appointmentAPI = {
  create: (data) => api.post('/appointments', data),
  getAll: () => api.get('/appointments'),
  getById: (id) => api.get(`/appointments/${id}`),
  updateStatus: (id, status) => api.put(`/appointments/${id}/status?status=${status}`),
  getByStatus: (status) => api.get(`/appointments/status/${status}`),
};

export default api;
