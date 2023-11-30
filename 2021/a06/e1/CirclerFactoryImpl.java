package a06.e1;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CirclerFactoryImpl implements CirclerFactory {

    public abstract class AbstractCircler<T> implements Circler<T> {
        protected List<T> source;
        protected Iterator<T> iter;

        @Override
        public void setSource(final List<T> elements) {
            this.source = elements;
            this.setIterator(elements.iterator());
        }

        protected void setIterator(final Iterator<T> iter){
            this.iter = iter;
        }

        @Override
        public abstract T produceOne();

        @Override
        public List<T> produceMany(int n) {
            return Stream.generate(() -> produceOne()).limit(n).collect(Collectors.toList());
        }

    }

    private class RefinedLefToRight<T> extends AbstractCircler<T>{

        @Override
        public T produceOne() {
            if(!this.iter.hasNext()) {
                setIterator(this.source.iterator());
            }
            return this.iter.next();
        }
    }


    @Override
    public <T> Circler<T> leftToRight() {
       return new RefinedLefToRight<>();
    }

   

    @Override
    public <T> Circler<T> alternate() {
        return new AbstractCircler<T>(){
            private boolean next = false;

            @Override
            public void setSource(final List<T> elements) {
                super.setSource(elements);
                next = false;
            }

            @Override
            public T produceOne() {
                if(!this.iter.hasNext()) {
                    if(next) {
                        setIterator(this.source.iterator());
                        next = false;
                    }
                    else {
                        setIterator(revertList().iterator());
                        next = true;
                    }
                }
                return this.iter.next();
            }

            private List<T> revertList(){
                return IntStream.iterate(this.source.size() - 1, k -> k >= 0, o -> o - 1).mapToObj(l -> this.source.get(l)).collect(Collectors.toList());
            }
    
        };
    }

    @Override
    public <T> Circler<T> stayToLast() {
        
        return new AbstractCircler<T>(){

            @Override
            public T produceOne() {
                if(!this.iter.hasNext()) {
                    setIterator(Stream.generate(() -> this.source.get(source.size() - 1)).iterator());
                }
                return this.iter.next();
            }
    
        };
            
    }


    public class CirclerRefinedSkip<T> extends AbstractCircler<T>{
        private final Circler<T> impCircler;
        private int cursor;
       
        public CirclerRefinedSkip(final Circler<T> implentCirce){
            this.impCircler = implentCirce;
        }

        @Override
        public void setSource(final List<T> elements) {
            super.setSource(elements);
            impCircler.setSource(elements);
            cursor = -1;
        }

        @Override
        public T produceOne() {
            T val = impCircler.produceOne();
            if(++this.cursor % 2 == 1){
                val = impCircler.produceOne();
                cursor++;
            }
            return val;
        }
    }

    
    @Override
    public <T> Circler<T> leftToRightSkipOne() {
        return new CirclerRefinedSkip<>(this.leftToRight());
    }

    @Override
    public <T> Circler<T> alternateSkipOne() {
        // TODO Auto-generated method stub
        return new CirclerRefinedSkip<>(this.alternate());
    }

    @Override
    public <T> Circler<T> stayToLastSkipOne() {
        return new CirclerRefinedSkip<>(this.stayToLast());
    }
    
}
