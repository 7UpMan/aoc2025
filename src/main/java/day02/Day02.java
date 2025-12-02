package day02;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * To solve https://adventofcode.com/2025/day/2
 * Solutions:
 * https://www.reddit.com/r/adventofcode/comments/1pbzqcx/2025_day_2_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 34826702005
 *         Part 2: 43287141963
 * 
 * 
 */
public class Day02 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    // private static final String FILENAME = "Test2.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    private List<Range> ranges = new ArrayList<>();
    private long resultsPart1 = 0;
    private long resultsPart2 = 0;
    private long largestIdNumber = 0;

    /**
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day02(List<String> inputLines) {
        // Parse input here
        inputLines.forEach(line -> Arrays.asList(line.split(",")).forEach(part -> {
            String[] nums = part.split("-");
            ranges.add(new Range(Long.parseLong(nums[0]), Long.parseLong(nums[1])));
        }));

        // Work out the largest id
        largestIdNumber = ranges.stream().mapToLong(r -> r.end).max().orElse(0);

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
        // Loop through all the ranges and solve part 1
        for (Range range : ranges) {
            // Process each range
            for (long i = range.start; i <= range.end; i++) {
                // Process each number in the range
                String numberStr = Long.toString(i);
                int numberStrLen = numberStr.length();
                String numberStrPart1 = numberStr.substring(0, numberStrLen / 2);
                String numberStrPart2 = numberStr.substring(numberStrLen / 2);
                if (numberStrPart1.equals(numberStrPart2)) {
                    resultsPart1 += i;
                }
            }
        }

        return Long.toString(resultsPart1);
    }

    /*
     * *****************************************************************************
     * Solve the puzzle part 2
     */

    /**
     * * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {
        // Loop through all the ranges and solve part 2
        for (Range range : ranges) {
            
            // Process each range
            for (long i = range.start; i <= range.end; i++) {
                // Change for part 2a or 2b
                if (!isIdValidPart2a(i)) {
                    resultsPart2 += i;
                }
            }
        }

        return Long.toString(resultsPart2);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * Work out if the ID is valid for part 2 using String objects and comparison.
     */
    private boolean isIdValidPart2a(long id) {
        // Process each number in the range
        String numberStr = Long.toString(id);
        int numberStrLen = numberStr.length();

        // Loop from 1 char long to half the length long
        for (int splitLen = 1; splitLen <= numberStrLen / 2; splitLen++) {
            // If numberStrLen is not divisable by splitLen, skip
            if (numberStrLen % splitLen != 0) {
                continue;
            }

            // What string are we looking for
            String stringToLookFor = numberStr.substring(0, splitLen);

            // Check if the entire string is made up of stringToLookFor
            boolean containsRepeatedString = true;
            for (int pos = 1; pos < numberStrLen / splitLen; pos++) {
                String nextPart = numberStr.substring(pos * splitLen, (pos + 1) * splitLen);
                if (!nextPart.equals(stringToLookFor)) {
                    containsRepeatedString = false;
                    break;
                }
            }

            // Check to see if we completed the loop with all parts matching
            if (containsRepeatedString) {
                return false;
            }
        }

        return true;
    }

    /**
     * Work out if the ID is valid for part 2 using mathematical operations.
     */
    private boolean isIdValidPart2b(long id) {
        int lengthOfId = (int) Math.log10(id)+1;

        for(int power = 1; power <= lengthOfId / 2; power++) {
            long mod = (long) Math.pow(10, power);
            long firstPart = id % mod;

            boolean keepChecking = true;
            for(int pos = 1; pos < lengthOfId/power && keepChecking; pos ++) {
                long nextPart = (id / (long) Math.pow(10, pos * power)) % mod;
                if (nextPart != firstPart) {
                    keepChecking = false;
                }
            }
            // We found an invalid ID
            if (keepChecking) {
                return false;
            }

        }
        
        return true;
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
                .readAllLines(Paths.get(Objects.requireNonNull(Day02.class.getResource(FILENAME)).toURI()));

        Day02 dayObject = new Day02(lines);

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

    private record Range(long start, long end) {
    }

}
