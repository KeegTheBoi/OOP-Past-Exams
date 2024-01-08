package a04b.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public class ParserFactoryImpl implements ParsersFactory {
	
	/**
	 * A parser for sequences of zero, one or many tokens t
	 * @param t
	 * @return a parser producing the length of the sequence
	 */
	 
	 private <R> Parser<R> stringParser(String t, Function<List<String>, R> mapper, Predicate<List<String>> emptyness) {
		return new Parser<R>() {
	
			private boolean broken = false;
			private List<String> list = new ArrayList<>();
			public boolean getNext(String token) {
				System.out.println("Incoming: " + token + " Expected → " + t);
				broken = !Optional.of(token).filter(d -> !broken).map(to -> to.equals(t)).orElse(false); 
				if(!broken) {
					list.add(token);
				}
				return !broken;
			}
			
			
			public boolean getAllInList(List<String> tokens) {
				System.out.println(tokens);
				var result = Optional.of(tokens).filter(d -> !broken).map(d -> d.stream().allMatch(v -> v.equals(t)));
				result.filter(r -> true).ifPresent(p -> list = List.copyOf(tokens));
				broken = !result.orElse(false);
				return !broken;
			}
				
			
			public R completeAndCreateResult() {
				System.out.println(list);
				return Optional.of(list).filter(d -> !broken || getAllInList(d)).filter(emptyness).map(mapper).orElseThrow(IllegalStateException::new); 
			}
		};
		
	}
	
	 
	public Parser<Integer> createSequenceParserToCount(String t) {
		System.out.println("Counter\n");
		return stringParser(t, l -> l.size(), o -> true);
	}
	
	/**
	 * A parser for one or many tokens t
	 * NOTE: this is optional for this exam!
	 * @param t
	 * @return a parser producing the concatenation of all tokens
	 */
	public Parser<String> createNonEmptySequenceParserToString(String t){
		System.out.println("NonEmpty\n");
		return stringParser(t, l -> l.stream().collect(Collectors.joining()), o -> !o.isEmpty());

	}
	
	/**
	 * A parser for expressions made by addition ("+") or subtraction ("-") of numbers ("0" or "1")
	 * @return a parser producing the result of evaluating the expression
	 */
	public Parser<Integer> createExpressionParserToResult() {
		System.out.println("Expression\n\n");
		return null;
	}
}
