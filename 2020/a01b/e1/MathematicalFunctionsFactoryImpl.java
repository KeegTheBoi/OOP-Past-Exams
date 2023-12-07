package a01b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MathematicalFunctionsFactoryImpl implements MathematicalFunctionsFactory {

    @Override
    public <A, B> MathematicalFunction<A, B> constant(Predicate<A> domainPredicate, B value) {
        return fromFunction(domainPredicate, b -> value);
    }

    @Override
    public <A, B> MathematicalFunction<A, A> identity(Predicate<A> domainPredicate) {
        return fromFunction(domainPredicate, Function.identity());
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromFunction(Predicate<A> domainPredicate, Function<A, B> function) {
        return new MathematicalFunction<A,B>() {

            @Override
            public Optional<B> apply(A a) {
                if(this.inDomain(a)) {
                    return Optional.of(a).map(function);
                }
                return Optional.empty();
            }

            @Override
            public boolean inDomain(A a) {
                return Optional.of(a).filter(domainPredicate).isPresent();
            }

            @Override
            public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> f) {
                return fromFunction(a -> f.inDomain(this.apply(a).get()), a -> f.apply(this.apply(a).get()).get());
            }

            @Override
            public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'withUpdatedValue'");
            }

            @Override
            public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
                 return fromMap(
                    subDomain.stream()
                    .filter(this::inDomain)
                    .map(
                            l -> new Pair<A, B>(
                            l,
                            this.apply(l).get()
                        )
                    )
                    .collect(Collectors.toMap(
                        Pair::x, 
                        Pair::y)
                    )
                );
            }
            
        };
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromMap(Map<A, B> map) {
        return new MathematicalFunction<A,B>() {

            @Override
            public Optional<B> apply(A a) {
                if(this.inDomain(a)) {
                    return Optional.of(map.get(a));
                }
                return Optional.empty();
            }

            @Override
            public boolean inDomain(A a) {
                return map.containsKey(a);
            }

            @Override
            public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> f) {
                return fromFunction(a -> f.inDomain(this.apply(a).get()), a -> f.apply(this.apply(a).get()).get());
            }

            @Override
            public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
                return null;
            }

            @Override
            public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
                return fromMap(
                    subDomain.stream()
                    .filter(this::inDomain)
                    .map(
                            l -> new Pair<A, B>(
                            l,
                            this.apply(l).get()
                        )
                    )
                    .collect(Collectors.toMap(
                        Pair::x, 
                        Pair::y)
                    )
                );
            }
            
        };
    }

}
