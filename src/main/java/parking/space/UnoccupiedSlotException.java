package parking.space;

/**
 * @author zakyalvan
 */
public class UnoccupiedSlotException extends RuntimeException {
    private final Slot slot;

    public UnoccupiedSlotException(Slot slot) {
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }
}
