import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { bookService } from '../services/api';
import { useProfile } from '../contexts/ProfileContext';
import { userService } from '../services/api';

export default function Profile() {
  const { user } = useAuth();
  const { profile, fetchProfile, loading } = useProfile();
  const [activeTab, setActiveTab] = useState('reading');
  const [readingLists, setReadingLists] = useState([]);
  const [newListName, setNewListName] = useState('');
  const [creatingList, setCreatingList] = useState(false);
  const [allBooks, setAllBooks] = useState([]);
  const [addBookState, setAddBookState] = useState({});

  useEffect(() => {
    if (user) {
      fetchProfile();
      fetchReadingLists();
      fetchAllBooks();
    }
  }, [user, fetchProfile]);

  const fetchReadingLists = async () => {
    try {
      const res = await userService.getReadingLists();
      setReadingLists(Array.isArray(res.data) ? res.data : []);
    } catch (e) {
      setReadingLists([]);
    }
  };

  const fetchAllBooks = async () => {
    try {
      const res = await bookService.searchBooks({});
      setAllBooks(res.data);
    } catch (e) {
      setAllBooks([]);
    }
  };

  const handleCreateList = async (e) => {
    e.preventDefault();
    if (!newListName.trim()) return;
    setCreatingList(true);
    try {
      await userService.createReadingList({ name: newListName });
      setNewListName('');
      fetchReadingLists();
    } finally {
      setCreatingList(false);
    }
  };

  const handleProgressUpdate = async (progressId, currentPage, totalPages, status) => {
    try {
      const progress = readingLists
        .flatMap(list => list.books || [])
        .find(p => (p.id || p.progress_id) === progressId);
      
      if (!progress) {
        console.error('Progress entry not found:', progressId);
        return;
      }

      await userService.updateBookProgress(progressId, {
        ...progress,
        currentPage: Number(currentPage),
        totalPages: Number(totalPages),
        status
      });

      await fetchReadingLists();
      await fetchProfile();
    } catch (error) {
      console.error('Error updating progress:', error);
      alert('Failed to update progress. Please try again.');
    }
  };

  const handleAddBookChange = (listId, field, value) => {
    setAddBookState((prev) => ({
      ...prev,
      [listId]: {
        ...prev[listId],
        [field]: value,
      },
    }));
  };

  const handleAddBookToList = async (e, list) => {
    e.preventDefault();
    const state = addBookState[list.list_id] || {};
    if (!state.bookId || !state.totalPages) return;
    await userService.addBookProgress({
      book: { id: Number(state.bookId) },
      readingList: { list_id: list.list_id },
      currentPage: 0,
      totalPages: Number(state.totalPages),
      status: 'To Read',
    });
    setAddBookState((prev) => ({ ...prev, [list.list_id]: {} }));
    await fetchReadingLists();
    await fetchProfile();
  };

  const handleDeleteList = async (listId) => {
    await userService.deleteReadingList(listId);
    await fetchReadingLists();
    await fetchProfile();
  };

  // Debug log for reading lists
  console.log('readingLists:', readingLists);

  if (!user) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <h2 className="text-2xl font-semibold text-gray-900">Please log in to view your profile</h2>
        </div>
      </div>
    );
  }

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
        {/* Profile Header */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <div className="flex items-center space-x-6">
            <div className="h-24 w-24 rounded-full bg-indigo-100 flex items-center justify-center">
              <span className="text-3xl text-indigo-600">
                {profile?.username?.charAt(0).toUpperCase()}
              </span>
            </div>
            <div>
              <h1 className="text-2xl font-bold text-gray-900">{profile?.username}</h1>
              <p className="text-gray-600">{profile?.email}</p>
              <p className="mt-2 text-gray-500">{profile?.bio || 'No bio yet'}</p>
            </div>
          </div>
        </div>

        {/* Profile Tabs */}
        <div className="bg-white rounded-lg shadow-md">
          <div className="border-b border-gray-200">
            <nav className="flex -mb-px">
              <button
                onClick={() => setActiveTab('reading')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'reading'
                    ? 'border-b-2 border-indigo-500 text-indigo-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Currently Reading
              </button>
              <button
                onClick={() => setActiveTab('read')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'read'
                    ? 'border-b-2 border-indigo-500 text-indigo-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Read Books
              </button>
              <button
                onClick={() => setActiveTab('clubs')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'clubs'
                    ? 'border-b-2 border-indigo-500 text-indigo-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                My Book Clubs
              </button>
              <button
                onClick={() => setActiveTab('readingLists')}
                className={`py-4 px-6 text-sm font-medium ${
                  activeTab === 'readingLists'
                    ? 'border-b-2 border-indigo-500 text-indigo-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                Reading Lists
              </button>
            </nav>
          </div>

          {/* Tab Content */}
          <div className="p-6">
            {activeTab === 'reading' && (
              <div className="space-y-6">
                {Array.isArray(profile?.currentlyReading) && profile.currentlyReading.length > 0 ? profile.currentlyReading.map((book, idx) => (
                  <div key={book.id || idx} className="bg-white rounded-lg shadow-sm border-4 border-blue-400 p-4 flex flex-col gap-2 mb-2">
                    <div className="flex items-center gap-4">
                      <img src={book.coverImage || 'https://via.placeholder.com/64x96?text=No+Image'} alt={book.title || 'No Title'} className="w-16 h-24 object-cover rounded" />
                      <div className="flex-1">
                        <h4 className="text-lg font-semibold text-gray-900">{book.title || 'Untitled Book'}</h4>
                        <p className="text-sm text-gray-600">by {book.author || 'Unknown Author'}</p>
                        <div className="w-full bg-gray-200 rounded-full h-2 mt-2">
                          <div
                            className="bg-indigo-600 h-2 rounded-full"
                            style={{ width: `${book.progress ? Math.round(book.progress) : 0}%` }}
                          ></div>
                        </div>
                        <p className="text-xs text-gray-500 mt-1">Progress: {book.progress ? `${Math.round(book.progress)}%` : '?'} complete</p>
                      </div>
                    </div>
                  </div>
                )) : (
                  <p className="text-gray-500">No books currently being read.</p>
                )}
              </div>
            )}

            {activeTab === 'read' && (
              <div className="space-y-6">
                {Array.isArray(profile?.readBooks) && profile.readBooks.length > 0 ? profile.readBooks.map((book, idx) => (
                  <div key={book.id || idx} className="bg-white rounded-lg shadow-sm border-4 border-green-400 p-4 flex flex-col gap-2 mb-2">
                    <div className="flex items-center gap-4">
                      <img src={book.coverImage || 'https://via.placeholder.com/64x96?text=No+Image'} alt={book.title || 'No Title'} className="w-16 h-24 object-cover rounded" />
                      <div className="flex-1">
                        <h4 className="text-lg font-semibold text-gray-900">{book.title || 'Untitled Book'}</h4>
                        <p className="text-sm text-gray-600">by {book.author || 'Unknown Author'}</p>
                        <div className="mt-2 flex items-center">
                          <span className="text-yellow-400">★</span>
                          <span className="ml-1 text-sm text-gray-600">{book.rating ?? 'No rating'}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                )) : (
                  <p className="text-gray-500">No books read yet.</p>
                )}
              </div>
            )}

            {activeTab === 'clubs' && (
              <div className="space-y-6">
                {Array.isArray(profile?.bookClubs) && profile.bookClubs.length > 0 ? profile.bookClubs.map((club, idx) => (
                  <div key={club.id || idx} className="bg-white rounded-lg shadow-sm border-4 border-purple-400 p-4 flex flex-col gap-2 mb-2">
                    <div className="flex flex-col gap-2">
                      <h4 className="text-lg font-semibold text-gray-900">{club.name || 'Unnamed Club'}</h4>
                      <p className="text-sm text-gray-600">{club.description || 'No description'}</p>
                      <p className="text-xs text-gray-500 mt-1">Current Book: <span className="font-semibold">{club.currentBook || 'No current book'}</span></p>
                      <p className="text-xs text-gray-500 mt-1">Next Meeting: <span className="font-semibold">{club.nextMeeting || 'No upcoming meetings'}</span></p>
                    </div>
                  </div>
                )) : (
                  <p className="text-gray-500">Not a member of any book clubs yet.</p>
                )}
              </div>
            )}

            {activeTab === 'readingLists' && (
              <div>
                <form onSubmit={handleCreateList} className="mb-4 flex gap-2">
                  <input
                    type="text"
                    value={newListName}
                    onChange={e => setNewListName(e.target.value)}
                    placeholder="New reading list name"
                    className="border rounded px-2 py-1 flex-1"
                  />
                  <button type="submit" className="bg-indigo-500 text-white px-4 py-1 rounded" disabled={creatingList}>
                    {creatingList ? 'Creating...' : 'Create List'}
                  </button>
                </form>
                {Array.isArray(readingLists) && readingLists.length === 0 && <p className="text-gray-500">No reading lists yet.</p>}
                <div className="space-y-6">
                  {Array.isArray(readingLists) && readingLists.map(list => (
                    <div key={list.list_id} className="bg-white rounded-lg shadow-sm border p-4">
                      <div className="flex justify-between items-center mb-2">
                        <h3 className="text-lg font-semibold text-gray-900">{list.name}</h3>
                        <button onClick={() => handleDeleteList(list.list_id)} className="text-red-500 hover:text-red-700 text-sm">Delete</button>
                      </div>
                      {(!Array.isArray(list.books) || list.books.length === 0) && <p className="text-gray-500">No books in this list.</p>}
                      <div className="space-y-2">
                        {/* Add Book to List Form */}
                        <form onSubmit={e => handleAddBookToList(e, list)} className="flex gap-2 mb-2">
                          <select
                            value={addBookState[list.list_id]?.bookId || ''}
                            onChange={e => handleAddBookChange(list.list_id, 'bookId', e.target.value)}
                            className="border rounded px-1"
                            required
                          >
                            <option value="">Select Book</option>
                            {allBooks.map(book => (
                              <option key={book.id} value={book.id}>{book.title}</option>
                            ))}
                          </select>
                          <input
                            type="number"
                            min="1"
                            placeholder="Total Pages"
                            value={addBookState[list.list_id]?.totalPages || ''}
                            onChange={e => handleAddBookChange(list.list_id, 'totalPages', e.target.value)}
                            className="border rounded px-1 w-24"
                            required
                          />
                          <button type="submit" className="bg-green-500 text-white px-2 rounded">Add Book</button>
                        </form>
                        {/* Existing books in list */}
                        {Array.isArray(list.books) && list.books.map((progress, idx) => {
                          console.log('progress entry:', progress);
                          const progressId = progress.id || progress.progress_id;
                          return (
                            <div key={progressId || idx} className="border-4 border-red-400 bg-yellow-100 rounded p-4 flex flex-col gap-2 mb-2 shadow-sm">
                              <div className="flex items-center gap-4">
                                <img src={progress.book?.coverImage || 'https://via.placeholder.com/64x96?text=No+Image'} alt={progress.book?.title || 'No Title'} className="w-16 h-24 object-cover rounded" />
                                <div className="flex-1">
                                  <h4 className="text-lg font-semibold text-gray-900">{progress.book?.title || 'Untitled Book'}</h4>
                                  <p className="text-sm text-gray-600">by {progress.book?.author || 'Unknown Author'}</p>
                                  <p className="text-xs text-gray-500 mt-1">Status: <span className="font-semibold">{progress.status || 'Unknown'}</span></p>
                                  <div className="w-full bg-gray-200 rounded-full h-2 mt-2">
                                    <div
                                      className="bg-indigo-600 h-2 rounded-full"
                                      style={{ width: `${progress.totalPages ? Math.round((progress.currentPage / progress.totalPages) * 100) : 0}%` }}
                                    ></div>
                                  </div>
                                  <p className="text-xs text-gray-500 mt-1">Progress: {progress.currentPage ?? '?'} / {progress.totalPages ?? '?'} pages</p>
                                </div>
                              </div>
                              <form onSubmit={e => { e.preventDefault(); handleProgressUpdate(progressId, Number(e.target.currentPage.value), Number(e.target.totalPages.value), e.target.status.value); }} className="flex gap-2 mt-2 items-center">
                                <input name="currentPage" type="number" min="0" max={progress.totalPages || 9999} defaultValue={progress.currentPage || 0} className="border rounded px-2 py-1 w-20" />
                                <input name="totalPages" type="number" min="1" defaultValue={progress.totalPages || 1} className="border rounded px-2 py-1 w-20" />
                                <select name="status" defaultValue={progress.status || 'To Read'} className="border rounded px-2 py-1">
                                  <option value="To Read">To Read</option>
                                  <option value="Reading">Reading</option>
                                  <option value="Completed">Completed</option>
                                </select>
                                <button type="submit" className="bg-indigo-500 text-white px-4 py-1 rounded hover:bg-indigo-600">Update</button>
                              </form>
                            </div>
                          );
                        })}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
} 