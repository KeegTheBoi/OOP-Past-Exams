package a02b.e1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        return new AbstractUniversityProgram(){
            @Override
            public void setFixedMinimumCredit() {
                setMapperMinimum(Collections.emptyMap());
                setCreditBound(c -> c == 60);
            }
        };       
    }

    @Override
    public UniversityProgram scientific() {
        return new AbstractUniversityProgram(){
            @Override
            public void setFixedMinimumCredit() {
                final Predicate<Integer> fixedPredicate =  t -> t >= 12;
                setMapperMinimum(Map.of(List.of(Sector.MATHEMATICS), fixedPredicate, List.of(Sector.COMPUTER_SCIENCE), fixedPredicate, List.of(Sector.PHYSICS), fixedPredicate));
                setCreditBound(c -> c == 60);
            }
          
        };   
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return new AbstractUniversityProgram(){
            @Override
            public void setFixedMinimumCredit() {
                setMapperMinimum(Map.of(List.of(Sector.COMPUTER_ENGINEERING, Sector.COMPUTER_SCIENCE), c -> c >= 30));
                setCreditBound(c -> c >= 48);
            }
          
        };   
    }

    @Override
    public UniversityProgram realistic() {
        return new AbstractUniversityProgram(){
            @Override
            public void setFixedMinimumCredit() {
                setMapperMinimum(Map.of(List.of(Sector.COMPUTER_ENGINEERING, Sector.COMPUTER_SCIENCE), w -> w >= 60, List.of(Sector.PHYSICS, Sector.MATHEMATICS), t -> t <= 18,
                    List.of(Sector.THESIS), q -> q == 24));
                setCreditBound(c -> c == 120);
            }
          
        };   
    }
    
}
