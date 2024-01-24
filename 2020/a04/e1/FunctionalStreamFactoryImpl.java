package a04.e1;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class FunctionalStreamFactoryImpl implements FunctionalStreamFactory {

    public abstract class NextResultImpl<E> implements FunctionalStream.NextResult<E> {
        protected final Deque<E> coda;
        public NextResultImpl(Deque<E> coda) {
            this.coda = coda;
            value = coda.removeFirst();
            coda.addLast(value);
        }
        private E value;
        @Override
        public E getElement() {
            return value;
        }

         @Override
        public abstract FunctionalStream<E> getStream();
    }

    public abstract class AbstractStream<E> implements FunctionalStream<E> {
        @Override
            public List<E> toList(int size) {
                return toStream().limit(size).collect(Collectors.toList());
            }

            @Override
            public Iterator<E> toIterator() {
                return toStream().iterator();
            }

            private Stream<E> toStream() {
                return Stream.iterate(this.next(), s -> s.getStream().next()).map(f -> f.getElement());
            }

            @Override
            public abstract NextResult<E> next();
    }
    
    @Override
    public <E> FunctionalStream<E> fromListRepetitive(List<E> list) {    
        return new AbstractStream<E>() {
            
            @Override
            public NextResult<E> next() {
                return new NextResultImpl<>(new LinkedList<>(list)) {

                    @Override
                    public FunctionalStream<E> getStream() {
                        return fromListRepetitive(List.copyOf(this.coda));
                    }
                    
                };
            }
            
        };
    }

    @Override
    public <E> FunctionalStream<E> iterate(E initial, UnaryOperator<E> op) {
        return new AbstractStream<E>() {
            
            @Override
            public NextResult<E> next() {
                return new NextResultImpl<> (new LinkedList<>(Set.of(initial))){

                    @Override
                    public FunctionalStream<E> getStream() {
                       return iterate(op.apply(initial), op);
                    }

                };
            }
            
        };
    }

    @Override
    public <A, B> FunctionalStream<B> map(FunctionalStream<A> fstream, Function<A, B> mapper) {
       return  fromListRepetitive(fstream.toList(10).stream().map(mapper).collect(Collectors.toList()));
    }

}
