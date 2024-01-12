package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory {

    private int delta = 0;
    private <T, O> PatternExtractor<T, O> general(Function<Stream<T>, O> sup, Predicate<T> predicate){
        delta = 0;
        return input -> Stream.iterate(0, k -> k < input.size(), p -> p + delta)
                .map(i -> 
                    input.stream()
                    .skip(i)
                    .takeWhile(predicate)
                    .peek(System.out::println)
                    .toList()
                ).peek(u -> delta = u.size() + 1)
                .map(List::stream)
                .map(sup)
                .toList();
    }

    @Override
    public PatternExtractor<Integer, Integer> countConsecutiveZeros() {
        
        return this.general(t -> Integer.parseInt(Long.toString(t.count())), l -> l == 0);
    }

    @Override
    public PatternExtractor<Double, Double> averageConsecutiveInRange(double min, double max) {
        return this.general(i -> i.mapToDouble(y -> y).average().getAsDouble(), o -> o < max && o > min);
    }

    @Override
    public PatternExtractor<String, String> concatenateBySeparator(String separator) {
        return this.general(i -> i.collect(Collectors.joining()), o -> !o.contains(separator));
    }

    @Override
    public PatternExtractor<String, Double> sumNumericStrings() {
        return this.general(i -> i.mapToDouble(k -> Double.parseDouble(k)).sum(), o -> tryParse(o));
    }

    private boolean tryParse(String val) {
        try{
            Double.parseDouble(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
}
