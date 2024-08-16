package Vista;

import Observer.ProdottoOsservatore;

//Classe che implementa l'interfaccia ProdottoOsservatore
public class VistaProdotto implements ProdottoOsservatore {
    private static VistaProdotto instance;
    private static boolean tornaAlMenu = false;

    //Utilizzo di Singleton
    private VistaProdotto() {}

    public static VistaProdotto getInstance() {
        if (instance == null) {
            instance = new VistaProdotto();
        }
        return instance;
    }

    //Metodi utilizzati in acquistoProdotti per definire quando la funzione
    //è terminata e quindi è necessario tornare al menù
    //Setter di torna al menù
    public static void setTornaAlMenu(boolean torna) {
        tornaAlMenu = torna;
    }

    //Getter di torna al menù
    public static boolean getTornaAlMenu() {
        return tornaAlMenu;
    }

    //Aggiorna tutti gli osservatori, in questo caso 1
    @Override
    public void aggiorna(String messaggio) {
        System.out.println("Vista: " + messaggio);
    }
}
