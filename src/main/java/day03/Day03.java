package day03;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * To solve https://adventofcode.com/2025/day/XX
 * Solutions:
 * https://www.reddit.com/r/adventofcode/comments/1pcvaj4/2025_day_3_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 17159
 *         Part 2: 170449335646486
 * 
 * 
 */
public class Day03 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    List<int[]> batteryList = new ArrayList<>();

    /**
     * *****************************************************************************
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day03(List<String> inputLines) {
        // Parse input here
        inputLines.forEach(line -> batteryList.add(
                line.chars()
                        .map(c -> c - '0')
                        .toArray()));
    }

    /**
     * *****************************************************************************
     * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {
        long total = 0;

        for (int[] battery : batteryList) {
            int value1 = 0;
            int position1 = 0;
            int value2 = 0;

            // Get 1st digit, stop 1 before the end
            for (int cell = 0; cell < battery.length - 1; cell++) {
                if (battery[cell] > value1) {
                    value1 = battery[cell];
                    position1 = cell;
                }
            }

            // Get the 2nd digit, start 1 after the 1st digit
            for (int cell = position1 + 1; cell < battery.length; cell++) {
                if (battery[cell] > value2) {
                    value2 = battery[cell];
                }
            }

            int result = value1 * 10 + value2;
            total += result;

            if (DEBUG) {
                System.out.printf("For battery: %s, max values are %d%n", intArrayToString(battery), result);
            }
        }

        return Long.toString(total);
    }

    /**
     * *****************************************************************************
     * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {
        long total = 0;

        // Loop through each battery
        for (int[] battery : batteryList) {
            // Track values and positons of our findings
            int[] values = new int[12];
            int[] positions = new int[12];

            for (int position = 0; position < 12; position++) {
                // We start at the beginning or just after the last found position
                int startPosition = (position == 0) ? 0 : positions[position - 1] + 1;
                // We need to end early enough to fit the remaining digits
                int endPosition = battery.length - (12 - position - 1); // Max value of position is 11

                for (int cell = startPosition; cell < endPosition; cell++) {
                    if (battery[cell] > values[position]) {
                        values[position] = battery[cell];
                        positions[position] = cell;
                    }
                }
            }

            // Process the int array into something useful
            String resultString = intArrayToString(values);
            long resultNumber = Long.parseLong(resultString);

            // Add to the total
            total += resultNumber;

            if (DEBUG) {
                System.out.printf("For battery: %s, max values are %s%n", intArrayToString(battery), resultString);
            }
        }

        return Long.toString(total);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * Convert an int array (the battery list) to a string without separators.
     * 
     * @param array
     * @return string representation of the array
     */
    private String intArrayToString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
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
                .readAllLines(Paths.get(Objects.requireNonNull(Day03.class.getResource(FILENAME)).toURI()));

        Day03 dayObject = new Day03(lines);

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
