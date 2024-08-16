package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
public class OrdineTest {

    private Ordine ordine;
    private UtenteRegistrato utente;
    private List<Prodotto> prodotti;
    private List<Integer> quantita;
    private List<Boolean> portareVia;

    @BeforeEach
    void setUp() {
        //Arrange
        utente = new UtenteRegistrato("test@example.com", "password");
        Prodotto prodotto1 = new Prodotto("Prodotto1", "Descrizione1", 10.00, 5);
        Prodotto prodotto2 = new Prodotto("Prodotto2", "Descrizione2", 15.00, 3);
        prodotti = Arrays.asList(prodotto1, prodotto2);
        quantita = Arrays.asList(2, 1);
        portareVia = Arrays.asList(true, false);
        double costoTotale = 35.0; //2 * 10.0 + 1 * 15.0

        ordine = new Ordine(utente, prodotti, quantita, portareVia, costoTotale);
    }

    @Test
    void getUtente() {
        //getUtente: Verifica che il metodo getUtente restituisca l'utente corretto.

        //Act
        UtenteRegistrato utenteResult = ordine.getUtente();

        //Assert
        assertEquals(utente, utenteResult);
    }

    @Test
    public void getProdotti() {
        //getProdotti: Controlla se getProdotti restituisce la lista corretta di prodotti.

        //Act
        List<Prodotto> prodottiResult = ordine.getProdotti();

        //Assert
        assertEquals(prodotti, prodottiResult);
    }

    @Test
    public void getQuantita() {
        //getQuantita: Testa la corretta restituzione della lista di quantità.

        //Act
        List<Integer> quantitaResult = ordine.getQuantita();

        //Assert
        assertEquals(quantita, quantitaResult);
    }

    @Test
    public void getPortareVia() {
        //getPortareVia: Verifica la restituzione della lista portareVia.

        //Act
        List<Boolean> portareViaResult = ordine.getPortareVia();

        //Assert
        assertEquals(portareVia, portareViaResult);
    }

    @Test
    public void getProdottiGestiti() {
        //getProdottiGestiti: Verifica che inizialmente prodottiGestiti sia una lista di false.

        //Act
        List<Boolean> prodottiGestitiResult = ordine.getProdottiGestiti();

        //Assert
        assertEquals(Arrays.asList(false, false), prodottiGestitiResult);
    }

    @Test
    public void setProdottiGestiti() {
        //setProdottiGestiti: Testa il metodo setProdottiGestiti e controlla se il cambiamento viene effettuato correttamente.

        //Arrange
        List<Boolean> nuoviProdottiGestiti = Arrays.asList(true, true);

        //Act
        ordine.setProdottiGestiti(nuoviProdottiGestiti);
        List<Boolean> prodottiGestitiResult = ordine.getProdottiGestiti();

        //Assert
        assertEquals(nuoviProdottiGestiti, prodottiGestitiResult);
    }

    @Test
    public void getProdottiRitirati() {
        //getProdottiRitirati: Verifica la lista prodottiRitirati.

        //Act
        List<Boolean> prodottiRitiratiResult = ordine.getProdottiRitirati();

        //Assert
        assertEquals(Arrays.asList(false, false), prodottiRitiratiResult);
    }

    @Test
    public void setProdottiRitirati() {
        //setProdottiRitirati: Controlla il settaggio della lista prodottiRitirati.

        //Arrange
        List<Boolean> nuoviProdottiRitirati = Arrays.asList(true, false);

        //Act
        ordine.setProdottiRitirati(nuoviProdottiRitirati);
        List<Boolean> prodottiRitiratiResult = ordine.getProdottiRitirati();

        //Assert
        assertEquals(nuoviProdottiRitirati, prodottiRitiratiResult);
    }

    @Test
    public void getCostoTotale() {
        //getCostoTotale: Verifica il valore del costo totale.

        //Act
        double costoTotaleResult = ordine.getCostoTotale();

        //Assert
        assertEquals(35.0, costoTotaleResult);
    }

    @Test
    public void getCompletato() {
        //getCompletato: Controlla lo stato iniziale del completamento dell'ordine.

        //Act
        boolean completatoResult = ordine.getCompletato();

        //Assert
        assertFalse(completatoResult);
    }

    @Test
    public void setCompletato() {
        //setCompletato: Verifica che lo stato di completamento possa essere aggiornato.

        //Arrange
        boolean nuovoStatoCompletato = true;

        //Act
        ordine.setCompletato(nuovoStatoCompletato);
        boolean completatoResult = ordine.getCompletato();

        //Assert
        assertTrue(completatoResult);
    }

    @Test
    public void getProdottiPortareViaCompletati() {
        //getProdottiPortareViaCompletati: Controlla lo stato dei prodotti da portare via.

        //Act
        boolean prodottiPortareViaCompletatiResult = ordine.getProdottiPortareViaCompletati();

        //Assert
        assertFalse(prodottiPortareViaCompletatiResult);
    }

    @Test
    public void setProdottiPortareViaCompletati() {
        //setProdottiPortareViaCompletati: Verifica il cambiamento dello stato dei prodotti da portare via.

        //Arrange
        boolean nuovoStatoPortareViaCompletati = true;

        //Act
        ordine.setProdottiPortareViaCompletati(nuovoStatoPortareViaCompletati);
        boolean prodottiPortareViaCompletatiResult = ordine.getProdottiPortareViaCompletati();

        //Assert
        assertTrue(prodottiPortareViaCompletatiResult);
    }

    @Test
    public void getProdottiNonPortareViaCompletati() {
        //setProdottiNonPortareViaCompletati: Controlla lo stato dei prodotti non da portare via.

        //Act
        boolean prodottiNonPortareViaCompletatiResult = ordine.getProdottiNonPortareViaCompletati();

        //Assert
        assertFalse(prodottiNonPortareViaCompletatiResult);
    }

    @Test
    public void setProdottiNonPortareViaCompletati() {
        //setProdottiNonPortareViaCompletati: Verifica il cambiamento dello stato dei prodotti non da portare via.

        //Arrange
        boolean nuovoStatoNonPortareViaCompletati = true;

        //Act
        ordine.setProdottiNonPortareViaCompletati(nuovoStatoNonPortareViaCompletati);
        boolean prodottiNonPortareViaCompletatiResult = ordine.getProdottiNonPortareViaCompletati();

        //Assert
        assertTrue(prodottiNonPortareViaCompletatiResult);
    }

    @Test
    public void testToString() {
        //testToString: Verifica la corretta formattazione della stringa restituita dal metodo toString.

        //Act
        String ordineString = ordine.toString();

        //Assert
        String expectedString = "Ordine{utente=test@example.com, prodotti=" + prodotti +
                ", quantita=" + quantita +
                ", portareVia=" + portareVia +
                ", costoTotale=35,00, completato=false}";
        assertEquals(expectedString, ordineString);
    }
}