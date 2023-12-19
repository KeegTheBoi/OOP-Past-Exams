package a01a.e2;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LogicsImpl implements Logic {

    Set<Coord> boat;
    private int size;
    private final int boatSize;
    private int count;
    int countWin;

    public LogicsImpl(int size, int boat) {
        this.size = size;
        this.boatSize = boat;
        init();
    }

    public void init() {
        int start = new Random().nextInt(size - boatSize);
        int y = new Random().nextInt(0, size);
        boat = IntStream.range(0, boatSize)
            .mapToObj(i -> 
                new Coord(
                    i + start,
                    y
                )
            ).collect(Collectors.toSet());
        boat.forEach(System.out::println);
    }

    @Override
    public String hit(Coord c) {
        if(boat.contains(c)) {
            countWin++;
            return "X";
        }else {
            count++;
            return "O";
        }
    }

    @Override
    public boolean isOVer() {
        return count > 5 || countWin == boatSize;
    }

}
