package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {
   
    private int delta;
    private int cum;

    public SubsequenceCombiner<Integer,Integer> tripletsToSum() {
        return tripletsMapper(s ->  s.mapToInt(Integer::intValue).sum());
    }
     
    public <X> SubsequenceCombiner<X,List<X>> tripletsToList() {
        return tripletsMapper(s -> s.collect(Collectors.toList()));
    }
    
    public SubsequenceCombiner<Integer,Integer> countUntilZero() {
       delta = 0;
       return re -> combinerMap(r -> r == 0, s -> (int)s.count(),t -> t,u -> delta = u);
    }

    public <X,Y> SubsequenceCombiner<X,Y> singleReplacer(Function<X,Y>
    function) {
        return re ->re.stream().map(function).collect(Collectors.toList());
    }
     
    public SubsequenceCombiner<Integer,List<Integer>> cumulateToList(int threshold) {
       delta = 0;
       cum = 0;
       return re -> combinerMap(r -> cum < treshold, s -> s.collect(Collectors.toList()), t -> cum += t, u -> { delta = u.size(); cum = 0; });

    private <X, Y> SubsequenceCombiner<X, Y> combinerMap(
         Predicate<X> pred, 
         Function<Stream<X>, Y> map,
         Consumer<X> action,
         Consumer<Y> reset
      ) {
          delta = 0;
          return re -> Stream.iterate(0, i -> i < e.size(), f -> f + delta)
               .map(sk-> map.apply(
                     re.stream()
                     .skip(sk)    
                     .takeWhile(predicate)
                     .peek(action) 
                  )
               )
               .peek(reset)
               .collect(Collectors.toList());
    }
    
    private <X, L> SubsequenceCombiner<X,L>  tripletsMapper(Function<Stream<X>, L> mapper) {
        return re -> IntStream.range(0, (re.size() + (re.size() % 3)) / 3)
            .mapToObj(
                i -> mapper.apply(
                    re.stream()
                    .skip(i*3)
                    .limit(3))              
            ).collect(Collectors.toList()); 
    }
}
