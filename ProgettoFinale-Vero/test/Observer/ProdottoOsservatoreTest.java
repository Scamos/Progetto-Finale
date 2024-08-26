package Observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class ProdottoOsservatoreTest {

    private TestProdottoOsservatore osservatore;

    //Implementazione concreta di ProdottoOsservatore per il test
    private static class TestProdottoOsservatore implements ProdottoOsservatore {
        private String ultimoMessaggio;

        @Override
        public void aggiorna(String messaggio) {
            this.ultimoMessaggio = messaggio;
        }

        public String getUltimoMessaggio() {
            return ultimoMessaggio;
        }
    }

    @BeforeEach
    void setUp() {
        osservatore = new TestProdottoOsservatore();
    }

    @Test
    void aggiorna() {
        //Arrange
        String messaggio = "Prodotto aggiornato";

        //Act
        osservatore.aggiorna(messaggio);

        //Assert
        assertEquals(messaggio, osservatore.getUltimoMessaggio());
    }

    @Test
    void aggiornaMessaggioVuoto() {
        //Arrange
        String messaggio = "";

        //Act
        osservatore.aggiorna(messaggio);

        //Assert
        assertEquals(messaggio, osservatore.getUltimoMessaggio());
    }

    @Test
    void aggiornaMessaggioNull() {
        //Arrange
        String messaggio = null;

        //Act
        osservatore.aggiorna(messaggio);

        //Assert
        assertNull(osservatore.getUltimoMessaggio());
    }

    @Test
    void aggiornaChiamateMultiple() {
        //Arrange
        String primoMessaggio = "Primo aggiornamento";
        String secondoMessaggio = "Secondo aggiornamento";

        //Act
        osservatore.aggiorna(primoMessaggio);
        osservatore.aggiorna(secondoMessaggio);

        //Assert
        assertEquals(secondoMessaggio, osservatore.getUltimoMessaggio());
    }
}