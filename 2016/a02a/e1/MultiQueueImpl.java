package a02a.e1;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class MultiQueueImpl<Q, T> implements MultiQueue<T,Q> {
	private final Map<Q, Deque<T>> multi = new HashMap<>();
	    /**
     * @return the set of queues currently available (or working)
     */
    public Set<Q> availableQueues() {
		return multi.keySet();
	}
    
    /**
     * @param creates a new queue
     * @throws IllegalArgumentException if queue is already available
     */
    public void openNewQueue(Q queue) {
		var value = getValue(queue, Predicate.not(multi::containsKey));
		multi.putIfAbsent(value, new ArrayDeque<>());
	}
	
	private Q getValue(Q queue, Predicate<Q> pred) {
		return Optional.of(queue).filter(pred).orElseThrow(IllegalArgumentException::new);
	}
	
	private Deque<T> getDeque(Q queue) {
		return multi.get(getValue(queue, multi::containsKey));
	}
    
    /**
     * @param queue, is the queue we check
     * @return whether queue is empty
     * @throws IllegalArgumentException if queue is not available
     */
    public boolean isQueueEmpty(Q queue) {
		return getDeque(queue).isEmpty();
	}
    
    /**
     * @param elem, is the element to add
     * @param queue, is the queue where the element is to be added
     * @throws IllegalArgumentException if queue is not available
     */
    public  void enqueue(T elem, Q queue) {
		getDeque(queue).add(elem);
	}
    
    /**
     * @param queue, is the queue where to take the next element
     * @return the next element in queue, or empty if there's none
     * @throws IllegalArgumentException if queue is not available
     */
    public Optional<T> dequeue(Q queue) {
		return Optional.of(getDeque(queue)).filter(Predicate.not(Deque::isEmpty)).map(Deque::removeFirst);
    }
    /**
     * dequeues one element from any queue, where possible
     * @return a map of dequeued elements 
     */
    public Map<Q,Optional<T>> dequeueOneFromAllQueues() {
		return multi.entrySet().stream().map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), dequeue(e.getKey()))).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
	}
    
    /**
     * @return the set of all enqueued elements
     */
    public Set<T> allEnqueuedElements() {
		return multi.values().stream().flatMap(Deque::stream).collect(Collectors.toSet());
	}
    
    /**
     * Empties a queue
     * @param queue, the queue to be emptied
     * @return the list of elements enqueued
     * @throws IllegalArgumentException if queue is not available
     */
    public List<T> dequeueAllFromQueue(Q queue) {
		Deque<T> defCopy = getDeque(queue);
		return defCopy.stream().map(s -> getDeque(queue).remove()).toList();
	}
    /**
     * Empties a queue and move all of its elements in some other available queue
     * @param , the queue to be emptied
     * @throws IllegalArgumentException if queue is not available
     * @throws IllegalStateException if there's no alternative queue for moving elements to
     */
    public void closeQueueAndReallocate(Q queue) {
		List<T> move = dequeueAllFromQueue(queue);
		var result = multi.keySet().stream().filter(o -> !o.equals(queue)).findAny().orElseThrow(IllegalStateException::new);
		multi.get(result).addAll(move);
		multi.remove(queue);
	}
 }
