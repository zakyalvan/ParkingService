package parking.space;

/**
 * @author zakyalvan
 */
public class SlotIndexException extends RuntimeException {
    private final Integer capacity;
    private final Integer index;

    public SlotIndexException(Integer capacity, Integer index) {
        super("Given slot index is null or outside of space capacity");
        this.capacity = capacity;
        this.index = index;
    }
}
