package Factory;

import Model.Prodotto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class ProdottoFactoryTest {

    @Test
    void creaProdottoValido() {
        //Arrange
        String nome = "Test Prodotto";
        String descrizione = "Descrizione del prodotto di test";
        double prezzo = 9.99;
        int quantita = 10;

        //Act
        Prodotto prodotto = ProdottoFactory.creaProdotto(nome, descrizione, prezzo, quantita);

        //Assert
        assertNotNull(prodotto);
        assertEquals(nome, prodotto.getNome());
        assertEquals(descrizione, prodotto.getDescrizione());
        assertEquals(prezzo, prodotto.getPrezzo(), 0.001);
        assertEquals(quantita, prodotto.getQuantita());
    }

    @Test
    void creaProdottoZeroQuantita() {
        //Arrange
        String nome = "Prodotto Zero";
        String descrizione = "Prodotto con quantità zero";
        double prezzo = 5.00;
        int quantita = 0;

        //Act
        Prodotto prodotto = ProdottoFactory.creaProdotto(nome, descrizione, prezzo, quantita);

        //Assert
        assertNotNull(prodotto);
        assertEquals(0, prodotto.getQuantita());
    }

    @Test
    void creaProdottoZeroPrezzo() {
        //Arrange
        String nome = "Prodotto Gratuito";
        String descrizione = "Prodotto con prezzo zero";
        double prezzo = 0.00;
        int quantita = 5;

        //Act
        Prodotto prodotto = ProdottoFactory.creaProdotto(nome, descrizione, prezzo, quantita);

        //Assert
        assertNotNull(prodotto);
        assertEquals(0.00, prodotto.getPrezzo(), 0.001);
    }

    @Test
    void creaProdottoDescrizioneVuota() {
        //Arrange
        String nome = "Prodotto Senza Descrizione";
        String descrizione = "";
        double prezzo = 15.50;
        int quantita = 3;

        //Act
        Prodotto prodotto = ProdottoFactory.creaProdotto(nome, descrizione, prezzo, quantita);

        //Assert
        assertNotNull(prodotto);
        assertEquals("", prodotto.getDescrizione());
    }
}