package a02a.e2;

public record Coord(int x, int y) {
    public static Coord moveUp(final Coord c) {
        return new Coord(c.x(), c.y() - 1);
    }

    public static Coord moveRight(final Coord c) {
        return new Coord(c.x() + 1, c.y());
    }

    public static Coord moveLeft(final Coord c) {
        return new Coord(c.x() - 1, c.y());
    }

    public static Coord moveDown(final Coord c) {
        return new Coord(c.x(), c.y() + 1);
    }
}
