package common;

import java.util.Objects;

public record CharMatrixLocation(int col, int row) implements Comparable<CharMatrixLocation> {

    public CharMatrixLocation(CharMatrixLocation location) {
        this(location.col, location.row);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    
    @Override
    public String toString() {
        return String.format("[X: %d, Y: %d]", col, row);
    }

    public boolean isEequal(CharMatrixLocation otherLocation) {
        return row == otherLocation.getRow() && col == otherLocation.getCol();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CharMatrixLocation)) {
            return false;
        } else {
            CharMatrixLocation other = (CharMatrixLocation) obj;
            return Objects.equals(row, other.row) && Objects.equals(col, other.col);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    /** 
     * Returns a new CharMatrixLocation object that is the current Location with the CharMatrixOffset added.
     * @param offset
     * @return CharMatrixLocation
     */
    public CharMatrixLocation addOffset(CharMatrixOffset offset) {
        return new CharMatrixLocation(col + offset.getColDelta(), row + offset.getRowDelta());
    }

    /** 
     * Returns a new CharMatrixLocation object that is the current Location with the CharMatrixOffset subtracted.
     * @param offset
     * @return CharMatrixLocation
     */
    public CharMatrixLocation subtractOffset(CharMatrixOffset offset) {
        return new CharMatrixLocation(col - offset.getColDelta(), row - offset.getRowDelta());
    }

    @Override
    public int compareTo(CharMatrixLocation o) {
        return row == o.row ? Integer.compare(col, o.col) : Integer.compare(row, o.row);
    }

    /** 
     * Returns a new CharMatrixLocation object that is the current Location with the provided deltas added.
     * @param rowDelta
     * @param colDelta
     * @return CharMatrixLocation
     */
    public CharMatrixLocation addOffset(int rowDelta, int colDelta) {
        return new CharMatrixLocation(row + rowDelta, col + colDelta);
    }
    
    
    /** Provides a CharMatrixOffset object which has the details from the current location to the provided location.
     * @param otherLocation
     * @return CharMatrixOffset
     */
    public CharMatrixOffset deltaToLocation(CharMatrixLocation otherLocation) {
        return new CharMatrixOffset(
            otherLocation.row() - row(),    
            otherLocation.col() - col()
        );
    }

    @Override
    public CharMatrixLocation clone() {
        return new CharMatrixLocation(row, col);
    }
}
