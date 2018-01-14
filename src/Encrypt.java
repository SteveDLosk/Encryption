import java.util.ArrayList;
import java.util.Random;

/**
 * Encrypts a file by performing a series of data manipulations based on the provided password
 * With a correct password, these manipulations can be undone.
 * Created by steve on 1/13/2018.
 */
public class Encrypt {

    public static void main (String[] args) {
        /**
         *  Runs on the command line.  Takes a file parameter, and a password
         */
        // testing
        args = new String[2];


        if (args.length != 2) {
            System.out.println("Usage: Encrypt <file name> <password>");
            System.out.println();
            System.exit(0);
        }

        // testing
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (byte i = 0; i < Byte.MAX_VALUE; i++) {
            bytes.add(i);
        }

        printBytes(bytes);
        bytes = wrapBytes(bytes, 2, 1, 39);
        System.out.println();
        printBytes(bytes);
        System.out.println();
        bytes = unwrapBytes(bytes, 2, 1, 39);
        printBytes(bytes);
    }
    private static ArrayList<Byte> wrapBytes (ArrayList<Byte> bytes, int step, int start) {
        /**
         *  Default implementation of wrapBytes, uses 128 as the incrementValue
         */
        return wrapBytes(bytes, step, start, 128);
    }
    private static ArrayList<Byte> wrapBytes (ArrayList<Byte> bytes, int step, int start, int incrementValue) {
        /**
         *  Takes an ArrayList of bytes, and starting at start, "wraps around" the byte at every increment of step
         *  to obfuscate the real data.  Adds incrementValue, wrapping around positive bytes to negative values
         */


        // instead of invalid argument, a negative start is treated as 0.
        if (start < 0) {
            start = 0;
        }

        // iterate through the input bytes, adding garbage every step
        // i = position in original ArrayList, j = position in step sequence
        for (int i = 0, j = step - start; i < bytes.size(); i++, j++) {
            if (j == step) {
                // wrap, then reset
                byte b = bytes.get(i);
                b += incrementValue;
                bytes.set(i, b);
                j = 0;
            }
        }

        return bytes;
    }
    private static ArrayList<Byte> unwrapBytes (ArrayList<Byte> bytes, int step, int start) {
        /**
         *  Default implementation of unwrapBytes, with a default increment value of 128.
         */

        return unwrapBytes(bytes, step, start, 128);
    }

    private static ArrayList<Byte> unwrapBytes (ArrayList<Byte> bytes, int step, int start, int incrementValue) {
        /**
         *  Takes an ArrayList of bytes, and starting at start, unwraps the "wraps around" at every increment of step
         *  to recover the real data.
         */

        // instead of invalid argument, a negative start is treated as 0.
        if (start < 0) {
            start = 0;
        }

        // iterate through the input bytes, adding garbage every step
        // i = position in original ArrayList, j = position in step sequence
        for (int i = 0, j = step - start; i < bytes.size(); i++, j++) {
            if (j == step) {
                // wrap, then reset
                byte b = bytes.get(i);
                b -= incrementValue;
                bytes.set(i, b);
                j = 0;
            }
        }

        return bytes;
    }

    private static ArrayList<Byte> fillWithGarbage (ArrayList<Byte> bytes, int step, int start) {
        /**
         *  Takes an arraylist of bytes, and starting at start, adds a randomly generated
         *  garbage byte every increment of step, to obfuscate the real data.
         */

        Random r = new Random();
        ArrayList<Byte> output = new ArrayList<>();

        // instead of invalid argument, a negative start is treated as 0.
        if (start < 0) {
            start = 0;
        }

        // iterate through the input bytes, adding garbage every step
        // i = position in original ArrayList, j = position in step sequence
        for (int i = 0, j = step - start; i < bytes.size(); i++, j++) {
            if (j == step) {
                // fill with garbage, then reset
                int garbage = r.nextInt(256) -128;
                byte b = (byte) garbage;
                output.add(b);
                j = 0;
            }
            output.add(bytes.get(i));
        }

        return output;
    }

    private static ArrayList<Byte> removeGarbage (ArrayList<Byte> bytes, int step, int start) {
        /**
         *  Takes an arraylist of bytes, and starting at start, removes a randomly generated
         *  garbage byte every increment of step.
         */

        ArrayList<Byte> output = new ArrayList<>();

        // instead of invalid argument, a negative start is treated as 0.
        if (start < 0) {
            start = 0;
        }

        // iterate through the input bytes, removing garbage every step
        // i = position in original ArrayList, j = position in step sequence
        for (int i = 0, j = step - start; i < bytes.size(); i++, j++) {
            if (j == step) {
                // skip garbage, then reset
                i++;
                j = 0;
            }
            output.add(bytes.get(i));
        }

        return output;
    }

    private static void printBytes (ArrayList<Byte> bytes) {
        for (byte b : bytes) {
            System.out.print(b + " ");

        }
    }
}
