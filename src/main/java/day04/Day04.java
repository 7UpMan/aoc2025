package day04;

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
 * https://www.reddit.com/r/adventofcode/comments/1pdr8x6/2025_day_4_solutions/
 * 
 * 
 * 
 * @author mat
 * 
 *         Part 1: 1449
 *         Part 2: 8746
 * 
 * 
 */
public class Day04 {
    // Variables to run harness
    private static final boolean DEBUG = false;
    private static final boolean PART1 = true;
    private static final boolean PART2 = true;
    private static final String FILENAME = "Input.txt";
    // private static final String FILENAME = "Test1.txt";
    private static final String DISPLAY_TEXT = "The solution to part %d is %s and took %d miliseconds%n";

    // Variables for this problem
    List<List<Character>> paperRolls = new ArrayList<>();
    int rows;
    int cols;

    /**
     * *****************************************************************************
     * Constructor. Read the input file and parse it into useful data
     * 
     * @param inputLines
     */
    public Day04(List<String> inputLines) {
        // Parse input here

        // Rather than use the imutable array from the Stream, convert to an ArrayListthat we can change.
        inputLines.forEach(line -> paperRolls.add(
                new ArrayList<>(
                line.chars().mapToObj(c -> (char) c).toList()))
        );
        rows = paperRolls.size();
        cols = paperRolls.get(0).size();
    }

    /**
     * *****************************************************************************
     * Solve part 1 of the puzzle
     * 
     * @return solution to part 1
     */
    public String solve1() {

        List<Point> movablePoints = getMovablePoints();

        if (DEBUG) {
            printPaperRolls();
        }

        return Integer.toString(movablePoints.size());
    }
    
    /**
     * *****************************************************************************
     * Solve part 2 of the puzzle
     * 
     * @return
     */
    public String solve2() {
        int rollsRemoved = 0;

        List<Point> movablePoints = getMovablePoints();

        // Keep going until no more movable points
        while (!movablePoints.isEmpty()) {
            movablePoints = getMovablePoints();
            rollsRemoved += movablePoints.size();
            movablePoints.forEach(p -> removePoint(p.row(), p.col()));
        }

        if (DEBUG) {
            printPaperRolls();
        }

        return Integer.toString(rollsRemoved);
    }

    /*
     * *****************************************************************************
     * Other methods
     */


    private void printPaperRolls() {
        paperRolls.forEach(line -> {
            line.forEach(System.out::print);
            System.out.println();
        });
    }

    /**
     * Remove point at row r and column c by setting it to '.'
     * @param r
     * @param c
     */
    private void removePoint(int r, int c) {
        paperRolls.get(r).set(c, '.');
    }

    /**
     * Get a list of all movable points, i.e. those with less than 4 touching rolls.
     * @return
     */
    private List<Point> getMovablePoints() {
        List<Point> movablePoints = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char theChar = paperRolls.get(r).get(c);
                if (theChar == '@' || theChar == 'x') {
                    if (touchingRolls(r, c) < 4) {
                        if (DEBUG) {
                            paperRolls.get(r).set(c, 'x');
                        }
                        // This point can be moved                        
                        movablePoints.add(new Point(r, c));
                    }
                }
            }
        }
        
        return movablePoints;
    }

    
    /**
     * Count number of touching rolls at row r and column c.  Outside the array counts as empty.
     * @param r
     * @param c
     * @return
     */
    private int touchingRolls(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                char theChar = getCharAt(r + dr, c + dc);
                if (theChar == '@' || theChar == 'x') {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Get character at row r and column c, or '.' if out of bounds.
     * 
     * @param r
     * @param c
     * @return
     */
    private char getCharAt(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return '.';
        }
        return paperRolls.get(r).get(c);
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
                .readAllLines(Paths.get(Objects.requireNonNull(Day04.class.getResource(FILENAME)).toURI()));

        Day04 dayObject = new Day04(lines);

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

    private record Point(int row, int col) {
    }
}
