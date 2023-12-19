package a01b.e1;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TraceImpl implements TraceFactory{

    @Override
    public <X> Trace<X> fromSuppliers(Supplier<Integer> sdeltaTime, Supplier<X> svalue, int size) {
        return new SimpleTrace<>(streamOfEvents(sdeltaTime, svalue, size));
    }

    @Override
    public <X> Trace<X> constant(Supplier<Integer> sdeltaTime, X value, int size) {
        return new SimpleTrace<>(streamOfEvents(sdeltaTime, () -> value, size));
    }

    @Override
    public <X> Trace<X> discrete(Supplier<X> svalue, int size) {
        return new SimpleTrace<>(streamOfEvents(() -> 1, svalue, size));
    }

    private <X> Stream<Event<X>> streamOfEvents(Supplier<Integer> sdeltaTime, Supplier<X> svalue, int size) {
        return Stream.iterate(0, delta -> delta + sdeltaTime.get()).map(d -> (Event<X>)new EventImpl<>(d, svalue.get())).limit(size);
    }

}   
