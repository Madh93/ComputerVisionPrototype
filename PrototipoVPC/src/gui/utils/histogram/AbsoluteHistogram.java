package gui.utils.histogram;

import java.util.ArrayList;
import java.util.HashMap;

import gui.utils.NamedImage;

public class AbsoluteHistogram extends AbstractHistogram {

    public AbsoluteHistogram(NamedImage image) {
        super("Diagrama Absoluto", image.getName());
        
        ArrayList pixelArray = getPixelArray(image);
        this.showHistogram(pixelArray);
    }
    
    /**
     * Devuelve un vector ordenado con los píxeles a partir
     * del conteo de pixeles de colores de la imagen
     * @return  el vector ordenado del nº pixeles/color
     */ 
    private ArrayList getPixelArray(NamedImage image) {
        
        ArrayList pixelArray = new ArrayList();
        HashMap<Integer, Integer> pixelIntegerCount = this.getColorToInteger(image.getPixelColorCount());
        
        for (int i=0; i<=255; i++) {
            
            Integer pixel = pixelIntegerCount.get(i);
            
            if (pixel != null)
                pixelArray.add(pixel);
            else
                pixelArray.add(0);
        }
        
        return pixelArray;
    }

}
