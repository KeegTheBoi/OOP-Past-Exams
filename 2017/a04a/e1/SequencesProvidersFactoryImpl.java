package a04a.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
/**
 * An interface to model a factory for various kinds of SequencesProvider
 */
public class SequencesProvidersFactoryImpl implements SequencesProvidersFactory {
	
	private enum Type<E> {
		
		COPIES(this::iterativeStream), ALTERNATING(this::alteratingStream);
		
		private BiFunction<E, E, Stream<List<E>>> traf;
		
		private Type(BiFunction<E, E, Stream<List<E>>> traf) {
			this.trasf = trasf;
		}
		
	}
	
	private class Provider<E> implements SequencesProvider<E> {
		
		private Iterator<List<E>> listIter;
		private Iterator<Stream<List<E>>> iterStream;
		
		public Provider(Stream<List<E>> streamer) {
			iterStream = Stream.generate(() -> streamer).iterator();
			listIter = iterStream.next().iterator();
		}
		
		public List<E> nextSequence() {
			var next = listIter.next();
			return next;
		}
		
		public boolean hasOtherSequences() {
			return listIter.hasNext();
		}
		
		public void reset() {
			listIter = iterStream.next().iterator();
		}
			
		
	}
	
	public <E> SequencesProvider<E> iterative(E e) {
		return new Provider<E>(iterativeStream(e, e));
	}
	
	private <E> Stream<List<E>> iterativeStream(E e, E e1)  {
		return Stream.iterate(0, i -> i + 1).map(i -> Collections.nCopies(i, e));
	}
	
	public <E> SequencesProvider<E> alternating(E e1, E e2) {
		return new Provider<E>(alternatingStream(e1, e2));
	}
	
	private <E> Stream<List<E>> alternatingStream(E e1, E e2) {
		return Stream.iterate(0, i -> i + 1).flatMap(i -> Stream.of(Collections.nCopies(i, e1), Collections.nCopies(i, e2)));
	}
	
	public <E> SequencesProvider<E> iterativeBounded(E e, int bound) {
		return new Provider<E>(iterativeStream(e).limit(bound));
	}
	
	public <E> SequencesProvider<E> alternatingBounded(E e1, E e2, int bound) {
		return new Provider<E>(alternatingStream(e1, e2).limit(bound));
	}
}
