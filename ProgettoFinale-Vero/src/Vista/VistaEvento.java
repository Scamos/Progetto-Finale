package Vista;

import Observer.EventoOsservatore;

//Implementazione dell'osservatore che aggiorna la vista
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

    //Aggiorna tutti gli osservatori, in questo caso solo 1
    @Override
    public void aggiorna(String messaggio) {
        System.out.println("Vista: " + messaggio);
    }
}
