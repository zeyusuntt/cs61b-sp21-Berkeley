package byow.Bsp;


import edu.neu.ccs.gui.Halo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BPSpace {
    public final int MIN_SPACE_SIZE = 5;
    public final int MIN_ROOM_SIZE = 3;
    public final int MIN_CEIL_FROM_SIDE = 1;
    public int width;
    public int height;
    public Position pos; // left lower point
    public BPSpace leftChild;
    public BPSpace rightChild;
    public Room room;
    public ArrayList<Position> path;
    public Position keyPoint;

    public BPSpace() {
        pos = null;
        leftChild = null;
        rightChild = null;
        room = null;
        path = null;
        keyPoint = null;
    }

    public BPSpace(Position p, int w, int h) {
        width = w;
        height = h;
        pos = p;
        leftChild = null;
        rightChild = null;
        room = null;
        path = null;
        keyPoint = null;
    }

    // create method
    // first partition, to create subspace
    // when to partition? when it's the leaf node, with null leftchild and rightchild
    // how to partition?
    // check the partition direction and size

    public boolean partition(Random random) {
        if (leftChild != null) {
            return false;
        }
        if (width <= 2 * MIN_SPACE_SIZE || height <= 2 * MIN_SPACE_SIZE) {
            return false;
        }
        int ver = -1;
        if (width >= height) {
            ver = 1;
        }
        else {
            ver = 0;
        }
        int maxSize;
        if (ver == 1) {
            maxSize = width - MIN_SPACE_SIZE;
        }
        else {
            maxSize = height - MIN_SPACE_SIZE;
        }

        int parSize = RandomUtils.uniform(random, MIN_SPACE_SIZE, maxSize);
        if (ver == 1) {
            leftChild = new BPSpace(pos, parSize, height);
            rightChild = new BPSpace(new Position(pos.x + parSize, pos.y), width - parSize, height);
        }
        else {
            leftChild = new BPSpace(new Position(pos.x, pos.y + parSize), width, height - parSize);
            rightChild = new BPSpace(pos, width, parSize);
        }
        return true;
    }

    public void createRoom(Random random) {
        // recursive: if it's leaf node, create room, else recursive
        if (leftChild != null && rightChild != null) {
            leftChild.createRoom(random);
            rightChild.createRoom(random);
        }
        else {
            // create room, randomly generate the position, width and height
            // first get the pos, and then generate the width and the height
            int posX = pos.x + RandomUtils.uniform(random, MIN_CEIL_FROM_SIDE, width - MIN_ROOM_SIZE);
            int posY = pos.y + RandomUtils.uniform(random, MIN_CEIL_FROM_SIDE, height -MIN_ROOM_SIZE);
            int roomWidth = RandomUtils.uniform(random, MIN_ROOM_SIZE, width - (posX-pos.x));
            int roomHeight = RandomUtils.uniform(random, MIN_ROOM_SIZE, height - (posY-pos.y));
            room = new Room(new Position(posX,posY), roomWidth, roomHeight);
        }
    }

    // room done, do with corridor
    // create two method, first is to create keyPoint, and then to create corridors between two children
    public Position createKeyPoint(Random random) {
        // if it's leaf node, create key point from the room
        // if it's not the leaf node, create key point from the path
        if (path != null){
            int i = RandomUtils.uniform(random, 0, path.size());
            this.keyPoint = path.get(i);
        }
        else if (room != null) {
            int x = RandomUtils.uniform(random, room.pos.x, room.pos.x + room.width);
            int y = RandomUtils.uniform(random, room.pos.y, room.pos.y + room.height);
            this.keyPoint = new Position(x,y);
        }
//        else {
//            int x = RandomUtils.uniform(random, pos.x, pos.x + width);
//            int y = RandomUtils.uniform(random, pos.y, pos.y + height);
//            this.keyPoint = new Position(x, y);
//        }
        return keyPoint;
    }

    public void connectChildPath(Random random) {
        // two situations: base case if leaf node and non leaf node
        if (room != null) {
            return;
        }
        // todo: when to create leaf node's keypoint???
        // when to create leaf node's keypoint???

        // if roomNum is odd, then one room might not be connected
        // how to solve: if child != null && child.room == null && child.child == null, createPoint anywhere
        if (leftChild != null && leftChild.room == null) {
            leftChild.connectChildPath(random);
        }
        if (rightChild != null && rightChild.room == null) {
            rightChild.connectChildPath(random);
        }
        if (leftChild != null && leftChild.room != null) {
            leftChild.createKeyPoint(random);
        }
        if (rightChild != null && rightChild.room != null) {
            rightChild.createKeyPoint(random);
        }
        HashSet<Position> p = Path.connectPoint(leftChild.keyPoint, rightChild.keyPoint, random);
        path = new ArrayList<>(p);
        createKeyPoint(random);
    }
}
