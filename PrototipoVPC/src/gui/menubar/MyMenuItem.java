package gui.menubar;

import javax.swing.JMenuItem;

import i18n.GUIStr;
import i18n.I18n;

/**
 *  Clase wrapper necesaria para permitir la identificacion univoca de los JMenuItem,
 *  sin importar la String que usen para mostrarse al mundo. 
 */
@SuppressWarnings("serial")
public class MyMenuItem extends JMenuItem {
    GUIStr stringId;
    
    MyMenuItem(GUIStr stringId){
        super(I18n.getString(stringId));
        this.stringId = stringId;
    }
    
    /*
     * Getters and setters.
     */

    public GUIStr getStringId() {
        return stringId;
    }
}
