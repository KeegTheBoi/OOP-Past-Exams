package a03b.e1;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EquivalenceFactoryImpl implements EquivalenceFactory {

    @Override
    public <X> Equivalence<X> fromPartition(Set<Set<X>> partition) {
        return fromFunction(
            partition.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
            , h-> partition.stream()
                .filter(g-> g.contains(h))
                .findFirst()
                .get());
    }

    @Override
    public <X> Equivalence<X> fromPredicate(Set<X> domain, BiPredicate<X, X> predicate) {
        return fromFunction(
            domain, 
            s -> domain.stream()
                .filter( y -> predicate.test(s, y))
                .findFirst()
                .get()
        );
    }

    @Override
    public <X> Equivalence<X> fromPairs(Set<Pair<X, X>> pairs) {
        return new EquivalenceImpl<>(pairs.stream().collect(Collectors.groupingBy(Pair::getX, Collectors.mapping(Pair::getY, Collectors.toSet()))));    
    }

    @Override
    public <X, Y> Equivalence<X> fromFunction(Set<X> domain, Function<X, Y> function) {
       return new EquivalenceImpl<>(domain.stream()
       .collect(
           Collectors.groupingBy(
               function, 
               Collectors.toSet()
           )
       ));
    }

}
