package ResponsabileBarVenditore;

import java.util.*;

import UtenteRegistrato.*;
import Ordine.*;
import Prodotto.*;

//Classe che rappresenta un Responsabile Bar oppure un Venditore
public class ResponsabileBarVenditore extends UtenteRegistrato {
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //già presenti in UtenteRegistrato

    //Costruttore che ha il compito di creare un nuovo Responsabile Bar
    //oppure un Venditore con gli attributi ereditati e volendo anche
    //nuovi attributi, che saranno ereditati dalle classi che estendono
    //ResponsabileBarVenditore
    public ResponsabileBarVenditore(String email, String password) {
        super(email, password);
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la visualizzazione degli ordini dei clienti
    public void visualizzaOrdiniClienti() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Visualizzazione degli ordini dei clienti:");
        List<Ordine> ordini = getTuttiGliOrdini(); //Ottiene la lista globale degli ordini

        //Controlla se la lista degli ordini è vuota
        if (ordini.isEmpty()) {
            System.out.println("Nessun ordine da visualizzare.");
            return;
        }

        //Mostra tutti gli ordini presenti nella lista
        for (int i = 0; i < ordini.size(); i++) {
            System.out.println("Ordine #" + (i + 1) + ":");
            System.out.println(ordini.get(i));
        }

        int numeroOrdine = -1;
        while (true) {
            System.out.print("Seleziona l'ordine da gestire (numero): ");
            if (scanner.hasNextInt()) {
                numeroOrdine = scanner.nextInt();
                scanner.nextLine();  //Consuma la newline
                if (numeroOrdine >= 1 && numeroOrdine <= ordini.size()) {
                    break;
                } else {
                    //L'input non è nel range valido, chiede quindi di inserire un numero valido
                    System.out.println("Numero ordine non valido. Inserisci un numero valido.");
                }
            } else {
                //L'input non è valido, chiede quindi di inserire un numero
                System.out.println("Input non valido. Inserisci un numero.");
                scanner.nextLine();  //Consuma la newline
            }
        }

        //Ottiene l'ordine in base all'indice selezionato con l'input
        Ordine ordineSelezionato = ordini.get(numeroOrdine - 1);

        System.out.print("Gestisci ordine:\n1) Gestione Ordine Cliente\n2) Gestione Prodotti da Portare Via\n");
        int scelta = scanner.nextInt();
        scanner.nextLine();  //Consuma la newline

        if (scelta == 1) {
            //Chiama il metodo per gestire gli ordini
            gestioneOrdiniClienti(ordineSelezionato);
        } else if (scelta == 2) {
            //Chiama il metodo per gestire i prodotti da portare via
            gestioneProdottiPortareVia(ordineSelezionato);
        } else {
            //Se la scelta non è valida, annulla la scelta e torna al menù
            System.out.println("Scelta non valida. Ritorno al menù principale");
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la ricerca degli ordini
    public void ricercaOrdiniClienti() {
        Scanner scanner = new Scanner(System.in);
        List<Ordine> ordini = getTuttiGliOrdini(); //Ottiene la lista degli ordini

        //Controlla se la lista è vuota
        if (ordini.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
            return;
        }

        int numeroOrdine = -1;
        while (true) {
            System.out.print("Seleziona l'ordine da gestire (numero): ");
            if (scanner.hasNextInt()) {
                numeroOrdine = scanner.nextInt();
                scanner.nextLine();
                if (numeroOrdine >= 1 && numeroOrdine <= ordini.size()) {
                    break;
                } else {
                    //L'input è fuori dal range
                    System.out.println("Numero ordine non valido.");
                }
            } else {
                System.out.println("Per favore, inserisci un numero valido.");
                scanner.next(); //Scarta l'input non valido
            }
        }

        //La selezione è valida quindi si può passare alla gestione dell'ordine
        Ordine ordineSelezionato = ordini.get(numeroOrdine - 1);
        gestioneOrdiniClienti(ordineSelezionato);
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la ricerca degli ordini con prodotti da portare via
    public void ricercaOrdiniPerProdottiPortareVia() {
        Scanner scanner = new Scanner(System.in);
        //Ottiene la lista degli ordini
        List<Ordine> ordini = getTuttiGliOrdini();

        //Controlla se sono presenti ordini
        if (ordini.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
            return;
        }

        List<Ordine> ordiniConPortareVia = new ArrayList<>(); //Crea la lista degli ordini con prodotti da portare via
        for (Ordine ordine : ordini) { //Controlla per ogni ordine se contiene prodotti da portare via, se sì la aggiunge
            if (ordine.getPortareVia().contains(true)) {
                ordiniConPortareVia.add(ordine);
            }
        }

        //Controlla se la lista di ordini con prodotti da portare via è vuota
        if (ordiniConPortareVia.isEmpty()) {
            System.out.println("Nessun ordine con prodotti da portare via trovato.");
            return;
        }

        //Mostra la lista degli ordini in cui sono stati trovati prodotti da portare via
        System.out.println("Ordini con prodotti da portare via:");
        for (int i = 0; i < ordiniConPortareVia.size(); i++) {
            System.out.println("Ordine #" + (i + 1) + ":");
            System.out.println(ordiniConPortareVia.get(i));
        }

        int numeroOrdine = -1;
        while (true) {
            System.out.print("Seleziona l'ordine da gestire (numero): ");
            if (scanner.hasNextInt()) {
                numeroOrdine = scanner.nextInt();
                scanner.nextLine();
                if (numeroOrdine >= 1 && numeroOrdine <= ordiniConPortareVia.size()) {
                    break;
                } else {
                    //L'input inserito non è in range
                    System.out.println("Numero ordine non valido.");
                }
            } else {
                System.out.println("Per favore, inserisci un numero valido.");
                scanner.next(); //Scarta l'input non valido
            }
        }
        //La selezione è valida quindi si può passare alla gestione dell'ordine con prodotti da portare via
        Ordine ordineSelezionato = ordiniConPortareVia.get(numeroOrdine - 1);
        gestioneProdottiPortareVia(ordineSelezionato);
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione degli ordini dei clienti
    public void gestioneOrdiniClienti(Ordine ordine) {
        //Gestione prodotti non da portare via
        //Controlla la presenza di prodotti non da portare via nell'ordine selezionato nella ricerca
        boolean presenzaProdottiNonPortareVia = false;
        for (boolean portareVia : ordine.getPortareVia()) {
            if (!portareVia) {
                presenzaProdottiNonPortareVia = true;
                break;
            }
        }

        //Setta già true se la lista è vuota
        if (!presenzaProdottiNonPortareVia) {
            ordine.setProdottiNonPortareViaCompletati(true);
        }

        if (ordine.getProdottiNonPortareViaCompletati()) {
            boolean tuttiNonPortareViaGestiti = true;
            List<Boolean> nonPortareVia = ordine.getPortareVia(); //Ottiene la lista di prodotti non da portare via
            List<Boolean> prodottiGestiti = ordine.getProdottiGestiti(); //Ottiene la lista di prodotti gestiti
            //Controlla se tutti i prodotti non da portare via sono stati gestiti
            for (int i = 0; i < nonPortareVia.size(); i++) {
                if (!nonPortareVia.get(i) && !prodottiGestiti.get(i)) {
                    tuttiNonPortareViaGestiti = false;
                    break;
                }
            }
            if (!tuttiNonPortareViaGestiti) {
                //Non tutti i prodotti non da portare via sono stati gestiti
                System.out.println("L'ordine ha prodotti non da portare via non ancora gestiti.\nGestisci prima i prodotti non da portare via");
                gestioneProdottiNonPortareVia(ordine);
            } else {
                //Tutti i prodotti non da portare via sono stati gestiti
                System.out.println("L'ordine ha tutti i prodotti non da portare via gestiti.");
            }
        } else {
            //Non tutti i prodotti non da portare via sono stati gestiti
            System.out.println("L'ordine ha prodotti non da portare via non ancora gestiti.\nGestisci prima i prodotti non da portare via");
            gestioneProdottiNonPortareVia(ordine);
        }

        //Gestione prodotti da portare via
        //Controlla la presenza di prodotti da portare via nell'ordine selezionato nella ricerca
        boolean presenzaProdottiPortareVia = false;
        for (boolean portareVia : ordine.getPortareVia()) {
            if (portareVia) {
                presenzaProdottiPortareVia = true;
                break;
            }
        }

        //Setta già true se la lista è vuota
        if (!presenzaProdottiPortareVia) {
            ordine.setProdottiPortareViaCompletati(true);
        }

        if (ordine.getProdottiPortareViaCompletati()) {
            //Controlla se tutti i prodotti da portare via sono stati ritirati
            boolean tuttiRitirati = true;
            List<Boolean> portareVia = ordine.getPortareVia();
            List<Boolean> prodottiRitirati = ordine.getProdottiRitirati();
            for (int i = 0; i < portareVia.size(); i++) {
                if (portareVia.get(i) && !prodottiRitirati.get(i)) {
                    tuttiRitirati = false;
                    break;
                }
            }

            if (tuttiRitirati) {
                //Tutti i prodotti da portare via sono stati ritirati
                System.out.print("Vuoi impostare l'ordine come completato? (s/n): ");
                Scanner scanner = new Scanner(System.in);
                String risposta = scanner.nextLine();
                if (risposta.equalsIgnoreCase("s")) {
                    ordine.setCompletato(true);
                    UtenteRegistrato.rimuoviOrdineGlobal(ordine); //Rimuove l'ordine dalla lista globale
                    ordine.getUtente().spostaOrdineCompletato(ordine); //Chiama il metodo per rimuovere l'Ordine
                    //dalla lista degli ordini attivi per inserirlo in quella degli ordini completati
                    System.out.println("Ordine impostato come completato.");
                } else {
                    //Avvisa sulla scelta di non segnare come completato l'ordine ed esce dal metodo
                    System.out.println("Ordine non impostato come completato.");
                }
            } else {
                //Non tutti i prodotti da portare via sono stati ritirati
                System.out.println("L'ordine ha prodotti da portare via non ancora ritirati. L'ordine non può essere completato.");
            }
        } else {
            //Non tutti i prodotti da portare via sono stati gestiti
            System.out.println("L'ordine ha prodotti da portare via non ancora gestiti. Gestisci prima i prodotti da portare via.");
            gestioneProdottiPortareVia(ordine);
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione dei prodotti non da portare via
    public void gestioneProdottiNonPortareVia(Ordine ordine) {
        //Implementazione della gestione dei prodotti non da portare via
        System.out.println("Gestione prodotti non da portare via:");
        boolean tuttiProdottiNonPortareViaGestiti = true;
        //Ottiene la lista dei prodotti gestiti
        List<Boolean> prodottiGestiti = ordine.getProdottiGestiti();

        //Esegue questo processo per ogni prodotto che trova nel Ordine
        //selezionato precedentemente nella ricerca
        for (int i = 0; i < ordine.getProdotti().size(); i++) {
            if (!ordine.getPortareVia().get(i)) { //Controlla che i prodotto non sono da portare via
                if (!prodottiGestiti.get(i)) { //Controlla se il prodotto è già gestito
                    System.out.println(ordine.getProdotti().get(i).getNome() + " - Quantità: " + ordine.getQuantita().get(i));
                    //Chiede al responsabile se vuole gestire il prodotto
                    System.out.print("Vuoi impostare il Prodotto non da Portare Via come pronto? (s/n): ");
                    Scanner scanner = new Scanner(System.in);
                    String risposta = scanner.nextLine();
                    if (risposta.equalsIgnoreCase("s")) {
                        prodottiGestiti.set(i, true);
                        System.out.println("Prodotto non da Portare Via impostato come pronto.");
                    } else {
                        tuttiProdottiNonPortareViaGestiti = false;
                        System.out.println("Prodotto non da Portare Via non impostato come pronto.");
                    }
                }
            }
        }

        //Metodi chiamati sull'ordine selezionato dalla ricerca
        ordine.setProdottiGestiti(prodottiGestiti); //Metodo per impostare la lista di prodotti gestiti
        ordine.setProdottiNonPortareViaCompletati(tuttiProdottiNonPortareViaGestiti); //Aggiorniamo la lista dei prodotti da ritirare

        //Controlla se i prodotti da portare via sono stati gestiti
        if (tuttiProdottiNonPortareViaGestiti) {
            ordine.setProdottiNonPortareViaCompletati(true);
            System.out.println("Tutti i prodotti non da portare via sono stati gestiti.");
        } else {
            System.out.println("Ci sono ancora prodotti non da portare via da gestire.");
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione dei prodotti da portare via
    public void gestioneProdottiPortareVia(Ordine ordine) {
        //Implementazione della gestione dei prodotti da portare via
        System.out.println("Gestione prodotti da portare via:");
        boolean tuttiProdottiPortareViaGestiti = true;
        List<Boolean> prodottiGestiti = ordine.getProdottiGestiti();  //Ottiene la lista dei prodotti gestiti
        List<Boolean> prodottiRitirati = ordine.getProdottiRitirati(); //Ottiene la lista dei prodotti ritirati

        //Esegue questo processo per ogni prodotto che trova nel Ordine
        //selezionato precedentemente nella ricerca
        for (int i = 0; i < ordine.getProdotti().size(); i++) {
            //Controlla se il prodotto è da portare via
            if (ordine.getPortareVia().get(i)) {
                if (!prodottiGestiti.get(i)) {  //Controlla se il prodotto è già stato gestito
                    System.out.println(ordine.getProdotti().get(i).getNome() + " - Quantità: " + ordine.getQuantita().get(i));
                    //Chiede al responsabile se vuole gestire il prodotto
                    System.out.print("Vuoi impostare il Prodotto da Portare Via come pronto? (s/n): ");
                    Scanner scanner = new Scanner(System.in);
                    String risposta = scanner.nextLine();
                    if (risposta.equalsIgnoreCase("s")) {
                        prodottiGestiti.set(i, true);  //Imposta il prodotto da portare via come gestito
                        prodottiRitirati.set(i, false); //Imposta il prodotto da portare via come da ritirare
                        System.out.println("Prodotto da Portare Via impostato come pronto.");
                    } else {
                        //Avvisa il responsabile della scelta di non gestire il prodotto
                        tuttiProdottiPortareViaGestiti = false;
                        System.out.println("Prodotto da Portare Via non impostato come pronto.");
                    }
                }
            }
        }

        //Metodi chiamati sull'ordine selezionato dalla ricerca
        ordine.setProdottiGestiti(prodottiGestiti); //Metodo per impostare la lista di prodotti gestiti
        ordine.setProdottiRitirati(prodottiRitirati); //Aggiorniamo la lista dei prodotti da ritirare
        ordine.setProdottiPortareViaCompletati(tuttiProdottiPortareViaGestiti); //Metodo per impostare i prodotti da portare via completati

        //Controlla se i prodotti da portare via sono stati gestiti
        if (tuttiProdottiPortareViaGestiti) {
            ordine.setProdottiPortareViaCompletati(true);
            System.out.println("Tutti i prodotti da portare via sono stati gestiti.");
        } else {
            System.out.println("Ci sono ancora prodotti da portare via da gestire.");
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la visualizzazione del magazzino
    public void visualizzaMagazzino() {
        Scanner scanner = new Scanner(System.in);
        //Ottiene il controller e la lista di Prodotto
        ProdottoController prodottoController = ProdottoController.getInstance();
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        //Controlla se la lista di prodotti è vuota
        if (prodotti.isEmpty()) {
            System.out.println("Non ci sono prodotti disponibili al momento");
            return;
        }

        //Mostra i prodotti presenti nella lista e chiede se si vuole gestire il magazzino
        System.out.println("Elenco dei prodotti:");
        for (int i = 0; i < prodotti.size(); i++) {
            System.out.println((i + 1) + ") " + prodotti.get(i).getNome() + " - Quantità: " + prodotti.get(i).getQuantita() + " - Prezzo: " + prodotti.get(i).getPrezzo());
        }

        System.out.print("Vuoi passare alla gestione del magazzino? (s/n): ");
        String rispostaGestione = scanner.nextLine();
        boolean scrittaValida = false;
        while (!scrittaValida){
            switch (rispostaGestione.toLowerCase()){
                case "s":
                    //Avvisa il responsabile e chiama il metodo per gestire il magazzino
                    System.out.println("Passaggio alla gestione del magazzino");
                    gestioneMagazzino();
                    scrittaValida = true;
                    break;
                case "n":
                    //Avvisa il responsabile del ritorno al menù ed esce dal loop
                    System.out.println("Ritorno al menù principale");
                    return;
                default:
                    //Avvisa per una scelta non valida e fa scegliere nuovamente
                    System.out.println("Scelta non valida\nInserisci un valore valido (s/n): ");
                    rispostaGestione = scanner.nextLine();
                    break;
            }
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono per la gestione del magazzino
    public void gestioneMagazzino() {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            //Rimane nel loop fino al completamento di un operazione o
            //fino alla scelta di ResponsabileBarVenditore
            System.out.println("Seleziona un'opzione:");
            System.out.println("1) Aggiungi prodotto");
            System.out.println("2) Modifica prodotto");
            System.out.println("3) Elimina prodotto");
            System.out.println("4) Torna al menù principale");

            int scelta = scanner.nextInt();
            scanner.nextLine();  //Consuma la newline

            switch (scelta) {
                case 1:
                    //Chiama il metodo interno per aggiungere un prodotto
                    aggiungiProdotto();
                    break;
                case 2:
                    //Chiama il metodo interno per modificare un prodotto
                    modificaProdotto();
                    break;
                case 3:
                    //Chiama il metodo interno per eliminare un prodotto
                    eliminaProdotto();
                    break;
                case 4:
                    //Avvisa il responsabile del ritorno al menù ed esce dal loop
                    continua = false;
                    System.out.println("Ritorno al menù principale");
                    break;
                default:
                    //Avvisa per una scelta non valida e fa scegliere nuovamente
                    System.out.println("Scelta non valida, riprova.");
                    break;
            }
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono interno a gestioneMagazzino per l'aggiunta di un prodotto
    public void aggiungiProdotto() {
        Scanner scanner = new Scanner(System.in);
        //Ottiene il controller di Prodotto
        ProdottoController prodottoController = ProdottoController.getInstance();

        //Chiede un nome per questo nuovo prodotto
        System.out.print("Inserisci il nome del prodotto: ");
        String nome = scanner.nextLine();

        //Chiede una descrizione per questo nuovo prodotto
        System.out.print("Inserisci la descrizione del prodotto: ");
        String descrizione = scanner.nextLine();

        //Chiede un prezzo valido per questo nuovo prodotto
        System.out.print("Inserisci il prezzo del prodotto (formato X.XX): ");
        String prezzoInput = scanner.nextLine();
        while (!prezzoInput.matches("\\d+\\.\\d{2}")) {
            System.out.println("Prezzo non valido. Inserisci un prezzo nel formato X.XX: ");
            prezzoInput = scanner.nextLine();
        }
        double prezzo = Double.parseDouble(prezzoInput);

        //Chiede una quantità valida per questo nuovo prodotto
        System.out.print("Inserisci la quantità del prodotto: ");
        int quantita = scanner.nextInt();
        while (quantita < 0) {
            System.out.println("La quantità deve essere superiore o uguale a 0. Inserisci una nuova quantità: ");
            quantita = scanner.nextInt();
        }

        scanner.nextLine();  //Consuma la newline

        //Chiama il metodo per creare il nuovo prodotto, utilizzando il pattern Factory
        Prodotto nuovoProdotto = ProdottoFactory.creaProdotto(nome, descrizione, prezzo, quantita);
        //Aggiungi il nuovo prodotto alla lista, utilizzando il Controller
        prodottoController.getListaProdotti().add(nuovoProdotto);

        System.out.println("Prodotto aggiunto con successo: " + nuovoProdotto);
        prodottoController.notificaOsservatori("Prodotto aggiunto con successo: " + nuovoProdotto);
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono interno a gestioneMagazzino per la modifica di un prodotto
    public void modificaProdotto() {
        Scanner scanner = new Scanner(System.in);
        //Ottiene la lista e il controller di Prodotto
        ProdottoController prodottoController = ProdottoController.getInstance();
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        //Controlla se la lista di prodotti è vuota
        if (prodotti.isEmpty()) {
            System.out.println("Non ci sono prodotti disponibili al momento");
            prodottoController.notificaOsservatori("Modifica non avvenuta.");
            return;
        }

        //Mostra i prodotti e chiede di inserire l'indice corrispondente
        System.out.print("Seleziona il numero corrispondente al prodotto da modificare: ");
        for (int i = 0; i < prodotti.size(); i++) {
            System.out.println((i + 1) + ") " + prodotti.get(i).getNome());
        }
        int sceltaProdotto = scanner.nextInt();
        scanner.nextLine();  //Consuma la newline rimasta

        //Controlla se la scelta del prodotto è valida
        if (sceltaProdotto < 1 || sceltaProdotto > prodotti.size()) {
            System.out.println("Selezione non valida. Ritorno al menù principale.");
            prodottoController.notificaOsservatori("Modifica non avvenuta.");
            return;
        }

        //Seleziona il prodotto in base all'indice inserito in input
        Prodotto prodottoSelezionato = prodotti.get(sceltaProdotto - 1);
        System.out.println("Hai selezionato il prodotto: " + prodottoSelezionato.getNome());
        boolean continua = true;
        while (continua) {
            //Rimane nel menù finché non decide diversamente il Responsabile
            System.out.println("Cosa vuoi modificare?");
            System.out.println("1) Nome prodotto");
            System.out.println("2) Descrizione prodotto");
            System.out.println("3) Prezzo prodotto");
            System.out.println("4) Quantità prodotto (Aggiungi quantità)");
            System.out.println("5) Annulla modifica");

            int scelta = scanner.nextInt();
            scanner.nextLine();  //Consuma la newline

            switch (scelta) {
                case 1:
                    //Mostra il nome attuale e chiede di aggiungerne uno nuovo
                    System.out.print("Inserisci il nuovo nome del prodotto (attuale: " + prodottoSelezionato.getNome() + "): ");
                    //Prende il nuovo nome inserito e chiama il Setter del nome
                    String nuovoNome = scanner.nextLine();
                    prodottoSelezionato.setNome(nuovoNome);
                    System.out.println("Nome del prodotto modificato con successo.");
                    prodottoController.notificaOsservatori("Nome aggiornato per il prodotto. - Nuovo nome: " + prodottoSelezionato.getNome());
                    break;
                case 2:
                    //Mostra la descrizione attuale e chiede di aggiungerne una nuova
                    System.out.print("Inserisci la nuova descrizione del prodotto (attuale: " + prodottoSelezionato.getDescrizione() + "): ");
                    //Prende la nuova descrizione inserita e chiama il Setter della descrizione
                    String nuovaDescrizione = scanner.nextLine();
                    prodottoSelezionato.setDescrizione(nuovaDescrizione);
                    System.out.println("Descrizione del prodotto modificata con successo.");
                    prodottoController.notificaOsservatori("Descrizione aggiornata per il prodotto: " + prodottoSelezionato.getNome() + " - Nuova descrizione: " + prodottoSelezionato.getDescrizione());
                    break;
                case 3:
                    //Mostra il prezzo attuale e chiede di aggiungere un prezzo valido
                    System.out.print("Inserisci il nuovo prezzo del prodotto (attuale: " + prodottoSelezionato.getPrezzo() + "): ");
                    String prezzoInput = scanner.nextLine();
                    while (!prezzoInput.matches("\\d+\\.\\d{2}")) {
                        System.out.println("Prezzo non valido. Inserisci un prezzo nel formato X.XX: ");
                        prezzoInput = scanner.nextLine();
                    }
                    //Prende il nuovo prezzo inserito e chiama il Setter del prezzo
                    double nuovoPrezzo = Double.parseDouble(prezzoInput);
                    prodottoSelezionato.setPrezzo(nuovoPrezzo);
                    System.out.println("Prezzo del prodotto modificato con successo.");
                    prodottoController.notificaOsservatori("Prezzo aggiornato per il prodotto: " + prodottoSelezionato.getNome() + " - Nuovo prezzo: " + prodottoSelezionato.getPrezzo());
                    break;
                case 4:
                    //Mostra la quantità attuale e chiede di aggiungere una quantità valida
                    System.out.print("Aggiungi una quantità al prodotto (attuale: " + prodottoSelezionato.getQuantita() + "): ");
                    int nuovaQuantita = scanner.nextInt();
                    while (nuovaQuantita <= 0) {
                        System.out.println("La quantità deve essere superiore a 0. Inserisci una nuova quantità: ");
                        nuovaQuantita = scanner.nextInt();
                    }
                    //Chiama il metodo per implementare la quantità inserita alla quantità già presente
                    prodottoSelezionato.incrementaQuantita(nuovaQuantita - prodottoSelezionato.getQuantita());
                    prodottoController.notificaOsservatori("Quantità aggiornata per il prodotto: " + prodottoSelezionato.getNome() + " - Nuova quantità: " + prodottoSelezionato.getQuantita());
                    System.out.println("Quantità del prodotto modificata con successo.");
                    break;
                case 5:
                    //Esci dal loop avvisando il Responsabile dell'annullamento della modifica
                    continua = false;
                    System.out.println("Modifica annullata. Ritorno al menù principale.");
                    prodottoController.notificaOsservatori("Modifica annullata.");
                    break;
                default:
                    //Avvisa della scelta non valida e fa ripetere la scelta
                    System.out.println("Scelta non valida, riprova.");
                    break;
            }
        }
    }

    //Metodo specifico della classe ResponsabileBarVenditore o delle classi
    //che la estendono interno a gestioneMagazzino per l'eliminazione di un prodotto
    public void eliminaProdotto() {
        Scanner scanner = new Scanner(System.in);
        //Ottiene il controller e la lista per i prodotti
        ProdottoController prodottoController = ProdottoController.getInstance();
        List<Prodotto> prodotti = prodottoController.getListaProdotti();

        //Controlla se ci sono prodotti nella lista
        if (prodotti.isEmpty()) {
            System.out.println("Non ci sono prodotti disponibili al momento.");
            prodottoController.notificaOsservatori("Eliminazione non avvenuta.");
            return;
        }

        //Mostra la lista di prodotti presenti
        System.out.println("Seleziona il prodotto da eliminare:");
        for (int i = 0; i < prodotti.size(); i++) {
            System.out.println((i + 1) + ") " + prodotti.get(i).getNome());
        }

        int indiceProdotto = scanner.nextInt() - 1;
        scanner.nextLine();  //Consuma la newline

        //Controlla se l'indice fornito è valido
        if (indiceProdotto < 0 || indiceProdotto >= prodotti.size()) {
            System.out.println("Indice prodotto non valido. Ritorno al menù principale.");
            prodottoController.notificaOsservatori("Eliminazione non avvenuta.");
            return;
        }

        //Selezione il prodotto in base all'indice fornito come input
        Prodotto prodottoDaEliminare = prodotti.get(indiceProdotto);

        boolean confermaValid = false;
        while (!confermaValid) {
            //Chiede conferma per poter eliminare il prodotto
            System.out.print("Sei sicuro di voler eliminare il prodotto " + prodottoDaEliminare.getNome() + "? (s/n): ");
            String conferma = scanner.nextLine().trim().toLowerCase();

            switch (conferma) {
                case "s":
                    //Rimuovo il Prodotto selezionato utilizzando l'indice inserito dal Responsabile
                    Prodotto prodottoEliminato = prodotti.remove(indiceProdotto);
                    System.out.println("Prodotto eliminato con successo: " + prodottoEliminato);
                    prodottoController.notificaOsservatori("Prodotto eliminato con successo: " + prodottoEliminato);
                    confermaValid = true;
                    break;
                case "n":
                    //Avvisa il Responsabile dell'operazione annullata ed esce dal loop
                    System.out.println("Eliminazione annullata.");
                    prodottoController.notificaOsservatori("Eliminazione annullata.");
                    confermaValid = true;
                    break;
                default:
                    //Avvisa della scelta non valida e chiede di reinserire l'input
                    System.out.println("Scelta non valida. Inserisci un valore valido (s/n): ");
                    break;
            }
        }
    }
    //Utilizzando l'ereditarietà, non è necessario riscrivere questi metodi
    //Getter e Setter
}
