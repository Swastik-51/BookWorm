import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { clubService } from '../services/api';
// import { userService } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { useProfile } from '../contexts/ProfileContext';

export default function BookClubs() {
  const { user } = useAuth();
  const { fetchProfile } = useProfile();
  const [clubs, setClubs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedClub, setSelectedClub] = useState(null);
  const [showJoinModal, setShowJoinModal] = useState(false);
  const [joinError, setJoinError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchClubs();
  }, []);

  const fetchClubs = async () => {
    try {
      setLoading(true);
      const response = await clubService.getClubs();
      setClubs(response.data);
    } catch (error) {
      console.error('Error fetching clubs:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleJoinClub = async (clubId) => {
    setJoinError('');
    console.log('Attempting to join club:', clubId);
    try {
      await clubService.joinClub(clubId);
      await fetchProfile();
      navigate(`/book-clubs/${clubId}`);
    } catch (error) {
      console.error('Error joining club:', error);
      setJoinError(error?.response?.data?.message || 'Failed to join club. Please try again.');
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900">Book Clubs</h1>
          <p className="mt-4 text-xl text-gray-600">
            Join a community of readers and discuss your favorite books
          </p>
        </div>

        {/* Book Clubs Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {Array.isArray(clubs) && clubs.map((club) => (
            <div
              key={club.id}
              className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
            >
              <div className="p-6">
                <h3 className="text-xl font-semibold text-gray-900">{club.name}</h3>
                <p className="mt-2 text-gray-600">{club.description}</p>
                <div className="mt-4 flex items-center justify-between">
                  <div className="flex items-center space-x-2">
                    <span className="text-sm text-gray-500">
                      {club.memberCount} members
                    </span>
                    <span className="text-sm text-gray-500">•</span>
                    <span className="text-sm text-gray-500">
                      {club.currentBook ? `${club.currentBook.title} by ${club.currentBook.author}` : 'No current book'}
                    </span>
                  </div>
                  {user && (
                    <button
                      onClick={() => {
                        setSelectedClub(club);
                        setShowJoinModal(true);
                      }}
                      className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors"
                    >
                      Join Club
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Join Club Modal */}
        {showJoinModal && selectedClub && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-lg p-6 max-w-md w-full">
              <h3 className="text-xl font-semibold text-gray-900 mb-4">
                Join {selectedClub.name}
              </h3>
              <p className="text-gray-600 mb-6">
                Are you sure you want to join this book club? You'll be able to participate in
                discussions and events.
              </p>
              {joinError && (
                <div className="text-red-500 mb-4">{joinError}</div>
              )}
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => { setShowJoinModal(false); setJoinError(''); }}
                  className="px-4 py-2 text-gray-600 hover:text-gray-800"
                >
                  Cancel
                </button>
                <button
                  onClick={() => handleJoinClub(selectedClub.id)}
                  className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                >
                  Join Club
                </button>
              </div>
            </div>
          </div>
        )}

        {/* No Clubs Message */}
        {clubs.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-500 text-lg">No book clubs available at the moment.</p>
          </div>
        )}
      </div>
    </div>
  );
} 