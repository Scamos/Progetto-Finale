package Main;

import java.util.*;

import Evento.*;
import Prodotto.*;
import Registrazione.*;

//Classe che contiene il metodo principale del programma
public class Main {
    public static void main(String[] args) {
        //Crea 4 oggetti, due per i controller e due per le viste
        VistaRegistrazione vista = new VistaRegistrazione();
        VistaEvento vistaEvento = VistaEvento.getInstance();
        RegistrazioneController controller = new RegistrazioneController(vista);
        EventoController eventoController = EventoController.getInstance();

        //Impostazione delle viste
        controller.setVista(vista);
        eventoController.setVistaEvento(vistaEvento);
        //Passa l'istanza della vista alla classe Registrazione
        Registrazione registrazione = Registrazione.getInstance(vista);
        //Le viste vengono aggiunte come osservatori dei rispettivi controller
        controller.aggiungiOsservatore(vista);
        eventoController.aggiungiOsservatore(vistaEvento);

        //Inizializza il VistaProdotto
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        ProdottoController prodottoController = ProdottoController.getInstance();
        prodottoController.aggiungiOsservatore(vistaProdotto);

        Scanner scanner = new Scanner(System.in);
        //Implemento un boolean, che mi servirà per chiedere
        //all'utente se vuole terminare il programma o continuare a usarlo
        boolean continua = true;

        //Resta nel loop finché non viene scelto dall'utente di terminare il programma
        while(continua) {
            System.out.println("Benvenuto! \nPer poter accedere ad un account esistente, inserire i dati adeguati;");
            System.out.println("Per Registrarsi con un nuovo account, inserire i propri dati.");
            //Viene richiesto all'utente di inserire un'email, la quale determina
            //la necessità di una registrazione o di un login; viene anche chiesta una password
            System.out.println("Inserisci email:");
            String email = scanner.nextLine();
            System.out.println("Inserisci password:");
            String password = scanner.nextLine();

            //Viene avviato il processo di registrazione chiamando il
            //metodo registrazione del controller
            controller.registrazioneUtente(email, password);

            //Si rimane nel loop finché l'utente non sceglie di
            //chiudere e terminare il programma

            //Inizialmente, considera ciò che l'Utente inserisce
            //come non valido
            boolean inputValido = false;
            //Verifica la risposta del Utente
            while (!inputValido) {
                System.out.println("Vuoi continuare? (s/n)");
                String scelta = scanner.nextLine();

                //Verifica la scelta del Utente
                switch (scelta.toLowerCase()) {
                    case "s":
                        //L'Utente ha inserito un input valido
                        //che deve far continuare il programma
                        inputValido = true; //Esci dal loop
                        break;
                    case "n":
                        //L'Utente ha inserito un input valido
                        //che deve far chiudere il programma
                        inputValido = true; //Esci dal loop
                        continua = false; //Servirà per uscire dal loop più grande della registrazione
                        break;
                    default:
                        //Risposta non valida
                        System.out.println("Scelta non valida\nInserisci un valore valido");
                        break;
                }
            }
        }
    }
}
