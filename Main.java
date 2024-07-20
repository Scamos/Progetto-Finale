//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

//Modello (MVC)
//Classe che rappresenta un Utente Registrato
class UtenteRegistrato {
    private String email;
    private String password;
    private String numeroCarta;
    private String dataScadenza;
    private String nomeProprietario;
    private String cognomeProprietario;
    private String cvv;
    private double saldo;
    public EventoController eventoController;
    private static VistaEvento vistaEventoInstance; //Istanza singleton di VistaEvento
    private List<Evento> libreriaPersonale;

    //Costruttore che ha il compito di creare un nuovo Utente
    //con i suoi attributi che verranno ereditati dalle classi
    //che estendono UtenteRegistrato
    public UtenteRegistrato(String email, String password) {
        this.email = email;
        this.password = password;

        //this.eventoController = new EventoController(); //Inizializza eventoController
        this.eventoController = EventoController.getInstance();

        this.libreriaPersonale = new ArrayList<>();
        //Creazione dell'istanza di VistaEvento solo se non è già stata creata

        vistaEventoInstance = VistaEvento.getInstance();
        /*if (vistaEventoInstance == null) {
            vistaEventoInstance = new VistaEvento();
            //Aggiunta dell'osservatore solo alla prima creazione dell'istanza
            eventoController.aggiungiOsservatore(vistaEventoInstance);
        }*/
    }

    //Metodo per ottenere l'istanza singleton di VistaEvento
    public static VistaEvento getVistaEventoInstance() {
        return VistaEvento.getInstance();
        //return vistaEventoInstance;
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione degli eventi
    public void visualizzaEventi() {
        //Ottiene il "database" degli Eventi
        List<Evento> eventi = eventoController.getListaEventi();
        // Crea la vista e la registra come osservatore

        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();

        //Controlla se è vuoto
        if (eventi.isEmpty()) {
            System.out.println("Non ci sono eventi disponibili al momento");
            vistaEvento.aggiorna("Tentato accesso ad una lista vuota di eventi");
        } else {
            //Se non è vuoto stampa tutti gli eventi
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
                        //perciò mi sposto in ricercaEventi
                        //Chiamata al metodo ricercaEventi
                        ricercaEventi();
                        break;
                    case "n":
                        input = true; //L'Utente ha inserito un input valido
                        System.out.println("Ricerca non eseguita");
                        vistaEvento.aggiorna("Ricerca non eseguita");
                        //Serve per uscire dal metodo visualizzaEventi e
                        //Tornare al menù principale
                        break; //Esci dal metodo senza andare in ricercaEventi
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
                //Chiedo al Organizzatore se vuole passare alla gestioneEventi
                System.out.println("Vuoi passare alla gestione degli eventi? (s/n)");
                //scanner = new Scanner(System.in);
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
                            //perciò mi sposto in gestioneEventi
                            //Chiamata al metodo gestioneEventi tramite cast a Organizzatore
                            ((Organizzatore) this).gestioneEventi();
                            break;
                        case "n":
                            //Siccome qui uscirà completamente dal metodo, non serve
                            //aggiornare inputValido
                            System.out.println("Ritorno al menù principale");
                            vistaEvento.aggiorna("Ritorno al menù principale");
                            //Serve per uscire dal metodo visualizzaEventi e
                            //Tornare al menù principale
                            return; //Esci dal metodo senza andare in gestioneEventi
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

    public void ricercaEventi() {
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome dell'evento da cercare:");
        String nomeEvento = scanner.nextLine();

        //Utilizzo il metodo trovaEvento, e lo eguaglio a evento di tipo Evento
        Evento evento = eventoController.trovaEvento(nomeEvento);
        //Richiesta al programma se ha trovato qualcosa
        if (evento != null) {
            System.out.println(evento);
            vistaEvento.aggiorna("Ricerca eseguita con successo");
            System.out.print("Vuoi partecipare a questo evento? (s/n): ");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("s")) {
                evento.incrementaPartecipanti();
                this.aggiungiEventoALibreria(evento);
                eventoController.notificaOsservatori("Numero di partecipanti aggiornato per l'evento: " + evento.getNome() + " - Nuovo numero: " + evento.getNumPartecipanti());
                System.out.println("Partecipazione inserita con successo.");
                //vistaEvento.aggiorna("Numero di partecipanti aggiornato: " + evento.getNumPartecipanti());
                System.out.println();
            } else if (risposta.equalsIgnoreCase("n")){
                System.out.println("Partecipazione non inserita.");
                vistaEvento.aggiorna("Numero di partecipanti invariato");
                System.out.println();
            } else {
                System.out.println("Scelta Invalida. Partecipazione non inserita.");
                vistaEvento.aggiorna("Numero di partecipanti invariato");
                System.out.println();
            }
        } else {
            System.out.println("Errore: Evento non trovato");
            vistaEvento.aggiorna("Errore durante la ricerca di un evento");
        }
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
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        ProdottoController prodottoController = ProdottoController.getInstance();
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        if (prodotti.isEmpty()) {
            System.out.println("Non ci sono prodotti disponibili al momento");
            vistaProdotto.aggiorna("Tentato accesso alla lista vuota di prodotti");
        } else {
            Scanner scanner = new Scanner(System.in);
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
                        System.out.println("Passaggio alla ricerca dei prodotti");
                        vistaProdotto.aggiorna("Passaggio alla ricerca dei prodotti");
                        ricercaProdotti();
                        valido = true;
                        break;
                    case "n":
                        System.out.println("Ricerca non eseguita");
                        vistaProdotto.aggiorna("Ricerca non eseguita");
                        valido = true;
                        break;
                    default:
                        System.out.println("Scelta non valida\nInserisci un valore valido (s/n): ");
                        rispostaRicerca = scanner.nextLine();
                        break;
                }
            }
            /*if (rispostaRicerca.equalsIgnoreCase("s")) {
                vistaProdotto.aggiorna("Passaggio alla ricerca dei prodotti");
                ricercaProdotti();
            }*/

            if (this instanceof ResponsabileBarVenditore) {
                System.out.print("Vuoi passare alla gestione del magazzino? (s/n): ");
                String rispostaGestione = scanner.nextLine();
                boolean scrittaValida = false;
                while (!scrittaValida){
                    switch (rispostaGestione.toLowerCase()){
                        case "s":
                            System.out.println("Passaggio alla gestione del magazzino");
                            vistaProdotto.aggiorna("Passaggio alla gestione del magazzino");
                            ((ResponsabileBarVenditore) this).gestioneMagazzino();
                            scrittaValida = true;
                            break;
                        case "n":
                            System.out.println("Ritorno al menù principale");
                            vistaProdotto.aggiorna("Ritorno al menù principale");
                            return;
                        default:
                            System.out.println("Scelta non valida\nInserisci un valore valido (s/n): ");
                            rispostaGestione = scanner.nextLine();
                            break;
                    }
                }
                /*if (rispostaGestione.equalsIgnoreCase("s")) {
                    vistaProdotto.aggiorna("Passaggio alla gestione del magazzino");
                    ((ResponsabileBarVenditore) this).gestioneMagazzino();
                }*/
            }
        }
    }

