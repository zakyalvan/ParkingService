package parking.space;

import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zakyalvan
 * @see NearestAllocationStrategy
 */
public class NearestAllocationStrategyTests {
    @Test
    public void givenAvailableSlots_whenAllocateForOneSlot_thenOneAvailableSlotOccupied() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(3, Occupant.carInfo("1234-235", PaintColor.RED));

        SlotRegistry registry = SlotRegistry.defaultWithCapacity(10, customizer);

        assertThat(registry.available().size(), is(8));
        assertThat(registry.occupied().size(), is(2));

        AllocationStrategy allocator = AllocationStrategy.lowerIndexNearer(registry);
        Slot allocatedSlot = allocator.allocateSlot(Occupant.carInfo("1234-456", PaintColor.BLACK));

        assertThat(allocatedSlot.index(), is(2));
        assertThat(allocatedSlot.available(), is(false));
        assertThat(registry.available().size(), is(7));
        assertThat(registry.occupied().size(), is(3));
    }

    @Test(expectedExceptions = AlreadyInsideException.class)
    public void givenAvailableSlots_whenAllocateForAlreadyEnterOccupant_thenThrowAlreadyInsideException() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ABC-1234-234-DF", PaintColor.WHITE));

        SlotRegistry registry = SlotRegistry.defaultWithCapacity(10, customizer);

        assertThat(registry.available().size(), is(9));
        assertThat(registry.occupied().size(), is(1));

        AllocationStrategy allocator = AllocationStrategy.lowerIndexNearer(registry);
        allocator.allocateSlot(Occupant.carInfo("abc-1234-234-df", PaintColor.WHITE));
    }

    @Test(expectedExceptions = FullyOccupiedException.class)
    public void givenFullyOccupiedSlots_whenAllocateForOneSlot_thenThrowFullyOccupiedException() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        SlotRegistry registry = SlotRegistry.defaultWithCapacity(2, customizer);

        assertThat(registry.available().size(), is(0));
        assertThat(registry.occupied().size(), is(2));

        AllocationStrategy allocator = AllocationStrategy.lowerIndexNearer(registry);
        allocator.allocateSlot(Occupant.carInfo("1234-456", PaintColor.BLACK));
    }

    @Test
    public void givenOneOccupiedSlots_whenReleaseOccupiedSlot_thenAllSlotsAvailable() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE));

        SlotRegistry registry = SlotRegistry.defaultWithCapacity(5, customizer);

        assertThat(registry.available().size(), is(4));
        assertThat(registry.occupied().size(), is(1));

        AllocationStrategy allocator = AllocationStrategy.lowerIndexNearer(registry);
        Slot releasedSlot = allocator.releaseSlot(1);

        assertThat(releasedSlot.index(), is(1));
        assertThat(releasedSlot.available(), is(true));
        assertThat(registry.available().size(), is(5));
        assertThat(registry.occupied().size(), is(0));
    }

    @Test(expectedExceptions = SlotUnoccupiedException.class)
    public void givenOneOccupiedSlots_whenReleaseUnoccupiedSlot_thenThrowSlotUnoccupiedException() {
        Consumer<SpaceInitializer> customizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE));

        SlotRegistry registry = SlotRegistry.defaultWithCapacity(5, customizer);

        assertThat(registry.available().size(), is(4));
        assertThat(registry.occupied().size(), is(1));

        AllocationStrategy allocator = AllocationStrategy.lowerIndexNearer(registry);
        allocator.releaseSlot(2);
    }
}
