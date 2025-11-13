package common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharMatrix {
    private int maxRows = 0;
    private int maxCols = 0;
    private char[][] matrix = null;

    public CharMatrix(int cols, int rows) {
        maxRows = rows;
        maxCols = cols;
        matrix = new char[maxCols][maxRows];
    }

    /**
     * Get the character at the specified location.
     * 
     * @param col
     * @param row
     * @return char
     * 
     */
    public char get(int cols, int rows) {
        return matrix[rows][cols];
    }

    
    /** 
     * Returns the char at the specified location.
     * @param location
     * @return char
     */
    public char get(CharMatrixLocation location) {
        return get(location.col(), location.row());
    }

    /**
     * Get the character at the specified location, but if the location is outside of the matrix
     * then return the provided errorChar.
     * @param location
     * @param errorChar
     * @return
     */
    public char getBoundsCheck(CharMatrixLocation location, char errorChar) {
        int row = location.getRow();
        int col = location.getCol();

        if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
            // Do nothing as outside bounds
            return errorChar;
        } else {
            // All ok so make assignment
            return get(row, col);
        }
    }

    /**
     * Get the character at the specified location, but if the location is outside of the matrix
     * then return the provided errorChar.
     * @param col
     * @param row
     * @param errorChar
     * @return
     */
    public char getBoundsCheck(int col, int row, char errorChar) {
        if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
            // Do nothing as outside bounds
            return errorChar;
        } else {
            // All ok so make assignment
            return get(col, row);
        }
    }

    /**
     * Set the character at the specified location to the given
     * character.
     * 
     * @param col
     * @param row
     * @param theChar
     */
    public void set(int col, int row, char theChar) {
        matrix[col][row] = theChar;
    }

    
    /** 
     * Set the character at the specified location to the given
     * character.
     * 
     * @param location
     * @param theChar
     */
    public void set(CharMatrixLocation location, char theChar) {
        set(location.getCol(), location.getRow(), theChar);
    }

    
    /** 
     * Set the specified location to have the specified char. If the location is outside of the matrix
     * then ignore the request, but don't throw an error.
     * @param col Target col
     * @param row Target row
     * @param theChar Char to store
     * @return boolean true if the data was stored (i.e. within bounds), false if outside of bounds.
     */
    public boolean setBoundsCheck(int col, int row, char theChar) {
        if (row < 0 || row >= maxRows || col < 0 || col >= maxCols) {
            // Do nothing as outside bounds
            return false;
        } else {
            // All ok so make assignment
            set(col, row, theChar);
            return true;
        }
    }

    
    /** 
     * Set the specified location to have the specified char. If the location is outside of the matrix
     * then ignore the request, but don't throw an error.
     * @param location Target location
     * @param theChar Char to store
     * @return boolean true if the data was stored (i.e. within bounds), false if outside of bounds.
     */
    public boolean setBoundsCheck(CharMatrixLocation location, char theChar) {
        return setBoundsCheck(location.getCol(), location.getRow(), theChar);
    }

    /**
     * Check to see if the provided location is valid within the matrix.
     * @param location
     * @return
     */
    public boolean isLocationValid(CharMatrixLocation location) {
        return !(location.getRow() < 0 || location.getRow() >= maxRows || 
        location.getCol() < 0 || location.getCol() >= maxCols);
    }


    /**
     * Set the specified row to the provided char array.
     * @param row
     * @param newRow
     */
    public void setRow(int row, char[] newRow) {
        for(int col=0; col<Math.min(newRow.length, maxCols); col++) {
            matrix[col][row] = newRow[col];
        }

    }

    /**
     * Parse the provided String into the specified row of the matrix.
     * @param row
     * @param string
     */
    public void parseRow(int row, String s) {
        setRow(row, s.toCharArray());
    }
    
    
    /**
     * Get the maximum number of rows. 
     * @return int
     */
    public int getRows() {
        return maxRows;
    }

    
    /** 
     * Get the maximum number of columns.
     * @return int
     */
    public int getCols() {
        return maxCols;
    }

    public String getRow(int row) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < maxCols ; i++) {
            sb.append(matrix[i][row]);
        }

        return sb.toString();

    }

    /**
     * Returns a Set of all the different characters within th ematrix. You can
     * provide a Set of characters to ignore within the data.
     * 
     * @param skipList A Set that contains a list of Characters to skip (probably
     *                 the '.' character)
     * @return Set<Character> The list of Characters to be skipped.
     */
    public Set<Character> getUniqueChars(Set<Character> skipList) {
        HashSet<Character> charactersUsed = new HashSet<>();
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                char theChar = matrix[col][row];

                if (skipList.contains(theChar)) {
                    // Do nothing
                } else {
                    charactersUsed.add(theChar);
                }
            }
        }
        return charactersUsed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                sb.append(matrix[col][row]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Fill the matrix with the provided char.
     * 
     * @param theChar
     * 
     */
    public void fill(char theChar) {
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                matrix[col][row] = theChar;
            }
        }
    }

    public int countChar(char theChar) {
        int count=0;
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if(matrix[col][row] == theChar) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Provide a List of all the CharMatrixLocations that hold the provided character.
     * 
     * @param theChar
     * @return List<CharMatrixLocation>
     */
    public List<CharMatrixLocation> getAllLocations(char theChar) {
        ArrayList<CharMatrixLocation> locations = new ArrayList<>();

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if (matrix[col][row] == theChar) {
                    locations.add(new CharMatrixLocation(col, row));
                }
            }
        }
        return locations;
    }

    
    /** 
     * Provide the unique CMLocation that holds the provided character.
     * There must be exactly one such location otherwise the program
     * will exit with an error.
     * 
     * @param theChar
     * @return CharMatrixLocation
     */
    public CharMatrixLocation getUniqCharLocation(char theChar) {
        ArrayList<CharMatrixLocation> locations = new ArrayList<>();

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if (matrix[col][row] == theChar) {
                    locations.add(new CharMatrixLocation(col, row));
                }
            }
        }
        
        if (locations.size() != 1) {
            System.err.printf("Only expecting 1 occurance of %c, got %d.  Stopping!!%n", theChar, locations.size());
            System.exit(1);
        }
        return locations.get(0);
    }

    @Override
    public CharMatrix clone() {
        CharMatrix newMatrix = new CharMatrix(maxCols, maxRows);
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                newMatrix.set(col, row, this.get(col, row));
            }
        }
        return newMatrix;
    }

}
