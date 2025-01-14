package Factory;

import Model.Registrazione;
import Vista.VistaRegistrazione;

//Classe che contiene il metodo statico che restituisce un'istanza di Registrazione
public class RegistrazioneFactory {
    public static Registrazione getRegistrazione(VistaRegistrazione vista) {
        return Registrazione.getInstance(vista);
    }
}
