package parking.space;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author zakyalvan
 */
class DefaultSlotRegistry implements SlotRegistry {
    private final Integer capacity;
    private final List<Slot> slots = new ArrayList<>();

    DefaultSlotRegistry(Integer registerCapacity) {
        this.capacity = registerCapacity;
        IntStream.rangeClosed(1, registerCapacity)
                .forEach(index -> slots.add(Slot.parkingSlot(index)));
    }

    @Override
    public Integer capacity() {
        return capacity;
    }

    @Override
    public Stream<Slot> stream() {
        return slots.stream();
    }

    @Override
    public Collection<Slot> available() {
        return slots.stream().filter(slot -> slot.available())
                .collect(toList());
    }

    @Override
    public Collection<Slot> occupied() {
        return slots.stream().filter(slot -> !slot.available())
                .collect(toList());
    }

    @Override
    public Iterator<Slot> iterator() {
        return slots.iterator();
    }
}
