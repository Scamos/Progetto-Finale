package Registrazione;

//Observer
//Interfaccia che definisce il metodo che gli osservatori
//devono implementare per ricevere notifiche
public interface RegistrazioneOsservatore {
    //Questo è un metodo per aggiornare la vista con un nuovo messaggio, il quale
    //stampa un messaggio; questo indica che la ricezione di una notifica è stata effettuata
    void aggiorna(String messaggio);
}
