package parking.space;

/**
 * @author zakyalvan
 */
public interface ExitGate {
    String gateName();
    Space fromSpace();
    boolean isClosed();
    Slot leave(Integer slotIndex);
}
