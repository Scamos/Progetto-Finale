package Model;

import java.util.*;
import Vista.VistaRegistrazione;

//Singleton
//Design Pattern che permette di avere una sola istanza dell'oggetto
//Registrazione nell'applicazione
//Questa classe rappresenta la registrazione
public class Registrazione {
    //Attributo per accettare un parametro di tipo VistaRegistrazione
    private VistaRegistrazione vista;
    //Attributo statico che contiene l'unica istanza di Registrazione nell'applicazione
    private static Registrazione istanza;
    //Lista degli utenti registrati
    private List<UtenteRegistrato> utentiRegistrati;

    //Costruttore privato per evitare la creazione di istanze aggiuntive
    private Registrazione(VistaRegistrazione vista) {
        this.vista = vista;
        utentiRegistrati = new ArrayList<>();

        //Creazione di Utenti
        UtenteRegistrato utente1 = new UtenteRegistrato("mariorossi@gmail.com", "1234");
        //Configurazione di un utente
        utente1.setNumeroCarta("1111222233334444");
        utente1.setDataScadenza("12/26");
        utente1.setNomeProprietario("Mario");
        utente1.setCognomeProprietario("Rossi");
        utente1.setCvv("123");
        utente1.setSaldo(100.00);
        utentiRegistrati.add(utente1);

        UtenteRegistrato utente2 = new UtenteRegistrato("luigibianchi@gmail.com", "4321");
        //Configurazione di un utente
        utente2.setNumeroCarta("5555666677778888");
        utente2.setDataScadenza("11/26");
        utente2.setNomeProprietario("Luigi");
        utente2.setCognomeProprietario("Bianchi");
        utente2.setCvv("456");
        utente2.setSaldo(150.00);
        utentiRegistrati.add(utente2);

        //Creazione di Responsabili Bar o Venditori
        ResponsabileBarVenditore barman = new ResponsabileBarVenditore("barman@gmail.com", "5678");
        //Configurazione di un responsabile bar o venditore
        barman.setNumeroCarta("9999000011112222");
        barman.setDataScadenza("10/26");
        barman.setNomeProprietario("Carlo");
        barman.setCognomeProprietario("Rassi");
        barman.setCvv("789");
        barman.setSaldo(200.00);
        utentiRegistrati.add(barman);

        ResponsabileBarVenditore venditore = new ResponsabileBarVenditore("venditore@gmail.com", "8765");
        //Configurazione di un responsabile bar o venditore
        venditore.setNumeroCarta("3333444455556666");
        venditore.setDataScadenza("09/26");
        venditore.setNomeProprietario("Antonio");
        venditore.setCognomeProprietario("Bensi");
        venditore.setCvv("012");
        venditore.setSaldo(250.00);
        utentiRegistrati.add(venditore);

        //Creazione di Organizzatori
        Organizzatore organizzatrice = new Organizzatore("organizzatrice@gmail.com", "abcd");
        //Configurazione di un organizzatore
        organizzatrice.setNumeroCarta("7777888899990000");
        organizzatrice.setDataScadenza("08/26");
        organizzatrice.setNomeProprietario("Maria");
        organizzatrice.setCognomeProprietario("Rosa");
        organizzatrice.setCvv("345");
        organizzatrice.setSaldo(300.00);
        utentiRegistrati.add(organizzatrice);

        Organizzatore organizzatore = new Organizzatore("organizzatore@gmail.com", "dcba");
        //Configurazione di un organizzatore
        organizzatore.setNumeroCarta("1111333355557777");
        organizzatore.setDataScadenza("07/26");
        organizzatore.setNomeProprietario("Mauro");
        organizzatore.setCognomeProprietario("Del Mulino");
        organizzatore.setCvv("678");
        organizzatore.setSaldo(350.00);
        utentiRegistrati.add(organizzatore);
    }

