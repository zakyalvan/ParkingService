package parking.space;

import java.time.LocalDateTime;

/**
 * @author zakyalvan
 */
public interface Occupancy {
    Slot slot();
    Occupant occupant();
    LocalDateTime timestamp();

    public static Occupancy createDefault(Slot slot, Occupant occupant, LocalDateTime timestamp) {
        return new DefaultOccupancy(slot, occupant, timestamp);
    }
}
