package Registrazione;

//Vista (MVC)
//Classe che implementa l'interfaccia RegistrazioneOsservatore
public class VistaRegistrazione implements RegistrazioneOsservatore {
    @Override
    public void aggiorna(String messaggio) {
        //Stampa un messaggio il quale indica che la ricezione
        //di una notifica è avvenuta e che la vista
        //è stata aggiornata
        System.out.println("Vista: " + messaggio);
    }
}
