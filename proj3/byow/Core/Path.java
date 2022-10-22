package byow.Core;

import java.util.HashSet;

public class Path {
    // straight path, so need to know two point at the end
    // have a variable to save the vertical or horizontal
    // boolean type: true vertical, false horizontal
    // need a set to save all the path point
    // include bendPath
    public Position startPos;
    public Position endPos;

    public Path() {

    }

    public Path(Position pos1, Position pos2) {
        startPos = pos1;
        endPos = pos2;
    }

    public HashSet<Position> pathSet(){
        HashSet<Position> pathSet = new HashSet<>();
        // straight path, startPos and endPos have at least x/y equal
        if (startPos.x == endPos.x) {
            for (int i = Math.min(startPos.y, endPos.y); i <= Math.max(startPos.y, endPos.y); i++) {
                Position cur = new Position(startPos.x, i);
                pathSet.add(cur);
            }
        }
        else if (startPos.y == endPos.y){
            for (int i = Math.min(startPos.x, endPos.x); i <= Math.max(startPos.x, endPos.x); i++) {
                Position cur = new Position(i, startPos.y);
                pathSet.add(cur);
            }
        }
        else {
            throw new UnsupportedOperationException("ERROR: startPos.x != endPos.x and startPos.y!= endPos.y");
        }
        return pathSet;
    }



}

