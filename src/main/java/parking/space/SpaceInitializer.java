package parking.space;

/**
 * Slot reservation initializer, intended for initializing slots in test.
 *
 * @author zakyalvan
 */
public interface SpaceInitializer {
    SpaceInitializer reserveSlot(Occupant occupant);
    SpaceInitializer reserveSlot(Integer index, Occupant occupant);

    /**
     * Factory method for default {@link SpaceInitializer}.
     *
     * @param slotRegistry
     * @return
     */
    public static SpaceInitializer defaultInitializer(SlotRegistry slotRegistry) {
        if(slotRegistry == null) {
            throw new IllegalArgumentException("Slot registry parameter must be provided");
        }
        return new DefaultSpaceInitializer(slotRegistry);
    }
}
