package Evento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

//Triple A pattern
//Arrange: fare il setup del oggetto test ecc.
//Act: eseguire l'effettivo run del metodo
//Assert: controllare il valore attuale se eguaglia quello aspettato
class EventoTest {

    private Evento evento;

    @BeforeEach
    public void setUp() {
        evento = new Evento("Concerto", "Via Roma", "2024-08-20", "20:00", "23:00");
    }

    @Test
    void getNome() {
        //Arrange
        String expectedNome = "Concerto";

        //Act
        String actualNome = evento.getNome();

        //Assert
        assertEquals(expectedNome, actualNome);
    }

    @Test
    void setNome() {
        //Arrange
        String nuovoNome = "Festival";

        //Act
        evento.setNome(nuovoNome);

        //Assert
        assertEquals(nuovoNome, evento.getNome());
    }

    @Test
    void getVia() {
        //Arrange
        String expectedVia = "Via Roma";

        //Act
        String actualVia = evento.getVia();

        //Assert
        assertEquals(expectedVia, actualVia);
    }

    @Test
    void setVia() {
        //Arrange
        String nuovaVia = "Via Milano";

        //Act
        evento.setVia(nuovaVia);

        //Assert
        assertEquals(nuovaVia, evento.getVia());
    }

    @Test
    void getData() {
        //Arrange
        LocalDate expectedData = LocalDate.of(2024, 8, 20);

        //Act
        LocalDate actualData = evento.getData();

        //Assert
        assertEquals(expectedData, actualData);
    }

    @Test
    void setData() {
        //Arrange
        LocalDate nuovaData = LocalDate.of(2024, 9, 1);

        //Act
        evento.setData(nuovaData);

        //Assert
        assertEquals(nuovaData, evento.getData());
    }

    @Test
    void getOraInizio() {
        //Arrange
        LocalTime expectedOraInizio = LocalTime.of(20, 0);

        //Act
        LocalTime actualOraInizio = evento.getOraInizio();

        //Assert
        assertEquals(expectedOraInizio, actualOraInizio);
    }

    @Test
    void setOraInizio() {
        //Arrange
        LocalTime nuovaOraInizio = LocalTime.of(19, 0);

        //Act
        evento.setOraInizio(nuovaOraInizio);

        //Assert
        assertEquals(nuovaOraInizio, evento.getOraInizio());
    }

    @Test
    void getOraFine() {
        //Arrange
        LocalTime expectedOraFine = LocalTime.of(23, 0);

        //Act
        LocalTime actualOraFine = evento.getOraFine();

        //Assert
        assertEquals(expectedOraFine, actualOraFine);
    }

    @Test
    void setOraFine() {
        //Arrange
        LocalTime nuovaOraFine = LocalTime.of(22, 30);

        //Act
        evento.setOraFine(nuovaOraFine);

        //Assert
        assertEquals(nuovaOraFine, evento.getOraFine());
    }

    @Test
    void getNumPartecipanti() {
        //Arrange
        int expectedNumPartecipanti = 0;

        //Act
        int actualNumPartecipanti = evento.getNumPartecipanti();

        //Assert
        assertEquals(expectedNumPartecipanti, actualNumPartecipanti);
    }

    @Test
    void setNumPartecipanti() {
        //Arrange
        int nuovoNumPartecipanti = 100;

        //Act
        evento.setNumPartecipanti(nuovoNumPartecipanti);

        //Assert
        assertEquals(nuovoNumPartecipanti, evento.getNumPartecipanti());
    }

    @Test
    void incrementaPartecipanti() {
        //Arrange
        int expectedNumPartecipanti = evento.getNumPartecipanti() + 1;

        //Act
        evento.incrementaPartecipanti();

        //Assert
        assertEquals(expectedNumPartecipanti, evento.getNumPartecipanti());
    }

    @Test
    void decrementaPartecipanti() {
        //Arrange
        evento.setNumPartecipanti(10);
        int expectedNumPartecipanti = 9;

        //Act
        evento.decrementaPartecipanti();

        //Assert
        assertEquals(expectedNumPartecipanti, evento.getNumPartecipanti());
    }

    @Test
    void testToString() {
        //Arrange
        String expectedString = "Evento{nome='Concerto', via='Via Roma', data=2024-08-20, oraInizio=20:00, oraFine=23:00}";

        //Act
        String actualString = evento.toString();

        //Assert
        assertEquals(expectedString, actualString);
    }
}