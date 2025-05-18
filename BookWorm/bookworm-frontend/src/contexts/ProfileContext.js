import React, { createContext, useContext, useState, useCallback } from 'react';
import { userService } from '../services/api';

const ProfileContext = createContext();

export function useProfile() {
  return useContext(ProfileContext);
}

export function ProfileProvider({ children }) {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProfile = useCallback(async () => {
    setLoading(true);
    try {
      const response = await userService.getProfile();
      setProfile(response.data);
    } catch (error) {
      setProfile(null);
    } finally {
      setLoading(false);
    }
  }, []);

  React.useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  return (
    <ProfileContext.Provider value={{ profile, setProfile, fetchProfile, loading }}>
      {children}
    </ProfileContext.Provider>
  );
} 