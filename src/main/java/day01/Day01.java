package day01;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * To solve https://adventofcode.com/2025/day/XX
 * Solutions:
 * 
 * 
 * 
 * 
 * @author mat
 * 
 *        Part 1: 275 Too low.
 *        Part 1: 969 Correct!
 *        Part 2: 3165 Too low.
 *        Part 2: 5876 Too low.
 *        Part 2: 5887 Correct!
 * 
 * 
 */
public class Day01 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    // private static final String FILENAME = "Test2.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    List<Rotation> puzzleInput = new ArrayList<>();

    /*
     * *****************************************************************************
     * Constructor
     */

    /**
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day01(List<String> inputLines) {
        // Parse input here
        
        inputLines.forEach(line -> puzzleInput.add(new Rotation(line)));
    }

    /*
     * *****************************************************************************
     * Solve the puzzle part 1
     */

    /**
     * * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {
        // Implement solution for part 1 here

        int dialPosition = 50;
        int hitZero = 0;

        for (Rotation rotation : puzzleInput) {
            if(rotation.direction == 'L') {
                dialPosition -= rotation.steps;
            } else if(rotation.direction == 'R') {
                dialPosition += rotation.steps;
            }
            dialPosition = (dialPosition + 100) % 100;
            if(dialPosition == 0)  {
                hitZero++;
            }
            if (DEBUG) {
                System.out.printf("Rotation: %s, Dial Position: %d, Hit Zero: %d%n", rotation, dialPosition, hitZero);
            }
        }
        // Implement solution for part 1 here
        return Integer.toString(hitZero);
    }

    /*
     * *****************************************************************************
     * Solve the puzzle part 1
     */

    /**
     * * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {
        // Implement solution for part 2 here

        int dialPosition = 50;
        int passedZero = 0;

        for (Rotation rotation : puzzleInput) {
            if(rotation.direction == 'L') {
                // This logic was nasty.  In essence we mirror the dial position so we can add steps rather than subtracting
                // Then put the dial position back to normal after the math.

                dialPosition = (100 - dialPosition)%100; // Mirror the dial position

                // Add the dial stpes rather than subtracting
                dialPosition += rotation.steps;

                // Now perform the addition on passed zero logic
                passedZero += dialPosition / 100;
                dialPosition = dialPosition % 100;

                // Mirror back
                dialPosition = (100 - dialPosition)%100;
                
            } else if(rotation.direction == 'R') {
                dialPosition += rotation.steps;
                // We are addings so easy.  If we are at 50 and add 150 or 151 this will catch both.
                passedZero += dialPosition / 100;
                dialPosition = dialPosition % 100;
            }

            if (DEBUG) {
                System.out.printf("Rotation: %s, Dial Position: %d, Passed Zero: %d%n", 
                    rotation, dialPosition, passedZero);
            }
        }
        
        return Integer.toString(passedZero);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * The problem description references 0x434C49434B, so I wanted to know what that is.
     */
    private void convertHex() {
        long hexValue = 0x434C49434BL;

        ArrayList<Byte> byteList = new ArrayList<>();
        while (hexValue != 0) {

            byte b = (byte) (hexValue & 0xFF);
            byteList.add(b);
            hexValue >>= 8;
        }

        System.out.print("Converted Hex: ");

        for (int i = byteList.size() - 1; i >= 0; i--) {
            System.out.print((char) byteList.get(i).byteValue());
        }
        System.out.println();
    }

    /*
     * *****************************************************************************
     * Main method test harness
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String solve1 = null;
        String solve2 = null;

        // Open and read the input file
        final List<String> lines = Files
                .readAllLines(Paths.get(Objects.requireNonNull(Day01.class.getResource(FILENAME)).toURI()));

        Day01 dayObject = new Day01(lines);

        // Time how long the tests take
        long startTime = System.currentTimeMillis();
        long endTimeSolve1 = startTime;
        long endTimeSolve2 = 0;
        if (PART1) {
            solve1 = dayObject.solve1();
            endTimeSolve1 = System.currentTimeMillis();
            System.out.printf(DISPLAY_TEXT, 1, solve1, endTimeSolve1 - startTime);
        }

        if (PART2) {
            solve2 = dayObject.solve2();
            endTimeSolve2 = System.currentTimeMillis();
            System.out.printf(DISPLAY_TEXT, 2, solve2, endTimeSolve2 - endTimeSolve1);
        }

        dayObject.convertHex();
    }



    /*
     * *****************************************************************************
     * Extra classes and records
     */
    
    private record Rotation(char direction, int steps) {

        public Rotation (String input) {
            this(input.charAt(0), Integer.parseInt(input.substring(1)));
        }
    }
}
