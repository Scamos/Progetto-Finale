package Vista;

import Observer.EventoOsservatore;

//Classe che implementa l'interfaccia dell'osservatore che aggiorna la vista: EventoOsservatore
public class VistaEvento implements EventoOsservatore {
    private static VistaEvento instance;

    //Utilizzo di Singleton
    private VistaEvento() {}

    public static VistaEvento getInstance() {
        if (instance == null) {
            instance = new VistaEvento();
        }
        return instance;
    }

    //Aggiorna tutti gli osservatori
    @Override
    public void aggiorna(String messaggio) {
        //Stampa un messaggio il quale indica che la ricezione
        //di una notifica è avvenuta e che la vista
        //è stata aggiornata
        System.out.println("Vista: " + messaggio);
    }
}
