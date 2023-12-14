package a01a.e1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(new ArrayList<String>(),
        (e, st) -> { st.add(e); return Optional.of(st); },
        s -> Optional.of((int)s.stream().filter(str -> str.length() == 0).count()));
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return generalised(
            new LinkedList<String>(),
            (e, st) -> {
                Optional<LinkedList<String>> update = e >= Integer.parseInt(st.size() == 0 ? String.valueOf(e) : st.getLast()) ? Optional.of(st) : Optional.empty();
                update.ifPresent(l -> st.add(String.valueOf(e)));
                return update;
            },
            s -> Optional.ofNullable(s.size() > 1 ? s.stream().collect(Collectors.joining(":")) : null));
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return generalised(
            new ArrayList<Integer>(),
            (e, st) -> {
                st.add(e);
                return st.size() <= 3 ? Optional.of(st) : Optional.empty();
            },
            s -> Optional.ofNullable(s.stream().collect(Collectors.toList())).filter(f -> f.stream().count() == 3).map(g -> g.stream().mapToInt(Integer::intValue).sum()));
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                return a1.end().isPresent() && a2.end().isPresent() ? Optional.of(new Pair<>(a1.end().get(), a2.end().get())) : Optional.empty();
            }
            
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E,O>() {
            S state = initial;
            @Override
            public boolean accept(E e) {
                
                Optional<S> res = stateFun.apply(e, state);   
                res.ifPresent(c -> state = c);
                return res.isPresent();
            }

            @Override
            public Optional<O> end() {
                return outputFun.apply(state);
            }
            
        };
    }

}
