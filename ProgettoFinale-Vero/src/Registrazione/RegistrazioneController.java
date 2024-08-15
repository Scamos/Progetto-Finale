package Registrazione;

import java.util.*;

//Controller (MVC)
//Gestisce la logica di registrazione degli utenti
public class RegistrazioneController {
    // Attributo per l'osservatore VistaRegistrazione
    private VistaRegistrazione vista;
    //Crea l'attributo per l'istanza di Registrazione che
    //sarà usato per effettuare operazioni di registrazione
    private Registrazione registra;
    //Crea la lista di osservatori che riceveranno una
    //notifica quando avviene un evento di registrazione
    private List<RegistrazioneOsservatore> osservatori;

    //Costruttore della classe RegistrazioneController.
    //Crea un'istanza di Registrazione usando la factory
    //RegistrazioneFactory e inizializza la lista di osservatori.
    public RegistrazioneController(VistaRegistrazione vista) {
        //Acquisisce un'istanza di Registrazione usando la
        //factory RegistrazioneFactory
        this.registra = RegistrazioneFactory.getRegistrazione(vista);
        //Inizializza la lista, che sarà una nuova ArrayList
        this.osservatori = new ArrayList<>();
    }

    //Metodo che viene usato per avviare il processo di registrazione di un utente
    public void registrazioneUtente(String email, String password) {
        //Controllo della email con regex
        if (email.matches("\\S+@[a-z]+\\.[a-z]+")) {
            //Chiama il boolean che sarà uscito dal metodo verificaEmail
            //e lo eguaglia a un boolean di questo metodo
            boolean emailNelDB = registra.verificaEmail(email, password);
            //Questo if controlla se il boolean è true
            if (emailNelDB) {
                Scanner scanner = new Scanner(System.in);
                boolean validInput = false;
                while (!validInput) {
                    System.out.println("Vuoi registrare i nuovi dati? (s/n)");
                    String scelta = scanner.nextLine();
                    switch (scelta.toLowerCase()) {
                        case "s":
                            //Nuova Email, dunque servirà la registrazione
                            registra.creaNuovoUtenteRegistrato(email, password);
                            System.out.println("Registrazione avvenuta con successo, esegui il Log in");
                            //Notifica gli osservatori dopo aver completato la creazione di un nuovo Utente
                            notificaOsservatori("Registrazione di un nuovo Utente avvenuta con successo");
                            validInput = true;
                            break;
                        case "n":
                            //Avvisa l'utente ed esce dal loop
                            System.out.println("Registrazione annullata.");
                            validInput = true;
                            break;
                        default:
                            //Avvisa l'utente e chiede di scegliere di nuovo
                            System.out.println("Scelta non valida. Inserisci nuovamente.");
                    }
                }
            } else {
                //Email già presente, dunque servirà il log in
                registra.verificaPassword(email, password);
            }
        } else {
            //Avvisa l'utente e annulla la registrazione
            System.out.println("Email non valida. Registrazione annullata.");
        }
    }

    //Metodo per impostare la vista nel controller
    public void setVista(VistaRegistrazione vista) {
        this.vista = vista;
    }

    //Metodo che viene usato per aggiungere un osservatore alla lista degli osservatori
    public void aggiungiOsservatore(RegistrazioneOsservatore osservatore) {
        osservatori.add(osservatore);
    }

    //Metodo che viene usato per notificare tutti gli osservatori con un messaggio specifico
    public void notificaOsservatori(String messaggio) {
        for (RegistrazioneOsservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }
}
