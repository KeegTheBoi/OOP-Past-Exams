package a03b.e1;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EquivalenceImpl<Y, X> implements Equivalence<X>{

    private Map<Y, Set<X>> map;

    public EquivalenceImpl(Map<Y, Set<X>> map) {
        this.map = map;
    }
    @Override
    public boolean areEquivalent(X x1, X x2) {
        return this.map.values().stream()
        .anyMatch(t -> t.contains(x1) && t.contains(x2));
    }

    @Override
    public Set<X> domain() {
        return this.map.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<X> equivalenceSet(X x) {
        return this.map.values().stream()
            .filter(h-> h.contains(x))
            .findFirst()
            .get();
    }

    @Override
    public Set<Set<X>> partition() {
        return new LinkedHashSet<>(this.map.values());
    }

    @Override
    public boolean smallerThan(Equivalence<X> eq) {
        return this.domain().stream()
        .allMatch(h-> eq.areEquivalent(h, h));
    }

}
