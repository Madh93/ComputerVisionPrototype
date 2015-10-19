package transform;

import java.awt.Color;

import gui.utils.NamedImage;

public abstract class AbstractImageTransformation {
    
    /**
     * Take an input image and output the transformed image.
     * @param img1 Input image.
     * @return Output (transformed) image.
     */
    public static NamedImage getTransformedImage(NamedImage img1){
        
        return createTransformedImage(img1, createLUT(img1));
    }
    
    /**
     * Having created a look-up table, take the input image and create the output image.
     * @param img1  Input image.
     * @param lut   Look-up table.
     * @return  Output (transformed) image.
     */
    protected static NamedImage createTransformedImage(NamedImage img1, LUT lut){
        
        NamedImage img2 = img1.deepishCopy();
        
        // TODO: La eterna pregunta.
        int imageWidth = img2.getWidth();
        int imageHeight = img2.getHeight();
        
        for (int i = 0; i < imageWidth; i++){
            for (int j = 0; j < imageHeight; j++){

                Color oldPixelColor = new Color(img1.getRGB(i, j));
                
                Color newPixelColor = lut.get(oldPixelColor);
                
                img2.setRGB(i, j, newPixelColor.getRGB());
            }
        }
        
        return img2;
    }
    
    /**
     * Creates transformation table for each unique value in the image.
     * More efficient and scalable than transformExtensive().
     * @param img
     */
    protected static LUT createLUT(NamedImage img){
        
        LUT transTable = new LUT();
        
        // Repeat for each of the colors.
        for (int vIn = 0; vIn < 255; vIn++){
            
            // For this color value, calculate the corresponding new color value.
            // This method will vary depending on the implementing class (Strategy design pattern).
            int vOut = getVOut(vIn, img);
            
            transTable.put(new Color(vIn, vIn, vIn), new Color(vOut, vOut, vOut));
        }
        
        return transTable;
    }
    
    /**
     * Given a color value, return its corresponding new value.
     * This method will be implemented differently by extending classes,
     * leading to the different types of image transformations.
     * @param vIn   Given color value
     * @param img   Complete image data, needed for some transformations.
     * @return New color value that corresponds to the given one.
     */
    protected static int getVOut(int vIn, NamedImage img) {
        // TODO Auto-generated method stub
        return 0;
    }
}
