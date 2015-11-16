package gui.utils.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;


public class NamedImage extends BufferedImage {
    private File file;
    private ColorHistogram pixelColorCount = new ColorHistogram();
    private Boolean validColorCount = false;    // So that we only count pixels if needed.
    private Boolean grayscale;  // Whether this image is grayscale
    private BrightnessAndContrast brightnessAndContrast = new BrightnessAndContrast();
    
    public NamedImage(BufferedImage bi, File file){
        super(bi.getColorModel(), bi.getRaster(), bi.getColorModel().isAlphaPremultiplied(), null);
        this.file = file;

        // TODO: seria posible que carguemos una imagen que no tenga ni
        // Asumimos que si no tiene 1 banda, tendra 3.
        if(this.getRaster().getNumBands() == 1){
            grayscale = true;
        } else {
            grayscale = false;
        }
    }
    
    /**
     * Returns the total number of pixels this image has. 
     * (this.getWidth() * this.getHeight() synonym)
     * @return  Number of pixels this image has.
     */
    public int getPixelCount(){
        // TODO seguro que getWidth() y getHeight() devuelven numero de pixeles nativos?? o de display?
        return this.getWidth() * this.getHeight();
    }
    
    /**
     * Returns the color for pixel in x and y coordinates
     * @return Color if exists pixel
     */
    public Color getPixelColor(int x, int y) {
        if (validPixel(x,y)){
            // TODO: Cuidado. Comprobar que esas bandas son asi. 
            if (isGrayscale()){
                return new Color(
                        this.getRaster().getSample(x,y,0),
                        this.getRaster().getSample(x,y,0),
                        this.getRaster().getSample(x,y,0));
            } else {
            return new Color(
                    this.getRaster().getSample(x,y,0),
                    this.getRaster().getSample(x,y,1),
                    this.getRaster().getSample(x,y,2));
            }
        }
        else
            return null;
    }
    
    /**
     * Validate x and y coordinates for image
     * @return boolean
     */
    private boolean validPixel(int x, int y) {
        if (x < this.getWidth() && y < this.getHeight())
            return true;
        else
            return false;
    }

    /**
     * Provides lazy evaluation simulation.
     * @return Number of pixels for each color in the image.
     */
    public ColorHistogram getPixelColorCount() {
        if(!validColorCount){
            countPixelsByColor();
        }
        return pixelColorCount;
    }
    
    /**
     * Count number of pixels for each color in the image.
     * Store the result in the internal pixelColorCount hash.
     * This costly operation is only called from getPixelColorCount(),
     * (when the pixelColorCount is needed) simulating lazy evaluation.
     */
    private void countPixelsByColor(){
        
        // TODO: Asegurarnos que BufferedImage nunca presenta informacion de conteo de colores falsa.

        pixelColorCount.clear();
        
        Raster raster = this.getRaster();
        int imageWidth = this.getWidth();
        int imageHeight = this.getHeight();
        
        for (int i = 0; i < imageWidth; i++){
            for (int j = 0; j < imageHeight; j++){
                // TODO: Demasiado ineficiente crear un Color cada vez?
                Color pixelColor = new Color(raster.getSample(i,j,0));
                if(!pixelColorCount.containsKey(pixelColor)){
                    pixelColorCount.put(pixelColor, 1);
                } else {
                    pixelColorCount.put(pixelColor, pixelColorCount.get(pixelColor) + 1);
                }
            }
        }
        
        validColorCount = true;
    }
    
    // TODO Recordar por que es deepish y no es deep.
    public NamedImage deepishCopy() {
        
        ColorModel cm = this.getColorModel();
        
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        
        WritableRaster raster = this.copyData(null);
        
        BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        
        return new NamedImage(bi, this.getFile());
    }
    
    /*
     * Interfaces con el File.
     */
    
    public String getName(){
        return file.getName();
    }
    
    public String getExtension() {
        
        int i = getName().lastIndexOf('.');
        return getName().substring(i+1);   
    }
    
    public String getDirectory() {
        return file.getAbsoluteFile().getParentFile().getAbsolutePath();
    }
    
    public File getFile() {
        return file;
    }
    
    /*
     * Setters and getters.
     */

    public Boolean isGrayscale() {
        return grayscale;
    }
    
    public float getBrightness() {
        return brightnessAndContrast.getBrightness(this);
    }
    
    public float getContrast() {
        return brightnessAndContrast.getContrast(this);
    }
}