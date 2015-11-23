package transform.vicinity;

import transform.vicinity.base.AbstractLinearFilter;
import transform.vicinity.base.Kernel;

public class GaussianBlur extends AbstractLinearFilter{

    int kerSize;
    
    public GaussianBlur(int kerSize){
        this.kerSize = kerSize;
    }
    
    @Override
    protected Kernel createKernel() {
        float data[][] = new float [kerSize][kerSize];
        
        int halfKernel = kerSize / 2;
        
        // We build the gaussian kernel depending on the size given.
        for(int x= halfKernel; x < kerSize; x++){
            for(int y= 0; y < kerSize; y++){
                // Calculate kernel element value
                int value = (int)(Math.pow(x, 2) + Math.pow(y, 2));
                // Since gaussian is symmetrical, we repeat value for the four quadrants.
                data[halfKernel - x][halfKernel - y] = value;
                data[halfKernel - x][halfKernel + y] = value;
                data[halfKernel + x][halfKernel - y] = value;
                data[halfKernel + x][halfKernel + y] = value;
            }
        }
        
        Kernel kernel = new Kernel(data);
        return kernel;
    }

}
