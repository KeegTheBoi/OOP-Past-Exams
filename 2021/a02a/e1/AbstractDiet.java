package a02a.e1;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractDiet implements Diet{
    
    private Predicate<Double> predicate;
    private final Set<Pair<String, Map<Nutrient, Integer>>> setNutrients;

    public AbstractDiet(){
        this.setNutrients = new LinkedHashSet<>();
    }

    public void addFood(String name, Map<Nutrient,Integer> nutritionMap){
        setNutrients.add(new Pair<String,Map<Nutrient,Integer>>(name, nutritionMap));
    }

    public boolean isValid(Map<String, Double> dietMap){
        this.getCostraint();
        double result = 0;
        for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
            result += this.setNutrients.stream().filter(c -> c.get1().equals(entry.getKey())).map(s -> s.get2())
            .findFirst().get().values().stream().mapToDouble(f -> f * entry.getValue() / 100).sum();
           
        }
        return this.predicate.test(result);
    }

    public abstract void getCostraint();

    public void setPredicate(Predicate<Double> pred){
        this.predicate = pred;
    }


}
