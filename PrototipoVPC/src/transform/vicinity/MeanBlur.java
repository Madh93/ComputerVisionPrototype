package transform.vicinity;

import transform.vicinity.base.AbstractLinearSingleFilter;
import transform.vicinity.base.Kernel;

public class MeanBlur extends AbstractLinearSingleFilter{

    @Override
    protected Kernel createKernel() {
        float data[][] = 
            {   
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1},
            };
        Kernel kernel = new Kernel(data);
        return kernel;
    }

}
