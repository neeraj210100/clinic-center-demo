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

// Lead API
export const leadAPI = {
  create: (data) => api.post('/leads', data),
  getAll: () => api.get('/leads'),
  getById: (id) => api.get(`/leads/${id}`),
  updateStatus: (id, status) => api.put(`/leads/${id}/status?status=${status}`),
  exportToExcel: () => api.get('/leads/export/excel', { responseType: 'blob' }),
};

export default api;
