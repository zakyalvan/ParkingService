package parking.space;

/**
 * @author zakyalvan
 */
public class FullyAvailableException extends RuntimeException {
    private final Space space;

    public FullyAvailableException(Space space) {
        super("no occupied slot to be released, all slots in space are available");
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }
}
