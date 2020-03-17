package cat.copernic.erick.projectm07;


import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class espera {
    public static void espera( int cuanto ){
        try {
            sleep( cuanto );
        } catch (InterruptedException ex) {
            Logger.getLogger(espera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}