package Vista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class VistaRegistrazioneTest {

    private VistaRegistrazione vistaRegistrazione;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        vistaRegistrazione = new VistaRegistrazione();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void aggiornaMessaggioCorretto() {
        //Arrange
        String messaggio = "Test di aggiornamento";

        //Act
        vistaRegistrazione.aggiorna(messaggio);

        //Assert
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Vista: Test di aggiornamento", output);
    }

    @Test
    void aggiornaMessaggioVuoto() {
        //Arrange
        String messaggio = "";

        //Act
        vistaRegistrazione.aggiorna(messaggio);

        //Assert
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Vista:", output);
    }

    @Test
    void aggiornaMessaggioNull() {
        //Arrange
        String messaggio = null;

        //Act & Assert
        assertDoesNotThrow(() -> vistaRegistrazione.aggiorna(messaggio));
    }
}