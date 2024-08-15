package Evento;

import java.time.LocalDate;
import java.time.LocalTime;

//Classe che rappresenta un oggetto Evento
public class Evento {
    //Attributi privati di Evento, che rappresentano le informazioni di base
    private String nome;
    private String via;
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private int numPartecipanti;

    //Costruttore che ha il compito di creare un nuovo Evento
    //con i suoi attributi
    public Evento(String nome, String via, String data, String oraInizio, String oraFine) {
        this.nome = nome;
        this.via = via;
        this.data = LocalDate.parse(data);
        this.oraInizio = LocalTime.parse(oraInizio);
        this.oraFine = LocalTime.parse(oraFine);
        this.numPartecipanti = 0;
    }

    //Metodi getter e setter di Evento
    //Getter di nome
    public String getNome() {
        return nome;
    }

    //Setter di nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    //Getter di via
    public String getVia() {
        return via;
    }

    //Setter di via
    public void setVia(String via) {
        this.via = via;
    }

    //Getter di data
    public LocalDate getData() {
        return data;
    }

    //Setter di data
    public void setData(LocalDate data) {
        this.data = data;
    }

    //Getter di ora inizio
    public LocalTime getOraInizio() {
        return oraInizio;
    }

    //Setter di ora inizio
    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    //Getter di ora fine
    public LocalTime getOraFine() {
        return oraFine;
    }

    //Setter di ora fine
    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    //Getter di posti rimanenti
    public int getNumPartecipanti() {
        return numPartecipanti;
    }

    //Setter di posti rimanenti
    public void setNumPartecipanti(int numPartecipanti) {
        this.numPartecipanti = numPartecipanti;
    }

    //Metodo che viene chiamato a ogni nuovo partecipante
    //a un Evento per aumentare il corrispettivo numero di partecipanti
    public void incrementaPartecipanti() {
        this.numPartecipanti++;
    }

    //Metodo che viene chiamato a ogni partecipante che non partecipa più
    //a un Evento per aumentare il corrispettivo numero di partecipanti
    public void decrementaPartecipanti() {
        if (this.numPartecipanti > 0) {
            this.numPartecipanti--;
        }
    }

    //Override del metodo toString che serve per poter
    //rappresentare l'oggetto in maniera leggibile
    @Override
    public String toString() {
        return "Evento{" +
                "nome='" + nome + '\'' +
                ", via='" + via + '\'' +
                ", data=" + data +
                ", oraInizio=" + oraInizio +
                ", oraFine=" + oraFine +
                '}';
    }
}