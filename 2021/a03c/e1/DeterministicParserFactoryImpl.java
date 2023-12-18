package a03c.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class DeterministicParserFactoryImpl implements DeterministicParserFactory {

    @Override
    public DeterministicParser oneSymbol(String s) {
        return k ->  k.get(0).equals(s) ? Optional.of(k.subList(1, k.size())) : Optional.empty();
    }

    @Override
    public DeterministicParser twoSymbols(String s1, String s2) {
        return k ->  k.get(0).equals(s1) && k.get(1).equals(s2)? Optional.of(k.subList(2, k.size())) : Optional.empty();
    }

    @Override
    public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'possiblyEmptyIncreasingSequenceOfPositiveNumbers'");
    }

    @Override
    public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
            DeterministicParser element) {
        return l ->  {
            var k = new LinkedList<String>(l);
            if(k.get(0).equals(start)) {
                k.removeFirst();
                return checkSequence(stop, delimiter, element, k);
            } 
            return Optional.empty();
            
        };
    }

    private Optional<List<String>> checkSequence(String stop, String delimiter,
            DeterministicParser element, List<String> k) {
        
        if(!(k = element.accepts(k).orElse(Collections.emptyList())).isEmpty()) {
            if(k.get(0).equals(stop)) {
                k.remove(0);
                return Optional.of(k);
            }
            if(k.get(0).equals(delimiter)){
                k.remove(0);
                return checkSequence(stop, delimiter, element, k);
            }
        }
        return Optional.empty();
    }

    @Override
    public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
        return l -> {
            List<String> k = new LinkedList<>(l);
            if(!(k = first.accepts(k).orElse(Collections.emptyList())).isEmpty()) {
                if(!(k = second.accepts(k).orElse(Collections.emptyList())).isEmpty()) {
                    return Optional.of(k);
                }
            }
            return Optional.empty();
        };
    }

}
