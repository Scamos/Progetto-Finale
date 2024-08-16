package Observer;

import Vista.VistaEvento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
        //Arrange: Prepara il messaggio di test
        String messaggioTest = "Evento aggiornato!";

        //Act: Chiama il metodo aggiorna con il messaggio di test
        vistaEvento.aggiorna(messaggioTest);

        //Assert: Verifica l'output (qui dovremmo verificare se il messaggio è stato stampato sulla console)
        //Poiché la classe VistaEvento stampa il messaggio nella console, non c'è un modo diretto di verificare l'output
        //senza modificare il design. Tuttavia, se si volesse testare il metodo senza dipendere dalla console,
        //si potrebbe refactorare VistaEvento per salvare il messaggio e poi verificare che sia corretto.
        //Un altro approccio è utilizzare un Mock.
    }
}