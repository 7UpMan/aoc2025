package common;
import java.util.Objects;


public record CharMatrixOffset(int colDelta, int rowDelta) implements Comparable<CharMatrixOffset> {

    public int getRowDelta() {
        return rowDelta;
    }

    public int getColDelta() {
        return colDelta;
    }

    public boolean isSizeZero() {
        return (rowDelta == 0 && colDelta == 0);
    }

    /**
     * Get a new CharMatrixOffset with the offsets added.
     * @param colAdd
     * @param rowAdd
     * 
     */
    public CharMatrixOffset getAdded(int colAdd, int rowAdd) {
        return new CharMatrixOffset(colDelta + colAdd, rowDelta + rowAdd);
    }

    /**
     * Get a new CharMatrixOffset with the offsets subtracted.
     * @param colAdd
     * @param rowAdd
     * 
     */
    public CharMatrixOffset getSubtracted(int colAdd, int rowAdd) {
        return new CharMatrixOffset(colDelta - colAdd, rowDelta - rowAdd);
    }

    /**
     * Get a new CharMatrixOffset with the offsets inverted.
     * 
     */
    public CharMatrixOffset getInverse() {
        return new CharMatrixOffset(colDelta * -1, rowDelta * -1);
    }

    /**
     * Get the size of the offset, defined as the sum of the absolute values of the row and column deltas.
     * @return int
     */
    public int getSize() {
        return Math.abs(rowDelta) + Math.abs(colDelta);
    }

    @Override
    public String toString() {
        return String.format("[X: %d, Y: %d]", colDelta, rowDelta);
    }

    public CharMatrixOffset clone() { 
        return new CharMatrixOffset(colDelta, rowDelta);
    }

    @Override
    public boolean equals (Object obj) {
         if (this == obj) {
            return true;
        } else if (!(obj instanceof CharMatrixOffset)) {
            return false;
        } else {
            CharMatrixOffset other = (CharMatrixOffset) obj;
            return Objects.equals(colDelta, other.colDelta)
              && Objects.equals(rowDelta, other.rowDelta);
        }
    }

    @Override
    public int compareTo(CharMatrixOffset o) {
        return rowDelta == o.rowDelta ? Integer.compare(colDelta, o.colDelta) : Integer.compare(rowDelta, o.rowDelta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colDelta, rowDelta);
    }
}
