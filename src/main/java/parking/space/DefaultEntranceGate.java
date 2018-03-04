package parking.space;

import java.time.LocalDateTime;

/**
 * @author zakyalvan
 */
public class DefaultEntranceGate implements EntranceGate, GateOperations {
    private final String gateName;
    private final ParkingSpace targetSpace;
    private final AllocationStrategy allocationStrategy;

    private boolean closed = true;

    public DefaultEntranceGate(String gateName, ParkingSpace targetSpace, AllocationStrategy allocationStrategy) {
        this.gateName = gateName;
        this.targetSpace = targetSpace;
        this.allocationStrategy = allocationStrategy;
    }

    @Override
    public String gateName() {
        return gateName;
    }

    @Override
    public Space targetSpace() {
        return targetSpace;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void openGate() {
        this.closed = false;
    }

    @Override
    public void closeGate() {
        this.closed = true;
    }

    @Override
    public Slot enter(Occupant occupant) {
        if(occupant == null) {
            throw new IllegalArgumentException("Occupant parameter must be provided");
        }

        if(isClosed()) {
            throw new GateClosedException();
        }

        if(targetSpace.isClosed()) {
            throw new SpaceClosedException();
        }

        return allocationStrategy.allocateSlot(occupant);
    }
}
