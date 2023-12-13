package a03a.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b){
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return next(a);
            }
            
        };
        
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return new DecisionChain<A,B>() {
            private boolean decide = true;
            @Override
            public Optional<B> result(A a) {
                return Optional.of(a).filter(predicate.and(l -> decide)).map(c -> positive);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                if(Optional.of(a).filter(predicate).isPresent()) {
                    decide = true;
                }
                else if(result(a).isEmpty()) {
                    decide = false;
                    return oneResult(negative);
                }
                
                return oneResult(positive);
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return new DecisionChain<A,B>() {

            @Override
            public Optional<B> result(A a) {
                if(!Optional.of(a).filter(predicate).isEmpty()) {
                    return positive.result(a);
                }
               return this.next(a).result(a);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return negative;
            }
            
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
