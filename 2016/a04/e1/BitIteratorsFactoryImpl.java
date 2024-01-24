package a04.e1;

import java.util.*;
import java.util.stream.*;

/* 
 * A factory for various iterators over bits. Recall that a java.util.Iterator<X> is an interface
 * with methods next() and hasNext(), used to model a (possibly infinite) source of elements of type X.
 * Recall that if hasNext() gives 'false' then next() should throw a NoSuchElementException.   
 */

public class BitIteratorsFactoryImpl implements BitIteratorsFactory {
    
    /**
     * @return an empty iterator
     */
    public Iterator<Bit> empty() {
		List<Bit> empty = new ArrayList<>();
		return empty.iterator();
	}
    
    /**
     * @return an iterator indefinitely producing ZEROs 
     */
    public Iterator<Bit> zeros() {
		return Stream.generate(() -> Bit.ZERO).iterator();
	}
    /**
     * @return an iterator indefinitely producing ONEs 
     */
    public Iterator<Bit> ones() {
		return Stream.generate(() -> Bit.ONE).iterator();
	}
    
    /**
     * @return an iterator producing 8 bits out of an integer in [0,255], starting from the least significative (LSB)
     * for instance: 127 produces ONE,ONE,ONE,ONE,ONE,ONE,ONE,ZERO
     * suggestion: 
     * - b % 2 gives the first bit, then do: b = b/2
     * - b % 2 now gives the second bit, then do: b = b/2
     * - b % 2 now gives the third bit,.. and so on
     */
    public Iterator<Bit> fromByteStartingWithLSB(int b) {
		
		return Stream.iterate(b, k -> k / 2).limit(8).map(y -> y  % 2 == 1 ? Bit.ONE : Bit.ZERO).iterator();
	}
        
    /**
     * @return an iterator of bits out of a list of bits
     */
    public Iterator<Bit> fromBitList(List<Bit> list) {
		return list.iterator();
	}
        
    /**
     * @return an iterator of bits out of a list of booleans: false becomes ZERO, true becomes ONE
     * note this is optional..
     */
    public Iterator<Bit> fromBooleanList(List<Boolean> list) {
		return list.stream().map(b -> b == true ? Bit.ONE :Bit.ZERO).iterator();
	}
}
