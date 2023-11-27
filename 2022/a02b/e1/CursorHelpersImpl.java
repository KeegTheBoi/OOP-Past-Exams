package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

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

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<X>() {
            private int cursor = 1;
            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                if(input.advance() && cursor < max){
                    cursor++;
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        do{
            consumer.accept(input.getElement());
        }while(input.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        List<X> outer = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            outer.add(input.getElement());
            if(!input.advance()){
                break;
            }
        }
        return List.copyOf(outer);
    }
    
}
