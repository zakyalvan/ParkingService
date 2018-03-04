package parking.space;

/**
 * A abstraction for {@link Space} entry access.
 *
 * @author zakyalvan
 */
public interface EntranceGate {
    String gateName();
    Space targetSpace();
    boolean isClosed();
    Slot enter(Occupant occupant);
}
