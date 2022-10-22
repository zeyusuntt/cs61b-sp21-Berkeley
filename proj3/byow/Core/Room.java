package byow.Core;

import java.util.HashSet;

public class Room {
    public Position pos; // bottom left point
    public int width;
    public int height;

    public Room() {

    }

    public Room(Position p, int w, int h) {
        this.pos = p;
        this.width = w;
        this.height = h;
    }

    // what method should room class have?
    // return height, width, center position, hashset of all the room points to fill the room with floor
    public int height(){
        return height;
    }
    public int width() {
        return width;
    }
    public Position pos() {
        return pos;
    }
    public Position centerPos() {
        // notice that odd and even number's centerPos is different
        Position center = new Position(width/2, height/2);
        return center;
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
