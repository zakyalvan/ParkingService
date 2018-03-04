package parking.space;

import java.util.function.Consumer;

/**
 * @author zakyalvan
 */
public interface Space {
    Integer capacity();

    boolean isClosed();
    void openSpace();
    void closeSpace();

    EntranceGate entranceGate();
    ExitGate exitGate();

    SlotRegistry slotRegistry();

    /**
     * Factory method for {@link ParkingSpace}
     *
     * @param spaceCapacity
     * @return
     */
    public static Space parkingWithCapacity(Integer spaceCapacity) {
        return Space.parkingWithCapacity(spaceCapacity, null);
    }

    /**
     * Factory method for {@link ParkingSpace} with initial slot registry customizer.
     *
     * @param spaceCapacity
     * @param initCustomizer
     * @return
     */
    public static Space parkingWithCapacity(Integer spaceCapacity, Consumer<SpaceInitializer> initCustomizer) {
        if(spaceCapacity == null || spaceCapacity <= 0) {
            throw new IllegalArgumentException("Space capacity parameter must be provided with positive integer value");
        }

        DefaultSlotRegistry slotRegistry = SlotRegistry.defaultWithCapacity(spaceCapacity);
        if(initCustomizer != null) {
            SpaceInitializer spaceInitializer = SpaceInitializer.defaultInitializer(slotRegistry);
            initCustomizer.accept(spaceInitializer);
        }
        AllocationStrategy allocationStrategy = AllocationStrategy.lowerIndexNearer(slotRegistry);
        return new ParkingSpace(slotRegistry, allocationStrategy);
    }
}
