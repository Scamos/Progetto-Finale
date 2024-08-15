package Organizzatore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import Evento.*;
import ResponsabileBarVenditore.*;
import UtenteRegistrato.*;

//Classe che rappresenta un Organizzatore
public class Organizzatore extends ResponsabileBarVenditore {
    //Utilizzando l'ereditarietà, non è necessario riscrivere gli attributi
    //già presenti in UtenteRegistrato o in ResponsabileBarVenditore

    //Costruttore che ha il compito di creare un nuovo Organizzatore
    //con gli attributi ereditati e volendo anche nuovi attributi specifici
    public Organizzatore(String email, String password) {
        super(email, password);
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
                    } else {
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
                    } else {
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
    protected void modificaEvento() {
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
    protected void eliminaEvento() {
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
    //Getter e Setter
}