package dayXX;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 *         Part 1: X
 *         Part 2: X
 * 
 * 
 */
public class DayXX {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem

    


    /**
     * Constructor.  Read the input file and parse it into useful data
     * @param inputLines
     */
    public DayXX(List<String> inputLines) {
        // Parse input here
        inputLines.forEach(line -> 
                System.out.println("Input Line: " + line)
        );
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
        return"solve1";
    }


    /*
     * *****************************************************************************
     * Solve the puzzle part 2
     */

    /**
     * * Solve part 2 of the puzzle
     * @return
     */
     public String solve2() {
        // Implement solution for part 2 here
        return "solve2";
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /*
     * *****************************************************************************
     * Main method test harness
     */
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        String solve1 = null;
        String solve2 = null;

        // Open and read the input file
        final List<String> lines = Files
                .readAllLines(Paths.get(Objects.requireNonNull(DayXX.class.getResource(FILENAME)).toURI()));

        DayXX dayObject = new DayXX(lines);

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
    }

       /*
     * *****************************************************************************
     * Extra classes and records
     */
}
