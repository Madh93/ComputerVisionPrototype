package gui.menubar;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import gui.dialog.BrightnessContrastWombo;
import gui.utils.RegionSelector;
import gui.utils.image.Entropy;
import gui.utils.image.NamedImage;
import i18n.GUIStr;
import i18n.I18n;
import main.MainWindow;
import transform.point.ColorToGrayscale;

@SuppressWarnings("serial")
public class ImageMenu extends AbstractMenu{
    
    final static GUIStr[] ACTION_NAMES =
        {
        GUIStr.IMAGE_MENU_CROP_SELECTION,
        GUIStr.SEPARATOR,
        GUIStr.IMAGE_MENU_TO_GRAYSCALE,
        GUIStr.SEPARATOR,
        GUIStr.IMAGE_MENU_ENTROPY,
        GUIStr.IMAGE_MENU_BRIGHTNESS_CONTRAST
        };
    
    public ImageMenu(MainWindow parentFrame) {
        super(parentFrame, GUIStr.IMAGE_MENU, ACTION_NAMES);
    }
    
    
    @SuppressWarnings("incomplete-switch")
    @Override
    protected void actionPerformedHandler(GUIStr actionName, ActionEvent e) {

        switch(actionName){
        case IMAGE_MENU_CROP_SELECTION:
            cropSelectionActionPerformed(e);
            break;
        case IMAGE_MENU_TO_GRAYSCALE:
            toGrayscaleActionPerformed(e);
            break;
        case IMAGE_MENU_ENTROPY:
            entropyActionPerformed(e);
            break;
        case IMAGE_MENU_BRIGHTNESS_CONTRAST:
            brightnessAndContrastActionPerformed(e);
            break;
        }
    }
    
    private void cropSelectionActionPerformed(ActionEvent e) {
    
        NamedImage image = null;
        RegionSelector selection = parentFrame.getFocusedSelector();
        Point origin = selection.getOrigin();
        Point end = selection.getEnd();
        
            
        if (origin != null && end != null) {
            
            int x = (int)Math.min(origin.getX(),end.getX());
            int y = (int)Math.min(origin.getY(),end.getY());
            int width = (int)Math.max(origin.getX(),end.getX()) - x;
            int height = (int)Math.max(origin.getY(),end.getY()) - y;
            
            image = parentFrame.getFocusedImage();
            BufferedImage bi = image.getSubimage(x, y, width, height);
            NamedImage image2 = new NamedImage(bi,image.getFile());
            
            parentFrame.createImageFrame(image2);
        }
    }
    
    private void toGrayscaleActionPerformed(ActionEvent e){
        if (!assertImageSelected()) return;
        if(parentFrame.getFocusedImage().isGrayscale()){
            parentFrame.showErrorDialog(GUIStr.DIALOG_ERROR_GRAYSCALE_IMAGE);
            return;
        }
        
        transform(new ColorToGrayscale());   
    }
    
    private void entropyActionPerformed(ActionEvent e){
        
        if (!assertImageSelected()) return;
        
        float entropy = Entropy.calculate(parentFrame.getFocusedImage());
        
        JOptionPane.showMessageDialog(parentFrame,
                I18n.getString(GUIStr.IMAGE_MENU_ENTROPY_TEXT) + " " + entropy,
                I18n.getString(GUIStr.IMAGE_MENU_ENTROPY),
                JOptionPane.PLAIN_MESSAGE); 
    }
    
    private void brightnessAndContrastActionPerformed(ActionEvent e) {
    
        NamedImage image = parentFrame.getFocusedImage();
        BrightnessContrastWombo dialog = null;
        
        if (image == null)
            parentFrame.showErrorDialog(GUIStr.DIALOG_ERROR_NO_SELECTED_IMAGE);
        else 
            dialog = new BrightnessContrastWombo(parentFrame,image);    
    }
        
}
