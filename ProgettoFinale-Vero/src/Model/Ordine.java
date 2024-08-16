package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Classe che rappresenta un oggetto Ordine
public class Ordine {
    //Attributi privati di Ordine, che rappresentano le informazioni di base
    private UtenteRegistrato utente;
    private List<Prodotto> prodotti;
    private List<Integer> quantita;
    private List<Boolean> portareVia;
    private List<Boolean> prodottiGestiti;
    private List<Boolean> prodottiRitirati;
    private double costoTotale;
    private boolean completato;
    private boolean prodottiPortareViaCompletati;
    private boolean prodottiNonPortareViaCompletati;

    //Costruttore che ha il compito di creare un nuovo Ordine
    //con i suoi attributi
    public Ordine(UtenteRegistrato utente, List<Prodotto> prodotti, List<Integer> quantita, List<Boolean> portareVia, double costoTotale) {
        this.utente = utente;
        this.prodotti = prodotti;
        this.quantita = quantita;
        this.portareVia = portareVia;
        this.prodottiGestiti = new ArrayList<>(Collections.nCopies(prodotti.size(), false));
        this.prodottiRitirati = new ArrayList<>(Collections.nCopies(prodotti.size(), false));
        this.costoTotale = costoTotale;
        this.completato = false;
        this.prodottiPortareViaCompletati = false;
        this.prodottiNonPortareViaCompletati = false;
    }

    //Getter del Utente Registrato
    public UtenteRegistrato getUtente() {
        return utente;
    }

    //Getter dei prodotti
    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    //Getter della lista dei prodotti gestiti
    public List<Integer> getQuantita() {
        return quantita;
    }

    //Getter dei prodotti da portare via
    public List<Boolean> getPortareVia() {
        return portareVia;
    }

    //Getter dei prodotti gestiti
    public List<Boolean> getProdottiGestiti() {
        return prodottiGestiti;
    }

    //Setter dei prodotti gestiti
    public void setProdottiGestiti(List<Boolean> prodottiGestiti) {
        this.prodottiGestiti = prodottiGestiti;
    }

    //Getter dei prodotti ritirati
    public List<Boolean> getProdottiRitirati() {
        return prodottiRitirati;
    }

    //Setter dei prodotti ritirati
    public void setProdottiRitirati(List<Boolean> prodottiRitirati) {
        this.prodottiRitirati = prodottiRitirati;
    }

    //Getter di costoTotale
    public double getCostoTotale() {
        return costoTotale;
    }

    //Getter di ordine completato
    public boolean getCompletato() {
        return completato;
    }

    //Setter di ordine completato
    public void setCompletato(boolean completato) {
        this.completato = completato;
    }

    //Getter di prodotti non da portare via completati
    public boolean getProdottiPortareViaCompletati() {
        return prodottiPortareViaCompletati;
    }

    //Setter di prodotti da portare via completati
    public void setProdottiPortareViaCompletati(boolean prodottiPortareViaCompletati) {
        this.prodottiPortareViaCompletati = prodottiPortareViaCompletati;
    }

    //Getter di prodotti non da portare via completati
    public boolean getProdottiNonPortareViaCompletati() {
        return prodottiNonPortareViaCompletati;
    }

    //Setter di prodotti non da portare via completati
    public void setProdottiNonPortareViaCompletati(boolean prodottiNonPortareViaCompletati) {
        this.prodottiNonPortareViaCompletati = prodottiNonPortareViaCompletati;
    }

    //Override del metodo toString che serve per poter
    //rappresentare l'oggetto in maniera leggibile
    @Override
    public String toString() {
        return "Ordine{" +
                "utente=" + utente.getEmail() +
                ", prodotti=" + prodotti +
                ", quantita=" + quantita +
                ", portareVia=" + portareVia +
                ", costoTotale=" + String.format("%.2f", costoTotale) +
                ", completato=" + completato +
                '}';
    }
}
