package Model;

import java.util.*;

import Controller.EventoController;
import Controller.ProdottoController;
import Vista.VistaEvento;
import Vista.VistaProdotto;

//Modello (MVC)
//Classe che rappresenta un Utente Registrato
public class UtenteRegistrato {
    //Attributi privati dell'utente, che rappresentano le informazioni di base
    private String email;
    private String password;
    private String numeroCarta;
    private String dataScadenza;
    private String nomeProprietario;
    private String cognomeProprietario;
    private String cvv;
    private double saldo;

    //Riferimento al controller degli eventi associato a questo utente
    public EventoController eventoController;

    //Istanza singleton della vista associata agli eventi (VistaEvento)
    private static VistaEvento vistaEventoInstance;

    //Libreria personale dell'utente contenente eventi
    private List<Evento> libreriaPersonale;

    //Liste per tenere traccia degli ordini attivi e completati
    private List<Ordine> ordini; //Ordini attivi
    private List<Ordine> ordiniCompletati; //Ordini completati

    //Lista condivisa per tenere traccia di tutti gli ordini (statico)
    private static List<Ordine> tuttiGliOrdini = new ArrayList<>();

    //Costruttore che ha il compito di creare un nuovo Utente
    //con i suoi attributi che verranno ereditati dalle classi
    //che estendono UtenteRegistrato
    public UtenteRegistrato(String email, String password) {
        this.email = email;
        this.password = password;

        //Inizializza il controller degli eventi utilizzando il pattern singleton
        this.eventoController = EventoController.getInstance();

        //Inizializza la libreria personale come una lista vuota
        this.libreriaPersonale = new ArrayList<>();

        //Creazione dell'istanza di VistaEvento solo se non è già stata creata (singleton)
        vistaEventoInstance = VistaEvento.getInstance();

        //Inizializza le liste degli ordini attivi e completati
        this.ordini = new ArrayList<>();
        this.ordiniCompletati = new ArrayList<>();
    }

    //Metodo per ottenere l'istanza singleton di VistaEvento
    public static VistaEvento getVistaEventoInstance() {
        return VistaEvento.getInstance();
    }

    //Aggiunge un ordine alla lista degli ordini attivi
    public void aggiungiOrdine(Ordine ordine) {
        ordini.add(ordine);
    }

    //Ottiene la lista degli ordini attivi
    public List<Ordine> getOrdini() {
        return ordini;
    }

    //Ottiene la lista degli ordini completati
    public List<Ordine> getOrdiniCompletati() {
        return ordiniCompletati;
    }

    //Aggiunge un ordine alla lista condivisa, che serve a tenere traccia di tutti gli ordini (statico)
    public static void aggiungiOrdineGlobal(Ordine ordine) {
        tuttiGliOrdini.add(ordine);
    }

    //Ottiene la lista condivisa degli ordini
    public static List<Ordine> getTuttiGliOrdini() {
        return tuttiGliOrdini;
    }

    //Rimuove un ordine alla lista condivisa
    public static void rimuoviOrdineGlobal(Ordine ordine) {
        tuttiGliOrdini.remove(ordine);
    }

    //Rimuove un ordine dalla lista degli ordini attivi per inserirlo in quella degli ordini completati
    public void spostaOrdineCompletato(Ordine ordine) {
        ordini.remove(ordine);
        ordiniCompletati.add(ordine);
    }

