package a03a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.plaf.OptionPaneUI;

public class ParserFactoryImpl implements ParserFactory{

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
               return acceptedSequences.contains(mapToList(iterator));               
            }
            
        };
    }

    private <O> List<O> mapToList(Iterator<O> iter){
        List<O> outList = new ArrayList<>();
        while(iter.hasNext()){
            outList.add(iter.next());
        }
        return outList;
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<>(){

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (!iterator.hasNext()){
                    return isFinal;
                }
                Optional<Parser<X>> applied = nextParser.apply(iterator.next());
                Optional<Boolean> verified = applied.map(new Function<Parser<X>,Boolean>() {

                    @Override
                    public Boolean apply(Parser<X> t) {
                        return t.accept(iterator);
                    }
                    
                });
                return verified.orElse(false);
                
            }
        };
        
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return null;
    }
    
}
