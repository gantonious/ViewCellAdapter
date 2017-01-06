package ca.antonious.sample;

import java.util.Random;

/**
 * Created by George on 2017-01-05.
 */

public class Utils {
    public static String getRandomLetter() {
        Random r = new Random();
        return String.valueOf((char)(r.nextInt(26) + 'A'));
    }
}
