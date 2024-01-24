package a03a.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b){
        return oneResultFilterMapper(a -> true, ar -> b);
        
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return twoWay(predicate, oneResult(positive), oneResult(negative));
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        var mappedList = mapList.stream().map(p -> new Pair<Predicate<A>, B>(a -> p.get1().equals(a), p.get2())).collect(Collectors.toList());
        return switchChain(mappedList, defaultReply);
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
            return new DecisionChain<A,B>() {
                private boolean decide = true;
                @Override
                public Optional<B> result(A a) {
                    return Optional.of(a).filter(predicate.and(l -> decide)).map(c -> positive.finalResult(a));
                }
    
                @Override
                public DecisionChain<A, B> next(A a) {
                    if(Optional.of(a).filter(predicate).isPresent()) {
                        decide = true;
                    }
                    else if(result(a).isEmpty()) {
                        decide = false;
                        return oneResult(negative.finalResult(a));
                    }             
                    return oneResult(positive.finalResult(a));
                }
                
            };
    }

    public <A, B> DecisionChain<A, B> oneResultFilterMapper(Predicate<A> predicate, Function<A, B> func) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(a).filter(predicate).map(func);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return next(a);
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return twoWay(
            a -> cases.contains(a), 
            oneResultFilterMapper(
                a -> cases.stream().filter(g -> g.get1().test(a)).findFirst().isPresent(), 
                as ->  cases.stream().filter(g -> g.get1().test(as)).findFirst().map(Pair::get2).get()), 
            oneResult(defaultReply));
    }
    
}
