package a03a.e1;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class GroupingFactoryImpl implements GroupingFactory {

    public class GroupImpl<G, V> implements Grouping<G, V> {
        private final Map<G, Set<V>> map;

        public GroupImpl(Map<G, Set<V>> map) {
            this.map = map;
        }

        @Override
        public Set<V> getValuesOfGroup(G group) {
            return this.map.get(group);
        }

        @Override
        public Set<G> getGroups() {
            return this.map.keySet();
        }

        @Override
        public Optional<G> getGroupOf(V data) {
            return this.map.entrySet().stream()
                .filter (
                    s -> s.getValue().stream()
                    .filter(m -> m.equals(data))
                    .findFirst().isPresent())
                    .map(k -> k.getKey()
                )
                .findFirst();
        }      

        @Override
        public Map<G, Set<V>> asMap() {
            return this.map;
        }

        @Override
        public Grouping<G, V> combineGroups(G initial1, G initial2, G result) {
            throw new UnsupportedOperationException("Unimplemented method 'combineGroups'");
        }
        
    }

    public static <S> Stream<S> asStream(Iterator<S> iterator) {
        return iterator.hasNext() ? Stream.iterate
            (iterator.next()
            , v -> v != null, 
            o -> iterator.hasNext() ? iterator.next() : null
        ) : Stream.empty();
    }

    @Override
    public <G, V> Grouping<G, V> fromPairs(Iterable<Pair<G, V>> values) {
        Stream<Pair<G, V>> v = asStream(values.iterator());
        return new GroupImpl<G, V>(v
            .collect(
                Collectors.groupingBy(
                    Pair::getX, 
                    Collectors.mapping(
                        Pair::getY, 
                        Collectors.toSet())
                )
            ));
        
    }

    @Override
    public <V> Grouping<V, V> singletons(Set<V> values) {
        return this.fromFunction(values, Function.identity());
    }

    @Override
    public <V> Grouping<V, V> withChampion(Set<V> values, BiPredicate<V, V> sameGroup, Predicate<V> champion) {
        Stream<V> stream = asStream(values.iterator());
        var listInput = stream.collect(Collectors.toList());
        var mapChampions = listInput.stream().collect(Collectors.partitioningBy(champion));  
        return this.fromPairs(listInput.stream()
        .map(
            c -> new Pair<>(
                mapChampions.get(true).stream()
                .filter(l -> sameGroup.test(c, l))
                .findFirst().get(), 
                c
            )
        )
        .collect(Collectors.toSet())
        );    
    }

    @Override
    public <G, V> Grouping<G, V> fromFunction(Set<V> values, Function<V, G> mapper) {
        return this.generalFunctionandCollector(values, mapper);
    }

    private <G, V> Grouping<G, V> generalFunctionandCollector(Set<V> values, Function<V, G> mapper) {
        Stream<V> stream = asStream(values.iterator());
        return new GroupImpl<G, V>(stream
            .collect(
                Collectors.groupingBy(
                    mapper,
                    Collectors.toSet()
                )
            )
        );
    }
    
}
