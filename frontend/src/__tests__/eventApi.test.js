import { getAllEvents, getEventById, createEvent } from '../api/eventApi';
import api from '../api/axiosConfig';

jest.mock('../api/axiosConfig');

describe('Event API', () => {
  beforeEach(() => {
    api.get.mockClear();
    api.post.mockClear();
  });

  describe('getAllEvents', () => {
    it('should fetch all events successfully', async () => {
      const mockEvents = [
        {
          id: 1,
          title: 'Gala du club',
          type: 'SOIREE',
          date: '2025-05-15'
        },
        {
          id: 2,
          title: 'Stage M18',
          type: 'STAGE',
          date: '2025-06-01'
        }
      ];

      api.get.mockResolvedValueOnce({ data: mockEvents });

      const result = await getAllEvents();

      expect(api.get).toHaveBeenCalledWith('/events');
      expect(result).toEqual(mockEvents);
    });

    it('should throw error when fetching events fails', async () => {
      const error = new Error('Network error');
      api.get.mockRejectedValueOnce(error);

      await expect(getAllEvents()).rejects.toThrow('Network error');
    });
  });

  describe('getEventById', () => {
    it('should fetch a single event by id', async () => {
      const mockEvent = {
        id: 1,
        title: 'Gala du club',
        type: 'SOIREE',
        date: '2025-05-15',
        location: 'Club House',
        capacity: 100
      };

      api.get.mockResolvedValueOnce({ data: mockEvent });

      const result = await getEventById(1);

      expect(api.get).toHaveBeenCalledWith('/events/1');
      expect(result).toEqual(mockEvent);
    });

    it('should throw error when event not found', async () => {
      const error = new Error('Event not found');
      api.get.mockRejectedValueOnce(error);

      await expect(getEventById(999)).rejects.toThrow('Event not found');
    });
  });

  describe('createEvent', () => {
    it('should create a new event successfully', async () => {
      const eventData = {
        title: 'New Event',
        type: 'TOURNOI',
        date: '2025-07-20',
        location: 'Stadium',
        capacity: 500
      };

      const mockResponse = {
        id: 3,
        ...eventData
      };

      api.post.mockResolvedValueOnce({ data: mockResponse });

      const result = await createEvent(eventData);

      expect(api.post).toHaveBeenCalledWith('/events', eventData);
      expect(result).toEqual(mockResponse);
      expect(result.id).toBe(3);
    });

    it('should throw error when event creation fails', async () => {
      const eventData = {
        title: 'Invalid Event',
        type: 'INVALID'
      };

      const error = new Error('Validation error');
      api.post.mockRejectedValueOnce(error);

      await expect(createEvent(eventData)).rejects.toThrow('Validation error');
    });
  });
});
