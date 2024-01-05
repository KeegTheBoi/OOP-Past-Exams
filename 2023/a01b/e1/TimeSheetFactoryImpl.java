package a01b.e1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private class TimeSheetImpl implements TimeSheet {
        
        private final Map<Pair<String, String>, Integer> sheet = new HashMap<>();

        public TimeSheetImpl(List<String> activities, List<String> days, List<List<Integer>> data) {
            IntStream.range(0, activities.size())
                .boxed()
                .flatMap(i -> 
                    IntStream.range(0, days.size())
                    .mapToObj(j -> new Pair<>(i, j))
                )
                .forEach(p -> 
                    sheet.put(
                        new Pair<>(activities.get(p.get1()), days.get(p.get2())),
                        data.get(p.get1()).get(p.get2())
                    )
                );      
        }

        @Override
        public List<String> activities() {
            return sheet.keySet().stream().map(Pair::get1).sorted().distinct().toList();
        }

        @Override
        public List<String> days() {
            return sheet.keySet().stream().map(Pair::get2).sorted().distinct().toList();
        }

        @Override
        public int getSingleData(String activity, String day) {
            return Optional.of(new Pair<>(activity, day)).filter(sheet::containsKey).map(sheet::get).orElse(0);
        }

        @Override
        public Map<String, Integer> sumsPerActivity() {
            return sheet.entrySet().stream().collect(Collectors.groupingBy(p -> p.getKey().get1(), Collectors.summingInt(Map.Entry::getValue)));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return sheet.entrySet().stream().collect(Collectors.groupingBy(p -> p.getKey().get2(), Collectors.summingInt(Map.Entry::getValue)));
        }
        
    }

    private static String act = "act";
    private static String day = "day";

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        return new TimeSheetImpl(
            IntStream.rangeClosed(1, numActivities).mapToObj(i -> act.concat(String.valueOf(i))).toList(),
            IntStream.rangeClosed(1, numNames).mapToObj(i -> day.concat(String.valueOf(i))).toList(),
            IntStream.range(0, numActivities).mapToObj(i -> IntStream.range(0, numNames).mapToObj(u -> hours).toList()).toList()
        );
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetImpl(activities, days, data);
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
    
        return new TimeSheetImpl(
            IntStream.rangeClosed(1, numActivities).mapToObj(i -> act.concat(String.valueOf(i))).toList(),
            IntStream.rangeClosed(1, numDays).mapToObj(i -> day.concat(String.valueOf(i))).toList(),
            null
        );
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        // TODO Auto-generated method stub
        return null;
    }

}
