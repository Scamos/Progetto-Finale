package Model;

import Controller.ProdottoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class ResponsabileBarVenditoreTest {

    private ResponsabileBarVenditore responsabile;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        responsabile = new ResponsabileBarVenditore("test@esempio.com", "password");
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void visualizzaOrdiniClienti() {
        //Arrange
        List<Ordine> ordini = new ArrayList<>();
        ordini.add(new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.0));
        UtenteRegistrato.aggiungiOrdineGlobal(ordini.get(0));
        System.setIn(new ByteArrayInputStream("1\n2\n".getBytes())); //Simula la selezione dell'ordine e l'uscita

        //Act
        responsabile.visualizzaOrdiniClienti();

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Visualizzazione degli ordini dei clienti:"));
        assertTrue(output.contains("Ordine #1:"));
    }

    @Test
    void ricercaOrdiniClienti() {
        //Arrange
        /*List<Ordine> ordini = new ArrayList<>();
        ordini.add(new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.0));
        UtenteRegistrato.aggiungiOrdineGlobal(ordini.get(0));
        System.setIn(new ByteArrayInputStream("1\n".getBytes())); //Simula la selezione dell'ordine

        //Act
        responsabile.ricercaOrdiniClienti();

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Seleziona l'ordine da gestire (numero):"));*/
    }

    @Test
    void ricercaOrdiniPerProdottiPortareVia() {
        //Arrange
        List<Ordine> ordini = new ArrayList<>();
        List<Boolean> portareVia = new ArrayList<>();
        portareVia.add(true);
        ordini.add(new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), portareVia, 100.0));
        UtenteRegistrato.aggiungiOrdineGlobal(ordini.get(0));
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        //Act
        responsabile.ricercaOrdiniPerProdottiPortareVia();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Ordini con prodotti da portare via:"));
    }

    @Test
    void gestioneOrdiniClienti() {
        //Arrange
        Ordine ordine = new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.0);
        System.setIn(new ByteArrayInputStream("n\n".getBytes())); //Simula l'input del responsabile per non gestire i prodotti

        //Act
        responsabile.gestioneOrdiniClienti(ordine);

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Ordine non impostato come completato."));
    }

    @Test
    void gestioneProdottiNonPortareVia() {
        //Arrange
        Ordine ordine = new Ordine(new UtenteRegistrato("user@example.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.0);
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));

        //Act
        responsabile.gestioneProdottiNonPortareVia(ordine);

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Gestione prodotti non da portare via:"));
    }

    @Test
    void gestioneProdottiPortareVia() {
        //Arrange
        Ordine ordine = new Ordine(new UtenteRegistrato("user@example.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.0);
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));

        //Act
        responsabile.gestioneProdottiPortareVia(ordine);

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Gestione prodotti da portare via:"));
    }

    @Test
    void visualizzaMagazzino() {
        //Arrange
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.0, 5));
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));

        //Act
        responsabile.visualizzaMagazzino();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Elenco dei prodotti:"));
    }

    @Test
    void gestioneMagazzino() {
        //Arrange
        System.setIn(new ByteArrayInputStream("4\n".getBytes()));

        //Act
        responsabile.gestioneMagazzino();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Seleziona un'opzione:"));
    }

    @Test
    void aggiungiProdotto() {
        //Arrange
        System.setIn(new ByteArrayInputStream("TestProdotto\nDescrizione\n10.00\n5\n".getBytes()));

        //Act
        responsabile.aggiungiProdotto();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Prodotto aggiunto con successo:"));
    }

    @Test
    void modificaProdotto() {
        //Arrange
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.0, 5));
        System.setIn(new ByteArrayInputStream("1\n5\n".getBytes()));

        //Act
        responsabile.modificaProdotto();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Modifica annullata. Ritorno al menù principale."));
    }

    @Test
    void eliminaProdotto() {
        //Arrange
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.0, 5));
        System.setIn(new ByteArrayInputStream("1\nn\n".getBytes()));

        //Act
        responsabile.eliminaProdotto();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Eliminazione annullata."));
    }
}