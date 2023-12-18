package a01b.e1;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    @Override
    public Flattener<Integer, Integer> sumEach() {
       return l -> l.stream().map(s -> s.stream().mapToInt(Integer::intValue).sum()).collect(Collectors.toList());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return li-> li.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return li -> IntStream.range(
            0, 
            (li.size() % 2 + li.size()) / 2
        )
        .mapToObj(
            i -> li.stream()
                .skip(i * 2).limit(2)
                .map(g -> g.stream().collect(Collectors.joining()))
                .collect(Collectors.joining())
        )           
        .collect(Collectors.toList());
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return li -> li.stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return zi -> IntStream.range(0, zi.get(0).size()).mapToObj(i -> zi.stream().mapToInt(h -> h.get(i)).sum()).collect(Collectors.toList());
    }
    
}
