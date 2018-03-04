package parking.space;

import java.util.Optional;

/**
 * @author zakyalvan
 */
class DefaultSpaceInitializer implements SpaceInitializer {
    private final SlotRegistry slotRegistry;

    DefaultSpaceInitializer(SlotRegistry slotRegistry) {
        this.slotRegistry = slotRegistry;
    }

    @Override
    public SpaceInitializer reserveSlot(Occupant occupant) {
        Slot slot = slotRegistry.available().stream().findAny().get();
        if(slot instanceof OccupiedSlot) {
            ((OccupiedSlot) slot).occupy(occupant);
        }
        else {
            throw new SlotOccupyException(slot);
        }
        return this;
    }

    @Override
    public SpaceInitializer reserveSlot(Integer index, Occupant occupant) {
        Optional<Slot> optionalSlot = slotRegistry.available().stream()
                .filter(slot -> slot.index() == index)
                .findFirst();
        if(optionalSlot.isPresent()) {
            if(optionalSlot.get() instanceof OccupiedSlot) {
                ((OccupiedSlot) optionalSlot.get()).occupy(occupant);
            }
            else {
                throw new SlotOccupyException(optionalSlot.get());
            }
        }
        else {
            throw new SpaceInitializationException();
        }
        return this;
    }
}
