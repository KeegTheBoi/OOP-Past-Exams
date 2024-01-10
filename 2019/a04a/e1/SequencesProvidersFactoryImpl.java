package a04a.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public class SequencesProvidersFactoryImpl implements SequencesProvidersFactory {

	private class Provider<E> implements SequencesProvider<E> {	
		private Iterator<List<E>> iter;
		private Supplier<Stream<List<E>>> suppl;
		
		public Provider(Supplier<Stream<List<E>>> streamer) {
			suppl = streamer;
			iter = suppl.get().iterator();
		}
		
		public List<E> nextSequence() {
			return iter.next();
		}
		
		public boolean hasOtherSequences() {
			return iter.hasNext();
		}
		
		public void reset() {
			iter = suppl.get().iterator();
		}	
	}
	
	public <E> SequencesProvider<E> iterative(E e) {
		return new Provider<E>(() -> iterativeStream(e));
	}
	
	private <E> Stream<List<E>> iterativeStream(E e)  {
		return Stream.iterate(0, i -> i + 1).map(i -> Collections.nCopies(i, e));
	}
	
	public <E> SequencesProvider<E> alternating(E e1, E e2) {
		return new Provider<E>(() -> alternatingStream(e1, e2));
	}
	
	private <E> Stream<List<E>> alternatingStream(E e1, E e2) {
		return Stream.iterate(0, i -> i + 1).flatMap(i -> Stream.of(Collections.nCopies(i, e1), Collections.nCopies(i, e2)));
	}
	
	public <E> SequencesProvider<E> iterativeBounded(E e, int bound) {
		return new Provider<E>(() -> iterativeStream(e).limit(bound));
	}
	
	public <E> SequencesProvider<E> alternatingBounded(E e1, E e2, int bound) {
		return new Provider<E>(() -> alternatingStream(e1, e2).limit(bound));
	}
}
