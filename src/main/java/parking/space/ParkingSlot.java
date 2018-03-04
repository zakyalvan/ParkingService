package parking.space;

import java.util.Objects;

/**
 * @author zakyalvan
 */
class ParkingSlot implements OccupiedSlot {
    private final Integer index;
    private Occupant occupant;

    ParkingSlot(Integer index) {
        this.index = index;
    }

    public Integer index() {
        return index;
    }
    @Override
    public Occupant occupant() {
        return occupant;
    }
    @Override
    public boolean available() {
        return occupant == null;
    }

    @Override
    public Slot occupy(Occupant occupant) {
        this.occupant = occupant;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(index, that.index);
    }
    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "index=" + index +
                ", occupant=" + occupant +
                '}';
    }
}
