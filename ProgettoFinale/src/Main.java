//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//Modello (MVC)
//Classe che rappresenta un Utente Registrato
class UtenteRegistrato {
    private String email;
    private String password;

    //Costruttore che ha il compito di creare un nuovo Utente
    //con l'email e la password
    public UtenteRegistrato(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione degli eventi
    public void visualizzaEventi() {
        //Implementazione della visualizzazione degli eventi
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione della lotteria
    public void visualizzaLotteria() {
        //Implementazione della visualizzazione della lotteria
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la partecipazione alla lotteria
    public void partecipaLotteria() {
        //Implementazione della partecipazione alla lotteria
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione dei prodotti
    public void visualizzaProdotti() {
        //Implementazione della visualizzazione dei prodotti
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per l'acquisto dei prodotti
    public void acquistoProdotti() {
        //Implementazione dell'acquisto dei prodotti
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione della libreria personale
    public void visualizzaLibreriaPersonale() {
        //Implementazione della visualizzazione della libreria personale
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la gestione della libreria personale
    public void gestioneLibreriaPersonale() {
        //Implementazione della gestione della libreria personale
    }

    //Getter per email
    public String getEmail() {
        return email;
    }

    //Getter per password
    public String getPassword() {
        return password;
    }
}
//Classe che rappresenta un Responsabile Bar oppure un Venditore
class ResponsabileBarVenditore extends UtenteRegistrato {
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //email e password già presenti in UtenteRegistrato

    //Costruttore che ha il compito di creare un nuovo Responsabile Bar
    //oppure un Venditore con l'email e la password
    public ResponsabileBarVenditore(String email, String password) {
        super(email, password);
        //Inizializza altri dati predefiniti per il Responsabile di Bar o Venditore
        //Ad esempio, nome, cognome, etc.
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la visualizzazione degli ordini dei clienti
    public void visualizzaOrdiniClienti() {
        //Implementazione della visualizzazione degli ordini dei clienti
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione degli ordini dei clienti
    public void gestioneOrdiniClienti() {
        //Implementazione della gestione degli ordini dei clienti
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione dei prodotti da portare via
    public void gestioneProdottiPortareVia() {
        //Implementazione della gestione dei prodotti da portare via
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la visualizzazione del magazzino
    public void visualizzaMagazzino() {
        //Implementazione della visualizzazione del magazzino
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione del magazzino
    public void gestioneMagazzino() {
        //Implementazione della gestione del magazzino
    }
    //Utilizzando l'ereditarietà, non è necessario riscrivere questi metodi
    //Getter per email e password
}
//Classe che rappresenta un Organizzatore
class Organizzatore extends ResponsabileBarVenditore {
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //email e password già presenti in UtenteRegistrato

    //Costruttore che ha il compito di creare un nuovo Organizzatore
    //con l'email e la password
    public Organizzatore(String email, String password) {
        super(email, password);
        //Inizializza altri dati predefiniti per l'Organizzatore
        //Ad esempio, nome, cognome, etc.
    }

    //Metodo specifico della classe Organizzatore
    //per la creazione delle lotterie
    public void creazioneLotteria() {
        //Implementazione della creazione delle lotterie
    }

    //Metodo specifico della classe Organizzatore
    //per la gestione delle lotterie
    public void gestioneLotteria() {
        //Implementazione della gestione delle lotterie
    }

    //Metodo specifico della classe Organizzatore
    //per la creazione degli eventi
    public void creazioneEventi() {
        //Implementazione della creazione degli eventi
    }

    //Metodo specifico della classe Organizzatore
    //per la gestione degli eventi
    public void gestioneEventi() {
        //Implementazione della gestione degli eventi
    }

    //Metodo specifico della classe Organizzatore
    //per la visualizzazione delle partecipazioni agli eventi
    public void visualizzaPartecipazioneEventi() {
        //Implementazione della visualizzazione delle partecipazioni agli eventi
    }
    //Utilizzando l'ereditarietà, non è necessario riscrivere questi metodi
    //Getter per email e password
}

//Controller (MVC)
//Gestisce la logica di registrazione degli utenti
class RegistrazioneController {
    //Crea l'attributo registra di tipo Registrazione che
    //sarà usato per effettuare operazioni di registrazione
    private Registrazione registra;
    //Crea la lista di osservatori che riceveranno una
    //notifica quando avviene un evento di registrazione
    private List<RegistrazioneOsservatore> osservatori;

    //Costruttore della classe RegistrazioneController.
    //Crea un'istanza di Registrazione usando la factory
    //RegistrazioneFactory e inizializza la lista di osservatori.
    public RegistrazioneController() {
        //Acquisisce un'istanza di Registrazione usando la
        //factory RegistrazioneFactory
        this.registra = RegistrazioneFactory.getRegistrazione();
        //Inizializza la lista, che sarà una nuova ArrayList
        this.osservatori = new ArrayList<>();
    }

    //Metodo che viene usato per avviare il processo di registrazione di un utente
    public void registrazioneUtente(String email, String password) {
        //Chiama il boolean che sarà uscito dal metodo verificaEmail
        //e lo eguaglia a un boolean di questo metodo
        boolean emailNelDB = registra.verificaEmail(email, password);
        //Questo if controlla se il boolean è true
        if (emailNelDB) {
            //Nuova Email, dunque servirà la registrazione
            registra.creaNuovoUtenteRegistrato(email, password);
            //registra.verificaPassword(email, password);
            System.out.println("Registrazione avvenuta con successo, esegui il Log in");
            //Notifica gli osservatori dopo aver completato la creazione di un nuovo Utente
            notificaOsservatori("Registrazione di un nuovo Utente avvenuta con successo");
        } else {
            //Email già presente, dunque servirà il log in
            registra.verificaPassword(email, password);
            //registra.mostraErroreRegistrazione();
            //Notifica gli osservatori in caso di errore durante la registrazione FALSE
            //Notifica gli osservatori dopo aver completato la verifica della password FALSE
            //Notifica gli osservatori dopo aver effettuato il Log out TRUE
            notificaOsservatori("Log Out avvenuto con successo");
        }
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

//Factory
//Classe che contiene il metodo statico che restituisce un'istanza di Registrazione
class RegistrazioneFactory {
    public static Registrazione getRegistrazione() {
        return Registrazione.getInstance();
    }
}

//Singleton
//Design Pattern che permette di avere una sola istanza dell'oggetto
//Registrazione nell'applicazione
class Registrazione {
    //Attributo statico che contiene l'unica istanza di Registrazione nell'applicazione
    private static Registrazione istanza;
    private List<UtenteRegistrato> utentiRegistrati;
    //private static List<UtenteRegistrato> utentiRegistrati = new ArrayList<>();

    //Costruttore privato per evitare la creazione di istanze aggiuntive
    private Registrazione() {
        utentiRegistrati = new ArrayList<>();
        //Aggiunta di Utenti già registrati, che permette di utilizzarli come esempi
        utentiRegistrati.add(new UtenteRegistrato("mariorossi@gmail.com", "1234"));
        utentiRegistrati.add(new UtenteRegistrato("luigibianchi@gmail.com", "4321"));
        //Aggiunta di Responsabili Bar oppure Venditori già registrati
        utentiRegistrati.add(new ResponsabileBarVenditore("barman@gmail.com", "5678"));
        utentiRegistrati.add(new ResponsabileBarVenditore("venditore@gmail.com", "8765"));
        //Aggiunta di Organizzatori già registrati
        utentiRegistrati.add(new Organizzatore("organizzatrice@gmail.com", "abcd"));
        utentiRegistrati.add(new Organizzatore("organizzatore@gmail.com", "dcba"));
    }

    //Metodo che restituisce l'istanza esistente o ne crea una nuova se non esiste già
    public static Registrazione getInstance() {
        if (istanza == null) {
            istanza = new Registrazione();
        }
        return istanza;
    }

    //Metodo per verificare se l'email inserita dall'utente è presente
    //nel "database" oppure no
    public boolean verificaEmail(String email, String password) {
        //Verifica nel "database" se l'email esiste già
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email)) {
                return false; //L'Email è già presente nel "database"
            }
        }
        //Se l'email non è presente nel "database", si deve quindi
        //creare un nuovo utente, che faremo nel metodo registrazione
        //creaNuovoUtenteRegistrato(email, password);
        return true; //L'Email non è presente nel "database"
    }

    //Metodo usato per verificare la password degli utenti, viene
    //chiamato nel caso l'email sia valida
    public void verificaPassword(String email, String password) {
        //Verifica nel "database" se l'email è uguale a quella passata dal Utente,
        //se trova una email nel "database" allora esegue la verifica nella password
        //per la email trovata
        boolean utenteNelDB = false;
        //Esegue la ricerca nel "database"
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email)) {
                utenteNelDB = true;
                if (utente.getPassword().equals(password)) {
                    //La Password che l'Utente ha inserito è corretta,
                    //l'autenticazione è stata fatta correttamente
                    chiamaUtenteRegistrato(email, password);
                    return;
                }
                break; //Uscita dal ciclo perché l'utente è presente nel DB
            }
        }
        //Questo avviene quando l'utente non è stato trovato,
        //se succede deve mostrare un errore di autenticazione
        if (!utenteNelDB) {
            mostraErroreRegistrazione();
        } else {
            //Questo avviene se l'utente è stato trovato ma la password non
            //corrisponde, dunque bisogna mostrare un errore adeguato
            System.out.println("Errore: Password incorretta");
        }
        //Se non viene trovata una corrispondenza nella lista di UtenteRegistrato,
        //verifica se l'utente è un ResponsabileBarVenditore o un Organizzatore
            /*if (utente instanceof ResponsabileBarVenditore) {
                ResponsabileBarVenditore responsabile = (ResponsabileBarVenditore) utente;
                if (responsabile.getEmailResponsabileBarVenditore().equals(email) && responsabile.getPasswordResponsabileBarVenditore().equals(password)) {
                    chiamaUtenteRegistrato(email, password);
                    return;
                }
            } else if (utente instanceof Organizzatore) {
                Organizzatore organizzatore = (Organizzatore) utente;
                if (organizzatore.getEmailOrganizzatore().equals(email) && organizzatore.getPasswordOrganizzatore().equals(password)) {
                    chiamaUtenteRegistrato(email, password);
                    return;
                }
            }*/

        //La Password è scorretta, l'autenticazione non è avvenuta con successo
        //L'ho inserito nel caso non abbia trovato l'utente, quindi
        //questo non serve, se no lo chiama sempre
        //mostraErroreRegistrazione();
    }

    //Metodo che viene chiamato ogni volta che è presente un errore durante la registrazione
    public void mostraErroreRegistrazione() {
        //Mostra messaggio di errore
        System.out.println("Errore: Dati inseriti incorretti");
    }

    //Metodo che viene chiamato alla creazione di un nuovo utente
    public void creaNuovoUtenteRegistrato(String email, String password) {
        //Salva nel "database" il nuovo utente
        UtenteRegistrato nuovoUtente = new UtenteRegistrato(email, password);
        utentiRegistrati.add(nuovoUtente);
    }

    //Metodo che viene chiamato alla registrazione di un utente, sia se già
    //presente sia se è appena stato creato
    public void chiamaUtenteRegistrato(String email, String password) {
        //Accesso all'utente registrato
        System.out.println("Utente autenticato: " + email);
        System.out.println("Benvenuto, " + email + "!");

        Scanner scanner = new Scanner(System.in);
        int scelta = 0;

        //Verifica se l'utente è un ResponsabileBarVenditore
        boolean isResponsabileBarVenditore = false;
        //Verifica se l'utente è un Organizzatore
        boolean isOrganizzatore = false;
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email) && utente instanceof ResponsabileBarVenditore) {
                isResponsabileBarVenditore = true;
                break;
            }
        }
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email) && utente instanceof Organizzatore) {
                isOrganizzatore = true;
            }
        }

        //Menù di scelta di un utente autenticato
        while (scelta != 8) {
            System.out.println("\nScegli una delle varie opzioni:");
            System.out.println("1) Visualizzazione Eventi");
            System.out.println("2) Visualizzazione Lotteria");
            System.out.println("3) Partecipazione Lotteria");
            System.out.println("4) Visualizzazione Prodotti");
            System.out.println("5) Acquisto Prodotti");
            System.out.println("6) Visualizzazione Libreria Personale");
            System.out.println("7) Gestione Libreria Personale");

            //Se il login è stato effettuato da un ResponsabileBarVenditore o da
            //un Organizzatore, visualizza le opzioni che possiedono in più
            if (isResponsabileBarVenditore || isOrganizzatore) {
                System.out.println("9) Visualizzazione Ordini Clienti");
                System.out.println("10) Gestione Ordini Clienti");
                System.out.println("11) Gestione Prodotti da Portare Via");
                System.out.println("12) Visualizzazione Magazzino");
                System.out.println("13) Gestione Magazzino");
            }

            //Se il login è stato effettuato da un Organizzatore,
            //visualizza le opzioni che possiede in più
            if (isOrganizzatore) {
                System.out.println("14) Creazione Lotteria");
                System.out.println("15) Gestione Lotteria");
                System.out.println("16) Creazione Eventi");
                System.out.println("17) Gestione Eventi");
                System.out.println("18) Visualizza Partecipazioni Eventi");
            }

            System.out.println("8) Logout");

            //Permette di leggere la scelta dell'utente
            scelta = scanner.nextInt();

            //Esegui l'azione che l'utente ha scelto
            switch (scelta) {
                case 1:
                    //Implementa la visualizzazione degli eventi
                    break;
                case 2:
                    //Implementa la visualizzazione della lotteria
                    break;
                case 3:
                    //Implementa la partecipazione alla lotteria
                    break;
                case 4:
                    //Implementa la visualizzazione dei prodotti
                    break;
                case 5:
                    //Implementa l'acquisto dei prodotti
                    break;
                case 6:
                    //Implementa la visualizzazione della libreria personale
                    break;
                case 7:
                    //Implementa la gestione della libreria personale
                    break;
                case 8:
                    //Esegue il logout
                    System.out.println("Logout effettuato.");
                    //Ritorna alla registrazione
                    break;
                case 9:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Implementa la visualizzazione degli ordini dei clienti
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 10:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Implementa la gestione degli ordini dei clienti
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 11:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Implementa la gestione dei prodotti da portare via
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 12:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Implementa la visualizzazione del magazzino
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 13:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Implementa la gestione del magazzino
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 14:
                    if (isOrganizzatore) {
                        //Implementa la creazione della lotteria
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 15:
                    if (isOrganizzatore) {
                        //Implementa la gestione della lotteria
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 16:
                    if (isOrganizzatore) {
                        //Implementa la creazione degli eventi
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 17:
                    if (isOrganizzatore) {
                        //Implementa la gestione degli eventi
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 18:
                    if (isOrganizzatore) {
                        //Implementa la visualizzazione delle partecipazioni agli eventi
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                default:
                    //Se l'utente ha scelto qualcosa che non è presente nel menù
                    System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                    break;
            }
        }
    }
}

//Observer
//Interfaccia che definisce il metodo che gli osservatori
//devono implementare per ricevere notifiche
interface RegistrazioneOsservatore {
    //Questo è un metodo per aggiornare la vista con un nuovo messaggio, il quale
    //stampa un messaggio; questo indica che la ricezione di una notifica è stata effettuata
    void aggiorna(String messaggio);
}

//Vista (MVC)
//Classe che implementa l'interfaccia RegistrazioneOsservatore
class VistaRegistrazione implements RegistrazioneOsservatore {
    @Override
    public void aggiorna(String messaggio) {
        //Stampa un messaggio il quale indica che la ricezione
        //di una notifica è avvenuta.
        System.out.println("Vista: " + messaggio);
    }
}

//Classe che contiene il metodo principale del programma
public class Main {
    public static void main(String[] args) {
        //Crea 2 oggetti, uno per il controller e uno per la vista
        RegistrazioneController controller = new RegistrazioneController();
        VistaRegistrazione vista = new VistaRegistrazione();
        //La vista viene aggiornata come osservatore del controller
        controller.aggiungiOsservatore(vista);

        Scanner scanner = new Scanner(System.in);
        //Implemento un boolean, che mi servirà per chiedere
        //all'utente se vuole terminare il programma o continuare a usarlo
        boolean continua = true;

        while(continua) {
            System.out.println("Benvenuto! \nPer poter accedere ad un account esistente, inserire i dati adeguati;");
            System.out.println("Per Registrarsi con un nuovo account, inserire dei dati, dopodiché accedere all'account registrato.");
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
            System.out.println("Vuoi continuare? (s/n)");
            String scelta = scanner.nextLine();
            if (scelta.equalsIgnoreCase("n")) {
                continua = false;
            }
        }
    }
}