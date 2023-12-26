package a02c.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListSplitterFactoryImpl implements ListSplitterFactory {

    private <X> ListSplitter<X> splitFixed(final int dividend, final boolean offset){
        return l-> IntStream.range(0, ((offset ? (l.size() + dividend)  % dividend : 0) + l.size()) / dividend)
                .mapToObj(y -> l.stream().skip(dividend * y).limit(dividend).collect(Collectors.toList()))
                .collect(Collectors.toList());
            
    }

    @Override
    public <X> ListSplitter<X> asPairs() {
        return splitFixed(2, false);
    }

    @Override
    public <X> ListSplitter<X> asTriplets() {
        return splitFixed(3, false);
    }

    @Override
    public <X> ListSplitter<X> asTripletsWithRest() {
        return splitFixed(3, true);
    }

    @Override
    public <X> ListSplitter<X> bySeparator(X separator) {
        return byPredicate(c -> !c.equals(separator));
    }

    @Override
    public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(final List<X> list) {
                int delta = 0;
                boolean first = false;
                final List<List<X>> outer = new ArrayList<>();
                for (int i = 0; i < list.size(); i+=delta) {
                    int skip;
                    if(!first){
                        skip = list.stream().skip(i + 1).takeWhile(predicate).collect(Collectors.toList()).size();
                        outer.add(list.stream().skip(i).takeWhile(predicate).collect(Collectors.toList()));
                        first = true;
                    } else {
                        skip = list.stream().skip(i + 1).takeWhile(predicate.negate()).collect(Collectors.toList()).size();
                        outer.add(list.stream().skip(i).takeWhile(predicate.negate()).collect(Collectors.toList()));
                        first = false;
                    }
                    
                    delta = skip + 1;
                }
                return Collections.unmodifiableList(outer);
            }
        };
    }
    
}