    public void ricercaProdotti() {
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome del prodotto:");
        String nomeProdotto = scanner.nextLine();
        Prodotto prodotto = ProdottoController.getInstance().trovaProdotto(nomeProdotto);
        if (prodotto != null) {
            System.out.println(prodotto);
            vistaProdotto.aggiorna("Ricerca eseguita con successo");
            System.out.print("Vuoi acquistare questo prodotto? (s/n): ");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("s")) {
                if (prodotto.getQuantita() > 0) {
                    acquistoProdotti(prodotto);
                } else {
                    System.out.println("La quantità disponibile del prodotto è zero. Acquisto non possibile.");
                    vistaProdotto.aggiorna("Acquisto non possibile: quantità del prodotto pari a zero.");
                }
            }
        } else {
            System.out.println("Errore: Prodotto non trovato");
            vistaProdotto.aggiorna("Errore durante la ricerca di un prodotto");
        }
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per l'acquisto dei prodotti
    public void acquistoProdotti(Prodotto prodotto) {
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quanta quantità vuoi acquistare? ");
        int quantita = scanner.nextInt();
        scanner.nextLine();

        if (quantita > prodotto.getQuantita()) {
            System.out.println("Errore: Quantità richiesta superiore alla quantità disponibile. Acquisto non effettuato.");
            vistaProdotto.aggiorna("Acquisto non effettuato: quantità richiesta superiore alla disponibile.");
            return;
        }

        prodotto.decrementaQuantita(quantita);
        ProdottoController.getInstance().notificaOsservatori("Quantità aggiornata per il prodotto: " + prodotto.getNome() + " - Nuova quantità: " + prodotto.getQuantita());
        System.out.println("Acquisto effettuato con successo.");
        vistaProdotto.aggiorna("Quantità aggiornata: " + prodotto.getQuantita());
        System.out.println();
    }

    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la visualizzazione della libreria personale
    public void visualizzaLibreriaPersonale() {
        if (libreriaPersonale.isEmpty()) {
            System.out.println("Non sono presenti eventi. Ritorno al menù principale.");
            return;
        } else {
            System.out.println("Eventi nella tua libreria personale:");
            for (Evento evento : libreriaPersonale) {
                System.out.println(evento);
            }
        }
        System.out.println("Vuoi passare alla gestione degli eventi? (s/n)");
        Scanner scanner = new Scanner(System.in);
        String scelta = scanner.nextLine();
        boolean input = false;
        while (!input) {
            switch (scelta.toLowerCase()) {
                case "s":
                    input = true;
                    gestioneLibreriaPersonale();
                    break;
                case "n":
                    System.out.println("Fine della visualizzazione della libreria.");
                    input = true;
                    break;
                default:
                    System.out.println("Scelta non valida\nInserisci un valore valido (s/n)");
                    scelta = scanner.nextLine();
                    break;
            }
        }
    }


    //Metodo della classe UtenteRegistrato o delle classi che la
    //estendono per la gestione della libreria personale
    public void gestioneLibreriaPersonale() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Eventi nella tua libreria personale:");
        for (int i = 0; i < libreriaPersonale.size(); i++) {
            System.out.println((i + 1) + ") " + libreriaPersonale.get(i).getNome());
        }

        System.out.print("Seleziona il numero corrispondente all'evento da gestire: ");
        int sceltaEvento = scanner.nextInt();
        scanner.nextLine();  // Consuma la newline rimasta

        if (sceltaEvento < 1 || sceltaEvento > libreriaPersonale.size()) {
            System.out.println("Selezione non valida");
            return;
        }

        Evento eventoSelezionato = libreriaPersonale.get(sceltaEvento - 1);
        System.out.println("Hai selezionato l'evento: " + eventoSelezionato.getNome());
        System.out.print("Vuoi eliminare questo evento dalla tua libreria? (s/n): ");
        String risposta = scanner.nextLine();

        if (risposta.equalsIgnoreCase("s")) {
            libreriaPersonale.remove(eventoSelezionato);
            eventoSelezionato.decrementaPartecipanti();
            eventoController.notificaOsservatori("Numero di partecipanti aggiornato per l'evento: " + eventoSelezionato.getNome() + " - Nuovo numero: " + eventoSelezionato.getNumPartecipanti());
            System.out.println("Evento eliminato dalla tua libreria personale.");
        } else {
            System.out.println("Evento non eliminato.");
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

    public List<Evento> getLibreriaPersonale() {
        return libreriaPersonale;
    }

    public void aggiungiEventoALibreria(Evento evento) {
        libreriaPersonale.add(evento);
    }
}
//Classe che rappresenta un Responsabile Bar oppure un Venditore
class ResponsabileBarVenditore extends UtenteRegistrato {
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //email e password già presenti in UtenteRegistrato

