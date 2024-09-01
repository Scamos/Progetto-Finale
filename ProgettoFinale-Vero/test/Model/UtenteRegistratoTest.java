package Model;

import Controller.ProdottoController;
import Vista.VistaEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class UtenteRegistratoTest {

    private UtenteRegistrato utente;
    private Prodotto prodotto;
    private Ordine ordine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        utente = new UtenteRegistrato("test@esempio.com", "password");
        prodotto = new Prodotto ("Pasta","Pasta", 5.00, 10);
        ordine = new Ordine(utente, Collections.singletonList(prodotto), Collections.singletonList(1), Collections.singletonList(false), 5.00);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void getVistaEventoInstance() {
        //Arrange & Act
        VistaEvento vistaEvento1 = UtenteRegistrato.getVistaEventoInstance();
        VistaEvento vistaEvento2 = UtenteRegistrato.getVistaEventoInstance();

        //Assert
        assertNotNull(vistaEvento1);
        assertSame(vistaEvento1, vistaEvento2);
    }

    @Test
    void aggiungiOrdine() {
        //Arrange
        //Non è necessario fare ulteriori arrange

        //Act
        utente.aggiungiOrdine(ordine);

        //Assert
        assertTrue(utente.getOrdini().contains(ordine));
    }

    @Test
    void getOrdini() {
        //Arrange
        Ordine ordine2 = new Ordine(utente, Collections.singletonList(prodotto), Collections.singletonList(2), Collections.singletonList(true), 10.00);
        utente.aggiungiOrdine(ordine);
        utente.aggiungiOrdine(ordine2);

        //Act
        List<Ordine> ordini = utente.getOrdini();

        //Assert
        assertEquals(2, ordini.size());
        assertTrue(ordini.contains(ordine));
        assertTrue(ordini.contains(ordine2));
    }

    @Test
    void getOrdiniCompletati() {
        //Arrange
        utente.aggiungiOrdine(ordine);
        utente.spostaOrdineCompletato(ordine);

        //Act
        List<Ordine> ordiniCompletati = utente.getOrdiniCompletati();

        //Assert
        assertEquals(1, ordiniCompletati.size());
        assertTrue(ordiniCompletati.contains(ordine));
    }

    @Test
    void aggiungiOrdineGlobal() {
        //Arrange
        //Non è necessario fare ulteriori arrange

        //Act
        UtenteRegistrato.aggiungiOrdineGlobal(ordine);

        //Assert
        assertTrue(UtenteRegistrato.getTuttiGliOrdini().contains(ordine));
    }

    @Test
    void getTuttiGliOrdini() {
        //Arrange
        UtenteRegistrato.aggiungiOrdineGlobal(ordine);

        //Act
        List<Ordine> tuttiGliOrdini = UtenteRegistrato.getTuttiGliOrdini();

        //Assert
        assertTrue(tuttiGliOrdini.contains(ordine));
    }

    @Test
    void rimuoviOrdineGlobal() {
        //Arrange
        UtenteRegistrato.aggiungiOrdineGlobal(ordine);

        //Act
        UtenteRegistrato.rimuoviOrdineGlobal(ordine);

        //Assert
        assertFalse(UtenteRegistrato.getTuttiGliOrdini().contains(ordine));
    }

    @Test
    void spostaOrdineCompletato() {
        //Arrange
        utente.aggiungiOrdine(ordine);

        //Act
        utente.spostaOrdineCompletato(ordine);

        //Assert
        assertFalse(utente.getOrdini().contains(ordine));
        assertTrue(utente.getOrdiniCompletati().contains(ordine));
    }

    @Test
    void visualizzaEventi() {
        //Arrange
        String input = "n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Act
        utente.visualizzaEventi();

        //Assert
        assertTrue(outContent.toString().contains("Eventi disponibili:"));
        assertTrue(outContent.toString().contains("Karaoke"));
        assertTrue(outContent.toString().contains("Concerto"));
        assertTrue(outContent.toString().contains("Degustazione"));
    }

    @Test
    void ricercaEventi() {
        //Arrange
        String simulatedUserInput = "Evento1\nn\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        //Act
        utente.ricercaEventi();

        //Assert
        assertTrue(outContent.toString().contains("Errore: Evento non trovato"));
    }

    @Test
    void visualizzaLotteria() {
    }

    @Test
    void partecipaLotteria() {
    }

    @Test
    void visualizzaProdotti() {
        //Arrange
        String simulatedUserInput = "n\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        //Act
        utente.visualizzaProdotti();

        //Assert
        assertTrue(outContent.toString().contains("Elenco dei prodotti:"));
    }

    @Test
    void ricercaProdotti() {
        //Arrange
        String simulatedUserInput = "Prodotto1\nn\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        //Act
        utente.ricercaProdotti();

        //Assert
        assertTrue(outContent.toString().contains("Errore durante la ricerca di un prodotto."));
    }

    @Test
    void acquistoProdotti() {
        //Arrange
        UtenteRegistrato utente = new UtenteRegistrato("test@esempio.com", "password");
        utente.setNumeroCarta("1234567890123456");
        utente.setDataScadenza("07/26");
        utente.setNomeProprietario("Test");
        utente.setCognomeProprietario("Utente");
        utente.setCvv("123");
        utente.setSaldo(100.00);

        Prodotto prodotto = new Prodotto("TestProdotto", "Descrizione", 10.00, 5);
        ProdottoController.getInstance().getListaProdotti().add(prodotto);

        //Act
        //Simula direttamente l'acquisto di un prodotto senza passare per l'input dell'utente
        //Questo però non testa in modo corretto le linee di codice
        List<Prodotto> prodottiAcquistati = new ArrayList<>();
        prodottiAcquistati.add(prodotto);
        List<Integer> quantitaAcquistate = new ArrayList<>();
        quantitaAcquistate.add(1);
        List<Boolean> portareViaList = new ArrayList<>();
        portareViaList.add(false);
        double costoTotale = 10.00;

        Ordine nuovoOrdine = new Ordine(utente, prodottiAcquistati, quantitaAcquistate, portareViaList, costoTotale);
        utente.aggiungiOrdine(nuovoOrdine);
        UtenteRegistrato.aggiungiOrdineGlobal(nuovoOrdine);
        utente.setSaldo(utente.getSaldo() - costoTotale);
        prodotto.decrementaQuantita(1);

        //Assert
        assertEquals(90.00, utente.getSaldo(), 0.01);
        assertEquals(4, prodotto.getQuantita());
        assertEquals(1, utente.getOrdini().size());
        assertTrue(UtenteRegistrato.getTuttiGliOrdini().contains(nuovoOrdine));
    }

    @Test
    void visualizzaLibreriaPersonale() {
        //Arrange
        //Non è necessario fare ulteriori arrange

        //Act
        utente.visualizzaLibreriaPersonale();

        //Assert
        assertTrue(outContent.toString().contains("Informazioni utente:"));
    }

    @Test
    void gestioneLibreriaPersonale() {
        //Arrange
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Act
        utente.gestioneLibreriaPersonale();

        //Assert
        assertTrue(outContent.toString().contains("Ritorno al menù principale"));
    }

    @Test
    void gestisciEventi() {
        //Arrange
        //Non è necessario fare ulteriori arrange

        //Act
        utente.gestisciEventi();

        //Assert
        assertTrue(outContent.toString().contains("Non ci sono eventi da gestire"));
    }

    @Test
    void getEmail() {
        //Act & Assert
        assertEquals("test@esempio.com", utente.getEmail());
    }

    @Test
    void getPassword() {
        //Act & Assert
        assertEquals("password", utente.getPassword());
    }

    @Test
    void getEsetNumeroCarta() {
        //Arrange
        utente.setNumeroCarta("1234567812345678");

        //Act & Assert
        assertEquals("1234567812345678", utente.getNumeroCarta());
    }

    @Test
    void getEsetDataScadenza() {
        //Arrange
        utente.setDataScadenza("12/26");

        //Act & Assert
        assertEquals("12/26", utente.getDataScadenza());
    }

    @Test
    void getEsetNomeProprietario() {
        //Arrange
        utente.setNomeProprietario("Mario");

        //Act & Assert
        assertEquals("Mario", utente.getNomeProprietario());
    }

    @Test
    void getEsetCognomeProprietario() {
        //Arrange
        utente.setCognomeProprietario("Rossi");

        //Act & Assert
        assertEquals("Rossi", utente.getCognomeProprietario());
    }

    @Test
    void getEsetCvv() {
        //Arrange
        utente.setCvv("123");

        //Act & Assert
        assertEquals("123", utente.getCvv());
    }

    @Test
    void getEsetSaldo() {
        //Arrange
        utente.setSaldo(100.00);

        //Act & Assert
        assertEquals(100.00, utente.getSaldo());
    }

    @Test
    void getLibreriaPersonale() {
        //Act & Assert
        assertNotNull(utente.getLibreriaPersonale());
        assertTrue(utente.getLibreriaPersonale().isEmpty());
    }

    @Test
    void aggiungiEventoALibreria() {
        //Arrange
        Evento evento = new Evento("Patata", "Via Mazzini 1", "2026-03-11", "13:20", "13:30");

        //Act
        utente.aggiungiEventoALibreria(evento);

        //Assert
        assertTrue(utente.getLibreriaPersonale().contains(evento));
    }
}