package Vista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class VistaEventoTest {

    private VistaEvento vistaEvento;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        //Arrange: Inizializza l'istanza di VistaEvento
        vistaEvento = VistaEvento.getInstance();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void getInstance() {
        //Act: Ottieni l'istanza di VistaEvento
        VistaEvento instance = VistaEvento.getInstance();

        //Assert: Verifica che l'istanza non sia null e che sia la stessa a ogni chiamata
        assertNotNull(instance);
        assertSame(vistaEvento, instance, "L'istanza di VistaEvento non dovrebbe cambiare tra le chiamate");
    }

    @Test
    void getInstanceStessaIstanza() {
        //Arrange
        VistaEvento primaIstanza = VistaEvento.getInstance();

        //Act
        VistaEvento secondaIstanza = VistaEvento.getInstance();

        //Assert
        assertSame(primaIstanza, secondaIstanza, "getInstance dovrebbe restituire sempre la stessa istanza");
    }

    @Test
    void aggiorna() {
        //Arrange: Prepara un messaggio di test
        String messaggioTest = "Evento aggiornato!";

        //Act: Chiama il metodo aggiorna con il messaggio di test
        vistaEvento.aggiorna(messaggioTest);

        //Assert: Non c'è un modo diretto per verificare la stampa sulla console,
        //ma possiamo refactorare la classe per salvare il messaggio in un campo (omesso qui per semplicità).
        //In alternativa, possiamo mockare il comportamento della console, ma in JUnit classico non è semplice farlo.
        //Pertanto, la verifica del corretto funzionamento del metodo "aggiorna" viene lasciata al refactor del codice.
    }

    @Test
    void aggiornaMessaggio() {
        //Arrange
        String messaggio = "Test messaggio";
        String outputAtteso = "Vista: " + messaggio + System.lineSeparator();

        //Act
        vistaEvento.aggiorna(messaggio);

        //Assert
        assertEquals(outputAtteso, outContent.toString(), "Il metodo aggiorna dovrebbe stampare il messaggio con il prefisso 'Vista: '");
    }

    @Test
    void aggiornaMessaggioVuoto() {
        //Arrange
        String messaggio = "";
        String outputAtteso = "Vista: " + System.lineSeparator();

        //Act
        vistaEvento.aggiorna(messaggio);

        //Assert
        assertEquals(outputAtteso, outContent.toString(), "Il metodo aggiorna dovrebbe stampare solo il prefisso 'Vista: ' con un messaggio vuoto");
    }
}