package Observer;

//Interfaccia che definisce l'osservatore per i prodotti
public interface ProdottoOsservatore {
    //Questo è un metodo per aggiornare la vista con un nuovo messaggio, il quale
    //stampa un messaggio; questo indica che la ricezione di una notifica è stata effettuata
    void aggiorna(String messaggio);
}
