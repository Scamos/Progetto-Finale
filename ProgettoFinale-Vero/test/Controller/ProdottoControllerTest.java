package Controller;

import Model.Prodotto;
import Observer.ProdottoOsservatore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class ProdottoControllerTest {

    private ProdottoController controller;

    @BeforeEach
    void setUp() {
        controller = ProdottoController.getInstance();
    }

    @Test
    void getInstance() {
        //Arrange
        ProdottoController instance1 = ProdottoController.getInstance();
        ProdottoController instance2 = ProdottoController.getInstance();

        //Act & Assert
        assertSame(instance1, instance2, "getInstance dovrebbe restituire la stessa istanza");
    }

    @Test
    void aggiungiOsservatore() {
        //Arrange
        TestProdottoOsservatore osservatore = new TestProdottoOsservatore();

        //Act
        controller.aggiungiOsservatore(osservatore);

        //Assert
        controller.notificaOsservatori("Test");
        assertTrue(osservatore.isNotificato(), "L'osservatore dovrebbe essere notificato");
    }

    @Test
    void notificaOsservatori() {
        //Arrange
        TestProdottoOsservatore osservatore1 = new TestProdottoOsservatore();
        TestProdottoOsservatore osservatore2 = new TestProdottoOsservatore();
        controller.aggiungiOsservatore(osservatore1);
        controller.aggiungiOsservatore(osservatore2);

        //Act
        controller.notificaOsservatori("Notifica Test");

        //Assert
        assertTrue(osservatore1.isNotificato(), "Osservatore 1 dovrebbe essere notificato");
        assertTrue(osservatore2.isNotificato(), "Osservatore 2 dovrebbe essere notificato");
        assertEquals("Notifica Test", osservatore1.getMessaggio(), "Osservatore 1 dovrebbe ricevere il messaggio corretto");
        assertEquals("Notifica Test", osservatore2.getMessaggio(), "Osservatore 2 dovrebbe ricevere il messaggio corretto");
    }

    @Test
    void getListaProdotti() {
        //Arrange & Act
        List<Prodotto> prodotti = controller.getListaProdotti();

        //Assert
        assertNotNull(prodotti, "La lista dei prodotti non dovrebbe essere null");
        assertFalse(prodotti.isEmpty(), "La lista di prodotti non dovrebbe essere vuota");
    }

    @Test
    void trovaProdottoCorretto() {
        //Arrange
        String nomeProdotto = "Pane";

        //Act
        Prodotto prodottoTrovato = controller.trovaProdotto(nomeProdotto);

        //Assert
        assertNotNull(prodottoTrovato, "Dovrebbe trovare un prodotto");
        assertEquals(nomeProdotto, prodottoTrovato.getNome(), "Il prodotto trovato dovrebbe avere il nome corretto");
    }

    @Test
    void trovaProdottoScorretto() {
        //Arrange
        String nomeProdotto = "ProdottoInesistente";

        //Act
        Prodotto prodottoTrovato = controller.trovaProdotto(nomeProdotto);

        //Assert
        assertNull(prodottoTrovato, "Dovrebbe restituire null per un prodotto non esistente");
    }

    // Helper class for testing observers
    private static class TestProdottoOsservatore implements ProdottoOsservatore {
        private boolean notificato = false;
        private String messaggio;

        @Override
        public void aggiorna(String messaggio) {
            this.notificato = true;
            this.messaggio = messaggio;
        }

        public boolean isNotificato() {
            return notificato;
        }

        public String getMessaggio() {
            return messaggio;
        }
    }
}