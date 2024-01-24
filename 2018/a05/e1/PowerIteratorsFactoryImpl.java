package a05.e1;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;


public class PowerIteratorsFactoryImpl implements PowerIteratorsFactory {

    private <X> PowerIterator<X> fromIterator(Iterator<X> iter) {
        return new PowerIterator<X>() {
            List<X> soFar = new ArrayList<>();
            @Override
            public Optional<X> next() {
                return Optional.of(iter)
                    .filter(Iterator::hasNext)
                    .map(Iterator::next)
                    .map(i -> {
                            soFar.add(i);
                            return i;
                        }
                    );
            }

            @Override
            public List<X> allSoFar() {
                return soFar;
            }

            @Override
            public PowerIterator<X> reversed() {
                List<X> reversed = new ArrayList<>(soFar);
                Collections.reverse(reversed);
                return fromIterator(reversed.iterator());
            }

        };
    }


    @Override
    public PowerIterator<Integer> incremental(int start, UnaryOperator<Integer> successive) {
        return fromIterator(Stream.iterate(start, successive).iterator());
    }

    @Override
    public <X> PowerIterator<X> fromList(List<X> list) {
        return fromIterator(list.iterator());
    }

    @Override
    public PowerIterator<Boolean> randomBooleans(int size) {
        return fromIterator(
            Stream.generate(Random::new)
                .map(Random::nextBoolean)
                .limit(size).iterator()
        );
    }

}
