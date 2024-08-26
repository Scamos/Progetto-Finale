package Observer;

import Vista.VistaEvento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class EventoOsservatoreTest {

    private VistaEvento vistaEvento;

    @BeforeEach
    public void setUp() {
        //Arrange: Inizializza l'istanza di VistaEvento
        vistaEvento = VistaEvento.getInstance();
    }

    @Test
    void aggiorna() {
        //Arrange
        String messaggio = "Evento aggiornato";
        String outputAtteso = "Vista: " + messaggio + System.lineSeparator();

        //Act
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        vistaEvento.aggiorna(messaggio);

        //Assert
        assertEquals(outputAtteso, outContent.toString());
    }

    @Test
    void getInstanceStessaIstanza() {
        //Arrange
        VistaEvento primaIstanza = VistaEvento.getInstance();

        //Act
        VistaEvento secondaIstanza = VistaEvento.getInstance();

        //Assert
        assertSame(primaIstanza, secondaIstanza);
    }

    @Test
    void aggiornaMessaggioVuoto() {
        //Arrange
        String messaggio = "";
        String outputAtteso = "Vista: " + System.lineSeparator();

        //Act
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        vistaEvento.aggiorna(messaggio);

        //Assert
        assertEquals(outputAtteso, outContent.toString());
    }
}