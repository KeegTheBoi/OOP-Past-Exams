package a01a.e2;

import java.util.Optional;

public interface Logic {
    Optional<String> hit(Coord cord);

    boolean isOver();
}
