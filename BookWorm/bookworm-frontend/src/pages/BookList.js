import React, { useState, useEffect, useCallback } from 'react';
import { bookService } from '../services/api';

export default function BookList() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState({
    genre: '',
    rating: '',
    sortBy: 'title'
  });

  const fetchBooks = useCallback(async () => {
    try {
      setLoading(true);
      const response = await bookService.searchBooks({
        query: searchQuery,
        ...filters
      });
      setBooks(Array.isArray(response.data) ? response.data : []);
      console.log('books:', response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
      setBooks([]);
    } finally {
      setLoading(false);
    }
  }, [searchQuery, filters]);

  useEffect(() => {
    fetchBooks();
  }, [fetchBooks]);

  const handleSearch = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Search and Filters */}
        <div className="mb-8">
          <div className="flex flex-col md:flex-row gap-4">
            <div className="flex-1">
              <input
                type="text"
                placeholder="Search books..."
                value={searchQuery}
                onChange={handleSearch}
                className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              />
            </div>
            <div className="flex gap-4">
              <select
                name="genre"
                value={filters.genre}
                onChange={handleFilterChange}
                className="px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              >
                <option value="">All Genres</option>
                <option value="fiction">Fiction</option>
                <option value="non-fiction">Non-Fiction</option>
                <option value="mystery">Mystery</option>
                <option value="sci-fi">Science Fiction</option>
              </select>
              <select
                name="rating"
                value={filters.rating}
                onChange={handleFilterChange}
                className="px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              >
                <option value="">All Ratings</option>
                <option value="4">4+ Stars</option>
                <option value="3">3+ Stars</option>
                <option value="2">2+ Stars</option>
              </select>
              <select
                name="sortBy"
                value={filters.sortBy}
                onChange={handleFilterChange}
                className="px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              >
                <option value="title">Sort by Title</option>
                <option value="rating">Sort by Rating</option>
                <option value="date">Sort by Date</option>
              </select>
            </div>
          </div>
        </div>

        {/* Loading State */}
        {loading ? (
          <div className="flex justify-center items-center h-64">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <>
            {/* Results Count */}
            <div className="mb-4">
              <p className="text-gray-600">
                Found {books.length} {books.length === 1 ? 'book' : 'books'}
              </p>
            </div>

            {/* Books Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {(Array.isArray(books) ? books : []).map((book) => (
                <div key={book.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
                  <img
                    src={book.coverImage}
                    alt={book.title}
                    className="w-full h-48 object-cover"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = 'https://picsum.photos/seed/default/400/600';
                    }}
                  />
                  <div className="p-4">
                    <h3 className="text-lg font-semibold text-gray-900">{book.title}</h3>
                    <p className="text-sm text-gray-600">{book.author}</p>
                    <div className="mt-2 flex items-center justify-between">
                      <div className="flex items-center">
                        <span className="text-yellow-400">★</span>
                        <span className="ml-1 text-sm text-gray-600">{book.average_rating}</span>
                      </div>
                      <span className="text-xs text-gray-500">{book.genre}</span>
                    </div>
                    <p className="mt-2 text-sm text-gray-500 line-clamp-2">{book.description}</p>
                  </div>
                </div>
              ))}
            </div>

            {/* No Results */}
            {books.length === 0 && (
              <div className="text-center py-12">
                <p className="text-gray-500 text-lg">No books found matching your criteria.</p>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
} 