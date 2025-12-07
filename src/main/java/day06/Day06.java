package day06;

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
 * https://www.reddit.com/r/adventofcode/comments/1pfguxk/2025_day_6_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 6172481852142
 *         Part 2: 10188206723429
 * 
 * 
 */
public class Day06 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    List<List<Long>> numberLines = new ArrayList<>();
    List<char[]> numberLinesArray = new ArrayList<>();
    List<String> functionLine = new ArrayList<>();

    /**
     * *****************************************************************************
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day06(List<String> inputLines) {

        // Process the lines for part 1
        for (int lineNum = 0; lineNum < inputLines.size() - 1; lineNum++) {

            numberLines.add(
                    Arrays.stream(cleanSpaces(inputLines.get(lineNum)).split(" "))
                            .map(Long::parseLong)
                            .collect(Collectors.toList()));

            numberLinesArray.add(inputLines.get(lineNum).toCharArray());
        }
        functionLine = Arrays.asList(cleanSpaces(inputLines.get(inputLines.size() - 1)).split(" "));

    }

    /**
     * *****************************************************************************
     * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {
        ArrayList<Long> results = new ArrayList<>();
        for (int col = 0; col < functionLine.size(); col++) {
            // Start off with the first number in the column
            long colResult = numberLines.get(0).get(col);

            for (int row = 1; row < numberLines.size(); row++) {
                long cell = numberLines.get(row).get(col);
                switch (functionLine.get(col)) {
                    case "+":
                        colResult += cell;
                        break;
                    case "*":
                        colResult *= cell;
                        break;
                    default:
                        System.out.printf("Unknown function %s at column %d%n", functionLine.get(col), col);
                }
            }
            results.add(colResult);
        }

        long finalResult = results.stream().reduce(0L, Long::sum);
        return Long.toString(finalResult);
    }

    /**
     * *****************************************************************************
     * Solve part 2 of the puzzle
     * 
     * We consider the input to be a series of "problems".  We store the answer to
     * each "problem" in an ArrayList for processing when we are done.
     * Each column is it processed to build a number.  Then apply that number to the 
     * problem total.
     * 
     * @return
     */
    public String solve2() {

        int workingRows = numberLinesArray.size();
        int columnWidth = numberLinesArray.get(0).length;
        int problemNumber = 0;
        long problemTotal = 0;
        ArrayList<Long> columnResults = new ArrayList<>();
        boolean isFunctionAdd = true;
        boolean newProblem = true;

        for (int col = 0; col < columnWidth; col++) {
            boolean columnBreak = true;

            // If we have a new problem, initialize everything
            if (newProblem) {
                problemTotal = 0;
                isFunctionAdd = functionLine.get(problemNumber).equals("+");
                newProblem = false;
                if (!isFunctionAdd) {
                    problemTotal = 1; // Initialize for multiplication
                }
            }

            // Process this column into a number
            int columnTotal = 0;
            for (int row = 0; row < workingRows; row++) {
                char cell = numberLinesArray.get(row)[col];
                if (cell == ' ') {
                    // Do nothing
                } else {
                    columnBreak = false;
                    columnTotal = columnTotal * 10 + Character.getNumericValue(cell);
                }
            }

            // IF this is a column break, store the problem total and move to next problem
            if (columnBreak) {
                // Move to next problem number
                columnResults.add(problemTotal);
                problemNumber++;
                newProblem = true;
                continue;
            }

            if (DEBUG) {
                System.out.printf("Column %d total is %d, Problem %d total is %d, function %s%n", col, columnTotal,
                        problemNumber, problemTotal, functionLine.get(problemNumber));
            }

            // Still in the problem, so apply this column total to the problem total
            if (isFunctionAdd) {
                problemTotal += columnTotal;
            } else {
                // Must be *
                problemTotal *= columnTotal;
            }
        }
        // Add on the last problem total because there is no column break at the end
        columnResults.add(problemTotal);

        // Calculate and return the result
        long finalResult = columnResults.stream().reduce(0L, Long::sum);
        
        return Long.toString(finalResult);
    }

    /*
     * *****************************************************************************
     * Other methods
     */

    public static String cleanSpaces(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
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
                .readAllLines(Paths.get(Objects.requireNonNull(Day06.class.getResource(FILENAME)).toURI()));

        Day06 dayObject = new Day06(lines);

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