    //Metodo che restituisce l'istanza esistente o ne crea una nuova se non esiste già
    public static Registrazione getInstance(VistaRegistrazione vista) {
        if (istanza == null) {
            istanza = new Registrazione(vista);
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
        //Se l'email non è presente nel "database", si potrà quindi
        //creare un nuovo utente
        return true; //L'Email non è presente nel "database"
    }

    //Metodo usato per verificare la password degli utenti, viene
    //chiamato nel caso l'email sia valida
    public void verificaPassword(String email, String password) {
        //Verifica nel "database" se l'email è uguale a quella passata dal Utente
        boolean utenteNelDB = false;
        //Esegue la ricerca nel "database"
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email)) {
                utenteNelDB = true;
                if (utente.getPassword().equals(password)) {
                    //La Password che l'Utente ha inserito è corretta,
                    //l'autenticazione è stata fatta correttamente
                    //Notifica gli osservatori del login con successo
                    vista.aggiorna("Login eseguito con successo di " + email + "\n");
                    //Chiama il metodo che mostrà il menù principale con un utente registrato
                    chiamaUtenteRegistrato(email, password);
                    return;
                }
                break; //Uscita dal ciclo perché l'utente è presente nel "DB"
            }
        }
        //Questo avviene quando l'utente non è stato trovato,
        //se succede deve mostrare un errore di autenticazione
        if (!utenteNelDB) {
            mostraErroreRegistrazione();
            //Aggiorna la vista
            vista.aggiorna("Errore: l'Utente non è stato trovato\nLog in Annullato\n");
        } else {
            //Questo avviene se l'utente è stato trovato ma la password non
            //corrisponde, dunque bisogna mostrare un errore adeguato
            System.out.println("Errore: Password incorretta");
            //Aggiorna la vista
            vista.aggiorna("Errore: Password incorretta\nLog in Annullato\n");
        }
    }

    //Metodo che viene chiamato ogni volta che è presente un errore durante la registrazione
    public void mostraErroreRegistrazione() {
        //Mostra messaggio di errore
        System.out.println("Errore: Dati inseriti incorretti");
        vista.aggiorna("Errore: Dati inseriti incorretti");
    }

    //Metodo che viene chiamato alla creazione di un nuovo utente
    public void creaNuovoUtenteRegistrato(String email, String password) {
        //Salva nel "database" il nuovo utente
        UtenteRegistrato nuovoUtente = new UtenteRegistrato(email, password);
        //Aggiunta facoltativa delle informazioni della carta di credito
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vuoi inserire le informazioni della carta di credito? (s/n)");
        String scelta = scanner.nextLine();
        if (scelta.equalsIgnoreCase("s")) {
            //Validazione del numero della carta
            boolean valid = false;
            while (!valid) {
                System.out.println("Inserisci il numero della carta di credito (16 cifre):");
                String numeroCarta = scanner.nextLine();
                //Controllo del numero di carta con regex
                if (numeroCarta.matches("\\d{16}")) {
                    //Set del numero di carta
                    nuovoUtente.setNumeroCarta(numeroCarta);
                    valid = true;
                } else {
                    //Avvisa l'utente dell'inserimento di un numero di carta non valido e lo chiede nuovamente
                    System.out.println("Numero della carta non valido.");
                }
            }

            //Validazione della data di scadenza
            valid = false;
            while (!valid) {
                System.out.println("Inserisci la data di scadenza (MM/YY):");
                String dataScadenza = scanner.nextLine();
                //Controllo della data di scadenza con regex
                if (dataScadenza.matches("^(0[1-9]|1[0-2])/([2-9][5-9]|[3-9][0-9])$")) {
                    //Set della data di scadenza
                    nuovoUtente.setDataScadenza(dataScadenza);
                    valid = true;
                } else {
                    //Avvisa l'utente dell'inserimento di una data di scadenza non valida e la chiede nuovamente
                    System.out.println("Data di scadenza non valida.");
                }
            }

            //Validazione del nome del proprietario
            valid = false;
            while (!valid) {
                System.out.println("Inserisci il nome del proprietario della carta:");
                String nomeProprietario = scanner.nextLine();
                //Controllo del nome con regex
                if (nomeProprietario.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$") && !nomeProprietario.isEmpty()) {
                    //Set del cognome
                    nuovoUtente.setNomeProprietario(nomeProprietario);
                    valid = true;
                } else {
                    //Avvisa l'utente dell'inserimento di un nome non valido e lo chiede nuovamente
                    System.out.println("Nome del proprietario non valido.");
                }
            }

            //Validazione del cognome del proprietario
            valid = false;
            while (!valid) {
                System.out.println("Inserisci il cognome del proprietario della carta:");
                String cognomeProprietario = scanner.nextLine();
                //Controllo del cognome con regex
                if (cognomeProprietario.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$") && !cognomeProprietario.isEmpty()) {
                    //Set del nome
                    nuovoUtente.setCognomeProprietario(cognomeProprietario);
                    valid = true;
                } else {
                    //Avvisa l'utente dell'inserimento di un cognome non valido e lo chiede nuovamente
                    System.out.println("Cognome del proprietario non valido.");
                }
            }

            //Validazione del CVV
            valid = false;
            while (!valid) {
                System.out.println("Inserisci il CVV della carta (3 cifre):");
                String cvv = scanner.nextLine();
                //Controllo del CVV con regex
                if (cvv.matches("\\d{3}")) {
                    //Set del CVV
                    nuovoUtente.setCvv(cvv);
                    valid = true;
                } else {
                    //Avvisa l'utente dell'inserimento di un CVV non valido e lo chiede nuovamente
                    System.out.println("CVV non valido.");
                }
            }

            //Validazione del saldo
            valid = false;
            while (!valid) {
                System.out.println("Inserisci il saldo iniziale della carta:");
                String saldoInput = scanner.nextLine();
                //Controllo del saldo con regex
                if (saldoInput.matches("^\\d+\\.\\d{2}$")) {
                    try {
                        double saldo = Double.parseDouble(saldoInput);
                        if (saldo >= 0) {
                            //Set del saldo
                            nuovoUtente.setSaldo(saldo);
                            valid = true;
                        } else {
                            //Avvisa l'utente dell'inserimento di un saldo negativo e lo chiede nuovamente
                            System.out.println("Saldo non valido. Deve essere maggiore o uguale a zero.");
                        }
                    } catch (NumberFormatException e) {
                        //Avvisa l'utente dell'inserimento di un input non numerico e lo chiede nuovamente
                        System.out.println("Saldo non valido.");
                    }
                } else {
                    //Avvisa l'utente dell'inserimento di un saldo non valido e lo chiede nuovamente
                    System.out.println("Saldo non valido. Deve essere un numero con due cifre decimali.");
                }
            }
        }

        //Inserisci nella lista degli utenti il nuovo utente
        utentiRegistrati.add(nuovoUtente);
        System.out.println("Nuovo utente registrato con successo!");

        //Chiama la funzione per eseguire il log in del nuovo utente
        chiamaUtenteRegistrato(email, password);
    }

    //Metodo che viene chiamato alla registrazione di un utente, sia se già
    //presente sia se è appena stato creato
    public void chiamaUtenteRegistrato(String email, String password) {
        //Accesso all'utente registrato
        System.out.println("Utente autenticato: " + email);
        System.out.println("Benvenuto, " + email + "!");

        Scanner scanner = new Scanner(System.in);
        int scelta = 0;

        //Prende l'Utente corrispondente nell'elenco degli utenti registrati
        UtenteRegistrato utenteRegistrato = null;
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email)) {
                //Setta utenteRegistrato dopo averlo trovato
                utenteRegistrato = (UtenteRegistrato) utente;
                break;
            }
        }
        //Verifica se l'utente è un ResponsabileBarVenditore
        boolean isResponsabileBarVenditore = false;
        //Prende il ResponsabileBarVenditore corrispondente nell'elenco degli utenti registrati
        ResponsabileBarVenditore responsabileBarVenditore = null;
        //Verifica se l'utente è un Organizzatore
        boolean isOrganizzatore = false;
        //Prende l'Organizzatore corrispondente nell'elenco degli utenti registrati
        Organizzatore organizzatore = null;
        //Passaggio di verifica per ResponsabileBarVenditore
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email) && utente instanceof ResponsabileBarVenditore) {
                //Setta responsabileBarVenditore dopo averlo trovato, se lo trova
                responsabileBarVenditore = (ResponsabileBarVenditore) utente;
                //Imposta a vero se trova il ResponsabileBarVenditore
                isResponsabileBarVenditore = true;
                break;
            }
        }
        //Passaggio di verifica per Organizzatore
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email) && utente instanceof Organizzatore) {
                //Setta organizzatore dopo averlo trovato, se lo trova
                organizzatore = (Organizzatore) utente;
                //Imposta a vero se trova l'Organizzatore
                isOrganizzatore = true;
            }
        }

        //Menù di scelta di un utente autenticato
        while (scelta != 8) {
            System.out.println("Utente autenticato: " + email);
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

            //In ogni caso, questa scelta sarà presente e sarà
            //in fondo al menù
            System.out.println("8) Logout");

            //Permette di leggere la scelta dell'utente
            scelta = scanner.nextInt();

            //Esegui l'azione che l'utente ha scelto
            switch (scelta) {
                case 1:
                    utenteRegistrato.visualizzaEventi();
                    break;
                case 2:
                    utenteRegistrato.visualizzaLotteria();
                    break;
                case 3:
                    utenteRegistrato.partecipaLotteria();
                    break;
                case 4:
                    utenteRegistrato.visualizzaProdotti();
                    break;
                case 5:
                    //Acquisto prodotti
                    utenteRegistrato.ricercaProdotti();
                    break;
                case 6:
                    utenteRegistrato.visualizzaLibreriaPersonale();
                    break;
                case 7:
                    utenteRegistrato.gestioneLibreriaPersonale();
                    break;
                case 8:
                    //Esegue il logout
                    System.out.println("Logout effettuato.");
                    vista.aggiorna("Logout effettuato con successo per l'utente: " + email);
                    //Ritorna alla registrazione
                    break;

                //Le scelte, da questo caso in poi, hanno il controllo per verificare se sono metodi
                //possibili da chiamare (da ResponsabileBarVenditore o classi che la estendono)
                case 9:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        responsabileBarVenditore.visualizzaOrdiniClienti();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 10:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Gestione degli ordini dei clienti
                        responsabileBarVenditore.ricercaOrdiniClienti();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 11:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        //Gestione degli ordini con prodotti da portare via
                        responsabileBarVenditore.ricercaOrdiniPerProdottiPortareVia();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 12:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        responsabileBarVenditore.visualizzaMagazzino();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 13:
                    if (isResponsabileBarVenditore || isOrganizzatore) {
                        responsabileBarVenditore.gestioneMagazzino();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }

                    //Le scelte, da questo caso in poi, hanno il controllo per verificare se sono metodi
                    //possibili da chiamare (da Organizzatore)
                case 14:
                    if (isOrganizzatore) {
                        organizzatore.creazioneLotteria();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 15:
                    if (isOrganizzatore) {
                        organizzatore.gestioneLotteria();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 16:
                    if (isOrganizzatore) {
                        organizzatore.creazioneEventi();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 17:
                    if (isOrganizzatore) {
                        organizzatore.gestioneEventi();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                case 18:
                    if (isOrganizzatore) {
                        organizzatore.visualizzaPartecipazioneEventi();
                        break;
                    } else {
                        System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                        break;
                    }
                default:
                    //Se l'utente ha scelto qualcosa che non è presente nel menù
                    //a lui disponibile, viene avvisato e si rimane nel loop
                    System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
                    break;
            }
        }
    }

    public List<UtenteRegistrato> getUtentiRegistrati() {
        return utentiRegistrati;
    }
}
