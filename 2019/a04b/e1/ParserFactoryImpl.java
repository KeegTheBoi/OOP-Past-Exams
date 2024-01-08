package a04b.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public class ParserFactoryImpl implements ParsersFactory {
	
	private static Integer convert(final List<String> list) {
		return list.stream().reduce(0, (a, b) -> a + tryParseInt(b, a), Integer::sum) ;
	}

	public static int tryParseInt(String value, int accum) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return value.equals("+") ? 0 : -accum - 1;
		}
	}

	private <R> Parser<R> stringParser(String t, Function<List<String>, R> mapper, Predicate<List<String>> emptyness, BiPredicate<String, Pair<Boolean, Boolean>> pred) {
		return new Parser<R>() {

			private boolean numberExpected = true;
			private boolean broken = false;
			private List<String> list = new ArrayList<>();
			private int counter = 0;

			public boolean getNext(String token) {
				numberExpected = counter++ % 2 == 0 ? true : false;
				broken = !pred.test(token, new Pair<>(numberExpected, !broken));
				if(!broken) {
					list.add(token);
				}
				return !broken;
			}
			
			@Override
			public boolean getAllInList(List<String> tokens) {
				var result = Optional.of(tokens).filter(d -> !broken).map(d -> d.stream().allMatch(this::getNext));
				broken = !result.orElse(false);
				return !broken;
			}

			@Override
			public R completeAndCreateResult() {
				return Optional.of(list).filter(d -> !broken || getAllInList(d)).filter(emptyness).map(mapper).orElseThrow(IllegalStateException::new); 
				
			}
		};
		
	}
	
	 
	public Parser<Integer> createSequenceParserToCount(String t) {
		return stringParser(t, l -> l.size(), o -> true, (token, e) -> Optional.of(token).filter(d -> e.getY()).map(to -> to.equals(t)).orElse(false));
	}
	
	public Parser<String> createNonEmptySequenceParserToString(String t){
		return stringParser(t, l -> l.stream().collect(Collectors.joining()), o -> !o.isEmpty(), (token, e) -> Optional.of(token).filter(d -> e.getY()).map(to -> to.equals(t)).orElse(false));
	}

	public Parser<Integer> createExpressionParserToResult() {
		
		return stringParser("", ParserFactoryImpl::convert, o -> true, (token, e) -> Optional.of(token).filter(l -> e.getY()).filter(t -> e.getX()).map(t -> t.equals("0") || t.equals("1"))
					.or(() -> Optional.of(token).filter(l -> e.getY()).map(t -> t.equals("+") || t.equals("-"))).orElse(false));
	}
}
