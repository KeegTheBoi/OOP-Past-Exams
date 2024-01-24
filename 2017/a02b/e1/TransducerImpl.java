package a02b.e1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransducerImpl<X, Y> implements Transducer<X, Y>{

    protected int size;
    protected List<X> values;
    private boolean input;
    private Function<Stream<X>, Y> terminator;

    public TransducerImpl(int size, Function<Stream<X>, Y> terminator){
        this.size = size;
        values = new ArrayList<>();
        this.input = true;
        this.terminator = terminator;
    }

    @Override
    public void provideNextInput(X x) {
        input = values.add(x);
    }

    @Override
    public void inputIsOver() {
        if(!input) {
            throw new IllegalStateException();
        }
        input = false;
    }

    @Override
    public boolean isNextOutputReady() {
        return input ? input && values.size() >= this.size : values.size() > 0;
    }

    @Override
    public Y getOutputElement() {
        if(!isNextOutputReady()) {
            throw new IllegalStateException();
        }
        var defCopy = values.stream().limit(size).collect(Collectors.toSet());
        values.removeAll(defCopy);
        return this.terminator.apply(defCopy.stream());
    }


    @Override
    public boolean isOutputOver() {
        return values.isEmpty() && !input;
    }

}
