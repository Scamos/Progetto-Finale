package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class ProdottoTest {

    private Prodotto prodotto;

    @BeforeEach
    void setUp() {
        prodotto = new Prodotto("Test Prodotto", "Descrizione Test", 10.00, 5);
    }

    @Test
    void getNome() {
        //Arrange
        String expectedNome = "Test Prodotto";

        //Act
        String actualNome = prodotto.getNome();

        //Assert
        assertEquals(expectedNome, actualNome);
    }

    @Test
    void setNome() {
        //Arrange
        String newNome = "Nuovo Prodotto";

        //Act
        prodotto.setNome(newNome);

        //Assert
        assertEquals(newNome, prodotto.getNome());
    }

    @Test
    void getDescrizione() {
        //Arrange
        String expectedDescrizione = "Descrizione Test";

        //Act
        String actualDescrizione = prodotto.getDescrizione();

        //Assert
        assertEquals(expectedDescrizione, actualDescrizione);
    }

    @Test
    void setDescrizione() {
        //Arrange
        String newDescrizione = "Nuova Descrizione";

        //Act
        prodotto.setDescrizione(newDescrizione);

        //Assert
        assertEquals(newDescrizione, prodotto.getDescrizione());
    }

    @Test
    void getPrezzo() {
        //Arrange
        double expectedPrezzo = 10.00;

        //Act
        double actualPrezzo = prodotto.getPrezzo();

        //Assert
        assertEquals(expectedPrezzo, actualPrezzo, 0.001);
    }

    @Test
    void setPrezzo() {
        //Arrange
        double newPrezzo = 15.00;

        //Act
        prodotto.setPrezzo(newPrezzo);

        //Assert
        assertEquals(newPrezzo, prodotto.getPrezzo(), 0.001);
    }

    @Test
    void getQuantita() {
        //Arrange
        int expectedQuantita = 5;

        //Act
        int actualQuantita = prodotto.getQuantita();

        //Assert
        assertEquals(expectedQuantita, actualQuantita);
    }

    @Test
    void incrementaQuantita() {
        //Arrange
        int incremento = 3;
        int expectedQuantita = 8;

        //Act
        prodotto.incrementaQuantita(incremento);

        //Assert
        assertEquals(expectedQuantita, prodotto.getQuantita());
    }

    @Test
    void decrementaQuantita() {
        //Arrange
        int decremento = 2;
        int expectedQuantita = 3;

        //Act
        prodotto.decrementaQuantita(decremento);

        //Assert
        assertEquals(expectedQuantita, prodotto.getQuantita());
    }

    @Test
    void decrementaQuantitaOltreLimite() {
        //Arrange
        int decremento = 10;
        int expectedQuantita = 5;
        //Rimane 5 poiché se la quantità che cerca di decrementare è oltre il limite,
        //Il metodo viene annullato e la quantità rimane invariata

        //Act
        prodotto.decrementaQuantita(decremento);

        //Assert
        assertEquals(expectedQuantita, prodotto.getQuantita());
    }

    @Test
    void testToString() {
        //Arrange
        String expected = "Prodotto{nome='Test Prodotto', descrizione='Descrizione Test', prezzo=10,00, quantita=5}";

        //Act
        String actual = prodotto.toString();

        //Assert
        assertEquals(expected, actual);
    }
}