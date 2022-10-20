package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Scanner;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    // addHexagon function, input with length s and the adding position
    // first, initialize the ter, need to know the width and height
    // Second, build TETile[][]
    // Third, add Hexagon to the current TETile[][], need to know the position

    // addHexagon
    // first, generate the number of every x of s length hexagon, getHexNum
    // second, draw a row, fillRow
    // third, draw every column, fulfilled in the main
    // other function: randomTile()
    public static int[] getHexNum(int s) {
        // todo
        // generate the number of every x of s length hexagon
        int[] hexLen = new int[2 * s];
        for (int i = 0; i < s; i++) {
            hexLen[i] = s + 2 * i;
        }
        for (int i = s; i < 2 * s; i++) {
            hexLen[i] = 3 * s - 2 - 2 * (i - s);
        }
        return hexLen;
    }

    public static void fillRow(TETile[][] tiles, int startx, int starty, int l, TETile t) {
        // todo
        // second, draw a row, fillRow
//        TETile randomTile = randomTile();
        for (int x = startx; x < startx + l; x++) {
            tiles[x][starty] = t;
        }
    }

    public static TETile randomTile() {
        // todo
        // Picks a RANDOM tile with a 33% change of being
        // a wall, 33% chance of being a flower, and 33%
        // chance of being empty space.
        Random RANDOM = new Random();
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.WALL;
            case 2: return Tileset.AVATAR;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    public static void addHex(TETile[][] tiles, int startx, int starty, int s) {
        // todo
        // add one hex to tiles, notice we should add 19 hex
        int[] length= getHexNum(s);
        // for column, fillRow
        TETile t = randomTile();
        for (int i = 0; i < s; i++) {
            fillRow(tiles, startx - i, starty - i, length[i], t);
        }
        for (int i = 0; i < s; i++) {
            fillRow(tiles, startx - s + 1 + i, starty -s -i, length[i +s], t);
        }
    }

    public class Coord{
        int x = 0;
        int y = 0;

        public Coord(int sx, int sy) {
            this.x = sx;
            this.y = sy;
        }
        public Coord() {
        }
    }

    public static Coord[] hexCor(int width, int height, int s) {
        // todo
        // generate int[19] which record the coordinate, have turns

        Coord[] cor = new Coord[19];
        for (int i = 7; i < 12; i++) {
            HexWorld test = new HexWorld();
            cor[i] = test. new Coord();
            cor[i].x = width/2 - s/2;
            cor[i].y = height-1 - (i-7)*2*s;
        }
        for (int i = 3; i < 7; i++) {
            HexWorld test = new HexWorld();
            cor[i] = test. new Coord();
            cor[i].x = width/2 - s/2 - (2*s-1);
            cor[i].y = height-1 - s - (i-3)*2*s;
        }
        for (int i = 12; i < 16; i++) {
            HexWorld test = new HexWorld();
            cor[i] = test. new Coord();
            cor[i].x = width/2 - s/2 + (2*s-1);
            cor[i].y = height-1 - s - (i-12)*2*s;
        }
        for (int i = 0; i < 3; i++) {
            HexWorld test = new HexWorld();
            cor[i] = test. new Coord();
            cor[i].x = width/2 - s/2 - 2*(2*s-1);
            cor[i].y = height-1 - 2*s - (i-0)*2*s;
        }
        for (int i = 16; i < 19; i++) {
            HexWorld test = new HexWorld();
            cor[i] = test. new Coord();
            cor[i].x = width/2 - s/2 + 2*(2*s-1);
            cor[i].y = height-1 - 2*s - (i-16)*2*s;
        }
        return cor;
    }
    public static void hex(TETile[][] tiles, int s) {
        int height = tiles[0].length;
        int width = tiles.length;
        Coord[] cor = hexCor(width, height, s);
        for (int i = 0; i < cor.length; i++) {
            addHex(tiles, cor[i].x, cor[i].y, s);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter any number: ");

        // This method reads the number provided using keyboard
        int s = scan.nextInt();

        // Closing Scanner after the use
        scan.close();
        int width = 11*s -6 +s;
        int height = 10 * s + s;
        ter.initialize(width, height);
        TETile[][] tiles = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        hex(tiles, s);

        ter.renderFrame(tiles);

    }
}