    //Costruttore che ha il compito di creare un nuovo Responsabile Bar
    //oppure un Venditore con gli attributi ereditati e volendo anche
    //nuovi attributi, che saranno ereditati dalle classi che estendono
    //ResponsabileBarVenditore
    public ResponsabileBarVenditore(String email, String password) {
        super(email, password);
        //Inizializza altri dati per ResponsabileBarVenditore
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
        Scanner scanner = new Scanner(System.in);
        ProdottoController prodottoController = ProdottoController.getInstance();
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        if (prodotti.isEmpty()) {
            System.out.println("Non ci sono prodotti disponibili al momento");
            return;
        }

        System.out.println("Elenco dei prodotti:");
        for (int i = 0; i < prodotti.size(); i++) {
            System.out.println((i + 1) + ") " + prodotti.get(i).getNome() + " - Quantità: " + prodotti.get(i).getQuantita() + " - Prezzo: " + prodotti.get(i).getPrezzo());
        }

        System.out.print("Seleziona il numero corrispondente al prodotto da gestire: ");
        int sceltaProdotto = scanner.nextInt();
        scanner.nextLine();  // Consuma la newline rimasta

        if (sceltaProdotto < 1 || sceltaProdotto > prodotti.size()) {
            System.out.println("Selezione non valida");
            return;
        }

        Prodotto prodottoSelezionato = prodotti.get(sceltaProdotto - 1);
        System.out.println("Hai selezionato il prodotto: " + prodottoSelezionato.getNome());
        System.out.print("Vuoi modificare la quantità del prodotto? (s/n): ");
        String rispostaQuantita = scanner.nextLine();

        if (rispostaQuantita.equalsIgnoreCase("s")) {
            System.out.print("Inserisci la nuova quantità: ");
            int nuovaQuantita = scanner.nextInt();
            prodottoSelezionato.incrementaQuantita(nuovaQuantita - prodottoSelezionato.getQuantita());
            prodottoController.notificaOsservatori("Quantità aggiornata per il prodotto: " + prodottoSelezionato.getNome() + " - Nuova quantità: " + prodottoSelezionato.getQuantita());
            System.out.println("Quantità aggiornata con successo.");
        } else {
            System.out.println("Quantità non modificata.");
        }
    }
    //Utilizzando l'ereditarietà, non è necessario riscrivere questi metodi
    //Getter per email e password
}
//Classe che rappresenta un Organizzatore
class Organizzatore extends ResponsabileBarVenditore{
    //private EventoController eventoController;
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //email e password già presenti in UtenteRegistrato

    //Costruttore che ha il compito di creare un nuovo Organizzatore
    //con gli attributi ereditati e volendo anche nuovi attributi
    public Organizzatore(String email, String password) {
        super(email, password);
        //Inizializza altri dati per Organizzatore
        //this.eventoController = new EventoController();
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
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        //Creazione degli eventi
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creazione di un nuovo evento:");

        try{
            //Dopo aver creato il nuovo scanner, lo uso per prendere quello
            //che mi scrive l'Organizzatore
            System.out.print("Nome dell'evento: ");
            String nomeEvento = scanner.nextLine();

            System.out.print("Via dell'evento: ");
            String viaEvento = scanner.nextLine();

            System.out.print("Data dell'evento (formato YYYY-MM-DD): ");
            String dataEvento = scanner.nextLine();

            System.out.print("Ora di inizio dell'evento (formato HH:MM): ");
            String oraInizio = scanner.nextLine();

            System.out.print("Ora di fine dell'evento (formato HH:MM): ");
            String oraFine = scanner.nextLine();

            //Crea l'oggetto Evento utilizzando i dati forniti dal Organizzatore
            Evento nuovoEvento = new Evento(nomeEvento, viaEvento, dataEvento, oraInizio, oraFine);

            //L'evento viene aggiunto alla lista degli eventi
            eventoController.aggiungiEvento(nuovoEvento);

            //Stampa il report
            System.out.println("Evento creato con successo!");
            vistaEvento.aggiorna("Creazione di un nuovo evento avvenuta con successo\nNome: " + nomeEvento);
        }catch(Exception e){
            //Se si verifica una qualsiasi eccezione nell'inserimento
            //dei dati dell'evento, notifica l'Organizzatore e la vista dell'errore
            System.out.println("Si è verificato un errore durante la creazione dell'evento.");
            System.out.println("Assicurati di inserire i dati in modo corretto, segui il formato.");
            vistaEvento.aggiorna("Errore durante la creazione di un nuovo evento");
        }
    }

    //Metodo specifico della classe Organizzatore
    //per la gestione degli eventi
    public void gestioneEventi() {
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu Gestione Eventi:");
            System.out.println("1) Modifica Evento");
            System.out.println("2) Elimina Evento");
            System.out.println("3) Torna al menù principale");

            int scelta = scanner.nextInt();
            scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()

            switch (scelta) {
                case 1:
                    //Prende la lista degli eventi dal controller
                    List<Evento> eventi = eventoController.getListaEventi();
                    //Controlla se è vuota
                    if (eventi.isEmpty()){
                        System.out.println("Non ci sono eventi disponibili al momento");
                        vistaEvento.aggiorna("Tentato accesso ad una lista vuota di eventi");
                        break;
                    }else{
                        modificaEvento();
                        break;
                    }
                case 2:
                    //Prende la lista degli eventi dal controller
                    eventi = eventoController.getListaEventi();
                    //Controlla se è vuota
                    if (eventi.isEmpty()){
                        System.out.println("Non ci sono eventi disponibili al momento");
                        vistaEvento.aggiorna("Tentato accesso ad una lista vuota di eventi");
                        break;
                    }else{
                        eliminaEvento();
                        break;
                    }
                case 3:
                    return; //Torna al Menù principale
                default:
                    System.out.println("Opzione inserita non presente. Scrivi un valore valido:");
            }
        }
    }

