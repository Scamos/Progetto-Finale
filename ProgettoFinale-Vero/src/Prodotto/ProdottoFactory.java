package Prodotto;

//Classe che contiene il metodo statico che crea un Prodotto e lo restituisce
public class ProdottoFactory {
    public static Prodotto creaProdotto(String nome, String descrizione, double prezzo, int quantita) {
        return new Prodotto(nome, descrizione, prezzo, quantita);
    }
}
