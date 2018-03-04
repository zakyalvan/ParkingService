package parking.space;


import java.time.LocalDateTime;

/**
 * @author zakyalvan
 */
class DefaultOccupancy implements Occupancy {
    private final Slot slot;
    private final Occupant occupant;
    private final LocalDateTime timestamp;

    DefaultOccupancy(Slot slot, Occupant occupant, LocalDateTime timestamp) {
        this.slot = slot;
        this.occupant = occupant;
        this.timestamp = timestamp;
    }

    @Override
    public Slot slot() {
        return slot;
    }

    @Override
    public Occupant occupant() {
        return occupant;
    }

    @Override
    public LocalDateTime timestamp() {
        return timestamp;
    }
}
