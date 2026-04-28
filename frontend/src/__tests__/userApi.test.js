import { getUserProfile } from '../api/userApi';

describe('User API', () => {
  const mockResponse = {
    ok: true,
    json: jest.fn()
  };

  beforeEach(() => {
    fetch.resetMocks();
  });

  describe('getUserProfile', () => {
    it('should fetch user profile successfully', async () => {
      const mockUser = {
        id: 1,
        username: 'testuser',
        email: 'test@example.com',
        role: 'USER',
        createdAt: '2025-01-01T10:00:00Z'
      };

      mockResponse.json.mockResolvedValueOnce(mockUser);
      global.fetch.mockResolvedValueOnce(mockResponse);

      const result = await getUserProfile('testuser');

      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/users/testuser',
        {
          method: 'GET',
          headers: { 'Content-Type': 'application/json' }
        }
      );

      expect(result).toEqual(mockUser);
    });

    it('should throw error when user not found', async () => {
      const errorResponse = { ok: false };
      global.fetch.mockResolvedValueOnce(errorResponse);

      await expect(getUserProfile('nonexistent')).rejects.toThrow(
        'Utilisateur introuvable'
      );
    });

    it('should throw error on network failure', async () => {
      const networkError = new Error('Network error');
      global.fetch.mockRejectedValueOnce(networkError);

      await expect(getUserProfile('testuser')).rejects.toThrow('Network error');
    });
  });
});
