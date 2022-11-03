package byow.Bsp;

import java.util.HashSet;
import java.util.Random;

public class Path {
    // notice it's a static class, so we don't have non-static variables like startPos and endPos,
    // this is a difference from version1
    // the advantage is that it's very convenient, we don't have to create a new instance
    public static HashSet<Position> connectPoint(Position pos1, Position pos2, Random random) {
        // goal: creating a path between two points, the path could be straight, bend, double-bend
        // first, check if x or y is equal, if so, return straight path, no need!!!!
        // second, randomly generate int ver, if ver = 1, first ver and then hon
        // third, get the direction of the path, then we can just use start to end
        // fourth, randomly generate the bend point and save the path point to the HashSet
        HashSet<Position> result = new HashSet<>();
        int ver = -1;
        ver = RandomUtils.uniform(random, 0, 2);

        int distanceX = pos2.x - pos1.x;
        int distanceY = pos2.y - pos1.y;
        int increaseX = distanceX == 0 ? 0: distanceX/Math.abs(distanceX);
        int increaseY = distanceY == 0 ? 0: distanceY/Math.abs(distanceY);
        int x = pos1.x;
        int y = pos1.y;
        if (ver == 1) {
            // add check y1 = y2
            int midY;
            if (pos1.y == pos2.y) {
                midY = y;
            }
            else {
                midY = y + RandomUtils.uniform(random,0, Math.abs(distanceY)) * increaseY;
            }
            while (y != midY) {
                y += increaseY;
                result.add(new Position(x,y));
            }
            while (x != pos2.x) {
                x += increaseX;
                result.add(new Position(x, y));
            }
            while (y != pos2.y) {
                y += increaseY;
                result.add(new Position(x, y));
            }
        }
        else if (ver == 0) {
            int midX;
            if (pos1.x == pos2.x) {
                midX = x;
            }
            else {
                midX = x + RandomUtils.uniform(random, 0, Math.abs(distanceX)) * increaseX;
            }
            while (x != midX) {
                x += increaseX;
                result.add(new Position(x, y));
            }
            while (y != pos2.y) {
                y += increaseY;
                result.add(new Position(x, y));
            }
            while (x != pos2.x) {
                x += increaseX;
                result.add(new Position(x, y));
            }
        }
        return result;
    }
}