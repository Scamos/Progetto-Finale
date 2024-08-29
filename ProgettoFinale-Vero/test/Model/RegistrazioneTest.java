package Model;

import Vista.VistaRegistrazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class RegistrazioneTest {

    private Registrazione registrazione;
    private VistaRegistrazione vista;

    @BeforeEach
    void setUp() {
        vista = new VistaRegistrazione();
        registrazione = Registrazione.getInstance(vista);
    }

    @Test
    void getInstance() {
        //Arrange
        VistaRegistrazione nuovaVista = new VistaRegistrazione();

        //Act
        Registrazione instance1 = Registrazione.getInstance(vista);
        Registrazione instance2 = Registrazione.getInstance(nuovaVista);

        //Assert
        assertSame(instance1, instance2);
    }

    @Test
    void verificaEmailNuova() {
        //Arrange
        String email = "nuovaemail@esempio.com";
        String password = "password123";

        //Act
        boolean result = registrazione.verificaEmail(email, password);

        //Assert
        assertTrue(result);
    }

    @Test
    void verificaEmailEsistente() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";

        //Act
        boolean result = registrazione.verificaEmail(email, password);

        //Assert
        assertFalse(result);
    }

    @Test
    void verificaPasswordCorretta() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";
        String input = "8\n"; //Simula l'input dell'utente per il logout
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.verificaPassword(email, password));
    }

    @Test
    void verificaPasswordErrata() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "password_errata";
        String input = ""; //Non serve l'input simulato per questo caso
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.verificaPassword(email, password));
    }

    @Test
    void mostraErroreRegistrazione() {
        //Act
        registrazione.mostraErroreRegistrazione();

        //Assert
        assertDoesNotThrow(() -> registrazione.mostraErroreRegistrazione());
    }

    @Test
    void creaNuovoUtenteRegistrato() {
        //Arrange
        String email = "nuovoutente@esempio.com";
        String password = "nuovapassword";

        //Act
        UtenteRegistrato nuovoUtente = new UtenteRegistrato(email, password);
        nuovoUtente.setNumeroCarta("1234567890123456");
        nuovoUtente.setDataScadenza("12/25");
        nuovoUtente.setNomeProprietario("John");
        nuovoUtente.setCognomeProprietario("Doe");
        nuovoUtente.setCvv("123");
        nuovoUtente.setSaldo(100.00);

        registrazione.getUtentiRegistrati().add(nuovoUtente);

        //Assert
        assertFalse(registrazione.verificaEmail(email, password)); //Restituisce false poiché l'email esiste

        UtenteRegistrato utenteRecuperato = null;
        for (UtenteRegistrato utente : registrazione.getUtentiRegistrati()) {
            if (utente.getEmail().equals(email)) {
                utenteRecuperato = utente;
                break;
            }
        }

        assertNotNull(utenteRecuperato);
        assertEquals(email, utenteRecuperato.getEmail());
        assertEquals(password, utenteRecuperato.getPassword());
        assertEquals("1234567890123456", utenteRecuperato.getNumeroCarta());
        assertEquals("12/25", utenteRecuperato.getDataScadenza());
        assertEquals("John", utenteRecuperato.getNomeProprietario());
        assertEquals("Doe", utenteRecuperato.getCognomeProprietario());
        assertEquals("123", utenteRecuperato.getCvv());
        assertEquals(100.00, utenteRecuperato.getSaldo(), 0.001);
    }

    @Test
    void chiamaUtenteRegistrato() {
        //Arrange
        String email = "mariorossi@gmail.com";
        String password = "1234";
        String input = "8\n"; //Simula l'input dell'utente per il logout
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //Act & Assert
        assertDoesNotThrow(() -> registrazione.chiamaUtenteRegistrato(email, password));
    }
}