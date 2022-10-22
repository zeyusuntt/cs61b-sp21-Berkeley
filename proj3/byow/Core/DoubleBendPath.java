package byow.Core;

import java.util.HashSet;

public class DoubleBendPath extends Path {
    public Position startPos;
    public Position endPos;
    public Position midPos;
    // vertical = 1: first vertical and then horizontal
    // vertical = 0: first horizontal and then vertical
    public int vertical;

    public DoubleBendPath() {

    }

    public DoubleBendPath(Position pos1, Position pos2, Position pos3, int ver) {
        startPos = pos1;
        endPos = pos2;
        midPos = pos3;
        vertical = ver;
    }

    public HashSet<Position> pathSet() {
        HashSet<Position> pathSet = new HashSet<>();
        BendPath startMid = new BendPath(startPos,midPos,vertical);
        BendPath endMid = new BendPath(endPos, midPos, vertical);
        pathSet.addAll(startMid.pathSet());
        pathSet.addAll(endMid.pathSet());
        return pathSet;
    }

}