    //Metodo interno al metodo gestioneEventi
    private void modificaEvento() {
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        //Modificare un evento
        Scanner scanner = new Scanner(System.in);
        //Prende la lista degli eventi dal controller
        List<Evento> eventi = eventoController.getListaEventi();
        //Mostra la lista degli eventi al Organizzatore
        System.out.println("Elenco degli eventi:");
        for (int i = 0; i < eventi.size(); i++) {
            //Restituisco tutti i nomi degli eventi presenti
            System.out.println((i + 1) + ") " + eventi.get(i).getNome());
        }

        //Richiesta di selezione dell'evento da modificare
        System.out.print("Seleziona il numero corrispondente all'evento da modificare: ");
        int sceltaEvento = scanner.nextInt();
        scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()

        //Verifica se l'indice selezionato è una possibile scelta
        if (sceltaEvento < 1 || sceltaEvento > eventi.size()) {
            System.out.println("Selezione non valida");
            System.out.println("Ritorno al menù di gestione degli eventi");
            vistaEvento.aggiorna("Tentato accesso ad un evento non esistente");
            return; //Torna al menù di modifica degli Eventi
        }

        //Quando l'evento selezionato è valido, lo si prende
        Evento eventoDaModificare = eventi.get(sceltaEvento - 1);

        //Menù di modifica Evento selezionato con i suoi dati
        System.out.println("Scegli un dato da modificare per l'evento '" + eventoDaModificare.getNome() + "'");
        System.out.println("1) Nome");
        System.out.println("2) Via");
        System.out.println("3) Data");
        System.out.println("4) Ora di inizio");
        System.out.println("5) Ora di fine");
        System.out.println("6) Annulla");

        int sceltaModifica = scanner.nextInt();
        scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()
        //Inizialmente, considera ciò che l'Organizzatore inserisce
        //come non valido
        boolean inputValido = false;

        //Esce solo se Organizzatore digita il valore di uscita corretto
        while(!inputValido){
            switch (sceltaModifica) {
              case 1:
                  inputValido = true; //L'Organizzatore ha inserito un input valido
                System.out.print("Nuovo nome: ");
                String nuovoNome = scanner.nextLine();
                eventoDaModificare.setNome(nuovoNome);
                //Aggiorna il nome dell'evento (implementazione
                //specifica che dipenderà dalla classe Evento)
                  //Notifica l'avvenuta modifica
                  System.out.println("Nuovo nome inserito con successo");
                  vistaEvento.aggiorna("Modifica del nome avvenuta con successo");
                break;
              case 2:
                  inputValido = true; //L'Organizzatore ha inserito un input valido
                System.out.print("Nuova via: ");
                String nuovaVia = scanner.nextLine();
                eventoDaModificare.setVia(nuovaVia);
                //Aggiorna la via dell'evento (implementazione
                //specifica che dipenderà dalla classe Evento)
                  //Notifica l'avvenuta modifica
                  System.out.println("Nuova via inserita con successo");
                  vistaEvento.aggiorna("Modifica della via avvenuta con successo");
                break;
              case 3:
                  inputValido = true; //L'Organizzatore ha inserito un input valido
                  //Continua a fare questo ciclo finché non ottiene un valore accettato
                do {
                    try {
                        System.out.print("Nuova data (formato YYYY-MM-DD): ");
                        String nuovaData = scanner.nextLine();
                        //Converge la stringa in un oggetto LocalDate
                        LocalDate data = LocalDate.parse(nuovaData);
                        //Passo l'oggetto modificato
                        eventoDaModificare.setData(data);
                        //Aggiorna la data dell'evento (implementazione
                        //specifica che dipenderà dalla classe Evento)
                        break; //Esci se la data viene inserita correttamente
                    } catch (DateTimeParseException e) {
                        System.out.println("Inserire un numero valido per la data");
                        vistaEvento.aggiorna("Modifica della data fallita");
                    }
                } while (true);
                //Mostra quando la data è stata inserita con successo
                  //ed esce dal case
                System.out.println("Nuova data inserita con successo");
                  vistaEvento.aggiorna("Modifica della data avvenuta con successo");
                break;
              case 4:
                  inputValido = true; //L'Organizzatore ha inserito un input valido
                  //Continua a fare questo ciclo finché non ottiene un valore accettato
                do {
                    try {
                        System.out.print("Nuova ora di inizio (formato HH:MM): ");
                        String nuovaOraInizio = scanner.nextLine();
                        //Converge la stringa in un oggetto LocalTime
                        LocalTime oraInizio = LocalTime.parse(nuovaOraInizio);
                        //Passo l'oggetto modificato
                        eventoDaModificare.setOraInizio(oraInizio);
                        //Aggiorna l'ora di inizio dell'evento (implementazione
                        //specifica che dipenderà dalla classe Evento)
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Inserire un numero valido per l'ora di inizio.");
                        vistaEvento.aggiorna("Modifica dell'ora di inizio fallita");
                    }
                } while (true);
                  //Mostra quando l'ora di inizio è stata inserita con
                  //successo ed esce dal case
                System.out.println("Nuova ora di inizio inserita con successo");
                  vistaEvento.aggiorna("Modifica dell'ora di inizio avvenuta con successo");
                break;
              case 5:
                  inputValido = true; //L'Organizzatore ha inserito un input valido
                  //Continua a fare questo ciclo finché non ottiene un valore accettato
                do {
                    try {
                        System.out.print("Nuova ora di fine (formato HH:MM): ");
                        String nuovaOraFine = scanner.nextLine();
                        //Converge la stringa in un oggetto LocalTime
                        LocalTime oraFine = LocalTime.parse(nuovaOraFine);
                        //Passo l'oggetto modificato
                        eventoDaModificare.setOraFine(oraFine);
                        //Aggiorna l'ora di fine dell'evento (implementazione
                        //specifica che dipenderà dalla classe Evento)
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Inserire un numero valido per l'ora di fine.");
                        vistaEvento.aggiorna("Modifica dell'ora di fine fallita");
                    }
                } while (true);
                  //Mostra quando l'ora di fine è stata inserita con
                  //successo ed esce dal case
                System.out.println("Nuova ora di fine inserita con successo");
                  vistaEvento.aggiorna("Modifica dell'ora di fine avvenuta con successo");
                break;
              case 6:
                  //Siccome qui uscirà completamente dal metodo, non serve
                  //aggiornare inputValido
                  //Caso usato per uscire dal ciclo
                System.out.println("Ritorno al menù di gestione degli eventi");
                  vistaEvento.aggiorna("Ritorno al menù di gestione degli eventi");
                  //Serve per uscire dal metodo modificaEvento e
                  //Tornare al menù di gestioneEvento
                return;
              default:
                System.out.println("Scelta non valida.\nInserisci un valore valido");
                  sceltaModifica = scanner.nextInt(); //L'Organizzatore deve inserire un nuovo valore
                  scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()
                break;
            }
        }
        //Qualsiasi modifica accada, una volta avvenuta, tornerà al menù di gestioneEventi
        System.out.println("Ritorno al menù di gestione degli eventi");
        vistaEvento.aggiorna("Ritorno al menù di gestione degli eventi");
    }

