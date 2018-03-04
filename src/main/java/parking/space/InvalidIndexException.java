package parking.space;

/**
 * @author zakyalvan
 */
public class InvalidIndexException extends RuntimeException {
    private final Integer capacity;
    private final Integer index;

    public InvalidIndexException(Integer capacity, Integer index) {
        super("Given slot index is null or outside of space capacity");
        this.capacity = capacity;
        this.index = index;
    }
}
