import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';
import { bookService } from '../services/api';

export default function Home() {
  const { user } = useAuth();
  const [featuredBooks, setFeaturedBooks] = useState([]);
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const [featuredResponse, recommendationsResponse] = await Promise.all([
          bookService.getFeaturedBooks(),
          bookService.getRecommendations()
        ]);
        setFeaturedBooks(Array.isArray(featuredResponse.data) ? featuredResponse.data : []);
        setRecommendations(Array.isArray(recommendationsResponse.data) ? recommendationsResponse.data : []);
        console.log('featuredBooks:', featuredResponse.data);
      } catch (error) {
        console.error('Error fetching books:', error);
        setFeaturedBooks([]);
        setRecommendations([]);
      } finally {
        setLoading(false);
      }
    };

    fetchBooks();
  }, []);

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <div className="relative bg-gradient-to-r from-indigo-600 to-purple-600 text-white overflow-hidden">
        <div className="absolute inset-0 bg-black opacity-20"></div>
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-32">
          <div className="max-w-3xl">
            <h1 className="text-5xl font-extrabold tracking-tight sm:text-6xl lg:text-7xl mb-6">
              Discover Your Next
              <span className="block text-yellow-300">Favorite Book</span>
            </h1>
            <p className="text-xl sm:text-2xl text-gray-100 mb-8">
              Join a community of readers, explore new genres, and share your literary journey.
            </p>
            {!user && (
              <div className="flex flex-col sm:flex-row gap-4">
                <Link
                  to="/register"
                  className="inline-flex items-center justify-center px-8 py-3 border border-transparent text-base font-medium rounded-md text-indigo-600 bg-white hover:bg-gray-50 transition-colors shadow-lg"
                >
                  Get Started
                </Link>
                <Link
                  to="/books"
                  className="inline-flex items-center justify-center px-8 py-3 border border-white text-base font-medium rounded-md text-white hover:bg-indigo-700 transition-colors"
                >
                  Browse Books
                </Link>
              </div>
            )}
          </div>
        </div>
        <div className="absolute bottom-0 left-0 right-0 h-16 bg-gradient-to-t from-gray-50 to-transparent"></div>
      </div>

      {/* Featured Books Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="flex items-center justify-between mb-8">
          <h2 className="text-3xl font-bold text-gray-900">Featured Books</h2>
          <Link
            to="/books"
            className="text-indigo-600 hover:text-indigo-500 font-medium"
          >
            View all →
          </Link>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          {(Array.isArray(featuredBooks) ? featuredBooks : []).map((book) => (
            <div
              key={book.id}
              className="group bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition-shadow duration-300"
            >
              <div className="relative">
                <img
                  src={book.coverImage}
                  alt={book.title}
                  className="w-full h-64 object-cover transform group-hover:scale-105 transition-transform duration-300"
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = 'https://picsum.photos/seed/default/400/600';
                  }}
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
              </div>
              <div className="p-6">
                <h3 className="text-xl font-semibold text-gray-900 mb-2 line-clamp-1">
                  {book.title}
                </h3>
                <p className="text-sm text-gray-600 mb-3">{book.author}</p>
                <div className="flex items-center justify-between">
                  <div className="flex items-center">
                    <span className="text-yellow-400">★</span>
                    <span className="ml-1 text-sm text-gray-600">{book.average_rating}</span>
                  </div>
                  <span className="text-xs text-gray-500">{book.genre}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Reading Recommendations */}
      <div className="bg-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
          <div className="flex items-center justify-between mb-8">
            <h2 className="text-3xl font-bold text-gray-900">Recommended for You</h2>
            <Link
              to="/books"
              className="text-indigo-600 hover:text-indigo-500 font-medium"
            >
              View all →
            </Link>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
            {recommendations.map((book) => (
              <div
                key={book.id}
                className="group bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition-shadow duration-300"
              >
                <div className="relative">
                  <img
                    src={book.coverImage}
                    alt={book.title}
                    className="w-full h-64 object-cover transform group-hover:scale-105 transition-transform duration-300"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = 'https://picsum.photos/seed/default/400/600';
                    }}
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                </div>
                <div className="p-6">
                  <h3 className="text-xl font-semibold text-gray-900 mb-2 line-clamp-1">
                    {book.title}
                  </h3>
                  <p className="text-sm text-gray-600 mb-3">{book.author}</p>
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <span className="text-yellow-400">★</span>
                      <span className="ml-1 text-sm text-gray-600">{book.average_rating}</span>
                    </div>
                    <span className="text-xs text-gray-500">{book.genre}</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Call to Action */}
      {!user && (
        <div className="bg-indigo-600">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
            <div className="text-center">
              <h2 className="text-3xl font-bold text-white mb-4">
                Ready to Start Your Reading Journey?
              </h2>
              <p className="text-xl text-indigo-100 mb-8">
                Join our community of book lovers and discover your next favorite read.
              </p>
              <Link
                to="/register"
                className="inline-flex items-center justify-center px-8 py-3 border border-transparent text-base font-medium rounded-md text-indigo-600 bg-white hover:bg-gray-50 transition-colors shadow-lg"
              >
                Sign Up Now
              </Link>
            </div>
          </div>
        </div>
      )}
    </div>
  );
} 