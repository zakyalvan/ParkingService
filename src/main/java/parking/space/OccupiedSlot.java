package parking.space;

/**
 * Contract for slot which can be occupied.
 *
 * @author zakyalvan
 * @see ParkingSlot
 */
public interface OccupiedSlot extends Slot {
    Slot occupy(Occupant occupant);
}
