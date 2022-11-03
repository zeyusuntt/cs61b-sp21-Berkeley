package byow.Bsp;


import java.util.HashSet;

public class Room {
    public Position pos;
    public int width;
    public int height;

    public Room() {

    }

    public Room(Position p, int w, int h) {
        this.pos = p;
        this.width = w;
        this.height = h;
    }

    public HashSet<Position> roomSet() {
        HashSet<Position> roomSet = new HashSet<>();
        for (int i = pos.x; i < pos.x + width; i++) {
            for (int j = pos.y; j < pos.y + height; j++) {
                Position cur = new Position(i, j);
                roomSet.add(cur);
            }
        }
        return roomSet;
    }
}