    //Metodo usato durante acquistoProdotti: controlla se l'input che legge è un numero intero,
    //se non lo è fa reinserire l'input
    private int leggiIntero(Scanner scanner, String messaggio) {
        while (true) {
            System.out.println(messaggio);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Per favore inserisci un numero intero.");
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione degli eventi
    public void visualizzaEventi() {
        //Ottiene la lista degli Eventi
        List<Evento> eventi = eventoController.getListaEventi();

        //Ottiene l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();

        //Controlla se la lista è vuota
        if (eventi.isEmpty()) {
            //Avvisa l'utente e torna al menù principale
            System.out.println("Non ci sono eventi disponibili al momento");
            vistaEvento.aggiorna("Tentato accesso ad una lista vuota di eventi");
        } else {
            //Se non è vuota, stampa tutti gli eventi
            System.out.println("Eventi disponibili:");
            for (Evento evento : eventi) {
                System.out.println(evento);
            }
            vistaEvento.aggiorna("Lista di eventi visualizzata con successo");
            System.out.println("Vuoi ricercare un evento specifico? (s/n)");
            System.out.println("Si specifica che ricercando un evento, si può decidere di partecipare ad esso");
            Scanner scanner = new Scanner(System.in);
            String scelta = scanner.nextLine();
            //Inizialmente, considera ciò che l'Utente inserisce
            //come non valido
            boolean input = false;
            //Verifica la risposta del Utente
            while (!input) {
                switch (scelta.toLowerCase()) {
                    case "s":
                        vistaEvento.aggiorna("Passaggio alla ricerca di un evento");
                        input = true; //L'Utente ha inserito un input valido
                        //perciò si sposta in ricercaEventi
                        //Chiamata al metodo ricercaEventi
                        ricercaEventi();
                        break;
                    case "n":
                        input = true; //L'Utente ha inserito un input valido
                        System.out.println("Ricerca non eseguita");
                        vistaEvento.aggiorna("Ricerca non eseguita");
                        //Serve per uscire dal metodo visualizzaEventi e
                        //tornare al menù principale
                        break; //Esce dal metodo senza andare in ricercaEventi
                    default:
                        //Risposta non valida
                        System.out.println("Scelta non valida\nInserisci un valore valido (s/n)");
                        scelta = scanner.nextLine(); //L'Utente deve inserire un nuovo valore
                        break;
                }
            }
            //Controllo se l'accesso è da parte di
            //un Organizzatore
            if (this instanceof Organizzatore) {
                //Chiede al Organizzatore se vuole passare alla gestioneEventi
                System.out.println("Vuoi passare alla gestione degli eventi? (s/n)");
                String risposta = scanner.nextLine();

                //Inizialmente, considera ciò che l'Organizzatore inserisce
                //come non valido
                boolean inputValido = false;
                //Verifica la risposta del Organizzatore
                while (!inputValido) {
                    switch (risposta.toLowerCase()) {
                        case "s":
                            vistaEvento.aggiorna("Valido accesso alla gestione degli eventi");
                            inputValido = true; //L'Organizzatore ha inserito un input valido
                            //perciò si sposta in gestioneEventi
                            //Chiamata al metodo gestioneEventi tramite cast a Organizzatore
                            ((Organizzatore) this).gestioneEventi();
                            break;
                        case "n":
                            //Siccome in questo punto uscirà completamente dal metodo, non serve
                            //aggiornare inputValido
                            System.out.println("Ritorno al menù principale");
                            vistaEvento.aggiorna("Ritorno al menù principale");
                            //Serve per uscire dal metodo visualizzaEventi e
                            //Tornare al menù principale
                            return; //Esce dal metodo senza andare in gestioneEventi
                        //e torna al menù principale
                        default:
                            //Risposta non valida
                            System.out.println("Scelta non valida\nInserisci un valore valido (s/n)");
                            risposta = scanner.nextLine(); //L'Organizzatore deve inserire un nuovo valore
                            break;
                    }
                }
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi
    //che la estendono per la ricerca di Eventi
    public void ricercaEventi() {
        //Ottiene l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome dell'evento da cercare:");
        String nomeEvento = scanner.nextLine();

        //Utilizza il metodo trovaEvento, e lo eguaglia a evento di tipo Evento
        Evento evento = eventoController.trovaEvento(nomeEvento);
        //Controlla se ha trovato un evento
        if (evento != null) {
            System.out.println(evento);
            vistaEvento.aggiorna("Ricerca eseguita con successo");
            System.out.print("Vuoi partecipare a questo evento? (s/n): ");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("s")) {
                //Chiama il metodo per incrementare i partecipanti dell'evento e
                //lo aggiunge alla libreria personale con il metodo apposito
                evento.incrementaPartecipanti();
                this.aggiungiEventoALibreria(evento);
                eventoController.notificaOsservatori("Numero di partecipanti aggiornato per l'evento: " + evento.getNome() + " - Nuovo numero: " + evento.getNumPartecipanti());
                System.out.println("Partecipazione inserita con successo.");
                System.out.println();
            } else if (risposta.equalsIgnoreCase("n")){
                //Avvisa l'utente della scelta di non partecipare e torna al menù principale
                System.out.println("Partecipazione non inserita.");
                vistaEvento.aggiorna("Numero di partecipanti invariato");
                System.out.println();
            } else {
                //Avvisa l'utente della scelta non valida e torna al menù principale
                System.out.println("Scelta Invalida. Partecipazione non inserita.");
                vistaEvento.aggiorna("Numero di partecipanti invariato");
                System.out.println();
            }
        } else {
            //Avvisa l'utente del evento non trovato ed esce dal metodo
            System.out.println("Errore: Evento non trovato");
            vistaEvento.aggiorna("Errore durante la ricerca di un evento");
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione della lotteria
    public void visualizzaLotteria() {
        //Possibile implementazione della visualizzazione della lotteria
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la partecipazione alla lotteria
    public void partecipaLotteria() {
        //Possibile implementazione della partecipazione alla lotteria
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione dei prodotti
    public void visualizzaProdotti() {
        //Ottiene le istanze della vista e del controller di prodotto
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        ProdottoController prodottoController = ProdottoController.getInstance();
        //Ottiene la lista dei prodotti
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        //Controlla se la lista di prodotti è vuota
        if (prodotti.isEmpty()) {
            //Avvisa l'utente e torna a menù principale
            System.out.println("Non ci sono prodotti disponibili al momento");
            vistaProdotto.aggiorna("Tentato accesso alla lista vuota di prodotti");
        } else {
            Scanner scanner = new Scanner(System.in);
            //Stampa i prodotti disponibili e chiede se si vuole passare alla ricerca dei prodotti
            System.out.println("Elenco dei prodotti:");
            for (int i = 0; i < prodotti.size(); i++) {
                System.out.println((i + 1) + ") " + prodotti.get(i).getNome() + " - Quantità: " + prodotti.get(i).getQuantita() + " - Prezzo: " + prodotti.get(i).getPrezzo());
            }
            vistaProdotto.aggiorna("Accesso effettuato con successo alla lista di prodotti");

            System.out.print("Vuoi passare alla ricerca di un prodotto? (s/n): ");
            String rispostaRicerca = scanner.nextLine();
            boolean valido = false;
            while (!valido){
                switch (rispostaRicerca.toLowerCase()){
                    case "s":
                        //Avvisa l'utente del passaggio di metodo e chiama la ricerca dei prodotti
                        System.out.println("Passaggio alla ricerca dei prodotti");
                        vistaProdotto.aggiorna("Passaggio alla ricerca dei prodotti");
                        ricercaProdotti();
                        valido = true;
                        break;
                    case "n":
                        //Avvisa l'utente della ricerca non eseguita e torna al menù principale
                        System.out.println("Ricerca non eseguita");
                        vistaProdotto.aggiorna("Ricerca non eseguita");
                        valido = true;
                        break;
                    default:
                        //Avvisa l'utente della scelta non valida e chiede nuovamente di fare una scelta
                        System.out.println("Scelta non valida\nInserisci un valore valido (s/n): ");
                        rispostaRicerca = scanner.nextLine();
                        break;
                }
            }

            //Controlla se l'utente registrato è un istanza di ResponsabileBarVenditore
            //(valido anche per le classi che la estendono)
            if (this instanceof ResponsabileBarVenditore) {
                System.out.print("Vuoi passare alla gestione del magazzino? (s/n): ");
                String rispostaGestione = scanner.nextLine();
                boolean scrittaValida = false;
                while (!scrittaValida){
                    switch (rispostaGestione.toLowerCase()){
                        case "s":
                            //Chiama il metodo di gestione del magazzino tramite cast a ResponsabileBarVenditore
                            //(valido anche per le classi che la estendono)
                            System.out.println("Passaggio alla gestione del magazzino");
                            vistaProdotto.aggiorna("Passaggio alla gestione del magazzino");
                            ((ResponsabileBarVenditore) this).gestioneMagazzino();
                            scrittaValida = true;
                            break;
                        case "n":
                            //Avvisa l'utente del ritorno al menù principale
                            System.out.println("Ritorno al menù principale");
                            vistaProdotto.aggiorna("Ritorno al menù principale");
                            return;
                        default:
                            //Avvisa l'utente della scelta non valida e chiede nuovamente di fare una scelta
                            System.out.println("Scelta non valida\nInserisci un valore valido (s/n): ");
                            rispostaGestione = scanner.nextLine();
                            break;
                    }
                }
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi
    //che la estendono per la ricerca dei prodotti
    public void ricercaProdotti() {
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean continuaRicerca = true;

        //Se torna al menù è true, esce dal metodo
        if (VistaProdotto.getTornaAlMenu()) {
            continuaRicerca = false;
        }
        while (continuaRicerca) {
            //Controlla il flag per tornare al menù principale
            System.out.println("Inserisci il nome del prodotto:");
            String nomeProdotto = scanner.nextLine();
            //Prende l'istanza del controller di prodotto e chiama il metodo per trovare un prodotto
            Prodotto prodotto = ProdottoController.getInstance().trovaProdotto(nomeProdotto);
            //Controlla che non sia null
            if (prodotto != null) {
                System.out.println(prodotto);
                vistaProdotto.aggiorna("Ricerca eseguita con successo");
                System.out.print("Vuoi acquistare questo prodotto? (s/n): ");
                String risposta = scanner.nextLine();
                //Controlla l'input dell'utente
                if (risposta.equalsIgnoreCase("s")) {
                    //Controlla se la quantità del prodotto cercato è superiore a zero
                    if (prodotto.getQuantita() > 0) {
                        //Chiama il metodo per acquistare i prodotti
                        acquistoProdotti(prodotto);
                    } else {
                        //Avvisa l'utente della quantità = 0 e controlla cosa inserisce l'utente in gestisciErrore:
                        //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                        if (!gestisciErrore(scanner, vistaProdotto, "La quantità disponibile del prodotto è zero. Vuoi cercare un altro prodotto?")) {
                            vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                            continuaRicerca = false;
                        }
                    }

                    //Caso in cui l'utente risponde "n" all'acquisto del prodotto
                } else if (risposta.equalsIgnoreCase("n")) {
                    System.out.print("Vuoi terminare la ricerca senza acquistare? (s/n): ");
                    risposta = scanner.nextLine();
                    //Controlla se l'utente vuole terminare il metodo senza passare all'acquisto
                    if (risposta.equalsIgnoreCase("s")) {
                        System.out.println("Ritorno al menù principale senza fare acquisti.");
                        vistaProdotto.aggiorna("Acquisto annullato. Ritorno al menù principale.");
                        continuaRicerca = false;
                    }
                }
            } else {
                //Avvisa l'utente della ricerca che ha dato risultato null e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                if (!gestisciErrore(scanner, vistaProdotto, "Errore durante la ricerca di un prodotto.")) {
                    continuaRicerca = false;
                }
            }

            //Se torna al menù è true, esce dal metodo
            if (VistaProdotto.getTornaAlMenu()) {
                continuaRicerca = false;
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per l'acquisto dei prodotti
    public void acquistoProdotti(Prodotto prodottoIniziale) {
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        Scanner scanner = new Scanner(System.in);
        double costoParziale = 0.00; //Inizializza il costo dell'acquisto
        List<Prodotto> prodottiAcquistati = new ArrayList<>(); //Lista dei prodotti acquistati
        List<Integer> quantitaAcquistate = new ArrayList<>(); //Lista delle quantità di prodotti acquistate
        List<Boolean> portareViaList = new ArrayList<>(); //Lista dello stato "portare via" dei prodotti
        List<Integer> quantitaOriginali = new ArrayList<>(); //Lista per memorizzare le quantità originali dei prodotti
        Set<Prodotto> prodottiAggiornati = new HashSet<>(); //Set per memorizzare i prodotti aggiornati
        //Controlla se l'utente ha già inserito i dati della carta di credito
        if (this.numeroCarta == null || this.numeroCarta.isEmpty() ||
                this.dataScadenza == null || this.dataScadenza.isEmpty() ||
                this.nomeProprietario == null || this.nomeProprietario.isEmpty() ||
                this.cognomeProprietario == null || this.cognomeProprietario.isEmpty() ||
                this.cvv == null || this.cvv.isEmpty()) {

            System.out.println("Non hai inserito le informazioni della carta di credito.");
            System.out.println("Vuoi inserire i tuoi dati della carta di credito? (s/n)");
            String scelta = scanner.nextLine();

            //Controlla se la scelta è valida e continua a chiederlo se non lo è
            while (!scelta.equalsIgnoreCase("s") && !scelta.equalsIgnoreCase("n")) {
                System.out.println("Scelta non valida. Vuoi inserire i tuoi dati della carta di credito? (s/n)");
                scelta = scanner.nextLine();
            }

            if (scelta.equalsIgnoreCase("n")) {
                //Se l'utente decide di non inserire i dati della sua carta,
                //Non si procede con l'acquisto e si esce dal metodo
                System.out.println("Acquisto annullato.");
                vistaProdotto.aggiorna("Acquisto non effettuato: informazioni carta non presenti.");
                return;

                //Se l'utente decide di inserire i dati della sua carta
                //che non aveva inserito durante la registrazione,
                //esegue tutto il procedimento di inserimento dati
                //e poi prosegue con l'acquisto
            } else if (scelta.equalsIgnoreCase("s")) {
                //Validazione del numero della carta
                boolean valid = false;
                while (!valid) {
                    System.out.println("Inserisci il numero della carta di credito (16 cifre):");
                    this.numeroCarta = scanner.nextLine();
                    //Controllo del numero della carta con regex
                    if (this.numeroCarta.matches("\\d{16}")) {
                        //Set del numero della carta
                        this.setNumeroCarta(numeroCarta);
                        valid = true;
                    } else {
                        //Avvisa l'utente di un numero della carta non valido
                        System.out.println("Numero della carta non valido.");
                    }
                }

                //Validazione della data di scadenza
                valid = false;
                while (!valid) {
                    System.out.println("Inserisci la data di scadenza (MM/YY):");
                    this.dataScadenza = scanner.nextLine();
                    //Controllo della data con regex
                    if (this.dataScadenza.matches("^(0[1-9]|1[0-2])/([2-9][5-9]|[3-9][0-9])$")) {
                        //Set della data di scadenza
                        this.setDataScadenza(dataScadenza);
                        valid = true;
                    } else {
                        //Avvisa l'utente di una data di scadenza non valida
                        System.out.println("Data di scadenza non valida.");
                    }
                }

                //Validazione del nome del proprietario
                valid = false;
                while (!valid) {
                    System.out.println("Inserisci il nome del proprietario della carta:");
                    this.nomeProprietario = scanner.nextLine();
                    //Controllo del nome con regex
                    if (this.nomeProprietario.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$")) {
                        //Set del nome
                        this.setNomeProprietario(nomeProprietario);
                        valid = true;
                    } else {
                        //Avvisa l'utente di un nome non valido
                        System.out.println("Nome del proprietario non valido.");
                    }
                }

                //Validazione del cognome del proprietario
                valid = false;
                while (!valid) {
                    System.out.println("Inserisci il cognome del proprietario della carta:");
                    this.cognomeProprietario = scanner.nextLine();
                    //Controllo del cognome con regex
                    if (this.cognomeProprietario.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$")) {
                        //Set del cognome
                        this.setCognomeProprietario(cognomeProprietario);
                        valid = true;
                    } else {
                        //Avvisa l'utente di un cognome non valido
                        System.out.println("Cognome del proprietario non valido.");
                    }
                }

                //Validazione del CVV
                valid = false;
                while (!valid) {
                    System.out.println("Inserisci il CVV della carta (3 cifre):");
                    this.cvv = scanner.nextLine();
                    //Controllo del CVV con regex
                    if (this.cvv.matches("\\d{3}")) {
                        //Set del CVV
                        this.setCvv(cvv);
                        valid = true;
                    } else {
                        //Avvisa l'utente di un CVV non valido
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
                                this.setSaldo(saldo);
                                valid = true;
                            } else {
                                //Avvisa l'utente di un saldo negativo
                                System.out.println("Saldo non valido. Deve essere maggiore o uguale a zero.");
                            }
                        } catch (NumberFormatException e) {
                            //Avvisa l'utente quando l'input non è numerico
                            System.out.println("Saldo non valido.");
                        }
                    } else {
                        //Avvisa l'utente quando l'input non è inserito correttamente
                        System.out.println("Saldo non valido. Deve essere un numero con due cifre decimali.");
                    }
                }
            }
        }

        //Boolean per il controllo sul continuo dell'acquisto
        boolean continuare = true;
        //Rimane nel loop per acquistare finché l'utente non decide di uscire
        //sia nel caso di annullamento acquisto, sia in quello di acquisto
        while (true) {
            Prodotto prodotto = prodottoIniziale != null ? prodottoIniziale : ricercaAltroProdotto(scanner);
            if (prodotto == null) {
                //Avvisa l'utente del prodotto non trovato o non disponibile e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Prodotto non trovato o non disponibile. Acquisto annullato.");
                if (!continuare) {
                    //Prima di uscire dal metodo, ripristina le quantità originali del prodotto in magazzino
                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                    }
                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                    return;
                } else {
                    //Se l'utente vuole continuare, rimane nel loop
                    continue;
                }
            }

            //Chiede all'utente di inserire una quantità da acquistare del prodotto
            System.out.print("Quanta quantità vuoi acquistare di " + prodotto.getNome() + "? ");
            int quantita = scanner.nextInt();
            scanner.nextLine();

            //Controlla se la quantità è minore o uguale a zero
            if (quantita <= 0) {
                //Avvisa l'utente della quantità <= 0 e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Quantità richiesta non valida. Deve essere maggiore di zero. Acquisto non effettuato.");
                if (!continuare) {
                    //Prima di uscire dal metodo, ripristina le quantità originali dei prodotti nel magazzino
                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                    }
                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                    return;
                } else {
                    //Se l'utente vuole continuare, rimane nel loop
                    continue;
                }

                //Controlla se la quantità è superiore a quella disponibile in magazzino
            } else if (quantita > prodotto.getQuantita()) {
                //Avvisa l'utente della quantità superiore a quella disponibile e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Quantità richiesta superiore alla quantità disponibile. Acquisto non effettuato.");
                if (!continuare) {
                    //Prima di uscire dal metodo, ripristina le quantità originali dei prodotti nel magazzino
                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                    }
                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                    return;
                } else {
                    //Se l'utente vuole continuare, rimane nel loop
                    continue;
                }
            }

            //Memorizza la quantità originale del prodotto
            quantitaOriginali.add(prodotto.getQuantita());
            boolean portareVia = false;
            //Richiede all'utente di inserire lo stato "portare via"
            System.out.print("Il prodotto è da portare via? (s/n): ");
            String rispostaPortareVia = scanner.nextLine();
            //Controlla se la risposta è valida e continua a chiederla finché non lo è
            while (!rispostaPortareVia.equalsIgnoreCase("s") && !rispostaPortareVia.equalsIgnoreCase("n")) {
                System.out.print("Risposta non valida. Il prodotto è da portare via? (s/n): ");
                rispostaPortareVia = scanner.nextLine();
            }
            if (rispostaPortareVia.equalsIgnoreCase("s")) {
                portareVia = true;
            }
            //Setta lo stato di "portare via"
            portareViaList.add(portareVia);

            //Aggiorna il costo dell'ordine e inserisci il prodotto con le sue caratteristiche all'oridne
            double costoProdotto = prodotto.getPrezzo() * quantita;
            costoParziale += costoProdotto;
            prodottiAcquistati.add(prodotto);
            quantitaAcquistate.add(quantita);
            //Aggiorna temporaneamente la quantità del prodotto
            prodotto.decrementaQuantita(quantita);
            prodottiAggiornati.add(prodotto); //Aggiungi il prodotto al set

            System.out.println("Prodotto aggiunto all'ordine: " + prodotto.getNome() + " - Quantità: " + quantita + " - Portare via: " + portareVia);
            System.out.print("Vuoi acquistare un altro prodotto? (s/n): ");
            String risposta = scanner.nextLine();
            boolean rispostaValida = false;
            while (!rispostaValida) {
                if (risposta.equalsIgnoreCase("n")) {
                    rispostaValida = true;
                } else if (risposta.equalsIgnoreCase("s")) {
                    //Verifica se ci sono ancora prodotti acquistabili
                    boolean prodottiDisponibili = false;
                    for (Prodotto p : ProdottoController.getInstance().getListaProdotti()) {
                        if (p.getQuantita() > 0) {
                            prodottiDisponibili = true;
                            break;
                        }
                    }
                    if (!prodottiDisponibili) {
                        //Avvisa l'utente della mancanza di prodotti da acquistare e passa all'acquisto dell'ordine attuale
                        System.out.println("Non ci sono altri prodotti acquistabili.");
                        //Esce quindi dal loop di aggiunta prodotti all'ordine
                        break;
                    }
                    //Resetta prodotto iniziale e rimane nel loop di acquisto, uscendo
                    //dal loop di controllo di richiesta acquisto altro prodotto
                    prodottoIniziale = null;
                    rispostaValida = true;
                } else {
                    //Avvisa l'utente della risposta non valida e chiede di inserire una nuova risposta
                    System.out.print("Risposta non valida. Vuoi acquistare un altro prodotto? (s/n): ");
                    risposta = scanner.nextLine();
                }
            }

            //Se l'utente non vuole più acquistare altri prodotti, esci dal loop
            if (risposta.equalsIgnoreCase("n")) {
                break;
            }
        }

        //Mostra il costo totale dell'ordine
        System.out.println("Costo totale: " + String.format("%.2f", costoParziale));

        //Visualizza l'ordine con tutti i dettagli per ogni prodotto
        while (true) {
            System.out.println("Ecco i tuoi prodotti nell'ordine:");
            for (int i = 0; i < prodottiAcquistati.size(); i++) {
                String dettaglioProdotto = (i + 1) + ") " + prodottiAcquistati.get(i).getNome() + " - Quantità: " + quantitaAcquistate.get(i);
                if (portareViaList.get(i)) {
                    dettaglioProdotto += " (da portare via)";
                }
                System.out.println(dettaglioProdotto);
            }

            //Mostra il costo dell'ordine e chiede all'utente se vuole modificarlo
            System.out.println("Costo totale: " + String.format("%.2f", costoParziale));
            System.out.print("Vuoi modificare l'ordine? (s/n): ");
            String rispostaModifica = scanner.nextLine();

            //Se l'utente non vuole modificare più nulla dal suo ordine,
            //passa direttamente al controllo del saldo
            if (rispostaModifica.equalsIgnoreCase("n")) {
                break;

                //Se l'utente vuole modificare qualcosa del suo ordine, mostra un piccolo menù di modifica ed
                //Esegue la modifica richiesta
            } else if (rispostaModifica.equalsIgnoreCase("s")) {
                System.out.print("1) Aggiungi prodotto\n2) Modifica la quantità di un prodotto\n3) Modifica stato da portare via o no\n4) Togli prodotto\n");
                int sceltaModifica = leggiIntero(scanner, "Inserisci la tua scelta: ");
                switch (sceltaModifica) {
                    case 1: //Aggiungi un nuovo prodotto all'ordine
                        //Chiama il metodo per ricercare un nuovo prodotto
                        Prodotto nuovoProdotto = ricercaAltroProdotto(scanner);
                        //Controlla che il prodotto passato non sia null
                        if (nuovoProdotto != null) {
                            //Chiede la quantità da acquistare del nuovo prodotto
                            System.out.print("Quanta quantità vuoi acquistare di " + nuovoProdotto.getNome() + "? ");
                            int nuovaQuantita = scanner.nextInt();
                            scanner.nextLine();
                            //Controlla che la quantità inserita in input sia in range
                            if (nuovaQuantita > 0 && nuovaQuantita <= nuovoProdotto.getQuantita()) {
                                boolean nuovoPortareVia = false;
                                //Chiede lo stato di "portare via" del nuovo prodotto
                                System.out.print("Il prodotto è da portare via? (s/n): ");
                                String nuovaRispostaPortareVia = scanner.nextLine();
                                //Controlla che l'input sia valido e continua a chiederlo in caso negativo
                                while (!nuovaRispostaPortareVia.equalsIgnoreCase("s") && !nuovaRispostaPortareVia.equalsIgnoreCase("n")) {
                                    System.out.print("Risposta non valida. Il prodotto è da portare via? (s/n): ");
                                    nuovaRispostaPortareVia = scanner.nextLine();
                                }
                                if (nuovaRispostaPortareVia.equalsIgnoreCase("s")) {
                                    nuovoPortareVia = true;
                                }
                                portareViaList.add(nuovoPortareVia);
                                double nuovoCostoProdotto = nuovoProdotto.getPrezzo() * nuovaQuantita;
                                costoParziale += nuovoCostoProdotto;
                                prodottiAcquistati.add(nuovoProdotto);
                                quantitaAcquistate.add(nuovaQuantita);
                                nuovoProdotto.decrementaQuantita(nuovaQuantita);
                                prodottiAggiornati.add(nuovoProdotto);
                                System.out.println("Prodotto aggiunto all'ordine: " + nuovoProdotto.getNome() + " - Quantità: " + nuovaQuantita + (nuovoPortareVia ? " (da portare via)" : ""));
                            } else {
                                //Avvisa l'utente della quantità non valida e controlla cosa inserisce l'utente in gestisciErrore:
                                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Quantità richiesta non valida.");
                                if (!continuare) {
                                    //Prima di uscire dal metodo, ripristina la quantità di prodotti nel magazzino
                                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                                    }
                                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                                    return;
                                } else {
                                    //Se l'utente vuole continuare, rimane nel loop
                                    continue;
                                }
                            }
                        } else {
                            //Avvisa l'utente del prodotto non trovato e controlla cosa inserisce l'utente in gestisciErrore:
                            //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                            continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Prodotto non trovato.");
                            if (!continuare) {
                                //Prima di uscire dal metodo, ripristina la quantità dei prodotti in magazzino
                                for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                    prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                                }
                                vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                                return;
                            } else {
                                //Se l'utente vuole continuare, rimane nel loop
                                continue;
                            }
                        }
                        break;
                    case 2: //Modifica la quantità di un prodotto nell'ordine
                        //Chiama il metodo leggi intero poiché c'è bisogno di un indice definito
                        int indiceProdotto = leggiIntero(scanner, "Inserisci il numero del prodotto di cui vuoi modificare la quantità: ") - 1;
                        //Controlla che l'indice inserito in input sia in range
                        if (indiceProdotto >= 0 && indiceProdotto < prodottiAcquistati.size()) {
                            //Seleziona il prodotto in base all'indice inserito in input
                            Prodotto prodottoDaModificare = prodottiAcquistati.get(indiceProdotto);
                            int vecchiaQuantita = quantitaAcquistate.get(indiceProdotto);
                            //Ripristina la quantità del prodotto in magazzino prima di chiedere la nuova quantità richiesta
                            prodottoDaModificare.incrementaQuantita(vecchiaQuantita);
                            System.out.print("Inserisci la nuova quantità per " + prodottoDaModificare.getNome() + ": ");
                            int nuovaQuantita = scanner.nextInt();
                            scanner.nextLine();
                            //Controlla che la nuova quantità sia in range
                            if (nuovaQuantita > 0 && nuovaQuantita <= prodottoDaModificare.getQuantita()) {
                                //Setta la nuova quantità e il nuovo costo di conseguenza
                                quantitaAcquistate.set(indiceProdotto, nuovaQuantita);
                                prodottoDaModificare.decrementaQuantita(nuovaQuantita);
                                double differenzaCosto = prodottoDaModificare.getPrezzo() * (nuovaQuantita - vecchiaQuantita);
                                costoParziale += differenzaCosto;
                            } else {
                                //Avvisa l'utente della quantità non valida e ripristina la vecchia quantità
                                //nell'acquisto e la decrementa di nuovo nel magazzino
                                System.out.println("Quantità non valida. Ripristino la vecchia quantità.");
                                prodottoDaModificare.decrementaQuantita(vecchiaQuantita);
                            }
                        } else {
                            //Avvisa l'utente del indice non valido e controlla cosa inserisce l'utente in gestisciErrore:
                            //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                            continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Indice prodotto non valido.");
                            if (!continuare) {
                                //Prima di uscire dal metodo, ripristina la quantità dei prodotti nel magazzino
                                for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                    prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                                }
                                vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                                return;
                            } else {
                                //Se l'utente vuole continuare, rimane nel loop
                                continue;
                            }
                        }
                        break;
                    case 3: //Modifica lo stato "da portare via" di un prodotto dell'ordine
                        //Chiama il metodo leggi intero poiché c'è bisogno di un indice definito
                        int indiceProdotto2 = leggiIntero(scanner, "Inserisci il numero del prodotto di cui vuoi modificare lo stato 'portare via': ") - 1;
                        //Controlla che l'indice inserito in input sia in range
                        if (indiceProdotto2 >= 0 && indiceProdotto2 < prodottiAcquistati.size()) {
                            //Aggiorna lo stato "da portare via"
                            boolean nuovoPortareVia = false;
                            System.out.print("Il prodotto è da portare via? (s/n): ");
                            String nuovaRispostaPortareVia = scanner.nextLine();
                            //Controlla se la risposta è valida:
                            //Se non lo è, continua a chiedere input
                            while (!nuovaRispostaPortareVia.equalsIgnoreCase("s") && !nuovaRispostaPortareVia.equalsIgnoreCase("n")) {
                                System.out.print("Risposta non valida. Il prodotto è da portare via? (s/n): ");
                                nuovaRispostaPortareVia = scanner.nextLine();
                            }
                            if (nuovaRispostaPortareVia.equalsIgnoreCase("s")) {
                                nuovoPortareVia = true;
                            }

                            //Setta il nuovo stato del prodotto nell'ordine
                            portareViaList.set(indiceProdotto2, nuovoPortareVia);
                            System.out.println("Stato 'da portare via' aggiornato per il prodotto: " + prodottiAcquistati.get(indiceProdotto2).getNome());
                        } else {
                            //Avvisa l'utente del indice non valido e controlla cosa inserisce l'utente in gestisciErrore:
                            //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                            continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Indice prodotto non valido.");
                            if (!continuare) {
                                //Prima di uscire dal metodo, ripristina la quantità dei prodotti nel magazzino
                                for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                    prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                                }
                                vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                                return;
                            } else {
                                //Se l'utente vuole continuare, rimane nel loop
                                continue;
                            }
                        }
                        break;
                    case 4: //Rimuove il prodotto dall'ordine
                        //Chiama il metodo leggi intero poiché è richiesto un indice definito
                        int indiceProdotto3 = leggiIntero(scanner, "Inserisci il numero del prodotto che vuoi rimuovere: ") - 1;
                        //Controlla se l'indice è interno al range
                        if (indiceProdotto3 >= 0 && indiceProdotto3 < prodottiAcquistati.size()) {
                            //Seleziona il prodotto in base all'indice inserito in input
                            Prodotto prodottoDaRimuovere = prodottiAcquistati.get(indiceProdotto3);
                            int quantitaDaRimuovere = quantitaAcquistate.get(indiceProdotto3);
                            //Chiama il metodo per aggiornare la quantità di prodotto nel magazzino
                            prodottoDaRimuovere.incrementaQuantita(quantitaDaRimuovere);
                            //Aggiorna il costo dell'ordine
                            double costoDaRimuovere = prodottoDaRimuovere.getPrezzo() * quantitaDaRimuovere;
                            costoParziale -= costoDaRimuovere;
                            //Rimuove il prodotto dalle apposite liste
                            prodottiAcquistati.remove(indiceProdotto3);
                            quantitaAcquistate.remove(indiceProdotto3);
                            portareViaList.remove(indiceProdotto3);
                        } else {
                            //Avvisa l'utente del indice prodotto non valido e controlla cosa inserisce l'utente in gestisciErrore:
                            //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                            continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Indice prodotto non valido.");
                            if (!continuare) {
                                //Prima di uscire dal metodo, ripristina la quantità dei prodotti nel magazzino
                                for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                    prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                                }
                                vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                                return;
                            } else {
                                //Se l'utente vuole continuare, rimane nel loop
                                continue;
                            }
                        }
                        break;
                    default: //Gestisci scelta non valida
                        //Avvisa l'utente della scelta non valida e controlla cosa inserisce l'utente in gestisciErrore:
                        //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                        continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Scelta non valida.");
                        if (!continuare) {
                            //Prima di uscire dal metodo, ripristina la quantità dei prodotti nel magazzino
                            for (int i = 0; i < prodottiAcquistati.size(); i++) {
                                prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                            }
                            vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                            return;
                        } else {
                            //Se l'utente vuole continuare, rimane nel loop
                            continue;
                        }
                }
            } else {
                //Avvisa l'utente della risposta non valida e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: Risposta non valida.");
                if (!continuare) {
                    //Prima di uscire dal metodo, ripristina la quantità dei prodotti nel magazzino
                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                    }
                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                    return;
                } else {
                    //Se l'utente vuole continuare, rimane nel loop
                    continue;
                }
            }
        }

        //Controlla se il saldo dell'utente sia minore del costo dell'ordine
        while (this.saldo < costoParziale) {
            System.out.println("Saldo insufficiente per completare l'acquisto. Ecco i tuoi prodotti nell'ordine:");
            for (int i = 0; i < prodottiAcquistati.size(); i++) {
                //Mostra tutti i prodotti nell'ordine con le loro caratteristiche
                String dettaglioProdotto = (i + 1) + ") " + prodottiAcquistati.get(i).getNome() + " - Quantità: " + quantitaAcquistate.get(i);
                if (portareViaList.get(i)) {
                    dettaglioProdotto += " (da portare via)";
                }
                System.out.println(dettaglioProdotto);
            }

            //Se il saldo è insufficiente, si chiede all'utente se vuole comunque terminare il suo acquisto
            System.out.println("Se si vuole terminare l'acquisto, verrà chiesto di togliere un po' di prodotti dall'ordine.\nVuoi continuare con l'acquisto? (s/n): ");
            String rispostaContinuare = scanner.nextLine();

            //Controlla che la risposta inserita dall'utente sia valida:
            //Se non lo è, chiede un nuovo input
            while (!rispostaContinuare.equalsIgnoreCase("s") && !rispostaContinuare.equalsIgnoreCase("n")) {
                System.out.println("Scelta non valida.\nSe si vuole terminare l'acquisto, verrà chiesto di togliere un po' di prodotti dall'ordine.\nVuoi continuare con l'acquisto? (s/n): ");
                rispostaContinuare = scanner.nextLine();
            }

            //Controlla se l'utente vuole continuare con l'acquisto in caso di errore
            if (rispostaContinuare.equalsIgnoreCase("n")) {
                //Prima di annullare l'acquisto, ripristina la quantità dei prodotti nel magazzino
                for (int i = 0; i < prodottiAcquistati.size(); i++) {
                    prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                }
                System.out.println("Acquisto annullato, ritorno al menù principale");
                vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale");
                return;
            }

            //Chiama il metodo per leggere interi siccome c'è bisogno di un indice definito
            int sceltaProdotto = leggiIntero(scanner, "Inserisci il numero del prodotto da cui vuoi rimuovere una quantità: ");

            //Controlla che l'indice selezionato sia nel range:
            //Se non lo è, chiedi nuovamente una scelta
            if (sceltaProdotto < 1 || sceltaProdotto > prodottiAcquistati.size()) {
                System.out.println("Scelta non valida.");
                continue;
            }

            //Seleziona il prodotto in base all'indice selezionato con l'input
            Prodotto prodottoDaRimuovere = prodottiAcquistati.get(sceltaProdotto - 1);
            int quantitaNellOrdine = quantitaAcquistate.get(sceltaProdotto - 1);

            //Chiama il metodo per leggere interi siccome c'è bisogno di una quantità definita
            int quantitaDaRimuovere = leggiIntero(scanner, "Inserisci la quantità da rimuovere da " + prodottoDaRimuovere.getNome() + ": ");

            //Controlla che la quantità selezionata sia nel range:
            //Se non lo è, chiedi nuovamente una scelta
            if (quantitaDaRimuovere <= 0 || quantitaDaRimuovere > quantitaNellOrdine) {
                System.out.println("Quantità non valida.");
                continue;
            }

            //Chiama il metodo per incrementare la quantità del prodotto nel magazzino
            //e setta la nuova quantità di prodotto
            prodottoDaRimuovere.incrementaQuantita(quantitaDaRimuovere);
            quantitaAcquistate.set(sceltaProdotto - 1, quantitaNellOrdine - quantitaDaRimuovere);

            //Aggiorna il costo dell'acquisto in base alla quantità rimossa di quel prodotto
            double costoDaRimuovere = prodottoDaRimuovere.getPrezzo() * quantitaDaRimuovere;
            costoParziale -= costoDaRimuovere;

            //Controlla se rimuove l'intera quantità di prodotto acquistato:
            //Se sì, rimuove il prodotto dalle liste
            if (quantitaAcquistate.get(sceltaProdotto - 1) == 0) {
                prodottiAcquistati.remove(sceltaProdotto - 1);
                quantitaAcquistate.remove(sceltaProdotto - 1);
            }

            //Mostra il costo aggiornato dopo la modifica
            System.out.println("Nuovo costo totale: " + String.format("%.2f", costoParziale));
        }

        while (true) {
            //Verifica dei dati della carta di credito dell'utente
            System.out.println("Per procedere con l'acquisto, conferma i tuoi dati della carta di credito.");
            System.out.println("Inserisci il numero della carta di credito (16 cifre):");
            String numeroCartaInserito = scanner.nextLine();
            System.out.println("Inserisci la data di scadenza (MM/YY):");
            String dataScadenzaInserita = scanner.nextLine();
            System.out.println("Inserisci il nome del proprietario della carta:");
            String nomeProprietarioInserito = scanner.nextLine();
            System.out.println("Inserisci il cognome del proprietario della carta:");
            String cognomeProprietarioInserito = scanner.nextLine();
            System.out.println("Inserisci il CVV della carta (3 cifre):");
            String cvvInserito = scanner.nextLine();

            //Controllo dei dati inseriti con quelli memorizzati
            if (!numeroCartaInserito.equals(this.numeroCarta) ||
                    !dataScadenzaInserita.equals(this.dataScadenza) ||
                    !nomeProprietarioInserito.equals(this.nomeProprietario) ||
                    !cognomeProprietarioInserito.equals(this.cognomeProprietario) ||
                    !cvvInserito.equals(this.cvv)) {

                //Avvisa l'utente dei dati scorretti inseriti e controlla cosa inserisce l'utente in gestisciErrore:
                //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                continuare = gestisciErrore(scanner, vistaProdotto, "Errore: I dati della carta di credito non sono corretti. Acquisto non effettuato.");
                if (!continuare) {
                    //Se l'utente non continua, bisogna ripristinare le quantità dei prodotti
                    for (int i = 0; i < prodottiAcquistati.size(); i++) {
                        prodottiAcquistati.get(i).incrementaQuantita(quantitaAcquistate.get(i));
                    }
                    vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                    return;
                }
            } else {
                break;
            }
        }

        //Aggiorna il saldo dell'utente
        this.saldo -= costoParziale;

        //Stampa tutti i prodotti acquistati, con le loro quantità
        for (int i = 0; i < prodottiAcquistati.size(); i++) {
            Prodotto prodotto = prodottiAcquistati.get(i);
            int quantita = quantitaAcquistate.get(i);

            //Controlla se il prodotto è da portare via, nel caso, stampa lo stato "da portare via"
            if (portareViaList.get(i)) {
                System.out.println("Hai acquistato " + quantita + " " + prodotto.getNome() + " (da portare via).");
            } else {
                System.out.println("Hai acquistato " + quantita + " " + prodotto.getNome() + ".");
            }
        }

        //Controlla se è già stato mostrato l'avviso di aggiornamento per quel prodotto
        for (Prodotto prodotto : prodottiAggiornati) {
            //Aggiorna una sola volta per ogni prodotto
            vistaProdotto.aggiorna("Quantità aggiornata per il prodotto: " + prodotto.getNome() + " - Nuova quantità: " + prodotto.getQuantita());
        }

        //Avvisa l'utente in maniera finale della buona riuscita dell'acquisto e mostra il saldo aggiornato
        System.out.println("Acquisto effettuato con successo.");
        System.out.println("Saldo rimanente: " + String.format("%.2f", this.saldo));
        vistaProdotto.aggiorna("Saldo aggiornato: " + String.format("%.2f", this.saldo));
        System.out.println();
        //Crea un ordine con i prodotti acquistati
        Ordine ordine = new Ordine(this, prodottiAcquistati, quantitaAcquistate, portareViaList, costoParziale);
        aggiungiOrdine(ordine); //Aggiunge l'ordine alla lista personale dell'utente
        aggiungiOrdineGlobal(ordine); //Aggiunge l'ordine alla lista globale

        //Viene settato a true il flag per tornare al menù principale
        VistaProdotto.setTornaAlMenu(true);
    }

    private Prodotto ricercaAltroProdotto(Scanner scanner) {
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        while (true) {
            System.out.println("Inserisci il nome del prodotto da cercare:");
            String nomeProdotto = scanner.nextLine();
            //Ottiene l'istanza del controller di prodotto e chiama il metodo che trova il prodotto
            //in base ad un input dell'utente
            Prodotto prodotto = ProdottoController.getInstance().trovaProdotto(nomeProdotto);
            if (prodotto != null) {
                //Controlla se la quantità presente è valida per l'acquisto
                if (prodotto.getQuantita() > 0) {
                    System.out.println(prodotto);
                    vistaProdotto.aggiorna("Ricerca eseguita con successo");
                    return prodotto;
                } else {
                    //Avvisa l'utente della quantità = 0 e controlla cosa inserisce l'utente in gestisciErrore:
                    //Se l'utente sceglie "n", avvisa l'annullamento dell'acquisto e torna al menù principale
                    if (!gestisciErrore(scanner, vistaProdotto, "Il prodotto trovato non è disponibile (quantità = 0). Vuoi cercare un altro prodotto?")) {
                        vistaProdotto.aggiorna("Acquisto annullato, ritorno al menù principale.");
                        return null;
                    }
                }
            } else {
                //Avvisa l'utente che non è stato trovato alcun prodotto dalla ricerca e chiede
                //di scegliere se tentare una nuova ricerca continuando con l'acquisto
                System.out.println("Prodotto non trovato. Vuoi riprovare? (s/n): ");
                String risposta = scanner.nextLine();
                if (!risposta.equalsIgnoreCase("s")) {
                    return null;
                }
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi
    //che la estendono per la gestione dell'errore durante un ordine
    private boolean gestisciErrore(Scanner scanner, VistaProdotto vistaProdotto, String messaggio) {
        System.out.println(messaggio);
        vistaProdotto.aggiorna(messaggio);
        //Chiede all'utente di scegliere se continuare e salva la risposta
        System.out.println("Vuoi continuare con l'acquisto dei prodotti? (s/n): ");
        String scelta = scanner.nextLine();
        //Controlla se la scelta è valida e continua a chiedere di scegliere finché non è valida
        while (!scelta.equalsIgnoreCase("s") && !scelta.equalsIgnoreCase("n")) {
            System.out.println("Scelta non valida. Vuoi continuare con l'acquisto dei prodotti? (s/n): ");
            scelta = scanner.nextLine();
        }
        return scelta.equalsIgnoreCase("s");
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione della libreria personale
    public void visualizzaLibreriaPersonale() {
        //Mostra le informazioni dell'utente registrato
        System.out.println("Informazioni utente:");
        System.out.println("Email: " + email);
        System.out.println("Nome (Proprietario carta): " + nomeProprietario);
        System.out.println("Cognome (Proprietario carta): " + cognomeProprietario);
        System.out.println("Saldo: " + String.format("%.2f", saldo));
        System.out.println();

        //Se l'utente non ha ancora effettuato ordini e non partecipa a nessun
        //evento, lo avvisa e torna al menù principale
        if (libreriaPersonale.isEmpty() && ordini.isEmpty() && ordiniCompletati.isEmpty()) {
            System.out.println("La libreria personale è vuota. Ritorno al menù principale.");
            return;
        }

        //Controlla se ci sono eventi nella libreria personale
        if (libreriaPersonale.isEmpty()) {
            System.out.println("Non sono presenti eventi nella libreria personale.");
        } else {
            //Se ci sono li stampa
            System.out.println("Eventi nella tua libreria personale:");
            for (Evento evento : libreriaPersonale) {
                System.out.println(evento);
            }
        }

        //Controlla se ci sono ordini in corso nella lista apposita
        if (ordini.isEmpty()) {
            System.out.println("Nessun ordine in corso presente.");
        } else {
            //Se ci sono li stampa
            System.out.println("Ordini in corso presenti:");
            for (Ordine ordine : ordini) {
                System.out.println(ordine);
            }
        }

        //Controlla se sono presenti ordini completati nella lista apposita
        if (ordiniCompletati.isEmpty()) {
            System.out.println("Nessun ordine completato.");
        } else {
            //Se ci sono li stampa
            System.out.println("Ordini completati:");
            for (Ordine ordine : ordiniCompletati) {
                System.out.println(ordine);
            }
        }

        System.out.println("Vuoi passare alla gestione della libreria personale? (s/n)");
        Scanner scanner = new Scanner(System.in);
        String scelta = scanner.nextLine();
        boolean input = false;

        //Rimane nel loop fino a quando l'utente non completa
        //una funzione o decide di tornare al menù principale
        while (!input) {
            switch (scelta.toLowerCase()) {
                case "s":
                    input = true;
                    //Chiama la funzione per gestire la libreria personale
                    gestioneLibreriaPersonale();
                    break;
                case "n":
                    //Avvisa l'utente e torna al menù principale
                    System.out.println("Fine della visualizzazione della libreria personale.");
                    input = true;
                    break;
                default:
                    //Avvisa l'utente e fai inserire nuovamente la scelta
                    System.out.println("Scelta non valida\nInserisci un valore valido (s/n)");
                    scelta = scanner.nextLine();
                    break;
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi
    //che la estendono per la gestione della libreria personale
    public void gestioneLibreriaPersonale() {
        Scanner scanner = new Scanner(System.in);
        //Rimane nel loop fino a quando l'utente non completa
        //una funzione o decide di tornare al menù principale
        while (true) {
            System.out.println("Vuoi gestire gli eventi o gli ordini?");
            System.out.println("1) Gestione eventi");
            System.out.println("2) Gestione ordini");
            System.out.println("3) Torna al menù principale");
            System.out.print("Seleziona un'opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();  // Consuma la newline

            switch (scelta) {
                case 1:
                    //Chiama la funzione per gestire gli eventi
                    gestisciEventi();
                    return;
                case 2:
                    //Chiama la funzione per gestire gli ordini
                    gestisciOrdini();
                    return;
                case 3:
                    //Avvisa l'utente e torna al menù principale
                    System.out.println("Ritorno al menù principale.");
                    return;
                default:
                    //Avvisa l'utente e fai ripetere la scelta
                    System.out.println("Scelta non valida. Per favore, seleziona una scelta valida.");
                    break;
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la gestione della libreria personale
    public void gestisciEventi() {
        Scanner scanner = new Scanner(System.in);

        //Controlla se ci sono eventi nella libreria
        if (libreriaPersonale.isEmpty()) {
            System.out.println("Non ci sono eventi da gestire.");
            return;
        } else {
            //Se ci sono stampali
            System.out.println("Eventi nella tua libreria personale:");
            for (int i = 0; i < libreriaPersonale.size(); i++) {
                System.out.println((i + 1) + ") " + libreriaPersonale.get(i).getNome());
            }

            System.out.print("Seleziona il numero corrispondente all'evento da gestire: ");
            int sceltaEvento = scanner.nextInt();
            scanner.nextLine();  //Consuma la newline rimasta

            //Controlla che l'indice sia nel range valido
            if (sceltaEvento < 1 || sceltaEvento > libreriaPersonale.size()) {
                System.out.println("Selezione non valida");
                return;
            }

            //Seleziona l'evento in base all'indice inserito in input
            Evento eventoSelezionato = libreriaPersonale.get(sceltaEvento - 1);
            System.out.println("Hai selezionato l'evento: " + eventoSelezionato.getNome());
            System.out.print("Vuoi eliminare questo evento dalla tua libreria? (s/n): ");
            String risposta = scanner.nextLine();

            if (risposta.equalsIgnoreCase("s")) {
                //Rimuove dalla libreria l'evento selezionato e chiama il metodo
                //per decrementare i partecipanti
                libreriaPersonale.remove(eventoSelezionato);
                eventoSelezionato.decrementaPartecipanti();
                eventoController.notificaOsservatori("Numero di partecipanti aggiornato per l'evento: " + eventoSelezionato.getNome() + " - Nuovo numero: " + eventoSelezionato.getNumPartecipanti());
                System.out.println("Evento eliminato dalla tua libreria personale.");
            } else {
                System.out.println("Evento non eliminato.");
            }
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi
    //che la estendono per la gestione degli ordini
    private void gestisciOrdini() {
        Scanner scanner = new Scanner(System.in);
        //Crea la lista che conterrà gli ordini con prodotti da portare via
        List<Ordine> ordiniConProdottiDaPortareVia = new ArrayList<>();
        for (Ordine ordine : ordini) {
            boolean contieneProdottiNonGestiti = false;
            for (int i = 0; i < ordine.getPortareVia().size(); i++) { //Per ogni ordine con prodotti da portare via,
                //controlla se sono presenti prodotti gestiti non ritirati fra i prodotti da portare via
                if (ordine.getPortareVia().get(i) && ordine.getProdottiGestiti().get(i) && !ordine.getProdottiRitirati().get(i)) {
                    contieneProdottiNonGestiti = true;
                    break;
                }
            }
            if (contieneProdottiNonGestiti) {
                //Aggiunge gli ordini trovati con il controllo precedente alla nuova lista
                ordiniConProdottiDaPortareVia.add(ordine);
            }
        }

        //Verifica se ci sono ordini da gestire
        if (ordiniConProdottiDaPortareVia.isEmpty()) {
            System.out.println("Non ci sono ordini da gestire / Non sono pronti i prodotti da ritirare.");
            return;
        }

        //Stampa solo gli ordini con prodotti da portare via pronti da ritirare
        System.out.println("Ordini effettuati con prodotti da portare via pronti da ritirare:");
        for (int i = 0; i < ordiniConProdottiDaPortareVia.size(); i++) {
            System.out.println((i + 1) + ") " + ordiniConProdottiDaPortareVia.get(i).toString());
        }

        //Seleziona un ordine da gestire
        int sceltaOrdine;
        while (true) {
            System.out.print("Seleziona il numero corrispondente all'ordine da gestire: ");
            String input = scanner.nextLine();
            try {
                sceltaOrdine = Integer.parseInt(input);
                //Controlla se la scelta è interna al range di ordini
                if (sceltaOrdine < 1 || sceltaOrdine > ordiniConProdottiDaPortareVia.size()) {
                    System.out.println("Numero ordine non valido. Per favore inserisci un numero valido.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) { //Si verifica se l'input non è numerico; chiede di inserire un nuovo input
                System.out.println("Input non valido. Per favore inserisci un numero.");
            }
        }

        //Prende l'ordine corrispondente all'indice selezionato in input
        Ordine ordineSelezionato = ordiniConProdottiDaPortareVia.get(sceltaOrdine - 1);

        //Chiede se si vuole gestire l'ordine selezionato, se sì, chiama il metodo apposito
        System.out.print("Attenzione! Potrai ritirare solo i prodotti pronti al ritiro\nVerrà mostrato un avviso per quei prodotti non ancora pronti\nVuoi ritirare i prodotti di questo ordine? (s/n): ");
        String rispostaOrdine = scanner.nextLine();
        if (rispostaOrdine.equalsIgnoreCase("s")) {
            gestisciOrdine(ordineSelezionato);
        } else {
            System.out.println("Prodotti di questo ordine non ritirati.");
        }
    }

    //Metodo interno al metodo gestisciOrdini
    protected void gestisciOrdine(Ordine ordine) {
        Scanner scanner = new Scanner(System.in);

        //Ottiene le liste degli ordini e dei prodotti
        List<Prodotto> prodotti = ordine.getProdotti();
        List<Integer> quantita = ordine.getQuantita();
        List<Boolean> portareVia = ordine.getPortareVia();
        List<Boolean> prodottiRitirati = ordine.getProdottiRitirati();
        List<Boolean> prodottiGestiti = ordine.getProdottiGestiti();

        //Esegue ciò che segue per ogni prodotto nell'ordine selezionato nella ricerca
        for (int i = 0; i < prodotti.size(); i++) {
            //Controlla se sono prodotti da portare via e non sono stati ritirati
            if (portareVia.get(i) && !prodottiRitirati.get(i)) {
                if (prodottiGestiti.get(i)) {
                    System.out.println("Prodotto da ritirare: " + prodotti.get(i).getNome() + " - Quantità: " + quantita.get(i));
                    System.out.print("Vuoi ritirare questo prodotto? (s/n): ");
                    String risposta = scanner.nextLine();

                    if (risposta.equalsIgnoreCase("s")) {
                        prodottiRitirati.set(i, true); //Impostia il prodotto come ritirato
                        System.out.println("Prodotto ritirato: " + prodotti.get(i).getNome());
                    } else {
                        //Avvisa che il prodotto non è stato ritirato
                        System.out.println("Il prodotto" + prodotti.get(i).getNome() + " non è stato ritirato");
                    }
                } else {
                    System.out.println("Prodotto " + prodotti.get(i).getNome() + " non ancora pronto per il ritiro.");
                }
            }
        }

        ordine.setProdottiRitirati(prodottiRitirati); //Aggiorna la lista dei prodotti ritirati

        //Verifica se tutti i prodotti da portare via sono stati ritirati
        boolean tuttiRitirati = true;
        for (int i = 0; i < prodotti.size(); i++) {
            if (portareVia.get(i) && !prodottiRitirati.get(i)) {
                tuttiRitirati = false;
                break;
            }
        }

        if (tuttiRitirati) {
            ordine.setProdottiPortareViaCompletati(true); //Se sono stati ritirati tutti i prodotti,
            //imposta a true il completamento del ritiro
            System.out.println("Tutti i prodotti da portare via sono stati ritirati. Ordine completato.");
        } else {
            //Se non sono stati ritirati tutti i prodotti, avvisa solo l'utente
            System.out.println("Non tutti i prodotti da portare via sono stati ritirati. Ordine ancora in attesa.");
        }
    }

    //Getter per email
    public String getEmail() {
        return email;
    }

    //Getter per password
    public String getPassword() {
        return password;
    }

    // Metodi getter e setter per le informazioni della carta di credito
    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getCognomeProprietario() {
        return cognomeProprietario;
    }

    public void setCognomeProprietario(String cognomeProprietario) {
        this.cognomeProprietario = cognomeProprietario;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Getter per la libreria personale dell'utente
    public List<Evento> getLibreriaPersonale() {
        return libreriaPersonale;
    }

    //Aggiunge un evento alla libreria personale dell'utente
    public void aggiungiEventoALibreria(Evento evento) {
        libreriaPersonale.add(evento);
    }
}
