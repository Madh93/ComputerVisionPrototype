package processing;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class BrightnessAndContrast {
    
    public void getBrightness(BufferedImage img){
        float sigma;
        float ro;
    }
    
    // TODO: Only works for grayscale images.
    public void setBrightness(BufferedImage img, int newBrightness, int newContrast){
        int oldBrightness = 0;
        int oldContrast = 0;
        
        float A = newBrightness / oldBrightness;
        float B = newContrast - A * oldContrast;
        
        HashMap<Integer, Integer> tabla = new HashMap<Integer, Integer>();
        
        for (int vIn = 0; vIn < 255; vIn++){
            
            int vOut = (int)(A * vIn + B);
            
            // Handle out-of-range values.
            if (vOut < 0){
                vOut = 0;
            }
            if (vOut > 255){
                vOut = 255;
            }
            // TODO: When there are out-of-range vOut values, we are giving a false result.
            
            tabla.put(vIn, vOut);
        }
        
        // Cuantos valores distintos da la tabla
        // Siempre seran igual o menos
    }
}