    //Metodo interno al metodo gestioneEventi
    private void eliminaEvento() {
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        //Eliminare un evento
        Scanner scanner = new Scanner(System.in);
        //Prende la lista degli eventi dal controller
        List<Evento> eventi = eventoController.getListaEventi();
        //Mostra la lista degli eventi al Organizzatore
        System.out.println("Elenco degli eventi:");
        for (int i = 0; i < eventi.size(); i++) {
            //Restituisco tutti i nomi degli eventi presenti
            System.out.println((i + 1) + ") " + eventi.get(i).getNome());
        }

        //Richiesta di selezione dell'evento da eliminare
        System.out.print("Seleziona il numero corrispondente all'evento da eliminare: ");
        int sceltaEvento = scanner.nextInt();
        scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()

        //Verifica se l'indice selezionato è una possibile scelta
        if (sceltaEvento < 1 || sceltaEvento > eventi.size()) {
            System.out.println("Selezione non valida");
            System.out.println("Ritorno al menù di gestione degli eventi");
            vistaEvento.aggiorna("Tentato accesso ad un evento non esistente");
            return; //Torna al menù di modifica degli Eventi
        }

        //Quando l'evento selezionato è valido, lo si prende
        Evento eventoDaEliminare = eventi.get(sceltaEvento - 1);

        //Richiesta di conferma per l'eliminazione del Evento selezionato
        System.out.print("Sei sicuro di voler eliminare l'evento '" + eventoDaEliminare.getNome() + "'? (s/n): ");
        String conferma = scanner.nextLine();

        //Inizialmente, considera ciò che l'Organizzatore inserisce
        //come non valido
        boolean inputValido = false;
        //Verifica la risposta del Organizzatore
        while (!inputValido) {
            switch (conferma.toLowerCase()) {
                case "s":
                    inputValido = true; //L'Organizzatore ha inserito un input valido
                    //Eliminazione confermata, rimuovi l'Evento dal "database"
                    eventi.remove(eventoDaEliminare);
                    System.out.println("Evento eliminato con successo.");
                    vistaEvento.aggiorna("Eliminazione dell'evento avvenuta con successo");

                    //Non serve sistemare il "database" poiché utilizzando una
                    //Lista, gli Eventi successivi scalano in automatico
                    break;
                case "n":
                    //Siccome qui uscirà completamente dal metodo, non serve
                    //aggiornare inputValido
                    //Eliminazione annullata
                    System.out.println("Eliminazione annullata\nRitorno al menù di gestione degli eventi");
                    vistaEvento.aggiorna("Eliminazione annullata");
                    //Serve per uscire dal metodo eliminaEvento e
                    //Tornare al menù di gestioneEvento
                    return; //Esci dal metodo senza eliminare e torna al menù di gestioneEventi
                default:
                    //Risposta non valida
                    System.out.println("Scelta non valida\nInserisci un valore valido (s/n)");
                    conferma = scanner.nextLine(); //L'Organizzatore deve inserire un nuovo valore
                    //scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()
                    break;
            }
        }
        //Una volta eliminato l'Evento, si tornerà al menù di gestioneEventi
        System.out.println("Ritorno al menù di gestione degli eventi");
        vistaEvento.aggiorna("Ritorno al menù di gestione degli eventi");
    }

    //Metodo specifico della classe Organizzatore
    //per la visualizzazione delle partecipazioni agli eventi
    public void visualizzaPartecipazioneEventi() {
        //Ottieni l'istanza di VistaEvento
        VistaEvento vistaEvento = UtenteRegistrato.getVistaEventoInstance();
        //Ottiene il "database" degli Eventi
        List<Evento> eventi = eventoController.getListaEventi();

        //Controlla se è vuoto
        if (eventi.isEmpty()) {
            System.out.println("Non ci sono eventi disponibili al momento");
            vistaEvento.aggiorna("Tentato accesso alla lista vuota di eventi");
        } else {
            Scanner scanner = new Scanner(System.in);
            //Mostra la lista degli eventi al Organizzatore
            System.out.println("Elenco degli eventi:");
            for (int i = 0; i < eventi.size(); i++) {
                //Restituisco tutti i nomi degli eventi presenti
                System.out.println((i + 1) + ") " + eventi.get(i).getNome());
            }

            //Richiesta di selezione dell'evento a cui visualizzare la partecipazione
            System.out.print("Seleziona il numero corrispondente all'evento da visualizzare: ");
            int sceltaEvento = scanner.nextInt();
            scanner.nextLine(); //Consuma il newline rimasto dopo nextInt()

            //Verifica se l'indice selezionato è una possibile scelta
            if (sceltaEvento < 1 || sceltaEvento > eventi.size()) {
                System.out.println("Selezione non valida");
                System.out.println("Ritorno al menù principale");
                vistaEvento.aggiorna("Tentato accesso ad un evento non esistente");
                return; //Torna al menù di modifica degli Eventi
            }

            //Quando l'evento selezionato è valido, lo si prende
            Evento eventoSelezionato = eventi.get(sceltaEvento - 1);
            System.out.println("Partecipazioni all'evento " + eventoSelezionato.getNome() + ":");
            //Stampa il numero di partecipazioni al Evento selezionato
            System.out.println("Numero di partecipanti: " + eventoSelezionato.getNumPartecipanti());
            vistaEvento.aggiorna("Visualizzazione delle partecipazioni avvenuta con successo");
        }
    }
    //Utilizzando l'ereditarietà, non è necessario riscrivere questi metodi
    //Getter per email e password
}

