package a02b.e1;


import java.util.stream.Collectors;

public class TransducerFactoryImpl implements TransducerFactory{

    @Override
    public <X> Transducer<X, String> makeConcatenator(int inputSize) {
        return new TransducerImpl<X,String>(inputSize, s -> s.map(l -> l.toString()).collect(Collectors.joining()));
    }

    @Override
    public Transducer<Integer, Integer> makePairSummer() {
        return new TransducerImpl<Integer,Integer>(2, s -> s.reduce(0, Integer::sum));
    }

}
