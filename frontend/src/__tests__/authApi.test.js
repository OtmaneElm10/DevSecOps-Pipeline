import { register, login } from '../api/authApi';

describe('Auth API', () => {
  const mockResponse = {
    ok: true,
    json: jest.fn()
  };

  beforeEach(() => {
    fetch.resetMocks();
  });

  describe('register', () => {
    it('should register a user successfully', async () => {
      const mockData = {
        id: 1,
        username: 'testuser',
        email: 'test@example.com',
        token: 'mock-token',
        role: 'USER'
      };

      mockResponse.json.mockResolvedValueOnce(mockData);
      global.fetch.mockResolvedValueOnce(mockResponse);

      const result = await register('testuser', 'test@example.com', 'password123');

      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/auth/register',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            username: 'testuser',
            email: 'test@example.com',
            password: 'password123'
          })
        }
      );

      expect(result).toEqual(mockData);
      expect(localStorage.setItem).toHaveBeenCalledWith('token', 'mock-token');
      expect(localStorage.setItem).toHaveBeenCalledWith('userId', 1);
      expect(localStorage.setItem).toHaveBeenCalledWith('username', 'testuser');
      expect(localStorage.setItem).toHaveBeenCalledWith('email', 'test@example.com');
      expect(localStorage.setItem).toHaveBeenCalledWith('role', 'USER');
    });

    it('should throw error on registration failure', async () => {
      const errorResponse = { ok: false };
      global.fetch.mockResolvedValueOnce(errorResponse);

      await expect(register('user', 'email@test.com', 'pass')).rejects.toThrow(
        "Erreur lors de l'inscription"
      );
    });
  });

  describe('login', () => {
    it('should login a user successfully', async () => {
      const mockData = {
        id: 1,
        username: 'testuser',
        email: 'test@example.com',
        token: 'mock-token',
        role: 'USER'
      };

      mockResponse.json.mockResolvedValueOnce(mockData);
      global.fetch.mockResolvedValueOnce(mockResponse);

      const result = await login('testuser', 'password123');

      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/auth/login',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            username: 'testuser',
            password: 'password123'
          })
        }
      );

      expect(result).toEqual(mockData);
      expect(localStorage.setItem).toHaveBeenCalledWith('token', 'mock-token');
      expect(localStorage.setItem).toHaveBeenCalledWith('username', 'testuser');
    });

    it('should throw error on login failure', async () => {
      const errorResponse = { ok: false };
      global.fetch.mockResolvedValueOnce(errorResponse);

      await expect(login('wronguser', 'wrongpass')).rejects.toThrow(
        'Identifiants incorrects ou utilisateur introuvable'
      );
    });
  });
});
