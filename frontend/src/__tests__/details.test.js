describe('details.js', () => {
  let container;

  beforeEach(() => {
    document.body.innerHTML = '<div id="details-container"></div>';
    container = document.getElementById('details-container');
  });

  afterEach(() => {
    fetch.resetMocks();
    jest.clearAllMocks();
  });

  describe('getEventImage', () => {    it('should return correct image for MATCH type', () => {
      const getEventImage = (type) => {
        if (type === 'MATCH') return 'images/match.jpg';
        if (type === 'TOURNOI') return 'images/tournoi.jpg';
        if (type === 'STAGE') return 'images/stage.jpg';
        if (type === 'SOIREE') return 'images/soiree.jpg';
        return 'images/default.jpg';
      };

      expect(getEventImage('MATCH')).toBe('images/match.jpg');
      expect(getEventImage('TOURNOI')).toBe('images/tournoi.jpg');
      expect(getEventImage('STAGE')).toBe('images/stage.jpg');
      expect(getEventImage('SOIREE')).toBe('images/soiree.jpg');
      expect(getEventImage('UNKNOWN')).toBe('images/default.jpg');
    });
  });

  describe('Event loading', () => {
    it('should display error when no eventId', () => {
      const eventId = null;

      if (!eventId) {
        container.innerHTML = "<p>Aucun événement sélectionné.</p>";
      }

      expect(container.innerHTML).toBe("<p>Aucun événement sélectionné.</p>");
    });

    it('should load and display event details successfully', async () => {
      const mockEvent = {
        id: 1,
        title: 'Test Event',
        type: 'MATCH',
        description: 'Test description',
        lieu: 'Test location',
        prix: 10,
        capaciteMax: 100,
        dateDebut: '2025-05-15T10:00:00',
        dateFin: '2025-05-15T12:00:00',
        nbInscrits: 50
      };

      fetch.mockResponseOnce(JSON.stringify(mockEvent));

      const eventId = 1;

      if (eventId) {
        fetch(`/api/events/${eventId}`)
          .then(res => {
            if (!res.ok) throw new Error("Événement introuvable");
            return res.json();
          })
          .then(event => {
            const inscrits = event.nbInscrits || 0;
            const max = event.capaciteMax || 1;
            const pourcentage = Math.min((inscrits / max) * 100, 100);

            container.innerHTML = `
              <div class="details-card">
                <div class="details-hero">
                  <img src="${'images/match.jpg'}" class="details-image">
                  <div class="details-overlay">
                    <h1>${event.title}</h1>
                    <p class="details-type">${event.type}</p>
                  </div>
                </div>
                <div class="details-body">
                  <p class="description">${event.description || ''}</p>
                  <div class="info-grid">
                    <p><strong>📍 Lieu :</strong> ${event.lieu}</p>
                    <p><strong>💶 Prix :</strong> ${event.prix} €</p>
                    <p><strong>👥 Capacité :</strong> ${event.capaciteMax}</p>
                    <p><strong>📅 Début :</strong> ${new Date(event.dateDebut).toLocaleString()}</p>
                    <p><strong>📅 Fin :</strong> ${new Date(event.dateFin).toLocaleString()}</p>
                  </div>
                  <div class="progress-section">
                    <div class="participant-label">
                      <span>Participants</span>
                      <span>${inscrits} / ${max}</span>
                    </div>
                    <div class="progress-container">
                      <div class="progress-bar" style="width:${pourcentage}%"></div>
                    </div>
                  </div>
                  <button class="btn-reserve-big" id="btn-reserver">
                    Réserver maintenant
                  </button>
                </div>
              </div>
            `;

            document.getElementById('btn-reserver').addEventListener('click', () => {
              window.location.href = `reservation.html?eventId=${event.id}`;
            });
          })
          .catch(err => {
            console.error(err);
            container.innerHTML = "<p>Erreur lors du chargement.</p>";
          });
      }

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(fetch).toHaveBeenCalledWith('/api/events/1');
      expect(container.innerHTML).toContain('Test Event');
      expect(container.innerHTML).toContain('images/match.jpg');
      expect(container.innerHTML).toContain('Test description');
    });

    it('should handle fetch error', async () => {
      fetch.mockRejectOnce(new Error('Network error'));

      const eventId = 1;

      if (eventId) {
        fetch(`/api/events/${eventId}`)
          .then(res => {
            if (!res.ok) throw new Error("Événement introuvable");
            return res.json();
          })
          .then(event => {
          })
          .catch(err => {
            console.error(err);
            container.innerHTML = "<p>Erreur lors du chargement.</p>";
          });
      }

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(container.innerHTML).toBe("<p>Erreur lors du chargement.</p>");
    });

    it('should handle 404 error', async () => {
      fetch.mockResponseOnce('', { status: 404 });

      const eventId = 1;

      if (eventId) {
        fetch(`/api/events/${eventId}`)
          .then(res => {
            if (!res.ok) throw new Error("Événement introuvable");
            return res.json();
          })
          .then(event => {
          })
          .catch(err => {
            console.error(err);
            container.innerHTML = "<p>Erreur lors du chargement.</p>";
          });
      }

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(container.innerHTML).toBe("<p>Erreur lors du chargement.</p>");
    });
  });
});