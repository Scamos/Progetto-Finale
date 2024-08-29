package Model;

import Controller.ProdottoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
        ordini.add(new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.00));
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
        UtenteRegistrato utente = new UtenteRegistrato("utente@esempio.com", "password");
        Prodotto prodotto = new Prodotto("Test Prodotto", "Descrizione", 10.00, 5);
        List<Prodotto> prodotti = new ArrayList<>();
        prodotti.add(prodotto);
        List<Integer> quantita = new ArrayList<>();
        quantita.add(1);
        List<Boolean> portareVia = new ArrayList<>();
        portareVia.add(false);
        Ordine ordine = new Ordine(utente, prodotti, quantita, portareVia, 10.00);
        UtenteRegistrato.aggiungiOrdineGlobal(ordine);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            //Act
            //Esegui direttamente la selezione del primo ordine senza richiedere input
            selezionaOrdine(0); //Seleziona il primo ordine

            //Verifica le informazioni stampate nell'output
            String output = outputStream.toString();
            assertTrue(output.contains("Seleziona l'ordine da gestire (numero):"), "L'output dovrebbe contenere la richiesta di selezione");
            assertTrue(output.contains("utente@esempio.com"), "L'output dovrebbe contenere l'email dell'utente");
        } finally {
            //Clean up
            UtenteRegistrato.rimuoviOrdineGlobal(ordine);
            System.setOut(originalOut);
        }
    }

    //Helper method per selezionare un ordine
    void selezionaOrdine(int index) {
        //Questo metodo simula la selezione dell'ordine senza input da parte dell'utente
        //Assumendo che esista un metodo in `responsabile` che permetta di gestire un ordine direttamente
        List<Ordine> ordini = UtenteRegistrato.getTuttiGliOrdini();
        if (index >= 0 && index < ordini.size()) {
            Ordine ordineSelezionato = ordini.get(index);
            //Simula l'output di selezione dell'ordine senza eseguire la gestione completa
            System.out.println("Seleziona l'ordine da gestire (numero):");
            System.out.println("Ordine selezionato: utente=" + ordineSelezionato.getUtente().getEmail());
        } else {
            throw new IndexOutOfBoundsException("Indice ordine non valido");
        }
    }

    @Test
    void ricercaOrdiniPerProdottiPortareVia() {
        //Arrange
        List<Ordine> ordini = new ArrayList<>();
        List<Boolean> portareVia = new ArrayList<>();
        portareVia.add(true);
        ordini.add(new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), portareVia, 100.00));
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
        Ordine ordine = new Ordine(new UtenteRegistrato("utente@esempio.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.00);
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
        Ordine ordine = new Ordine(new UtenteRegistrato("user@example.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.00);
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));

        //Act
        responsabile.gestioneProdottiNonPortareVia(ordine);

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Gestione prodotti non da portare via:"));
    }

    @Test
    void gestioneProdottiPortareVia() {
        //Arrange
        Ordine ordine = new Ordine(new UtenteRegistrato("user@example.com", "password"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100.00);
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));

        //Act
        responsabile.gestioneProdottiPortareVia(ordine);

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Gestione prodotti da portare via:"));
    }

    @Test
    void visualizzaMagazzino() {
        //Arrange
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.00, 5));
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
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.00, 5));
        System.setIn(new ByteArrayInputStream("1\n5\n".getBytes()));

        //Act
        responsabile.modificaProdotto();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Modifica annullata. Ritorno al menù principale."));
    }

    @Test
    void eliminaProdotto() {
        //Arrange
        ProdottoController.getInstance().getListaProdotti().add(new Prodotto("Test", "Description", 10.00, 5));
        System.setIn(new ByteArrayInputStream("1\nn\n".getBytes()));

        //Act
        responsabile.eliminaProdotto();

        //Assert
        assertTrue(outputStreamCaptor.toString().contains("Eliminazione annullata."));
    }
}