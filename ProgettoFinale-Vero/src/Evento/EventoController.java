package Evento;

import java.util.ArrayList;
import java.util.List;

//Gestisce la logica degli Eventi
public class EventoController {
    //Utilizzo di Singleton
    private static EventoController instance;
    private VistaEvento vistaEvento;
    //Creazione della lista di Eventi
    private List<Evento> listaEventi;
    //Creazione della lista di Osservatori per gli Eventi
    private List<EventoOsservatore> osservatori;

    //Costruttore delle liste
    protected EventoController() {
        //Creazione della lista con degli eventi già presenti
        this.listaEventi = new ArrayList<>();
        listaEventi.add(new Evento("Karaoke","Verdi 5","2024-09-10","21:30","23:00"));
        listaEventi.add(new Evento("Concerto","Viola 27","2024-09-11","21:30","23:00"));
        listaEventi.add(new Evento("Degustazione","Viola 3","2024-09-10","15:00","18:00"));
        this.osservatori = new ArrayList<>();
    }

    //Crea un'istanza o la restituisce se c'è già
    public static EventoController getInstance() {
        if (instance == null) {
            instance = new EventoController();
        }
        return instance;
    }

    //Setter della vista
    public void setVistaEvento(VistaEvento vistaEvento) {
        this.vistaEvento = vistaEvento;
    }

    //Aggiunge un osservatore all'elenco
    public void aggiungiOsservatore(EventoOsservatore osservatore) {
        osservatori.add(osservatore);
    }

    //Notifica tutti gli osservatori
    public void notificaOsservatori(String messaggio) {
        for (EventoOsservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }

    //Metodo che aggiunge Evento alla lista
    public void aggiungiEvento(Evento evento) {
        listaEventi.add(evento);
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
        return null; //Ritorna questo nel caso non è stato trovato
    }
}
