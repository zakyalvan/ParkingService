package parking.space;

/**
 * @author zakyalvan
 */
public class SlotOccupyException extends RuntimeException {
    private final Slot slot;

    public SlotOccupyException(Slot slot) {
        super("Can not occupy slot, not occupy-able slot");
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }
}
