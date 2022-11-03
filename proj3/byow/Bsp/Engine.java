package byow.Bsp;

import byow.Bsp.*;
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
//        Room[] roomArr = roomGenerator(random);
//        roomPrinter(roomArr,finalWorldFrame);
//        HashSet<Path> pathSet = roomPathGenerator(roomArr, random);
//        pathPrinter(pathSet, finalWorldFrame);
//        wallPrinter(finalWorldFrame);
        // add things to tileArr

        // display tileArr
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }


    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.interactWithInputString("sf");
    }
}
