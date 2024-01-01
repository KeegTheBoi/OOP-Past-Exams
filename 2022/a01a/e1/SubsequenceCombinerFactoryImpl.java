package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {
   
     
    private <X, L> SubsequenceCombiner<X,L>  tripletsMapper(Function<Stream<X>, L> mapper) {
        return re -> IntStream.range(0, (re.size() + (re.size() % 3)) / 3)
            .mapToObj(
                i -> mapper.apply(
                    re.stream()
                    .skip(i*3)
                    .limit(3))              
            ).collect(Collectors.toList()); 
    }
    /**
     * @return a SubsequenceCombiner that turns triplets of
    integers into their sum
     * e.g.: e1,e2,e3,e4,e5,e6,e7,e8 --> (e1+e2+e3),(e4+e5+e6),
    (e7+e8)
     */
    public SubsequenceCombiner<Integer,Integer> tripletsToSum() {
        return tripletsMapper(s ->  s.mapToInt(Integer::intValue).sum());
    }
     
    public <X> SubsequenceCombiner<X,List<X>> tripletsToList() {
        return tripletsMapper(s -> s.collect(Collectors.toList()));
    }
    
    private int nextInd;
    public SubsequenceCombiner<Integer,Integer> countUntilZero() {
        return l -> countWithPredicate(v -> v == 0, l);
    }

    public List<Integer> countWithPredicate(Predicate<Integer> p, List<Integer> list) {
        nextInd = 0;
        return IntStream.range(0, (int)list.stream().filter(p).count() + 1)
        .mapToObj(
            i -> { 
                var count = (int)list.stream().
                    skip(nextInd)
                    .takeWhile(p.negate())
                    .count();
                nextInd+=count + 1;
                return count;
            }
        ).filter(p.negate()).collect(Collectors.toList());
    }

    public <X,Y> SubsequenceCombiner<X,Y> singleReplacer(Function<X,Y>
    function) {
        return re ->re.stream().map(function).collect(Collectors.toList());
    }
    /**
     * @return a SubsequenceCombiner that turns subsequences of
    integers as soon as their sum
     * overcomes @threshold into a list of them
     * for an example, look at its testcase in class Test
     */
    private int delta;
    private int cum;

    public SubsequenceCombiner<Integer,List<Integer>> cumulateToList(int threshold) {
       return re -> Stream.iterate(0, i -> i < e.size(), i-> i + delta)
            .map(sk-> 
               re.stream()
               .skip(sk)    
               .takeWhile(u -> cum < treshold)
               .peek(k -> cum += k) 
               .collect(Collectors.toList())
            ).peek(l-> delta = l.size())
            .peek(h -> cum = 0)
            .collect(Collectors.toList());
    }

    

    
   
}
