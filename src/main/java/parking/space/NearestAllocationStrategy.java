package parking.space;

import java.util.Comparator;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * @author zakyalvan
 */
public class NearestAllocationStrategy implements AllocationStrategy {
    private static final Comparator<Slot> LOWER_INDEX_NEARER_COMPARATOR = (firstSlot, secondSlot) ->
            firstSlot.index().compareTo(secondSlot.index());

    private static final Comparator<Slot> HIGHER_INDEX_NEARER_COMPARATOR = (firstSlot, secondSlot) ->
            secondSlot.index().compareTo(firstSlot.index());

    private final SlotRegistry slotRegistry;

    private Comparator<Slot> distanceComparator;

    /**
     * @param slotRegistry
     */
    NearestAllocationStrategy(SlotRegistry slotRegistry) {
        this(slotRegistry, true);
    }

    /**
     * @param slotRegistry
     * @param lowerIndexNearer Flag represent whether lower slot index represent lowerIndexNearer slot from entrance gate.
     */
    NearestAllocationStrategy(SlotRegistry slotRegistry, boolean lowerIndexNearer) {
        this.slotRegistry = slotRegistry;
        setLowerIndexNearer(lowerIndexNearer);
    }

    public void setLowerIndexNearer(boolean lowerIndexNearer) {
        if(lowerIndexNearer) {
            this.distanceComparator = LOWER_INDEX_NEARER_COMPARATOR;
        }
        else {
            this.distanceComparator = HIGHER_INDEX_NEARER_COMPARATOR;
        }
    }

    @Override
    public Slot allocateSlot(Occupant occupant) {
        boolean alreadyInside = slotRegistry.occupied().stream()
                .map(slot -> slot.occupant()).collect(toList())
                .contains(occupant);
        if(alreadyInside) {
            throw new AlreadyInsideException(occupant);
        }

        if(slotRegistry.available().isEmpty()) {
            throw new FullyOccupiedException(null);
        }

        Optional<Slot> availableSlot = slotRegistry.available().stream()
                .sorted(distanceComparator).findFirst();

        Slot allocatedSlot = availableSlot.get();
        if(allocatedSlot instanceof OccupiedSlot) {
            ((OccupiedSlot) allocatedSlot).occupy(occupant);
            return allocatedSlot;
        }
        else {
            throw new SlotOccupyException(allocatedSlot);
        }
    }

    @Override
    public Slot releaseSlot(Integer slotIndex) {
        if (slotIndex > slotRegistry.capacity()) {
            throw new SlotIndexException(slotRegistry.capacity(), slotIndex);
        }

        if(slotRegistry.occupied().isEmpty()) {
            throw new FullyAvailableException(null);
        }

        Optional<Slot> occupiedSlot = slotRegistry.occupied().stream()
                .filter(slot -> slot.index() == slotIndex)
                .findFirst();

        if (occupiedSlot.isPresent() && occupiedSlot.get() instanceof OccupiedSlot) {
            ((OccupiedSlot) occupiedSlot.get()).occupy(null);
            return occupiedSlot.get();
        }
        else {
            throw new SlotUnoccupiedException();
        }
    }

    @Override
    public Slot releaseSlot(Slot slot) {
        return releaseSlot(slot.index());
    }
}
