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
                if (range.containsProduct(product)) {
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

        // Sort the array based on start number
        ranges.sort(null);

        List<Range> mergedRanges = mergeRanges(ranges);

        if (DEBUG) {
            System.out.printf("Merged from %d to %d ranges%n", ranges.size(), mergedRanges.size());
            System.out.println("Merged ranges:");
            mergedRanges.forEach(range -> System.out.printf("  %d-%d%n", range.start(), range.end()));
        }

        // Calculate the result and return
        long rangeTotal = mergedRanges.stream().mapToLong(Range::getRangeSize).sum();

        return Long.toString(rangeTotal);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    /**
     * Merges a list of ranges into the minimum number of ranges. If two ranges
     * touch each other or overlap, they are merged into one. The data is sorted by
     * start value before merging. So we never need to move the start, only extend
     * the end.
     * 
     * @param inputRanges
     * @return
     */
    private List<Range> mergeRanges(List<Range> inputRanges) {
        List<Range> mergedRanges = new ArrayList<>();

        // Prime the loop otherwise we the the range 0-0.
        Range newRange = inputRanges.get(0);

        // Go through the input ranges
        for (Range current : inputRanges) {

            // Can we extent the newRange or should we write it and create a new one?
            if (current.start() <= newRange.end() + 1) {
                // Extend the new range
                newRange = new Range(newRange.start(), Math.max(newRange.end(), current.end()));
            } else {
                // Save the old range and start a new one
                mergedRanges.add(newRange);
                newRange = current;
            }
        }
        mergedRanges.add(newRange);
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

    private record Range(long start, long end) implements Comparable<Range> {
        @Override
        public int compareTo(Range other) {
            if (this.start == other.start) {
                return Long.compare(this.end, other.end);
            }
            return Long.compare(this.start, other.start);
        }

        /**
         * Get the size of the range (inclusive)
         * 
         * @return
         */
        public long getRangeSize() {
            return end - start + 1;
        }

        public boolean containsProduct(long product) {
            return product >= start && product <= end;
        }
    }
}
