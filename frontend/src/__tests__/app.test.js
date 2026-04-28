describe('app.js', () => {
  const appHtml = '<div id="app"></div>';

  beforeEach(() => {
    document.body.innerHTML = appHtml;
    jest.resetModules();
  });

  it('should render event cards when events are returned', async () => {
    const mockGetAllEvents = jest.fn().mockResolvedValue([
      { title: 'Test Event 1', lieu: 'Paris', prix: 15 },
      { title: 'Test Event 2', lieu: 'Lyon', prix: 25 }
    ]);

    jest.doMock('../api/eventApi', () => ({
      getAllEvents: mockGetAllEvents
    }));

    await import('../app.js');

    const appDiv = document.getElementById('app');
    expect(mockGetAllEvents).toHaveBeenCalled();
    expect(appDiv.innerHTML).toContain('Test Event 1');
    expect(appDiv.innerHTML).toContain('Paris');
    expect(appDiv.innerHTML).toContain('15 €');
    expect(appDiv.innerHTML).toContain('Test Event 2');
    expect(appDiv.innerHTML).toContain('Lyon');
    expect(appDiv.innerHTML).toContain('25 €');
  });

  it('should render a message when no events are returned', async () => {
    const mockGetAllEvents = jest.fn().mockResolvedValue([]);

    jest.doMock('../api/eventApi', () => ({
      getAllEvents: mockGetAllEvents
    }));

    await import('../app.js');

    const appDiv = document.getElementById('app');
    expect(appDiv.innerHTML).toBe("<p>Aucun événement trouvé. La base de données est vide !</p>");
  });

  it('should render an error message when fetch fails', async () => {
    const mockGetAllEvents = jest.fn().mockRejectedValue(new Error('API down'));

    jest.doMock('../api/eventApi', () => ({
      getAllEvents: mockGetAllEvents
    }));

    await import('../app.js');

    const appDiv = document.getElementById('app');
    expect(appDiv.innerHTML).toContain('Erreur de connexion au serveur.');
    expect(appDiv.innerHTML).toContain("style=\"color:red\"");
  });
});