package gui.menubar;

import javax.swing.JMenuItem;

import gui.i18n.GUIStr;
import gui.i18n.I18n;

/**
 *  Clase wrapper necesaria para permitir la identificacion univoca de los JMenuItem,
 *  sin importar la String que usen para mostrarse al mundo. 
 */
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