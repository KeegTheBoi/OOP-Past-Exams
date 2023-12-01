package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers{

    public class RecursiveIteratorImpl<X> implements RecursiveIterator<X>{

        private final Iterator<X> lista;
        private final X newVal;
        public RecursiveIteratorImpl(X newValue, Iterator<X> iteratore){
            this.lista = iteratore;
            this.newVal = newValue;
        }

        @Override
        public X getElement() {
            return this.newVal;            
        }

        @Override
        public RecursiveIterator<X> next() {
            return this.lista.hasNext() ? new RecursiveIteratorImpl<>(this.lista.next(), this.lista) : null;
        }

    }

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return list.iterator().hasNext() ? new RecursiveIteratorImpl<>( list.iterator().next(),  list.listIterator(1)) : null;
        
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> out = new ArrayList<>();
        RecursiveIterator<X> next = input;
        while(out.size() < max && next != null){
            out.add(next.getElement());  
            next = input.next();                
        }
        return List.copyOf(out);
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(final RecursiveIterator<X> first, final RecursiveIterator<Y> second) {
        final List<X> firstRec = this.toList(first, Integer.MAX_VALUE);
        final List<Y> secondRec = this.toList(second, Integer.MAX_VALUE);
        List<Pair<X, Y>> outer = new ArrayList<>();
        final var iterF = firstRec.iterator();
        final var iterS = secondRec.iterator();
        while(iterF.hasNext() && iterS.hasNext()){
            outer.add(new Pair<X,Y>(iterF.next(), iterS.next()));
        }
        return this.fromList(List.copyOf(outer));
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        List<Integer> listIndexs = new ArrayList<>();
        List<X> inpRecurs = this.toList(iterator, Integer.MAX_VALUE);
        for (int i = 0; i < inpRecurs.size(); i++) {
            listIndexs.add(i);
        }
        return this.zip(this.fromList(List.copyOf(inpRecurs)), this.fromList(List.copyOf(listIndexs)));
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        final List<X> firstRec = this.toList(first, Integer.MAX_VALUE);
        final List<X> secondRec = this.toList(second, Integer.MAX_VALUE);
        List<X> outer = new ArrayList<>();
        final var iterF = firstRec.iterator();
        final var iterS = secondRec.iterator();
        while(iterF.hasNext() || iterS.hasNext()){
            if(outer.size() % 2 == 0){
                if(iterF.hasNext()){
                    outer.add(iterF.next());
                }
                else if (iterS.hasNext()){
                    outer.add(iterS.next());
                }
            }
            else{
                if(iterS.hasNext()){
                    outer.add(iterS.next());
                }
                else if (iterF.hasNext()){
                    outer.add(iterF.next());
                }
            }
            
        }
        return this.fromList(outer);
    }
    
}