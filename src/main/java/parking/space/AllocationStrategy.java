package parking.space;

/**
 * Contract for strategy object responsible in allocating {@link Slot} inside a space.
 *
 * @author zakyalvan
 * @see NearestAllocationStrategy
 */
public interface AllocationStrategy {
    /**
     * Allocate available slot.
     *
     * @return
     */
    Slot allocateSlot(Occupant occupant);

    /**
     *
     *
     * @param slotIndex
     * @return
     */
    Slot releaseSlot(Integer slotIndex);

    /**
     *
     *
     * @param slot
     * @return
     */
    Slot releaseSlot(Slot slot);

    /**
     * Factory method for {@link NearestAllocationStrategy}.
     *
     * @param slotRegistry
     * @return
     * @see NearestAllocationStrategy
     */
    public static AllocationStrategy lowerIndexNearer(SlotRegistry slotRegistry) {
        NearestAllocationStrategy slotAllocator = new NearestAllocationStrategy(slotRegistry);
        slotAllocator.setLowerIndexNearer(true);
        return slotAllocator;
    }

    /**
     *
     * @param slotRegistry
     * @return
     * @see NearestAllocationStrategy
     */
    public static AllocationStrategy higherIndexNearer(SlotRegistry slotRegistry) {
        NearestAllocationStrategy slotAllocator = new NearestAllocationStrategy(slotRegistry);
        slotAllocator.setLowerIndexNearer(false);
        return slotAllocator;
    }
}