//Classe che rappresenta un oggetto Evento
class Evento {
    private String nome;
    private String via;
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private int numPartecipanti;
    //private List<EventoOsservatore> osservatoriEvento;

    //Costruttore che ha il compito di creare un nuovo Evento
    //con i suoi attributi
    public Evento(String nome, String via, String data, String oraInizio, String oraFine) {
        this.nome = nome;
        this.via = via;
        this.data = LocalDate.parse(data);
        this.oraInizio = LocalTime.parse(oraInizio);
        this.oraFine = LocalTime.parse(oraFine);
        this.numPartecipanti = 0;
        //this.osservatoriEvento = new ArrayList<>();
    }

    //Metodi getter e setter di Evento
    //Getter di nome
    public String getNome() {
        return nome;
    }

    //Setter di nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    //Getter di via
    public String getVia() {
        return via;
    }

    //Setter di via
    public void setVia(String via) {
        this.via = via;
    }

    //Getter di data
    public LocalDate getData() {
        return data;
    }

    //Setter di data
    public void setData(LocalDate data) {
        this.data = data;
    }

    //Getter di ora inizio
    public LocalTime getOraInizio() {
        return oraInizio;
    }

    //Setter di ora inizio
    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    //Getter di ora fine
    public LocalTime getOraFine() {
        return oraFine;
    }

    //Setter di ora fine
    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    //Getter di posti rimanenti
    public int getNumPartecipanti() {
        return numPartecipanti;
    }

    //Setter di posti rimanenti
    public void setNumPartecipanti(int numPartecipanti) {
        this.numPartecipanti = numPartecipanti;
    }

    /*public void attach(EventoOsservatore osservatoreEvento) {
        osservatoriEvento.add(osservatoreEvento);
    }

    public void detach(EventoOsservatore osservatoreEvento) {
        osservatoriEvento.remove(osservatoreEvento);
    }*/

    public void incrementaPartecipanti() {
        this.numPartecipanti++;
        //notifyOsservatoriEvento();
    }

    public void decrementaPartecipanti() {
        if (this.numPartecipanti > 0) {
            this.numPartecipanti--;
        }
    }

    /*private void notifyOsservatoriEvento() {
        for (EventoOsservatore osservatoreEvento : osservatoriEvento) {
            osservatoreEvento.update(this);
        }
    }*/

    //Override del metodo toString che serve per poter
    //rappresentare l'oggetto in maniera leggibile
    @Override
    public String toString() {
        return "Evento{" +
                "nome='" + nome + '\'' +
                ", via='" + via + '\'' +
                ", data=" + data +
                ", oraInizio=" + oraInizio +
                ", oraFine=" + oraFine +
                //", numPartecipanti=" + numPartecipanti +
                '}';
    }
}

class Prodotto {
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;

    public Prodotto(String nome, String descrizione, double prezzo, int quantita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void incrementaQuantita(int quantita) {
        this.quantita += quantita;
    }

    public void decrementaQuantita(int quantita) {
        if (this.quantita >= quantita) {
            this.quantita -= quantita;
        }
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                '}';
    }
}

//Controller (MVC)
//Gestisce la logica di registrazione degli utenti
class RegistrazioneController {
    // Attributo per l'osservatore VistaRegistrazione
    private VistaRegistrazione vista;
    //Crea l'attributo registra di tipo Registrazione che
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
            //Notifica gli osservatori dopo aver effettuato il Log out
            //oppure dopo aver inserito una password incorretta TRUE
            //notificaOsservatori("Log Out avvenuto con successo");
        }
    }

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

//Gestisce la logica degli Eventi
class EventoController {
    private static EventoController instance;
    private VistaEvento vistaEvento;
    //Creazione della lista di Eventi
    private List<Evento> listaEventi;
    //Creazione della lista di Osservatori per gli Eventi
    private List<EventoOsservatore> osservatori;

    //Costruttore delle liste
    protected EventoController() {
        this.listaEventi = new ArrayList<>();
        listaEventi.add(new Evento("Karaoke","Verdi 5","2024-09-10","21:30","23:00"));
        listaEventi.add(new Evento("Concerto","Viola 27","2024-09-11","21:30","23:00"));
        listaEventi.add(new Evento("Degustazione","Viola 3","2024-09-10","15:00","18:00"));
        this.osservatori = new ArrayList<>();
    }

    public static EventoController getInstance() {
        if (instance == null) {
            instance = new EventoController();
        }
        return instance;
    }

    public void setVistaEvento(VistaEvento vistaEvento) {
        this.vistaEvento = vistaEvento;
    }

    // Aggiunge un osservatore all'elenco
    public void aggiungiOsservatore(EventoOsservatore osservatore) {
        osservatori.add(osservatore);
    }

