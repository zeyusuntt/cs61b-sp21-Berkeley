package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 50;
    public static final int ROOMWIDTHUPPERLIMIT = 9;
    public static final int ROOMWIDTHLOWERLIMIT = 3;
    public static final int ROOMHEIGHTUPPERLIMIT = 7;
    public static final int ROOMHEIGHTLOWERLIMIT = 3;
    public static final int ROOMNUMUPPERLIMIT = 30;
    public static final int ROOMNUMLOWERLIMIT = 20;
    public static final int BUFFER = 2;
    private Random rand;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        /*
        Requirements::
        * 1. The generated world must include distinct rooms and hallways, though it may also include outdoor spaces.
        * 2. At least some rooms should be rectangular, though you may support other shapes as well.
        * 3. Your world generator must be capable of generating hallways that include turns (or equivalently, straight hallways that intersect).
        * 4. The world should contain a random number of rooms and hallways.
        * 5. The locations of the rooms and hallways should be random.
        * 6. The width and height of rooms should be random.
        * 7. Hallways should have a width of 1 or 2 tiles and a random length.
        * 8. Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from unused spaces.
        * 9. Rooms and hallways should be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways.
        * 10. All rooms should be reachable, i.e. there should be no rooms with no way to enter
        * 11. The world should be substantially different each time, i.e. you should not have the same basic layout with easily predictable features
        * */

        // initialize engine
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        // generate tileArr
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        String strSeed = input.substring(1,input.length()-2);
        long seed=Long.parseLong(strSeed);
        Random random = new Random(seed);
        Room[] roomArr = roomGenerator(random);
        roomPrinter(roomArr,finalWorldFrame);
        HashSet<Path> pathSet = roomPathGenerator(roomArr, random);
        pathPrinter(pathSet, finalWorldFrame);
        wallPrinter(finalWorldFrame);
        // add things to tileArr

        // display tileArr
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }
    /*
    * roomGenerator could generate a random number of rooms, then generator each rooms pos, height, width
    * */
    public Room[] roomGenerator(Random random) {
        // initiate the roomArr, the num range is 15-25
        int roomNum = RandomUtils.uniform(random, ROOMNUMLOWERLIMIT, ROOMNUMUPPERLIMIT);
        Room[] roomArr = new Room[roomNum];
        // for each room, generate its random position and width and height
        // width range is 3-11, height range is 3-7
        for (int i = 0; i < roomArr.length; i ++) {
            roomArr[i] = new Room();
            int curWidth = RandomUtils.uniform(random, ROOMWIDTHLOWERLIMIT, ROOMWIDTHUPPERLIMIT);
            int curHeight = RandomUtils.uniform(random, ROOMHEIGHTLOWERLIMIT, ROOMHEIGHTUPPERLIMIT);
            int curX = RandomUtils.uniform(random, BUFFER, WIDTH-BUFFER-ROOMWIDTHUPPERLIMIT);
            int curY = RandomUtils.uniform(random, BUFFER, HEIGHT-BUFFER-ROOMHEIGHTUPPERLIMIT);
            Position curPos = new Position(curX, curY);
            roomArr[i].width = curWidth;
            roomArr[i].height = curHeight;
            roomArr[i].pos = curPos;
        }
        return roomArr;
    }

    public void roomPrinter(Room[] roomArr, TETile[][] tiles) {
        // for every room in the roomArr, get its hashset and use for loop to print
        TETile floor = Tileset.FLOOR;
        for (int i = 0; i < roomArr.length; i++) {
            for (Position curPos: roomArr[i].roomSet()) {
                tiles[curPos.x][curPos.y] = floor;
            }
        }
    }

    /*
    * now we should connect all the rooms with path
    * 1. select two rooms from roomArr
    * 2. generate random point from the two rooms
    * 3. connect the two points with path, return position set
    * 4. iterate for all the room group
    * */
    public Position randomRoomPosGenerator(Room room, Random random) {
        // generate random room point
        Position result = new Position();
        result.x = RandomUtils.uniform(random, room.pos.x, room.pos.x + room.width);
        result.y = RandomUtils.uniform(random, room.pos.y, room.pos.y + room.height);
        return result;
    }

    public Position randomPosGenerator(Position pos1, Position pos2, Random random) {
        // input two room points, generate the random point between the two points
        Position result = new Position();
        result.x = RandomUtils.uniform(random, Math.min(pos1.x, pos2.x) + 1, Math.max(pos1.x, pos2.x));
        result.y = RandomUtils.uniform(random, Math.min(pos1.y, pos2.y) + 1, Math.max(pos1.y, pos2.y));
        return result;
    }

    public Path connectPath(Position pos1, Position pos2, Random random) {
        // connect two points, return the set of the path position
        if (pos1.x == pos2.x || pos1.y == pos2.y) {
            Path result = new Path(pos1, pos2);
            return result;
        }
        else if (Math.abs(pos1.x - pos2.x) == 1 || Math.abs(pos1.y - pos2.y) == 1) {
            int verticalOrNot = RandomUtils.uniform(random, 0, 2);
            Path result = new BendPath(pos1, pos2, verticalOrNot);
            return result;
        }
        else {
            int doubleOrNot = RandomUtils.uniform(random,0,2);
            int verticalOrNot = RandomUtils.uniform(random, 0, 2);

            if (doubleOrNot == 0) {
                Path result = new BendPath(pos1, pos2, verticalOrNot);
                return result;
            }
            else {
                Position midPos = randomPosGenerator(pos1, pos2, random);
                Path result = new DoubleBendPath(pos1, pos2, midPos, verticalOrNot);
                return result;
            }
        }

    }

    public HashSet<Path> roomPathGenerator(Room[] roomArr, Random random) {
        // input the roomArr, and connect them with path, then return the position set
        HashSet<Path> result = new HashSet<>();
        for (int i = 0; i <roomArr.length - 1; i ++) {
            Position pos1 = randomRoomPosGenerator(roomArr[i],random);
            Position pos2 = randomRoomPosGenerator(roomArr[i+1], random);
            Path curPath = connectPath(pos1, pos2, random);
            result.add(curPath);
        }
        return result;
    }

    public void pathPrinter(HashSet<Path> pathPosSet, TETile[][] tiles) {
        // input the path position set, print the path
        TETile floor = Tileset.FLOOR;
        for (Path eachPath: pathPosSet) {
            for (Position eachPos: eachPath.pathSet()) {
                tiles[eachPos.x][eachPos.y] = floor;
            }
        }
    }


    /*
    * Wall! check the surrounding tile, if they are surrounded by floor, then give wall
    * */
    public boolean ifWall(Position pos, TETile[][] tiles) {
        // no need to check border, since we set buffer in the tiles[][]
        if (tiles[pos.x][pos.y] == Tileset.FLOOR) {
            return false;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i ==0 && j == 0) {
                    continue;
                }
                if (tiles[pos.x + i][pos.y + j] == Tileset.FLOOR) {
                    tiles[pos.x][pos.y] = Tileset.WALL;
                    return true;
                }
            }
        }
        return false;
    }

    public void wallPrinter(TETile[][] tiles) {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                Position curPos = new Position(i, j);
                ifWall(curPos, tiles);
            }
        }
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.interactWithInputString("sf");
    }
}
