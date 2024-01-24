package a02b.e1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public abstract class AbstractUniversityProgram implements UniversityProgram{
    
    protected final Map<String, Pair<Sector, Integer>> mapper; 
    private Predicate<Integer> creditBound;
    private Map<List<Sector>, Predicate<Integer>> mapperMinimum;
    
    public AbstractUniversityProgram(){
        mapper = new LinkedHashMap<>();
    }

    public void addCourse(String name, Sector sector, int credits){
        mapper.put(name, new Pair<>(sector, credits));
    }

    public boolean isValid(Set<String> courseNames){
        this.setFixedMinimumCredit();
        var mappersSectorValues = courseNames.stream().map(n -> this.mapper.get(n)).collect(Collectors.groupingBy(c -> c.get1(), 
            Collectors.summingInt(r -> r.get2())));
        if(!mapperMinimum.isEmpty()){
            for (Map.Entry<List<Sector>, Predicate<Integer>> entry : this.mapperMinimum.entrySet()) {
                if(!(entry.getValue().test(entry.getKey().stream().filter(l -> !Objects.isNull(mappersSectorValues.get(l))).mapToInt(t -> mappersSectorValues.get(t)).sum()))){
                    return false;
                }
            }
        }      
        return creditBound.test(courseNames.stream().mapToInt(s -> this.mapper.get(s).get2()).sum());

    }

    public abstract void setFixedMinimumCredit();

    public void setCreditBound(Predicate<Integer> creditBound) {
        this.creditBound = creditBound;
    }

    public void setMapperMinimum(Map<List<Sector>, Predicate<Integer>> mapperMinimum) {
        this.mapperMinimum = mapperMinimum;
    }

}
