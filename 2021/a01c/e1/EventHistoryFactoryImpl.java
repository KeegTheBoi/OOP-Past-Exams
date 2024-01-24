package a01c.e1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory{

    public <E> EventHistory<E> fromMap(Map<Double, E> map){
        return this.fromIterators(map.entrySet().stream().sorted((e1, e2) -> Double.compare(e1.getKey(), e2.getKey())).map(e -> e.getKey()).iterator(), 
        map.entrySet().stream().sorted((e1, e2) -> Double.compare(e1.getKey(), e2.getKey())).map(e -> e.getValue()).iterator());
    }
    
    public	<E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content){
    return new EventHistory<E>() {
                
                @Override
                public double getTimeOfEvent() {
                    return times.next();
                }
    
                @Override
                public E getEventContent() {
                    return content.next();
                }
    
                @Override
                public boolean moveToNextEvent() {
                    return times.hasNext() && content.hasNext();                                                     
                }
                
               };
    
    }
    
    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E>
    content, double initial, double delta) {
                return fromIterators(Stream.iterate(initial, i -> i + delta).iterator(), content.stream().iterator());
    }

    public <E> EventHistory<E>
    fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return fromMap(Stream.generate(content).limit(size).collect(Collectors.toMap(s -> Math.random(), c -> c)));
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        List<String> allLines = Files.readAllLines(Path.of(file));
        return fromMap(allLines.stream().map(l -> l.split(":", 2)).collect(Collectors.toMap(l -> Double.parseDouble(l[0]), c -> c[1])));
        
    }
}
