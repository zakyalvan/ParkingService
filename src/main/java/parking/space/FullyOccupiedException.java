package parking.space;

/**
 * Exception to thrown when trying to allocate a {@link Slot} from an fully allocated {@link Space}
 *
 * @author zakyalvan
 */
public class FullyOccupiedException extends RuntimeException {
    private final Space space;

    public FullyOccupiedException(Space space) {
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }
}
