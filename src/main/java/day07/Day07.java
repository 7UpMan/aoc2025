package day07;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * To solve https://adventofcode.com/2025/day/XX
 * Solutions:
 * https://www.reddit.com/r/adventofcode/comments/1pg9w66/2025_day_7_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 1516
 *         Part 2: 1393669447690
 * 
 * 
 */
public class Day07 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    private ArrayList<String> inputData = new ArrayList<>();
    private long[] beamLocations;
    private int splitCount = 0;

    /**
     * *****************************************************************************
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day07(List<String> inputLines) {
        // Parse input here
        inputLines.forEach(line -> inputData.add(line));
    }

    /**
     * *****************************************************************************
     * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {

        // Process the input data
        parseInputLines();

        return Integer.toString(splitCount);
    }

    /**
     * *****************************************************************************
     * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {

        return Long.toString(Arrays.stream(beamLocations).sum());

    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * Parse the input lines into useful data structures.
     * We have an array of beamLocations, each element is the number of beams at
     * that location.
     * 
     * We process each row, looking for splitters (^). If we find one and there is a
     * beam there, we split it to the left and right, and set the current location
     * to 0.
     * 
     * If 2 beams came in, we add 2 to the left and 2 to the right.
     * 
     * Each time we split a beam, we increment the splitCount.
     * 
     * The total number of beams at the end is the sum of the beamLocations array.
     */
    private void parseInputLines() {
        int rowWidth = inputData.get(0).length();
        beamLocations = new long[rowWidth];
        beamLocations[inputData.get(0).indexOf('S')] = 1;

        // Process every row after the first 1
        for (int rowNumber = 1; rowNumber < inputData.size(); rowNumber++) {
            String row = inputData.get(rowNumber);

            // Read each column and process
            for (int colNumber = 0; colNumber < rowWidth; colNumber++) {

                // Is there a splitter here?
                if (row.charAt(colNumber) == '^') {
                    // Founcd a splitter, do we have a beam to split?
                    if (beamLocations[colNumber] > 0) {
                        splitCount++;
                        // Split the beam
                        beamLocations[colNumber - 1] += beamLocations[colNumber];
                        beamLocations[colNumber + 1] += beamLocations[colNumber];
                        beamLocations[colNumber] = 0;
                    }
                }
            }
        }
    }

    /**
     * *****************************************************************************
     * Main method with test harness
     * 
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String solve1 = null;
        String solve2 = null;

        // Open and read the input file
        final List<String> lines = Files
                .readAllLines(Paths.get(Objects.requireNonNull(Day07.class.getResource(FILENAME)).toURI()));

        Day07 dayObject = new Day07(lines);

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
