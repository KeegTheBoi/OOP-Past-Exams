package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TimetableFactoryImpl implements TimetableFactory {

    @Override
    public Timetable empty() {
        return new Timetable() {
            List<Pair<String, String>> table = new ArrayList<>();

            @Override
            public Timetable addHour(String activity, String day) {
                table.add(new Pair<>(activity, day));
                return this;
            }

            @Override
            public Set<String> activities() {
                return table.stream().map(Pair::get1).distinct().collect(Collectors.toSet());
            }

            @Override
            public Set<String> days() {
                
                return table.stream().map(Pair::get2).distinct().collect(Collectors.toSet());
            }

            @Override
            public int getSingleData(String activity, String day) {
                
                return (int)table.stream().filter(p -> p.equals(new Pair<>(activity, day))).count();
            }

            @Override
            public int sums(Set<String> activities, Set<String> days) {
                return (int)table.stream().filter(t -> activities.contains(t.get1())).filter(l -> days.contains(l.get2())).count();
            }
            
        };
    }

    @Override
    public Timetable single(String activity, String day) {
        return empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        table2.activities().stream()
            .flatMap(a -> 
                table2.days().stream()
                .map(d -> new Pair<>(a, d))
                .filter(k -> table2.getSingleData(k.get1(), k.get2()) > 0)
                .peek(System.out::println)
            ).forEach(p -> table1.addHour(p.get1(), p.get2()));
        return table1;
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        //Works but clients need unconventional request, if you don't allow certain hours you shouldn't have some time table associated with
        Timetable cutted = empty();
        table.activities().stream()
            .flatMap(a -> 
                table.days().stream()
                .map(d -> new Pair<>(a, d))
                .filter(k -> this.inBetween(table, k, bounds))
                .peek(System.out::println)
            ).forEach(p -> cutted.addHour(p.get1(), p.get2()));
        return cutted;
    }

    private boolean inBetween(Timetable table, Pair<String, String> p, BiFunction<String, String, Integer> bound) {
        int single = table.getSingleData(p.get1(), p.get2());
        return single > 0 && single < bound.apply(p.get1(), p.get2());  
    }

}
