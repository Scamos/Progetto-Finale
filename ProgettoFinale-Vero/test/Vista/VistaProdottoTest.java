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
class VistaProdottoTest {

    private VistaProdotto vistaProdotto;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        vistaProdotto = VistaProdotto.getInstance();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void getInstance() {
        //Arrange
        VistaProdotto primaIstanza = VistaProdotto.getInstance();

        //Act
        VistaProdotto secondaIstanza = VistaProdotto.getInstance();

        //Assert
        assertSame(primaIstanza, secondaIstanza, "getInstance dovrebbe restituire la stessa istanza");
    }

    @Test
    void setTornaAlMenu() {
        //Arrange
        boolean valore = true;

        //Act
        VistaProdotto.setTornaAlMenu(valore);

        //Assert
        assertTrue(VistaProdotto.getTornaAlMenu(), "setTornaAlMenu dovrebbe impostare il valore correttamente");
    }

    @Test
    void getTornaAlMenu() {
        //Arrange
        boolean valoreAtteso = false;
        VistaProdotto.setTornaAlMenu(valoreAtteso);

        //Act
        boolean valoreOttenuto = VistaProdotto.getTornaAlMenu();

        //Assert
        assertEquals(valoreAtteso, valoreOttenuto, "getTornaAlMenu dovrebbe restituire il valore corretto");
    }

    @Test
    void aggiorna() {
        //Arrange
        String messaggio = "Test di aggiornamento";

        //Act
        vistaProdotto.aggiorna(messaggio);

        //Assert
        String output = outputStreamCaptor.toString().trim();
        assertEquals("Vista: Test di aggiornamento", output, "aggiorna dovrebbe stampare il messaggio corretto");
    }
}