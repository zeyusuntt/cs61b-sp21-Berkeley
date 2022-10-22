package byow.Core;

import java.util.HashSet;

public class BendPath extends Path{
    public Position startPos;
    public Position endPos;
    // vertical = 1: first vertical and then horizontal
    // vertical = 0: first horizontal and then vertical
    public int vertical;
    public BendPath() {

    }

    public BendPath(Position pos1, Position pos2, int ver) {
        startPos = pos1;
        endPos = pos2;
        vertical = ver;
    }

    public HashSet<Position> pathSet(){
        HashSet<Position> pathSet = new HashSet<>();
        // vertical and then horizontal
        if (vertical == 1) {
            Position midPos = new Position(startPos.x, endPos.y);
            for (int i = Math.min(startPos.y, midPos.y); i <= Math.max(startPos.y,midPos.y); i++) {
                Position cur = new Position(startPos.x,i);
                pathSet.add(cur);
            }
            for (int i = Math.min(midPos.x, endPos.x); i <= Math.max(midPos.x, endPos.x); i++) {
                Position cur = new Position(i, endPos.y);
                pathSet.add(cur);
            }
        }
        // horizontal and then vertical
        else if (vertical == 0){
            Position midPos = new Position(endPos.x, startPos.y);
            for (int i = Math.min(startPos.x, midPos.x); i <= Math.max(startPos.x, midPos.x); i++) {
                Position cur = new Position(i, startPos.y);
                pathSet.add(cur);
            }
            for (int i = Math.min(midPos.y, endPos.y); i<= Math.max(midPos.y, endPos.y); i++) {
                Position cur = new Position(endPos.x, i);
                pathSet.add(cur);
            }
        }
        else {
            throw new UnsupportedOperationException("ERROR: vertical must be 0 or 1");
        }
        return pathSet;
    }
}
