package a01a.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic{

    public enum Player {
        X, O
    }

    private final int size;
    private final Map<Coord, Player> setCoord;

    public LogicImpl(final int size) {
        this.size = size;
        this.setCoord = new HashMap<>();
    }

    private boolean second;

    @Override
    public Optional<String> hit(Coord cord) {
        if(this.isPosCorrect(cord)) {  
             
            if(second) {
                second = false;
                setCoord.put(cord, Player.O);  
                return Optional.of(Player.O.name());           
            } else {
                second = true;
                setCoord.put(cord, Player.X);  
                return Optional.of(Player.X.name());
            }           
        }
        return Optional.empty();
    }

    private boolean isPosCorrect(Coord pos) {
        return pos.y() + 1 == this.size || this.setCoord.containsKey(new Coord(pos.x(), pos.y() + 1)); 
    }

    @Override
    public boolean isOver() {
        var orizzonal = getListByOrient(false);
        
        var vertical = getListByOrient(true);

        for (int i = 0; i < this.size; i++) {
            
            if(countConsecutive(orizzonal, Player.X, i) ||
            countConsecutive(orizzonal, Player.O, i) ||
            countConsecutive(vertical, Player.X, i) ||
            countConsecutive(vertical, Player.O, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean countConsecutive(List<List<Coord>> cords, Player player, int i) {
        return cords.get(i).stream()
            .filter(r -> setCoord.containsKey(r))
            .dropWhile(c -> setCoord.get(c).equals(player))
            .takeWhile(o -> setCoord.get(o).equals(player.equals(Player.X) ? Player.O : Player.X))
            .count() == 3L;
    }

    private List<List<Coord>> getListByOrient(boolean vertical){
        return IntStream.range(0, size)
            .mapToObj(
                x -> IntStream.range(0, size)
                .mapToObj(y -> vertical ? new Coord(x, y) : new Coord(y, x))
                .collect(Collectors.toList())
            )
        .collect(Collectors.toList());
    }
    
    
}
