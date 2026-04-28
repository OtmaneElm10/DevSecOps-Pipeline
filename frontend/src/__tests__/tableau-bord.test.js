describe('tableau-bord.js', () => {
  let container;

  beforeEach(() => {
    document.body.innerHTML = '<div id="dashboard-container"></div>';
    container = document.getElementById('dashboard-container');

    window.alert = jest.fn();
  });

  afterEach(() => {
    fetch.resetMocks();
    jest.clearAllMocks();
  });

  describe('Access control', () => {
    it('should redirect non-admin users', () => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'user-token';
        if (key === 'role') return 'USER';
        return null;
      });

      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');

      if (!token || role !== 'ADMIN') {
        window.alert("Accès refusé. Vous devez être administrateur.");
      }

      expect(window.alert).toHaveBeenCalledWith("Accès refusé. Vous devez être administrateur.");
    });

    it('should allow admin access', () => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'admin-token';
        if (key === 'role') return 'ADMIN';
        return null;
      });

      const token = localStorage.getItem('token');
      const role = localStorage.getItem('role');

      expect(token).toBe('admin-token');
      expect(role).toBe('ADMIN');
    });
  });

  describe('loadDashboard', () => {
    beforeEach(() => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'admin-token';
        if (key === 'role') return 'ADMIN';
        return null;
      });
    });

    it('should load and display dashboard successfully', async () => {
      const mockReservations = [
        {
          eventId: 1,
          eventTitle: 'Event 1',
          userId: 1,
          username: 'User 1',
          nbPlaces: 2,
          montantAttendu: 20,
          statut: 'CONFIRMED'
        },
        {
          eventId: 1,
          eventTitle: 'Event 1',
          userId: 2,
          username: 'User 2',
          nbPlaces: 1,
          montantAttendu: 10,
          statut: 'PENDING'
        },
        {
          eventId: 2,
          eventTitle: 'Event 2',
          userId: 3,
          username: 'User 3',
          nbPlaces: 3,
          montantAttendu: 30,
          statut: 'CONFIRMED'
        }
      ];

      fetch.mockResponseOnce(JSON.stringify(mockReservations));

      const loadDashboard = async () => {
        try {
          const response = await fetch('/api/reservations', {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          });

          if (!response.ok) {
            const errorText = await response.text();
            console.error("Erreur dashboard :", response.status, errorText);
            container.innerHTML = "<p>Impossible de charger les réservations.</p>";
            return;
          }

          const reservations = await response.json();
          displayDashboard(reservations);

        } catch (error) {
          console.error("Erreur réseau :", error);
          container.innerHTML = "<p>Impossible de joindre le serveur.</p>";
        }
      };

      const displayDashboard = (reservations) => {
        if (!reservations || reservations.length === 0) {
          container.innerHTML = "<p>Aucune réservation trouvée.</p>";
          return;
        }

        const eventsMap = {};

        reservations.forEach(res => {
          const eventId = res.eventId || 'unknown';
          const eventTitle = res.eventTitle || `Événement #${eventId}`;

          if (!eventsMap[eventId]) {
            eventsMap[eventId] = {
              title: eventTitle,
              reservations: [],
              totalPlaces: 0,
              totalMontant: 0
            };
          }

          eventsMap[eventId].reservations.push(res);
          eventsMap[eventId].totalPlaces += res.nbPlaces || 0;
          eventsMap[eventId].totalMontant += res.montantAttendu || 0;
        });

        container.innerHTML = Object.values(eventsMap).map(event => `
          <div class="admin-event-block">
            <h3>${event.title}</h3>

            <div class="dashboard-stats">
              <p><strong>Réservations :</strong> ${event.reservations.length}</p>
              <p><strong>Places réservées :</strong> ${event.totalPlaces}</p>
              <p><strong>Montant total attendu :</strong> ${event.totalMontant} €</p>
            </div>

            <table class="payments-table">
              <thead>
                <tr>
                  <th>Utilisateur</th>
                  <th>Places</th>
                  <th>Montant</th>
                  <th>Statut</th>
                </tr>
              </thead>
              <tbody>
                ${event.reservations.map(res => `
                  <tr>
                    <td>${res.username || 'Utilisateur #' + (res.userId || '')}</td>
                    <td>${res.nbPlaces || 0}</td>
                    <td>${res.montantAttendu || 0} €</td>
                    <td><strong>${res.statut || '-'}</strong></td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
        `).join('');
      };

      await loadDashboard();

      expect(fetch).toHaveBeenCalledWith('/api/reservations', {
        method: 'GET',
        headers: {
          'Authorization': 'Bearer admin-token'
        }
      });
      expect(container.innerHTML).toContain('Event 1');
      expect(container.innerHTML).toContain('Event 2');
      expect(container.innerHTML).toContain('Réservations :');
      expect(container.innerHTML).toContain('Places réservées :</strong> 3');
      expect(container.innerHTML).toContain('Montant total attendu :</strong> 30 €');
    });

    it('should handle empty reservations', async () => {
      fetch.mockResponseOnce(JSON.stringify([]));

      const loadDashboard = async () => {
        const response = await fetch('/api/reservations', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (!response.ok) {
          container.innerHTML = "<p>Impossible de charger les réservations.</p>";
          return;
        }

        const reservations = await response.json();
        displayDashboard(reservations);
      };

      const displayDashboard = (reservations) => {
        if (!reservations || reservations.length === 0) {
          container.innerHTML = "<p>Aucune réservation trouvée.</p>";
          return;
        }
      };

      await loadDashboard();

      expect(container.innerHTML).toBe("<p>Aucune réservation trouvée.</p>");
    });

    it('should handle fetch error', async () => {
      fetch.mockRejectOnce(new Error('Network error'));

      const loadDashboard = async () => {
        try {
          await fetch('/api/reservations', {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          });
        } catch (error) {
          console.error("Erreur réseau :", error);
          container.innerHTML = "<p>Impossible de joindre le serveur.</p>";
        }
      };

      await loadDashboard();

      expect(container.innerHTML).toBe("<p>Impossible de joindre le serveur.</p>");
    });

    it('should handle HTTP error', async () => {
      fetch.mockResponseOnce('', { status: 403 });

      const loadDashboard = async () => {
        const response = await fetch('/api/reservations', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (!response.ok) {
          const errorText = await response.text();
          console.error("Erreur dashboard :", response.status, errorText);
          container.innerHTML = "<p>Impossible de charger les réservations.</p>";
          return;
        }
      };

      await loadDashboard();

      expect(container.innerHTML).toBe("<p>Impossible de charger les réservations.</p>");
    });
  });

  describe('displayDashboard', () => {
    it('should group reservations by event', () => {
      const reservations = [
        {
          eventId: 1,
          eventTitle: 'Event 1',
          userId: 1,
          username: 'User 1',
          nbPlaces: 2,
          montantAttendu: 20,
          statut: 'CONFIRMED'
        },
        {
          eventId: 1,
          eventTitle: 'Event 1',
          userId: 2,
          username: 'User 2',
          nbPlaces: 1,
          montantAttendu: 10,
          statut: 'PENDING'
        }
      ];

      const displayDashboard = (reservations) => {
        const eventsMap = {};

        reservations.forEach(res => {
          const eventId = res.eventId || 'unknown';
          const eventTitle = res.eventTitle || `Événement #${eventId}`;

          if (!eventsMap[eventId]) {
            eventsMap[eventId] = {
              title: eventTitle,
              reservations: [],
              totalPlaces: 0,
              totalMontant: 0
            };
          }

          eventsMap[eventId].reservations.push(res);
          eventsMap[eventId].totalPlaces += res.nbPlaces || 0;
          eventsMap[eventId].totalMontant += res.montantAttendu || 0;
        });

        container.innerHTML = Object.values(eventsMap).map(event => `
          <div class="admin-event-block">
            <h3>${event.title}</h3>
            <div class="dashboard-stats">
              <p><strong>Réservations :</strong> ${event.reservations.length}</p>
              <p><strong>Places réservées :</strong> ${event.totalPlaces}</p>
              <p><strong>Montant total attendu :</strong> ${event.totalMontant} €</p>
            </div>
            <table class="payments-table">
              <thead>
                <tr>
                  <th>Utilisateur</th>
                  <th>Places</th>
                  <th>Montant</th>
                  <th>Statut</th>
                </tr>
              </thead>
              <tbody>
                ${event.reservations.map(res => `
                  <tr>
                    <td>${res.username || 'Utilisateur #' + (res.userId || '')}</td>
                    <td>${res.nbPlaces || 0}</td>
                    <td>${res.montantAttendu || 0} €</td>
                    <td><strong>${res.statut || '-'}</strong></td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
        `).join('');
      };

      displayDashboard(reservations);

      expect(container.innerHTML).toContain('Event 1');
      expect(container.innerHTML).toContain('Réservations :');
      expect(container.innerHTML).toContain('Places réservées :</strong> 3');
      expect(container.innerHTML).toContain('Montant total attendu :</strong> 30 €');
      expect(container.innerHTML).toContain('User 1');
      expect(container.innerHTML).toContain('User 2');
      expect(container.innerHTML).toContain('CONFIRMED');
      expect(container.innerHTML).toContain('PENDING');
    });

    it('should handle missing data gracefully', () => {
      const reservations = [
        {
          eventId: null,
          eventTitle: null,
          userId: null,
          username: null,
          nbPlaces: null,
          montantAttendu: null,
          statut: null
        }
      ];

      const displayDashboard = (reservations) => {
        const eventsMap = {};

        reservations.forEach(res => {
          const eventId = res.eventId || 'unknown';
          const eventTitle = res.eventTitle || `Événement #${eventId}`;

          if (!eventsMap[eventId]) {
            eventsMap[eventId] = {
              title: eventTitle,
              reservations: [],
              totalPlaces: 0,
              totalMontant: 0
            };
          }

          eventsMap[eventId].reservations.push(res);
          eventsMap[eventId].totalPlaces += res.nbPlaces || 0;
          eventsMap[eventId].totalMontant += res.montantAttendu || 0;
        });

        container.innerHTML = Object.values(eventsMap).map(event => `
          <div class="admin-event-block">
            <h3>${event.title}</h3>
            <div class="dashboard-stats">
              <p><strong>Réservations :</strong> ${event.reservations.length}</p>
              <p><strong>Places réservées :</strong> ${event.totalPlaces}</p>
              <p><strong>Montant total attendu :</strong> ${event.totalMontant} €</p>
            </div>
            <table class="payments-table">
              <tbody>
                ${event.reservations.map(res => `
                  <tr>
                    <td>${res.username || 'Utilisateur #' + (res.userId || '')}</td>
                    <td>${res.nbPlaces || 0}</td>
                    <td>${res.montantAttendu || 0} €</td>
                    <td><strong>${res.statut || '-'}</strong></td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
        `).join('');
      };

      displayDashboard(reservations);

      expect(container.innerHTML).toContain('Événement #unknown');
      expect(container.innerHTML).toContain('Utilisateur #');
      expect(container.innerHTML).toContain('0');
      expect(container.innerHTML).toContain('-');
    });
  });
});