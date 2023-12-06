package a03a.e1;

import java.util.HashMap;
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

    public abstract class AbstractGrouping<P, G, V> implements Grouping<G, V> {
        private final Iterator<P> iterator;
        protected final Map<G, Set<V>> map;

        public AbstractGrouping(Iterator<P> iterator){
            this.iterator = iterator;
            map = new HashMap<>();
        }

        @Override
        public Set<V> getValuesOfGroup(G group) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getValuesOfGroup'");
        }

        @Override
        public Set<G> getGroups() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getGroups'");
        }

        @Override
        public Optional<G> getGroupOf(V data) {
            
            throw new UnsupportedOperationException("Unimplemented method 'getGroupOf'");
        }

        protected Stream<P> asStream() {
            return iterator.hasNext() ? Stream.iterate
                (iterator.next()
                , v -> v != null, 
                o -> this.iterator.hasNext() ? iterator.next() : null
            ) : Stream.empty();
        }

        @Override
        public abstract Map<G, Set<V>> asMap();

        @Override
        public Grouping<G, V> combineGroups(G initial1, G initial2, G result) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'combineGroups'");
        }
        
    }

    @Override
    public <G, V> Grouping<G, V> fromPairs(Iterable<Pair<G, V>> values) {
        return new AbstractGrouping<Pair<G,V>,G,V>(values.iterator()) {

            @Override
            public Map<G, Set<V>> asMap() {
                return this.asStream().collect(Collectors.groupingBy(Pair::get1), Collectors.mapping(Pair::get2, Collectors.toSet()));
            }
            
        };
        
    }

    @Override
    public <V> Grouping<V, V> singletons(Set<V> values) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'singletons'");
    }

    @Override
    public <V> Grouping<V, V> withChampion(Set<V> values, BiPredicate<V, V> sameGroup, Predicate<V> champion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withChampion'");
    }

    @Override
    public <G, V> Grouping<G, V> fromFunction(Set<V> values, Function<V, G> mapper) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromFunction'");
    }
    
}
