package a02a.e1;

import java.util.function.Predicate;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        return new AbstractDiet() {

            @Override
            public void getCostraint() {
                this.setPredicate(f -> f < 2000 && f > 1500);
            }
            
        };
    }

    @Override
    public Diet lowCarb() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lowCarb'");
    }

    @Override
    public Diet highProtein() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'highProtein'");
    }

    @Override
    public Diet balanced() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'balanced'");
    }
    
}
