package a05.e1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class StateFactoryImpl implements StateFactory {

    


    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S,A>() {

            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                return fun.apply(s).get2();
            }

            @Override
            public <B> State<S, B> map(Function<A, B> sfun) {
                return fromFunction(k -> new Pair<S, B>(nextState(k), sfun.apply(value(k))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                return Stream.iterate(value(s0), o -> value(nextState(s0))).iterator();
            }
            
        };
    }

    @Override
    public <S, A, B> State<S, B> compose(State<S, A> state1, State<S, B> state2) {
        return new State<S,B>() {

            @Override
            public S nextState(S s) {
                return state2.nextState(s);
            }

            @Override
            public B value(S s) {
                return state2.value(s);
            }

            @Override
            public <K> State<S, K> map(Function<B, K> fun) {
                
                return fromFunction(k -> new Pair<>(state2.nextState(state1.nextState(k)), 
                    fun.compose())));
            }

            @Override
            public Iterator<B> iterator(S s0) {
               return null;
            }
            
        };
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        
        return null;
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        
        return null;
    }
    
}
