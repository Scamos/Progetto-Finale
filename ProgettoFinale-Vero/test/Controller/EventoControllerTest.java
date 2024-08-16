package Controller;

import Model.Evento;
import Observer.EventoOsservatore;
import Vista.VistaEvento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class EventoControllerTest {

    private EventoController controller;

    @BeforeEach
    public void setUp() {
        //Arrange: Inizializza l'istanza di EventoController
        //Assicura che l'istanza precedente sia null prima di ogni test
        EventoControllerTest.resetInstance();
        controller = EventoController.getInstance();
    }

    @AfterEach
    public void tearDown() {
        //Resetta l'istanza dopo ogni test per prevenire la contaminazione tra i test
        EventoControllerTest.resetInstance();
    }

    private static void resetInstance() {
        try {
            Field instanceField = EventoController.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //getInstance: Verifica che il metodo getInstance restituisca sempre la stessa istanza di EventoController.
    @Test
    void getInstance() {
        //Act: Ottieni l'istanza del controller
        EventoController instance = EventoController.getInstance();

        //Assert: Verifica che l'istanza non sia null e sia la stessa
        assertNotNull(instance);
        assertSame(controller, instance);
    }

    //setVistaEvento: Verifica che il metodo setVistaEvento non generi eccezioni. Non ci sono getter disponibili per verificare direttamente, quindi il test si limita a verificare che non ci siano errori.
    @Test
    void setVistaEvento() {
        //Arrange: Crea una mock o un'istanza di VistaEvento
        VistaEvento vistaEvento = VistaEvento.getInstance();

        //Act: Imposta la vista evento
        controller.setVistaEvento(vistaEvento);

        //Assert: Verifica che la vista evento sia stata impostata correttamente
        //Nota: Non è presente un metodo getter per verificare, quindi questo test può essere limitato
        //a non lanciare eccezioni se non si può accedere alla vista evento.
        //assertEquals(vistaEvento, controller.getVistaEvento()); // Questo se esistesse un getter
    }

    //aggiungiOsservatore: Verifica che un osservatore possa essere aggiunto senza generare eccezioni.
    @Test
    void aggiungiOsservatore() {
        //Arrange: Crea un Osservatore
        EventoOsservatore osservatore = new EventoOsservatore() {
            @Override
            public void aggiorna(String messaggio) {
                //Metodo di esempio
            }
        };

        //Act: Aggiungi l'osservatore
        controller.aggiungiOsservatore(osservatore);

        //Assert: Non c'è un getter per gli osservatori, quindi si verifica che non lanci eccezioni
        //Se esistesse un getter o altro metodo per controllare, si potrebbe usare per verificare l'aggiunta
    }

    //notificaOsservatori: Verifica che gli osservatori ricevano correttamente i messaggi di notifica inviati dal controller.
    @Test
    void notificaOsservatori() {
        //Arrange: Crea un osservatore che cattura il messaggio
        final String[] messaggioRicevuto = {null};
        EventoOsservatore osservatore = messaggio -> messaggioRicevuto[0] = messaggio;
        controller.aggiungiOsservatore(osservatore);
        String messaggioTest = "Nuovo evento!";

        //Act: Notifica gli osservatori
        controller.notificaOsservatori(messaggioTest);

        //Assert: Verifica che l'osservatore abbia ricevuto il messaggio corretto
        assertEquals(messaggioTest, messaggioRicevuto[0]);
    }

    //aggiungiEvento: Verifica che un nuovo evento venga correttamente aggiunto alla lista degli eventi gestiti dal controller.
    @Test
    void aggiungiEvento() {
        //Arrange: Crea un nuovo evento
        Evento nuovoEvento = new Evento("Conferenza", "Piazza del Popolo", "2024-10-01", "10:00", "12:00");

        //Act: Aggiungi l'evento alla lista
        controller.aggiungiEvento(nuovoEvento);

        //Assert: Verifica che l'evento sia stato aggiunto alla lista
        List<Evento> listaEventi = controller.getListaEventi();
        assertTrue(listaEventi.contains(nuovoEvento));
    }

    //getListaEventi: Verifica che la lista degli eventi iniziali sia correttamente popolata.
    @Test
    void getListaEventi() {
        //Act: Ottieni la lista degli eventi
        List<Evento> listaEventi = controller.getListaEventi();

        //Assert: Verifica che la lista contenga gli eventi iniziali
        assertNotNull(listaEventi);
        assertEquals(3, listaEventi.size());
        assertEquals("Karaoke", listaEventi.get(0).getNome());
        assertEquals("Concerto", listaEventi.get(1).getNome());
        assertEquals("Degustazione", listaEventi.get(2).getNome());
    }

    //trovaEvento: Verifica che il metodo trovaEvento sia in grado di trovare un evento esistente e che ritorni null se l'evento non esiste.
    @Test
    void trovaEvento() {
        //Act: Trova un evento esistente
        Evento evento = controller.trovaEvento("Concerto");

        //Assert: Verifica che l'evento sia stato trovato correttamente
        assertNotNull(evento);
        assertEquals("Concerto", evento.getNome());

        //Act: Prova a trovare un evento che non esiste
        Evento eventoNonEsistente = controller.trovaEvento("Evento Inesistente");

        //Assert: Verifica che il metodo ritorni null se l'evento non esiste
        assertNull(eventoNonEsistente);
    }
}