    // Notifica tutti gli osservatori
    public void notificaOsservatori(String messaggio) {
        for (EventoOsservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }
    
    //Metodo che aggiunge Evento alla lista
    public void aggiungiEvento(Evento evento) {
        listaEventi.add(evento);
        //notificaOsservatori("Aggiunto un nuovo evento con successo: " + evento.getNome());
    }

    //Metodo che ottiene la lista
    public List<Evento> getListaEventi() {
        return listaEventi;
    }

    //Metodo che ottiene Evento dalla lista, usando il nome
    public Evento trovaEvento(String nomeEvento) {
        for (Evento evento : listaEventi) {
            //Togliamo sensibilità dalla ricerca, ignorando maiuscole e minuscole
            if (evento.getNome().equalsIgnoreCase(nomeEvento)) {
                return evento; //Ritorna l'Evento se è stato trovato
            }
        }
        //System.out.println("Errore: Evento non trovato"); Doppio
        return null; //Ritorna questo nel caso non è stato trovato
    }
}

class ProdottoController {
    private static ProdottoController instance;
    private List<Prodotto> listaProdotti;
    private List<ProdottoOsservatore> osservatori;

    private ProdottoController() {
        this.listaProdotti = new ArrayList<>();
        listaProdotti.add(new Prodotto("Pane", "Pane fresco in cassetta", 10.0, 10));
        listaProdotti.add(new Prodotto("Pasta", "Pasta fresca 500g", 20.0, 15));
        listaProdotti.add(new Prodotto("Pomodoro", "Pomodoro fresco 100g", 30.0, 20));
        this.osservatori = new ArrayList<>();
    }

    public static ProdottoController getInstance() {
        if (instance == null) {
            instance = new ProdottoController();
        }
        return instance;
    }

    public void aggiungiOsservatore(ProdottoOsservatore osservatore) {
        osservatori.add(osservatore);
    }

    public void notificaOsservatori(String messaggio) {
        for (ProdottoOsservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }

    public List<Prodotto> getListaProdotti() {
        return listaProdotti;
    }

    public Prodotto trovaProdotto(String nomeProdotto) {
        for (Prodotto prodotto : listaProdotti) {
            if (prodotto.getNome().equalsIgnoreCase(nomeProdotto)) {
                return prodotto;
            }
        }
        return null;
    }
}

//Factory
//Classe che contiene il metodo statico che restituisce un'istanza di Registrazione
class RegistrazioneFactory {
    public static Registrazione getRegistrazione(VistaRegistrazione vista) {
        return Registrazione.getInstance(vista);
    }
}

//Singleton
//Design Pattern che permette di avere una sola istanza dell'oggetto
//Registrazione nell'applicazione
class Registrazione {
    //Attributo per accettare un parametro di tipo VistaRegistrazione
    private VistaRegistrazione vista;
    //Attributo statico che contiene l'unica istanza di Registrazione nell'applicazione
    private static Registrazione istanza;
    private List<UtenteRegistrato> utentiRegistrati;
    //private static List<UtenteRegistrato> utentiRegistrati = new ArrayList<>();

    //Costruttore privato per evitare la creazione di istanze aggiuntive
    private Registrazione(VistaRegistrazione vista) {
        this.vista = vista;
        utentiRegistrati = new ArrayList<>();

        UtenteRegistrato utente1 = new UtenteRegistrato("mariorossi@gmail.com", "1234");
        utente1.setNumeroCarta("1111222233334444");
        utente1.setDataScadenza("12/26");
        utente1.setNomeProprietario("Mario");
        utente1.setCognomeProprietario("Rossi");
        utente1.setCvv("123");
        utente1.setSaldo(100.0);
        utentiRegistrati.add(utente1);

        UtenteRegistrato utente2 = new UtenteRegistrato("luigibianchi@gmail.com", "4321");
        utente2.setNumeroCarta("5555666677778888");
        utente2.setDataScadenza("11/26");
        utente2.setNomeProprietario("Luigi");
        utente2.setCognomeProprietario("Bianchi");
        utente2.setCvv("456");
        utente2.setSaldo(150.0);
        utentiRegistrati.add(utente2);

        ResponsabileBarVenditore barman = new ResponsabileBarVenditore("barman@gmail.com", "5678");
        barman.setNumeroCarta("9999000011112222");
        barman.setDataScadenza("10/26");
        barman.setNomeProprietario("Carlo");
        barman.setCognomeProprietario("Rassi");
        barman.setCvv("789");
        barman.setSaldo(200.0);
        utentiRegistrati.add(barman);

        ResponsabileBarVenditore venditore = new ResponsabileBarVenditore("venditore@gmail.com", "8765");
        venditore.setNumeroCarta("3333444455556666");
        venditore.setDataScadenza("09/26");
        venditore.setNomeProprietario("Antonio");
        venditore.setCognomeProprietario("Bensi");
        venditore.setCvv("012");
        venditore.setSaldo(250.0);
        utentiRegistrati.add(venditore);

        Organizzatore organizzatrice = new Organizzatore("organizzatrice@gmail.com", "abcd");
        organizzatrice.setNumeroCarta("7777888899990000");
        organizzatrice.setDataScadenza("08/26");
        organizzatrice.setNomeProprietario("Maria");
        organizzatrice.setCognomeProprietario("Rosa");
        organizzatrice.setCvv("345");
        organizzatrice.setSaldo(300.0);
        utentiRegistrati.add(organizzatrice);

        Organizzatore organizzatore = new Organizzatore("organizzatore@gmail.com", "dcba");
        organizzatore.setNumeroCarta("1111333355557777");
        organizzatore.setDataScadenza("07/26");
        organizzatore.setNomeProprietario("Mauro");
        organizzatore.setCognomeProprietario("Del Mulino");
        organizzatore.setCvv("678");
        organizzatore.setSaldo(350.0);
        utentiRegistrati.add(organizzatore);

        /*//Aggiunta di Utenti già registrati, che permette di utilizzarli come esempi
        utentiRegistrati.add(new UtenteRegistrato("mariorossi@gmail.com", "1234"));
        utentiRegistrati.add(new UtenteRegistrato("luigibianchi@gmail.com", "4321"));
        //Aggiunta di Responsabili Bar oppure Venditori già registrati
        utentiRegistrati.add(new ResponsabileBarVenditore("barman@gmail.com", "5678"));
        utentiRegistrati.add(new ResponsabileBarVenditore("venditore@gmail.com", "8765"));
        //Aggiunta di Organizzatori già registrati
        utentiRegistrati.add(new Organizzatore("organizzatrice@gmail.com", "abcd"));
        utentiRegistrati.add(new Organizzatore("organizzatore@gmail.com", "dcba"));*/
    }

