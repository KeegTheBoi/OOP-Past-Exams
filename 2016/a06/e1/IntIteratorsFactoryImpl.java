package a06.e1;

import java.util.*;
import java.util.stream.*;

/* 
 * A factory for various iterators over integers. Recall that a java.util.Iterator<X> is an interface
 * with methods next() and hasNext(), used to model a (possibly infinite) source of elements of type X.
 * Recall that if hasNext() gives 'false' then next() should throw a NoSuchElementException.
 * Note also that an iterator can actually by easily created also out of a collection or a stream.   
 */

public class IntIteratorsFactoryImpl implements IntIteratorsFactory {
     
    /**
     * @return an empty iterator
     */
    public Iterator<Integer> empty() {
		return Stream.<Integer>of().iterator();
	}
    
    /**
     * @return an iterator indefinitely producing zeros 
     */
    public Iterator<Integer> zeros() {
		return Stream.generate(() -> 0).iterator();
	}
    
    /**
     * @return an iterator indefinitely alternating ones and zeros, again and again
     * note its implementation is optional..
     */
    public Iterator<Integer> alternateOneAndZeroIndefinitely() {
		return Stream.generate(() -> 0).flatMap(i -> Stream.concat(Stream.of(i), Stream.of(1))).iterator();
	}
    
    /**
     * @return an iterator from start to end, both included
     */
    public Iterator<Integer> fromTo(int start, int end) {
		return IntStream.rangeClosed(start, end).boxed().map(Integer::intValue).iterator();
	}
        
    /**
     * @return an iterator indefinitely going from start to stop, again and again
     */
    public Iterator<Integer> fromToIndefinitely(int start, int stop) {
		return Stream.generate(() -> 0).flatMap(i -> IntStream.rangeClosed(start, stop).boxed().map(Integer::intValue)).iterator();
	}
	
    /**
     * @return an iterator out of a list
     */
    public Iterator<Integer> fromList(List<Integer> list) {
		return list.iterator();
	}
}
