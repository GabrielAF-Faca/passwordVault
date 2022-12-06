package passwordVaultApp;

import controller.Controller;
import view.Menu;

/**
 *
 * @author Gabriel Azenha Fachim
 */
public class Main {

    
    public static void main(String[] args) {
        
        Controller c = new Controller(new Menu());
        
        c.initController();
        
    }
    
}
