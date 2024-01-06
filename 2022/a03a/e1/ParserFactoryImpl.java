package a03a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.*;
import java.util.stream.Collectors;

import javax.swing.plaf.OptionPaneUI;

public class ParserFactoryImpl implements ParserFactory{

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
		return iterator -> acceptedSequences.contains(fromIterToList(iterator));               
    }

    private <O> List<O> fromIterToList(Iterator<O> iter){
        return Stream.iterate(iter, Iterator::hasNext, UnaryOperator.identity()).map(Iterator::next).toList(); 
    }

	private int c = 0;
    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
		
		return iter -> {
			c = 0;
			System.out.println(c);
			return fromGraphRecursive(x0, transitions, acceptanceInputs).accept(iter);
		};
        // TODO Auto-generated method stub
        
        
    }
    
    private <X> Parser<X> fromGraphRecursive(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
		
        var matched = transitions.stream().filter(p -> p.getX().equals(x0)).map(Pair::getY).collect(Collectors.toSet());
        System.out.println("GRAPH: x0 → " + x0 + "; count → " + c + "; matched → " + matched);
        return recursive(x -> 
				Optional.of(matched)
					.filter(m -> m.stream().anyMatch(p -> p.equals(x)))
					.map(t -> {
						c += Optional.of(x).filter(acceptanceInputs::contains).map(s -> 1).orElse(0);
						return fromGraphRecursive(x, transitions, acceptanceInputs); 
					}) ,
				c == 1
			);
	}

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return recursive(p -> 
				Optional.of(x0)
					.filter(x -> x.equals(p))
					.flatMap(q -> next.apply(q).map(v -> fromIteration(v, next))),
				next.apply(x0).isPresent()
			);
    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
		
        return iter -> {
			
			if(!iter.hasNext()) {
				return isFinal;
			}
			X val = iter.next();
			System.out.println("Current: " + val + "; hasNext() → " + iter.hasNext());
			return nextParser.apply(val).map(p -> p.accept(iter)).orElse(!iter.hasNext());	
		};
        
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return recursive(p -> Optional.of(parser).filter(pa -> p.equals(x)), false);
    }
    
}

