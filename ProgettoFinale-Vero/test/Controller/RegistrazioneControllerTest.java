package Controller;

import Observer.RegistrazioneOsservatore;
import Vista.VistaRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class RegistrazioneControllerTest {

    private RegistrazioneController controller;
    private VistaRegistrazione vista;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        vista = new VistaRegistrazione();
        controller = new RegistrazioneController(vista);
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void registrazioneUtenteValidaAnnullata() {
        //Arrange
        String email = "test@esempio.com";
        String password = "password123";
        String input = "n\n"; //Simuliamo la scelta di non registrare i dati del nuovo account
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Act
        controller.registrazioneUtente(email, password);

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Vuoi registrare i nuovi dati? (s/n)"));
        assertTrue(output.contains("Registrazione annullata."));
    }

    @Test
    void registrazioneUtenteInvalida() {
        //Arrange
        String email = "e-mail invalida";
        String password = "password123";

        //Act
        controller.registrazioneUtente(email, password);

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Email non valida. Registrazione annullata."));
    }

    @Test
    void setVista() {
        //Arrange
        VistaRegistrazione nuovaVista = new VistaRegistrazione();

        //Act
        controller.setVista(nuovaVista);

        //Assert
        //Non possiamo verificare direttamente che la vista sia stata impostata
        //poiché il campo è privato. Possiamo solo verificare che non ci siano eccezioni.
        assertDoesNotThrow(() -> controller.setVista(nuovaVista));
    }

    @Test
    void aggiungiOsservatore() {
        //Arrange
        RegistrazioneOsservatore osservatore = new VistaRegistrazione();

        //Act
        controller.aggiungiOsservatore(osservatore);

        //Assert
        //Verifichiamo indirettamente aggiungendo un osservatore e poi notificando
        controller.notificaOsservatori("Test");
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Vista: Test"));
    }

    @Test
    void notificaOsservatori() {
        //Arrange
        RegistrazioneOsservatore osservatore1 = new VistaRegistrazione();
        RegistrazioneOsservatore osservatore2 = new VistaRegistrazione();
        controller.aggiungiOsservatore(osservatore1);
        controller.aggiungiOsservatore(osservatore2);

        //Act
        controller.notificaOsservatori("Messaggio di test");

        //Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Vista: Messaggio di test"));
        //Verifichiamo che il messaggio appaia due volte, una per ogni osservatore
        assertEquals(2, output.split("Vista: Messaggio di test").length - 1);
    }
}