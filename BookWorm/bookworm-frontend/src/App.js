import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import { ProfileProvider } from './contexts/ProfileContext';

// Import pages
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import BookList from './pages/BookList';
import BookClubs from './pages/BookClubs';
import Profile from './pages/Profile';
import BookClubPage from './pages/BookClubPage';
import Navigation from './components/Navigation';

// Protected Route component
const ProtectedRoute = ({ children }) => {
  const { user, loading } = useAuth();
  
  if (loading) {
    return <div>Loading...</div>;
  }
  
  if (!user) {
    return <Navigate to="/login" />;
  }
  
  return children;
};

function App() {
  return (
    <AuthProvider>
      <ProfileProvider>
        <Router>
          <div className="min-h-screen bg-gray-100">
            <Navigation />
            <main className="container mx-auto px-4 py-8">
              <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/" element={
                  <ProtectedRoute>
                    <Home />
                  </ProtectedRoute>
                } />
                <Route path="/books" element={
                  <ProtectedRoute>
                    <BookList />
                  </ProtectedRoute>
                } />
                <Route path="/book-clubs" element={
                  <ProtectedRoute>
                    <BookClubs />
                  </ProtectedRoute>
                } />
                <Route path="/book-clubs/:id" element={
                  <ProtectedRoute>
                    <BookClubPage />
                  </ProtectedRoute>
                } />
                <Route path="/profile" element={
                  <ProtectedRoute>
                    <Profile />
                  </ProtectedRoute>
                } />
              </Routes>
            </main>
          </div>
        </Router>
      </ProfileProvider>
    </AuthProvider>
  );
}

export default App;
