package Model;

//Classe che rappresenta un oggetto Prodotto
public class Prodotto {
    //Attributi privati di Prodotto, che rappresentano le informazioni di base
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;

    //Costruttore che ha il compito di creare un nuovo Prodotto
    //con i suoi attributi
    public Prodotto(String nome, String descrizione, double prezzo, int quantita) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    //Getter del nome del prodotto
    public String getNome() {
        return nome;
    }

    //Setter del nome del prodotto
    public void setNome(String nome) {
        this.nome = nome;
    }

    //Getter della descrizione del prodotto
    public String getDescrizione() {
        return descrizione;
    }

    //Setter della descrizione del prodotto
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    //Getter del prezzo del prodotto
    public double getPrezzo() {
        return prezzo;
    }

    //Setter del prezzo del prodotto
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    //Getter della quantità di un prodotto
    public int getQuantita() {
        return quantita;
    }

    //Metodo per aumentare il quantitativo di un prodotto
    public void incrementaQuantita(int quantita) {
        this.quantita += quantita;
    }

    //Metodo per diminuire il quantitativo di un prodotto
    public void decrementaQuantita(int quantita) {
        if (this.quantita >= quantita) {
            this.quantita -= quantita;
        }
    }

    //Override del metodo toString che serve per poter
    //rappresentare l'oggetto in maniera leggibile
    @Override
    public String toString() {
        return "Prodotto{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + String.format("%.2f", prezzo) +
                ", quantita=" + quantita +
                '}';
    }
}
