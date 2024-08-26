package Factory;

import Model.Registrazione;
import Vista.VistaRegistrazione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class RegistrazioneFactoryTest {

    @Test
    void getRegistrazioneStessaIstanza() {
        //Arrange
        VistaRegistrazione vista1 = new VistaRegistrazione();
        VistaRegistrazione vista2 = new VistaRegistrazione();

        //Act
        Registrazione registrazione1 = RegistrazioneFactory.getRegistrazione(vista1);
        Registrazione registrazione2 = RegistrazioneFactory.getRegistrazione(vista2);

        //Assert
        assertNotNull(registrazione1);
        assertNotNull(registrazione2);
        assertSame(registrazione1, registrazione2, "Le istanze di Registrazione dovrebbero essere le stesse");
    }

    @Test
    void getRegistrazioneNonNull() {
        //Arrange
        VistaRegistrazione vista = new VistaRegistrazione();

        //Act
        Registrazione registrazione = RegistrazioneFactory.getRegistrazione(vista);

        //Assert
        assertNotNull(registrazione, "L'istanza di Registrazione non dovrebbe essere null");
    }

    @Test
    void getRegistrazioneIstanzaRegistrazione() {
        //Arrange
        VistaRegistrazione vista = new VistaRegistrazione();

        //Act
        Object result = RegistrazioneFactory.getRegistrazione(vista);

        //Assert
        assertTrue(result instanceof Registrazione, "Il risultato dovrebbe essere un'istanza di Registrazione");
    }
}