package parking.space;

/**
 * Exception to be thrown when trying to allocate {@link Slot} for an already occupying {@link Occupant}.
 *
 * @author zakyalvan
 */
public class AlreadyInsideException extends RuntimeException {
    private final Occupant occupant;

    public AlreadyInsideException(Occupant occupant) {
        this.occupant = occupant;
    }

    public Occupant getOccupant() {
        return occupant;
    }
}
