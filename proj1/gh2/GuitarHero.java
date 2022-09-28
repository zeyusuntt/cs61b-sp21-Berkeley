package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int KEYAMOUNT = KEYBOARD.length();

    public static void main(String[] args) {
        /* create 37 guitar strings, for concert A and C */
        GuitarString[] GuitarStrings = new GuitarString[KEYAMOUNT];
        for (int i = 0; i < KEYAMOUNT; i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12);
            GuitarStrings[i] = new GuitarString(frequency);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIndex = KEYBOARD.indexOf(key);
                GuitarStrings[keyIndex].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString elem: GuitarStrings) {
                sample += elem.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString elem: GuitarStrings) {
                elem.tic();
            }
        }
    }
}
