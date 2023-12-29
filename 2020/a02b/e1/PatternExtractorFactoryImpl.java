package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory {


    private <T, O> PatternExtractor<T, O> general(Function<Stream<T>, O> sup, Predicate<T> predicate){
        return input -> Stream.iterate(
                input.stream(),
                    f -> !f.isEmpty(),
                    s-> s.dropWhile(predicate.negate())
                            .takewhile(predicate)
                ).map(sup)
                .collect(Collectors.toList()));
            
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
