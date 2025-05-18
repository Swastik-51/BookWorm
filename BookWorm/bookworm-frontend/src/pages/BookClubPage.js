import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { clubService } from '../services/api';

const BookClubPage = () => {
  const { id } = useParams();
  const [club, setClub] = useState(null);
  const [discussions, setDiscussions] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchClubData = async () => {
      try {
        const [clubData, discussionsData] = await Promise.all([
          clubService.getClubDetails(id),
          clubService.getClubDiscussions(id)
        ]);
        setClub(clubData.data);
        setDiscussions(discussionsData.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to load club data');
        setLoading(false);
      }
    };

    fetchClubData();
  }, [id]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!newMessage.trim()) return;

    try {
      await clubService.createDiscussion(id, {
        content: newMessage,
        type: 'MESSAGE'
      });
      const discussionsData = await clubService.getClubDiscussions(id);
      setDiscussions(discussionsData.data);
      setNewMessage('');
    } catch (err) {
      setError('Failed to send message');
    }
  };

  if (loading) return <div className="text-center">Loading...</div>;
  if (error) return <div className="text-red-500 text-center">{error}</div>;
  if (!club) return <div className="text-center">Club not found</div>;

  return (
    <div className="max-w-6xl mx-auto">
      {/* Club Header */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <h1 className="text-3xl font-bold mb-4">{club.name}</h1>
        <p className="text-gray-600 mb-4">{club.description}</p>
        <div className="flex items-center space-x-4">
          <div className="text-sm text-gray-500">
            <span className="font-semibold">{club.memberCount}</span> members
          </div>
          {club.currentBook && (
            <div className="text-sm text-gray-500">
              Current Book: <span className="font-semibold">{club.currentBook.title}</span>
            </div>
          )}
        </div>
      </div>

      {/* Discussions Section */}
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-2xl font-bold mb-4">Discussions</h2>
        
        {/* Messages List */}
        <div className="space-y-4 mb-6 max-h-[500px] overflow-y-auto">
          {discussions.map((discussion, index) => (
            <div key={discussion.id || discussion.message_id || index} className="border-b pb-4">
              <div className="flex items-center mb-2">
                <span className="font-semibold">{discussion.sender ? discussion.sender.username : 'Unknown User'}</span>
                <span className="text-gray-500 text-sm ml-2">
                  {new Date(discussion.createdAt).toLocaleString()}
                </span>
              </div>
              <p className="text-gray-700">{discussion.content}</p>
            </div>
          ))}
        </div>

        {/* Message Input */}
        <form onSubmit={handleSendMessage} className="flex space-x-4">
          <input
            type="text"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Type your message..."
            className="flex-1 p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Send
          </button>
        </form>
      </div>
    </div>
  );
};

export default BookClubPage; 