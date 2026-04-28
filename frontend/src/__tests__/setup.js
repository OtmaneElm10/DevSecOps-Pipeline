import 'jest-fetch-mock';

const localStorageMock = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn(),
};

Object.defineProperty(window, 'localStorage', {
  value: localStorageMock,
  writable: true,
});

global.fetch = require('jest-fetch-mock');

beforeEach(() => {
  jest.clearAllMocks();
  fetch.resetMocks();
});
