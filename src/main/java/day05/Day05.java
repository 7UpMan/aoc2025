package day05;

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
 * https://www.reddit.com/r/adventofcode/comments/1pemdwd/2025_day_5_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 733
 *         Part 2: 345821388687084
 * 
 * 
 */
public class Day05 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    List<Range> ranges = new ArrayList<>();
    List<Long> products = new ArrayList<>();

    /**
     * *****************************************************************************
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day05(List<String> inputLines) {
        // Parse input here
        boolean processRanges = true;

        for (String line : inputLines) {
            if (line.isBlank()) {
                processRanges = false;
            } else if (processRanges) {
                String[] parts = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new Range(start, end));
            } else {
                long product = Long.parseLong(line);
                products.add(product);
            }
        }
    }

    /**
     * *****************************************************************************
     * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {
        // Loop through the product IDs
        int freshCount = 0;

        for (long product : products) {
            boolean isFresh = false;
            for (Range range : ranges) {
                if (product >= range.start() && product <= range.end()) {
                    if (DEBUG) {
                        System.out.printf("Product %d is in range %d-%d%n", product, range.start(), range.end());
                    }
                    isFresh = true;
                    break;
                }
            }
            if (isFresh) {
                freshCount++;
            }
        }
        return Integer.toString(freshCount);
    }

    /**
     * *****************************************************************************
     * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {

        
        boolean rangeCountChanged = true;
        List<Range> mergedRanges = ranges;

        while(rangeCountChanged) {
            int beforeCount = mergedRanges.size();
            mergedRanges = mergeRanges(mergedRanges);
            int afterCount = mergedRanges.size();
            rangeCountChanged = beforeCount != afterCount;
        }

        if (DEBUG) {
            System.out.println("Merged ranges:");
            for (Range range : mergedRanges) {
                System.out.printf("  %d-%d%n", range.start(), range.end());
            }
        }

        // Calculate the result
        long rangeTotal = 0;
        for (Range range : mergedRanges) {
            rangeTotal += (range.end() - range.start() + 1);
        }
        

        return Long.toString(rangeTotal);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * Merges overlapping ranges from the input list, reutrning a new list of merged ranges.
     * This process may need to be repeated until no more merges are possible.
     * 
     * @param inputRanges
     * @return list of merged ranges
     */
    public List<Range> mergeRanges(List<Range> inputRanges) {
        List<Range> mergedRanges = new ArrayList<>();

        // Go through the input ranges
        for (Range current : inputRanges) {

            boolean merged = false;

            // No check against existing merged ranges and update or create if not there
            for (int rangeIndex = 0; rangeIndex < mergedRanges.size(); rangeIndex++) {
                Range mergedRange = mergedRanges.get(rangeIndex);

                // Check for overlaps

                // Do we move the start forward
                if (current.start() < mergedRange.start() && current.end() >= mergedRange.start()
                        && current.end() <= mergedRange.end()) {
                    // Move start back
                    mergedRanges.set(rangeIndex, new Range(current.start(), mergedRange.end()));
                    merged = true;
                }
                // Do we move the end back
                else if (current.start() >= mergedRange.start() && current.start() <= mergedRange.end()
                        && current.end() > mergedRange.end()) {
                    // Move end back
                    mergedRanges.set(rangeIndex, new Range(mergedRange.start(), current.end()));
                    merged = true;
                }
                // Does current completely cover merged
                else if (current.start() <= mergedRange.start() && current.end() >= mergedRange.end()) {
                    mergedRanges.set(rangeIndex, new Range(current.start(), current.end()));
                    merged = true;
                }
                // Is current completely inside merged
                else if (current.start() >= mergedRange.start() && current.end() <= mergedRange.end()) {
                    // Do nothing
                    merged = true;
                }
            }

            // If we have not merged then add current as new range
            if (!merged) {
                mergedRanges.add(current);
            }
        }

        return mergedRanges;
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
                .readAllLines(Paths.get(Objects.requireNonNull(Day05.class.getResource(FILENAME)).toURI()));

        Day05 dayObject = new Day05(lines);

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
