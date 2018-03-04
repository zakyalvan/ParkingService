package parking.space;

import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author zakyalvan
 * @see ParkingSpace
 */
public class ParkingSpaceTest {
    @Test
    public void givenEmptySpace_whenOneOccupantEnter_thenNearestSlotMustBeOccupied() {
        Space space = Space.parkingWithCapacity(10);
        space.openSpace();

        assertThat(space.slotRegistry().available().size(), is(10));
        assertThat(space.slotRegistry().occupied().size(), is(0));
        Slot occupiedSlot = space.entranceGate().enter(CarInfo.withDetails("AB-123-456-CD", PaintColor.RED));
        assertThat(occupiedSlot, is(notNullValue()));
        assertThat(occupiedSlot.index(), is(1));
        assertThat(occupiedSlot.available(), is(false));
        assertThat(space.slotRegistry().available().size(), is(9));
        assertThat(space.slotRegistry().occupied().size(), is(1));
    }

    @Test
    public void givenOccupiedSlots_whenOneLeaveOccupiedSlot_thenSlotMustBecomeAvailable() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(10, customizer);
        space.openSpace();

        Slot occupiedSlot = space.slotRegistry().stream()
                .filter(slot -> slot.index() == 2)
                .findFirst().get();
        assertThat(occupiedSlot, is(notNullValue()));
        assertThat(occupiedSlot.index(), is(2));
        assertThat(occupiedSlot.available(), is(false));
        assertThat(space.slotRegistry().available().size(), is(8));
        assertThat(space.slotRegistry().occupied().size(), is(2));

        Slot leavedSlot = space.exitGate().leave(2);

        assertThat(leavedSlot, is(notNullValue()));
        assertThat(leavedSlot.index(), is(2));
        assertThat(leavedSlot.available(), is(true));
        assertThat(space.slotRegistry().available().size(), is(9));
        assertThat(space.slotRegistry().occupied().size(), is(1));
    }

    @Test(expectedExceptions = SlotUnoccupiedException.class)
    public void givenOccupiedSlots_whenOneLeaveUnoccupiedSlot_thenThrownSlotUnoccupiedException() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(10, customizer);
        space.openSpace();

        Slot occupiedSlot = space.slotRegistry().stream()
                .filter(slot -> slot.index() == 2)
                .findFirst().get();
        assertThat(occupiedSlot, is(notNullValue()));
        assertThat(occupiedSlot.index(), is(2));
        assertThat(occupiedSlot.available(), is(false));
        assertThat(space.slotRegistry().available().size(), is(8));
        assertThat(space.slotRegistry().occupied().size(), is(2));

        space.exitGate().leave(5);
    }

    @Test(expectedExceptions = SlotIndexException.class)
    public void givenOccupiedSlots_whenLeaveNotExistsSlot_thenThrownSlotIndexException() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(10, customizer);
        space.openSpace();

        assertThat(space.slotRegistry().available().size(), is(8));
        assertThat(space.slotRegistry().occupied().size(), is(2));

        space.exitGate().leave(11);
    }
}
