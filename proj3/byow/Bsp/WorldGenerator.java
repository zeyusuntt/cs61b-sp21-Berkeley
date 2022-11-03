package byow.Bsp;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class WorldGenerator {
    // need to do:
    // partition every node in the tree, thus need a while loop
    // first create the root, and then add it to the arrayDeque, and for every level of nodes, redo the same partition
    // use the return value of the partition to check if readd the value to the arrayDeque
    public final int MIN_ROOM_NUM = 8;
    public final int MAX_ROOM_NUM = 14;
    public final int WIDTH = 100;
    public final int HEIGHT = 40;
    public Position door;
    public HashSet<Position> floorPos = new HashSet<>();
    public HashSet<Position> wallPos;

    public void createWord(TETile[][] tiles, Random random) {
        List<BPSpace> listSpaces = new ArrayList<>(); // to save all the BSP
        ArrayDeque<BPSpace> spaceDeque = new ArrayDeque<>();

        BPSpace root = new BPSpace(new Position(0, 0), WIDTH, HEIGHT);
        spaceDeque.offer(root);
        listSpaces.add(root);
        int roomNum = RandomUtils.uniform(random, MIN_ROOM_NUM, MAX_ROOM_NUM);
        while (roomNum > 0 && !spaceDeque.isEmpty()) {
            BPSpace curSpace = spaceDeque.poll();
            if (curSpace.partition(random)) {
                listSpaces.add(curSpace.leftChild);
                listSpaces.add(curSpace.rightChild);
                spaceDeque.offer(curSpace.leftChild);
                spaceDeque.offer(curSpace.rightChild);
            }
            roomNum --;
        }

        root.createRoom(random);
//        for (BPSpace elem: listSpaces) {
//            elem.createKeyPoint(random);
//        }
        root.connectChildPath(random);
        ArrayList<Room> roomArrayList = new ArrayList<>();
        for (BPSpace elem: listSpaces) {
            if (elem.room != null) {
                roomArrayList.add(elem.room);
            }
        }
        // record room and path
        for (BPSpace elem: listSpaces) {
            if (elem.path != null) {
                floorPos.addAll(elem.path);
            }
            if (elem.room != null) {
                floorPos.addAll(elem.room.roomSet());
            }
        }
        // print floor
        for (Position elem: floorPos) {
            tiles[elem.x][elem.y] = Tileset.FLOOR;
        }

        // print wall
        wallPrinter(tiles);
    }

    public boolean ifWall(Position pos, TETile[][] tiles) {
        // no need to check border, since we set buffer in the tiles[][]
        if (tiles[pos.x][pos.y] == Tileset.FLOOR) {
            return false;
        }
        // for internal tile:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i ==0 && j == 0) {
                    continue;
                }
                if (pos.x + i >= 0 && pos.x + i <WIDTH && pos.y + j >= 0 && pos.y + j <HEIGHT && tiles[pos.x + i][pos.y + j] == Tileset.FLOOR) {
                    tiles[pos.x][pos.y] = Tileset.WALL;
                    return true;
                }
            }
        }
        return false;
    }

    public void wallPrinter(TETile[][] tiles) {
        for (int i = 0; i < WIDTH ; i++) {
            for (int j = 0; j < HEIGHT ; j++) {
                Position curPos = new Position(i, j);
                ifWall(curPos, tiles);
            }
        }
    }

    public void Engine(long seed) {
        Random random = new Random(seed);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        // generate tileArr
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        createWord(tiles,random);
        ter.renderFrame(tiles);
    }

    public static void main(String[] args) {
        WorldGenerator worldGenerator = new WorldGenerator();
        worldGenerator.Engine(1289698545);
    }

}
