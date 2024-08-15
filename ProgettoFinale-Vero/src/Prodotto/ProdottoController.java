package Prodotto;

import java.util.*;

//Gestisce la logica inerente ai prodotti
public class ProdottoController {
    //Utilizzo di Singleton
    private static ProdottoController instance;
    private List<Prodotto> listaProdotti;
    private List<ProdottoOsservatore> osservatori;

    //Costruttore selle liste
    private ProdottoController() {
        //Creazione della lista con dei prodotti già presenti
        this.listaProdotti = new ArrayList<>();
        listaProdotti.add(new Prodotto("Pane", "Pane fresco in cassetta", 5.00, 15));
        listaProdotti.add(new Prodotto("Pasta", "Pasta fresca 500g", 3.00, 20));
        listaProdotti.add(new Prodotto("Pomodoro", "Pomodoro fresco 100g", 2.00, 25));
        this.osservatori = new ArrayList<>();
    }

    //Crea un'istanza o la restituisce se c'è già
    public static ProdottoController getInstance() {
        if (instance == null) {
            instance = new ProdottoController();
        }
        return instance;
    }

    //Metodo per aggiungere un nuovo osservatore
    public void aggiungiOsservatore(ProdottoOsservatore osservatore) {
        osservatori.add(osservatore);
    }

    //Metodo per inviare una notifica a tutti gli osservatori
    public void notificaOsservatori(String messaggio) {
        for (ProdottoOsservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }

    //Metodo per restituire la lista dei prodotti
    public List<Prodotto> getListaProdotti() {
        return listaProdotti;
    }

    //Metodo per controllare la lista dei prodotti e verificare la presenza di un prodotto
    //chiamato allo stesso modo dell'input fornito
    public Prodotto trovaProdotto(String nomeProdotto) {
        for (Prodotto prodotto : listaProdotti) {
            if (prodotto.getNome().equalsIgnoreCase(nomeProdotto)) {
                return prodotto;
            }
        }
        return null;
    }
}
