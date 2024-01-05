package a01d.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import a01d.e1.Timetable.Day;

public class TimetableFactoryImpl implements TimetableFactory {

    public record Booking(String room, String course, Day day, int hour, int duration) {}
    
    @Override
    public Timetable empty() {
        return new Timetable() {
            List<Booking> list = new ArrayList<>();
            @Override
            public Set<String> rooms() {
                return list.stream().map(Booking::room).distinct().collect(Collectors.toSet());
            }

            @Override
            public Set<String> courses() {
                return list.stream().map(Booking::course).distinct().collect(Collectors.toSet());
            }

            @Override
            public List<Integer> hours() {
                return list.stream().map(Booking::hour).sorted().distinct().toList();
            }

            @Override
            public Timetable addBooking(String room, String course, Day day, int hour, int duration) {
                list.addAll(IntStream.range(hour, hour + duration)
                    .mapToObj(h -> new Booking(room, course, day, h, duration))
                    .toList());
                return this;
            }

            @Override
            public Optional<Integer> findPlaceForBooking(String room, Day day, int duration) {
                return this.hours()
                .stream()
                .filter(h -> Stream.iterate(h, i->i+1)
                        .limit(duration)
                        .allMatch(hh -> list.stream().noneMatch(b -> b.room.equals(room) && b.hour == hh)))
                .findFirst();
            }

            @Override
            public Map<Integer, String> getDayAtRoom(String room, Day day) {
                return list.stream().filter(t -> t.room().equals(room) && t.day().equals(day)).collect(Collectors.toMap(Booking::hour, Booking::course));
            }

            @Override
            public Optional<Pair<String, String>> getDayAndHour(Day day, int hour) {
                return list.stream().filter(t -> t.day == day && t.hour() == hour).map(t -> new Pair<>(t.course(), t.room())).findFirst(); 
            }

            @Override
            public Map<Day, Map<Integer, String>> getCourseTable(String course) {
                return list.stream().filter(t -> t.course().equals(course)).collect(Collectors.groupingBy(Booking::day, Collectors.toMap(Booking::hour, Booking::room)));
            }
            
        };
    }

}
