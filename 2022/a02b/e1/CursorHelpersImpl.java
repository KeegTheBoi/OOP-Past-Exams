package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> fromIterator(Iterator<X> iter){
        return new Cursor<X>() {
            X current = iter.next();

            @Override
            public X getElement() {
                return this.current;
            }

            @Override
            public boolean advance() {
                if(iter.hasNext()){
                    this.current = iter.next();
                    return true;
                }
                return false;
            }         
        };
    }
    

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return fromIterator(list.iterator());
    }

    @Override
    public Cursor<Integer> naturals() {
        return this.fromIterator(IntStream.iterate(0, i -> i + 1).iterator());
    }

    private <X> Stream<X> toStream(Cursor<X> input) {
        return Stream.concat(Stream.of(input), Stream.iterate(input, k -> k.advance(), UnaryOperator.identity())).map(Cursor::getElement);
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return fromIterator(toStream(input).limit(max).iterator());
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        toStream(input).forEach(consumer);
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        return toStream(input).limit(max).toList();
    }
    
}
