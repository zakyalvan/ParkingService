package parking.space;

/**
 * @author zakyalvan
 */
public class DefaultExitGate implements ExitGate, GateOperations {
    private final String gateName;
    private final ParkingSpace fromSpace;
    private final AllocationStrategy allocationStrategy;
    private boolean closed;

    public DefaultExitGate(String gateName, ParkingSpace fromSpace, AllocationStrategy allocationStrategy) {
        this.gateName = gateName;
        this.fromSpace = fromSpace;
        this.allocationStrategy = allocationStrategy;
    }

    @Override
    public String gateName() {
        return gateName;
    }

    @Override
    public Space fromSpace() {
        return fromSpace;
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
    public Slot leave(Integer slotIndex) {
        if(slotIndex == null || (slotIndex < 1 || slotIndex > fromSpace.slotRegistry().capacity())) {
            throw new SlotIndexException(fromSpace.slotRegistry().capacity(), slotIndex);
        }

        if(isClosed()) {
            throw new GateClosedException();
        }

        if(fromSpace.isClosed()) {
            throw new SpaceClosedException();
        }

        return allocationStrategy.releaseSlot(slotIndex);
    }
}
