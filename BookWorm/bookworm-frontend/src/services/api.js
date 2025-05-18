import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if it exists
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Authentication services
export const authService = {
  register: (userData) => api.post('/auth/register', userData),
  login: (credentials) => api.post('/auth/login', credentials),
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },
};

// Book services
export const bookService = {
  searchBooks: (params) => api.get('/books/search', { params }),
  getFeaturedBooks: () => api.get('/books/featured'),
  getRecommendations: () => api.get('/books/recommendations'),
  getBookDetails: (id) => api.get(`/books/${id}`),
  addToReadingList: (bookId) => api.post(`/books/${bookId}/reading-list`),
  updateReadingProgress: (bookId, progress) => api.put(`/books/${bookId}/progress`, { progress }),
  rateBook: (bookId, rating) => api.post(`/books/${bookId}/rate`, { rating }),
};

// Book club services
export const clubService = {
  getClubs: () => api.get('/clubs'),
  getClubDetails: (id) => api.get(`/clubs/${id}`),
  joinClub: (clubId) => api.post(`/clubs/${clubId}/join`),
  leaveClub: (clubId) => api.post(`/clubs/${clubId}/leave`),
  createClub: (clubData) => api.post('/clubs', clubData),
  updateClub: (clubId, clubData) => api.put(`/clubs/${clubId}`, clubData),
  getClubDiscussions: (clubId) => api.get(`/clubs/${clubId}/discussions`),
  createDiscussion: (clubId, discussionData) => api.post(`/clubs/${clubId}/discussions`, discussionData),
};

// User profile services
export const userService = {
  getProfile: () => api.get('/users/profile'),
  updateProfile: (profileData) => api.put('/users/profile', profileData),
  getReadingLists: () => api.get('/reading-lists/my'),
  createReadingList: (listData) => api.post('/reading-lists', listData),
  getBookProgress: () => api.get('/book-progress/my'),
  addBookProgress: (progressData) => api.post('/book-progress', progressData),
  updateBookProgress: (progressId, progressData) => api.put(`/book-progress/${progressId}`, progressData),
  getReadBooks: () => api.get('/users/read-books'),
  getBookClubs: () => api.get('/users/book-clubs'),
  deleteReadingList: (listId) => api.delete(`/reading-lists/${listId}`),
};

export default api; 