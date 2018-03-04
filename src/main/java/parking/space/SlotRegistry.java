package parking.space;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author zakyalvan
 */
public interface SlotRegistry extends Iterable<Slot> {
    Integer capacity();
    Stream<Slot> stream();
    Collection<Slot> available();
    Collection<Slot> occupied();

    public static DefaultSlotRegistry defaultWithCapacity(Integer spaceCapacity) {
        return new DefaultSlotRegistry(spaceCapacity);
    }
    public static DefaultSlotRegistry defaultWithCapacity(Integer spaceCapacity, Consumer<SpaceInitializer> slotCustomizer) {
        DefaultSlotRegistry slotRegistry = new DefaultSlotRegistry(spaceCapacity);
        if(slotCustomizer != null) {
            SpaceInitializer spaceInitializer = SpaceInitializer.defaultInitializer(slotRegistry);
            slotCustomizer.accept(spaceInitializer);
        }
        return slotRegistry;
    }
}
