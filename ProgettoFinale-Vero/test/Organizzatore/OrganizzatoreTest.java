package Organizzatore;

import Evento.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class OrganizzatoreTest {

    private Organizzatore organizzatore;
    private List<Evento> eventi; // Lista di eventi per il test

    @BeforeEach
    void setUp() {
        organizzatore = new Organizzatore("test@example.com", "password123");
        eventi = new ArrayList<>(); // Inizializza la lista di eventi per i test
    }

    @Test
    void creazioneLotteria() {
    }

    @Test
    void gestioneLotteria() {
    }

    @Test
    void creazioneEventi() {
        //Arrange
        Evento evento = new Evento("Festa di compleanno", "Via Roma 10", "2024-08-20", "18:00", "23:00");
        eventi.add(evento); // Aggiungi l'evento alla lista

        //Act
        //Simula l'aggiunta dell'evento nella lista di eventi
        List<Evento> listaEventi = eventi;

        //Assert
        assertNotNull(listaEventi);
        assertEquals(1, listaEventi.size());
        assertEquals("Festa di compleanno", listaEventi.get(0).getNome());
    }

    @Test
    void gestioneEventiModifica() {
        //Arrange
        Evento evento = new Evento("Concerto", "Piazza Centrale", "2024-09-15", "20:00", "22:00");
        eventi.add(evento); //Aggiungi l'evento alla lista
        int eventoScelto = 1;  //Scelta: il primo e unico evento nella lista
        int opzioneModifica = 1; //Modifica del nome
        String nuovoNome = "Concerto Modificato";

        //Act
        modificaEventoHelper(eventoScelto, opzioneModifica, nuovoNome, null, null, null, null);

        //Assert
        assertEquals("Concerto Modificato", eventi.get(eventoScelto - 1).getNome());
    }

    //Helper per simulare modificaEvento
    private void modificaEventoHelper(int eventoScelto, int sceltaModifica, String nuovoNome, String nuovaVia, String nuovaData, String nuovaOraInizio, String nuovaOraFine) {
        Evento eventoDaModificare = eventi.get(eventoScelto - 1);

        switch (sceltaModifica) {
            case 1:
                eventoDaModificare.setNome(nuovoNome);
                break;
            case 2:
                eventoDaModificare.setVia(nuovaVia);
                break;
            case 3:
                eventoDaModificare.setData(LocalDate.parse(nuovaData));
                break;
            case 4:
                eventoDaModificare.setOraInizio(LocalTime.parse(nuovaOraInizio));
                break;
            case 5:
                eventoDaModificare.setOraFine(LocalTime.parse(nuovaOraFine));
                break;
            default:
                throw new IllegalArgumentException("Scelta non valida");
        }
    }

    @Test
    public void gestioneEventiElimina() {
        //Arrange
        Evento evento = new Evento("Sagra del Vino", "Via del Vino 5", "2024-10-01", "12:00", "18:00");
        eventi.add(evento); //Aggiungi l'evento alla lista
        int eventoScelto = 1;  //Scelta: il primo e unico evento nella lista
        String confermaEliminazione = "s";

        //Act
        eliminaEventoHelper(eventoScelto, confermaEliminazione);
        List<Evento> listaEventi = eventi;

        //Assert
        assertTrue(listaEventi.isEmpty());
    }

    //Helper per simulare eliminaEvento
    private void eliminaEventoHelper(int eventoScelto, String confermaEliminazione) {
        Evento eventoDaEliminare = eventi.get(eventoScelto - 1);

        if ("s".equalsIgnoreCase(confermaEliminazione)) {
            eventi.remove(eventoDaEliminare);
        }
    }

    @Test
    void visualizzaPartecipazioneEventi() {
        //Arrange
        Evento evento = new Evento("Maratona", "Parco Comunale", "2024-11-10", "08:00", "14:00");
        evento.incrementaPartecipanti();
        eventi.add(evento); //Aggiungi l'evento alla lista

        //Act
        //Simula la visualizzazione della partecipazione
        //Non c'è bisogno di modificare la vista in questo test specifico

        //Assert
        assertEquals(1, evento.getNumPartecipanti());
    }
}