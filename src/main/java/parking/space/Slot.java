package parking.space;

/**
 * @author zakyalvan
 */
public interface Slot {
    Integer index();
    Occupant occupant();
    boolean available();

    /**
     * Factory method for this default {@link Slot} implementation.
     *
     * @param index
     * @return
     */
    public static ParkingSlot parkingSlot(Integer index) {
        if(index == null || index <= 0) {
            throw new IllegalArgumentException("Slot index must be positive integer");
        }
        return new ParkingSlot(index);
    }
}