    //Metodo che restituisce l'istanza esistente o ne crea una nuova se non esiste già
    public static Registrazione getInstance(VistaRegistrazione vista) {
        if (istanza == null) {
            istanza = new Registrazione(vista);
        }
        return istanza;
    }

    // Costruttore che accetta un'istanza della vista
    //public Registrazione(VistaRegistrazione vista) {
    //    this.vista = vista;
    //}

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
                    // Notifica gli osservatori del login con successo
                    vista.aggiorna("Login eseguito con successo di " + email + "\n");
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
            //Aggiorna la vista
            vista.aggiorna("Errore: l'Utente non è stato trovato\nLog in Annullato\n");
        } else {
            //Questo avviene se l'utente è stato trovato ma la password non
            //corrisponde, dunque bisogna mostrare un errore adeguato
            System.out.println("Errore: Password incorretta");
            //Aggiorna la vista
            vista.aggiorna("Errore: Password incorretta\nLog in Annullato\n");
        }

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
        // Aggiunta facoltativa delle informazioni della carta di credito
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vuoi inserire le informazioni della carta di credito? (s/n)");
        String scelta = scanner.nextLine();
        if (scelta.equalsIgnoreCase("s")) {
            System.out.println("Inserisci il numero della carta di credito:");
            String numeroCarta = scanner.nextLine();
            nuovoUtente.setNumeroCarta(numeroCarta);

            System.out.println("Inserisci la data di scadenza (MM/YY):");
            String dataScadenza = scanner.nextLine();
            nuovoUtente.setDataScadenza(dataScadenza);

            System.out.println("Inserisci il nome del proprietario della carta:");
            String nomeProprietario = scanner.nextLine();
            nuovoUtente.setNomeProprietario(nomeProprietario);

            System.out.println("Inserisci il cognome del proprietario della carta:");
            String cognomeProprietario = scanner.nextLine();
            nuovoUtente.setCognomeProprietario(cognomeProprietario);

            System.out.println("Inserisci il CVV della carta:");
            String cvv = scanner.nextLine();
            nuovoUtente.setCvv(cvv);

            System.out.println("Inserisci il saldo iniziale della carta:");
            double saldo = scanner.nextDouble();
            nuovoUtente.setSaldo(saldo);
        }
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
        for (UtenteRegistrato utente : utentiRegistrati) {
            if (utente.getEmail().equals(email) && utente instanceof ResponsabileBarVenditore) {
                //Setta responsabileBarVenditore dopo averlo trovato, se lo trova
                responsabileBarVenditore = (ResponsabileBarVenditore) utente;
                //Imposta a vero se trova il ResponsabileBarVenditore
                isResponsabileBarVenditore = true;
                break;
            }
        }
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
                    utenteRegistrato.visualizzaEventi();
                    break;
                case 2:
                    //Implementa la visualizzazione della lotteria
                    break;
                case 3:
                    //Implementa la partecipazione alla lotteria
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

//Interfaccia che definisce l'osservatore per gli eventi
interface EventoOsservatore {
    void aggiorna(String messaggio);
}

// Implementazione dell'osservatore che aggiorna la vista
class VistaEvento implements EventoOsservatore {
    private static VistaEvento instance;

    private VistaEvento() {}

    public static VistaEvento getInstance() {
        if (instance == null) {
            instance = new VistaEvento();
        }
        return instance;
    }

    @Override
    public void aggiorna(String messaggio) {
        System.out.println("Vista: " + messaggio);
    }
}

interface ProdottoOsservatore {
    void aggiorna(String messaggio);
}

//Vista (MVC)
//Classe che implementa l'interfaccia RegistrazioneOsservatore
class VistaRegistrazione implements RegistrazioneOsservatore {
    @Override
    public void aggiorna(String messaggio) {
        //Stampa un messaggio il quale indica che la ricezione
        //di una notifica è avvenuta e che la vista
        //è stata aggiornata
        System.out.println("Vista: " + messaggio);
    }
}

class VistaProdotto implements ProdottoOsservatore {
    private static VistaProdotto instance;

    private VistaProdotto() {}

    public static VistaProdotto getInstance() {
        if (instance == null) {
            instance = new VistaProdotto();
        }
        return instance;
    }

    @Override
    public void aggiorna(String messaggio) {
        System.out.println("Vista: " + messaggio);
    }
}

//Classe che contiene il metodo principale del programma
public class Main {
    public static void main(String[] args) {
        //Crea 4 oggetti, due per i controller e due per le viste
        VistaRegistrazione vista = new VistaRegistrazione();

        //VistaEvento vistaEvento = new VistaEvento();
        VistaEvento vistaEvento = VistaEvento.getInstance();

        RegistrazioneController controller = new RegistrazioneController(vista);

        //EventoController eventoController = new EventoController();
        EventoController eventoController = EventoController.getInstance();

        //Impostazione delle viste
        controller.setVista(vista);
        eventoController.setVistaEvento(vistaEvento);
        //Passa l'istanza della vista alla classe Registrazione
        Registrazione registrazione = Registrazione.getInstance(vista);
        //Registrazione registrazione = new Registrazione(vista);
        //Le viste vengono aggiunte come osservatori dei rispettivi controller
        controller.aggiungiOsservatore(vista);
        eventoController.aggiungiOsservatore(vistaEvento);

        // Inizializza il VistaProdotto
        VistaProdotto vistaProdotto = VistaProdotto.getInstance();
        ProdottoController prodottoController = ProdottoController.getInstance();
        prodottoController.aggiungiOsservatore(vistaProdotto);

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