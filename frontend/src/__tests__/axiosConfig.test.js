import api from '../api/axiosConfig';

describe('Axios Config', () => {
  it('should have correct baseURL', () => {
    expect(api.defaults.baseURL).toBe('http://192.168.75.61/api');
  });

  it('should have correct default headers', () => {
    expect(api.defaults.headers['Content-Type']).toBe('application/json');
  });

  it('should be an axios instance', () => {
    expect(typeof api.get).toBe('function');
    expect(typeof api.post).toBe('function');
    expect(typeof api.put).toBe('function');
    expect(typeof api.delete).toBe('function');
  });
});
