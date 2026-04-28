describe('main.js', () => {
  let eventList;

  beforeEach(() => {
    document.body.innerHTML = `
      <div id="event-list"></div>
      <button id="btn-create-event" style="display: none;"></button>
      <a id="link-login"></a>
      <a id="link-signup"></a>
      <button id="btn-logout" style="display: none;"></button>
      <a id="link-reservations" style="display: none;"></a>
      <a id="link-payments" style="display: none;"></a>
      <a id="link-profile" style="display: none;"></a>
      <a id="link-dashboard" style="display: none;"></a>
      <button class="filter-btn" data-type="">Tous</button>
      <button class="filter-btn" data-type="MATCH">Matchs</button>
    `;
    eventList = document.getElementById('event-list');

    window.confirm = jest.fn();
  });

  afterEach(() => {
    fetch.resetMocks();
    jest.clearAllMocks();
  });

  describe('getEventImage', () => {
    it('should return correct image for each event type', () => {
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

  describe('loadEvents', () => {
    beforeEach(() => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'mock-token';
        if (key === 'role') return 'USER';
        return null;
      });
    });

    it('should load and display events successfully', async () => {
      const mockEvents = [
        {
          id: 1,
          title: 'Test Event 1',
          type: 'MATCH',
          description: 'Description 1',
          lieu: 'Location 1',
          capaciteMax: 100,
          nbInscrits: 50
        },
        {
          id: 2,
          title: 'Test Event 2',
          type: 'SOIREE',
          description: 'Description 2',
          lieu: 'Location 2',
          capaciteMax: 200,
          nbInscrits: 150
        }
      ];

      fetch.mockResponseOnce(JSON.stringify(mockEvents));

      const loadEvents = (type = '') => {
        let url = '/api/events';
        if (type !== '') {
          url += `?type=${type}`;
        }

        fetch(url)
          .then(res => res.json())
          .then(events => {
            if (events.length === 0) {
              eventList.innerHTML = "<p>Aucun événement pour ce filtre.</p>";
              return;
            }

            eventList.innerHTML = events.map(event => {
              const inscrits = event.nbInscrits || 0;
              const max = event.capaciteMax || 1;
              const pourcentage = Math.min((inscrits / max) * 100, 100);

              return `
                <div class="card" data-id="${event.id}">
                  <div class="card-content">
                    <img src="images/match.jpg" class="event-image" alt="${event.type}">
                    <h2>${event.title}</h2>
                    <p>${event.description || ''}</p>
                    <p><strong>Lieu :</strong> ${event.lieu}</p>
                    <div class="progress-section">
                      <div class="participant-label">
                        <span>Participants</span>
                        <span>${inscrits} / ${max}</span>
                      </div>
                      <div class="progress-container">
                        <div class="progress-bar" style="width: ${pourcentage}%"></div>
                      </div>
                    </div>
                  </div>
                  <button class="btn-register" onclick="event.stopPropagation(); window.location.href='details.html?id=${event.id}'">
                    S'inscrire
                  </button>
                </div>
              `;
            }).join('');

            document.querySelectorAll('.card').forEach(card => {
              card.addEventListener('click', () => {
                const id = card.dataset.id;
                window.location.href = `details.html?id=${id}`;
              });
            });
          })
          .catch(err => console.error("Erreur back:", err));
      };

      loadEvents();

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(fetch).toHaveBeenCalledWith('/api/events');
      expect(eventList.innerHTML).toContain('Test Event 1');
      expect(eventList.innerHTML).toContain('Test Event 2');
      expect(eventList.innerHTML).toContain('images/match.jpg');
    });

    it('should handle empty events list', async () => {
      fetch.mockResponseOnce(JSON.stringify([]));

      const loadEvents = (type = '') => {
        let url = '/api/events';
        if (type !== '') {
          url += `?type=${type}`;
        }

        fetch(url)
          .then(res => res.json())
          .then(events => {
            if (events.length === 0) {
              eventList.innerHTML = "<p>Aucun événement pour ce filtre.</p>";
              return;
            }
          })
          .catch(err => console.error("Erreur back:", err));
      };

      loadEvents();

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(eventList.innerHTML).toBe("<p>Aucun événement pour ce filtre.</p>");
    });

    it('should filter events by type', async () => {
      const mockEvents = [
        {
          id: 1,
          title: 'Match Event',
          type: 'MATCH',
          description: 'Match description',
          lieu: 'Stadium',
          capaciteMax: 100,
          nbInscrits: 50
        }
      ];

      fetch.mockResponseOnce(JSON.stringify(mockEvents));

      const loadEvents = (type = '') => {
        let url = '/api/events';
        if (type !== '') {
          url += `?type=${type}`;
        }

        fetch(url)
          .then(res => res.json())
          .then(events => {
            eventList.innerHTML = events.map(event => {
              const inscrits = event.nbInscrits || 0;
              const max = event.capaciteMax || 1;
              const pourcentage = Math.min((inscrits / max) * 100, 100);

              return `
                <div class="card" data-id="${event.id}">
                  <div class="card-content">
                    <img src="images/match.jpg" class="event-image" alt="${event.type}">
                    <h2>${event.title}</h2>
                    <p>${event.description || ''}</p>
                    <p><strong>Lieu :</strong> ${event.lieu}</p>
                    <div class="progress-section">
                      <div class="participant-label">
                        <span>Participants</span>
                        <span>${inscrits} / ${max}</span>
                      </div>
                      <div class="progress-container">
                        <div class="progress-bar" style="width: ${pourcentage}%"></div>
                      </div>
                    </div>
                  </div>
                  <button class="btn-register" onclick="event.stopPropagation(); window.location.href='details.html?id=${event.id}'">
                    S'inscrire
                  </button>
                </div>
              `;
            }).join('');
          })
          .catch(err => console.error("Erreur back:", err));
      };

      loadEvents('MATCH');

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(fetch).toHaveBeenCalledWith('/api/events?type=MATCH');
    });
  });

  describe('deleteEvent', () => {
    beforeEach(() => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'mock-token';
        if (key === 'role') return 'USER';
        return null;
      });
    });

    it('should delete event successfully', async () => {
      window.confirm.mockReturnValue(true);
      fetch.mockResponseOnce('', { status: 200 });

      const deleteEvent = (eventId) => {
        if (!confirm("Êtes-vous sûr de vouloir supprimer cet événement ?")) {
          return;
        }

        fetch(`/api/events/${eventId}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer mock-token`,
            'Content-Type': 'application/json'
          }
        })
          .then(response => {
            if (response.ok) {
              alert("Événement supprimé avec succès !");
            } else {
              alert("Action refusée : vous n'avez pas les droits ou votre session a expiré.");
            }
          })
          .catch(error => {
            console.error("Erreur:", error);
          });
      };

      window.alert = jest.fn();

      deleteEvent(1);

      await new Promise(resolve => setTimeout(resolve, 0));

      expect(window.confirm).toHaveBeenCalledWith("Êtes-vous sûr de vouloir supprimer cet événement ?");
      expect(fetch).toHaveBeenCalledWith('/api/events/1', {
        method: 'DELETE',
        headers: {
          'Authorization': 'Bearer mock-token',
          'Content-Type': 'application/json'
        }
      });
      expect(window.alert).toHaveBeenCalledWith("Événement supprimé avec succès !");
    });

    it('should not delete when user cancels', () => {
      window.confirm.mockReturnValue(false);

      const deleteEvent = (eventId) => {
        if (!confirm("Êtes-vous sûr de vouloir supprimer cet événement ?")) {
          return;
        }
      };

      deleteEvent(1);

      expect(window.confirm).toHaveBeenCalledWith("Êtes-vous sûr de vouloir supprimer cet événement ?");
      expect(fetch).not.toHaveBeenCalled();
    });
  });

  describe('logout', () => {
    it('should logout user successfully', () => {
      window.confirm.mockReturnValue(true);
      window.alert = jest.fn();

      const logout = () => {
        if (!confirm("Voulez-vous vraiment vous déconnecter ?")) return;

        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
        localStorage.removeItem('email');

      };

      logout();

      expect(window.confirm).toHaveBeenCalledWith("Voulez-vous vraiment vous déconnecter ?");
      expect(localStorage.removeItem).toHaveBeenCalledWith('token');
      expect(localStorage.removeItem).toHaveBeenCalledWith('role');
      expect(localStorage.removeItem).toHaveBeenCalledWith('userId');
      expect(localStorage.removeItem).toHaveBeenCalledWith('username');
      expect(localStorage.removeItem).toHaveBeenCalledWith('email');
    });

    it('should not logout when user cancels', () => {
      window.confirm.mockReturnValue(false);

      const logout = () => {
        if (!confirm("Voulez-vous vraiment vous déconnecter ?")) return;
      };

      logout();

      expect(window.confirm).toHaveBeenCalledWith("Voulez-vous vraiment vous déconnecter ?");
      expect(localStorage.removeItem).not.toHaveBeenCalled();
    });
  });

  describe('UI visibility', () => {
    it('should show admin elements for admin user', () => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'admin-token';
        if (key === 'role') return 'ADMIN';
        return null;
      });

      const createBtn = document.getElementById('btn-create-event');
      const dashboardLink = document.getElementById('link-dashboard');

      if (createBtn && localStorage.getItem('role') === 'ADMIN') {
        createBtn.style.display = 'block';
      }

      if (dashboardLink && localStorage.getItem('role') === 'ADMIN') {
        dashboardLink.style.display = 'inline-block';
      }

      expect(createBtn.style.display).toBe('block');
      expect(dashboardLink.style.display).toBe('inline-block');
    });

    it('should show user elements for authenticated user', () => {
      localStorage.getItem.mockImplementation((key) => {
        if (key === 'token') return 'user-token';
        if (key === 'role') return 'USER';
        return null;
      });

      const loginLink = document.getElementById('link-login');
      const signupLink = document.getElementById('link-signup');
      const logoutBtn = document.getElementById('btn-logout');
      const reservationsLink = document.getElementById('link-reservations');
      const paymentsLink = document.getElementById('link-payments');
      const profileLink = document.getElementById('link-profile');
      const dashboardLink = document.getElementById('link-dashboard');

      if (localStorage.getItem('token')) {
        if (loginLink) loginLink.style.display = 'none';
        if (signupLink) signupLink.style.display = 'none';
        if (paymentsLink) paymentsLink.style.display = 'inline-block';
        if (reservationsLink) reservationsLink.style.display = 'inline-block';
        if (profileLink) profileLink.style.display = 'inline-block';
        if (logoutBtn) logoutBtn.style.display = 'inline-block';
        if (dashboardLink && localStorage.getItem('role') === 'ADMIN') dashboardLink.style.display = 'inline-block';
      }

      expect(loginLink.style.display).toBe('none');
      expect(signupLink.style.display).toBe('none');
      expect(logoutBtn.style.display).toBe('inline-block');
      expect(reservationsLink.style.display).toBe('inline-block');
      expect(paymentsLink.style.display).toBe('inline-block');
      expect(profileLink.style.display).toBe('inline-block');
      expect(dashboardLink.style.display).toBe('none'); 
    });
  });
});