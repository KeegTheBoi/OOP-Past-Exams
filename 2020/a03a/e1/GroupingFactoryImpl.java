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

    private class FromIteratorGrouping<G, V> implements Grouping<G, V> {
        final Iterator<Pair<G, V>> iterator;

        public FromIteratorGrouping(Iterator<Pair<G, V>> iterator){
            this.iterator = iterator;
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getGroupOf'");
        }

        private Stream<Pair<G, V>> asStream() {
            return Stream.iterate(iterator.next(), v -> v != null, o -> this.iterator.hasNext() ? iterator.next() : null);
        }

        @Override
        public Map<G, Set<V>> asMap() {
            // TODO Auto-generated method stub
           
            return  null;
        }

        @Override
        public Grouping<G, V> combineGroups(G initial1, G initial2, G result) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'combineGroups'");
        }
        
    }

    @Override
    public <G, V> Grouping<G, V> fromPairs(Iterable<Pair<G, V>> values) {
        return null;
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
