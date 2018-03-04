package parking.space;

/**
 * @author zakyalvan
 */
class ParkingSpace implements Space {
    public static final String DEFAULT_ENTRANCE_GATE = "default.entrance.gate";
    public static final String DEFAULT_EXIT_GATE = "default.exit.gate";

    private final SlotRegistry slotRegistry;

    private boolean closed = true;

    private DefaultEntranceGate entranceGate;
    private DefaultExitGate exitGate;

    ParkingSpace(SlotRegistry slotRegistry, AllocationStrategy allocationStrategy) {
        this.slotRegistry = slotRegistry;
        this.entranceGate = new DefaultEntranceGate(DEFAULT_ENTRANCE_GATE, this, allocationStrategy);
        this.exitGate = new DefaultExitGate(DEFAULT_EXIT_GATE, this, allocationStrategy);
    }

    public Integer capacity() {
        return slotRegistry.capacity();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    public void openSpace() {
        this.entranceGate.openGate();
        this.exitGate.openGate();
        this.closed = false;
    }

    public void closeSpace() {
        this.entranceGate.closeGate();
        this.exitGate.closeGate();
        this.closed = true;
    }

    public EntranceGate entranceGate() {
        return entranceGate;
    }

    public ExitGate exitGate() {
        return exitGate;
    }

    @Override
    public SlotRegistry slotRegistry() {
        return slotRegistry;
    }
}
