package parking.command.impl;

import org.testng.annotations.Test;
import parking.command.core.CommandDispatcher;
import parking.command.core.SmartCommandTranslator;
import parking.command.core.SmartResultFormatter;
import parking.space.*;

import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zakyalvan
 */
public class ParkCarCommandDispatchTest {
    @Test
    public void givenEmptyParkingSpace_whenDispatchWithParkCommandInput_thenAllocateOneSlot() {
        Space space = Space.parkingWithCapacity(10);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("park ASD-123-342-QWE White");
        assertThat(output, is("Allocated slot number: 1"));
    }

    @Test
    public void givenFullParkingSpace_whenDispatchWithParkCommandInput_thenRejectEntry() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(2, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("park ASD-123-342-QWE White");
        assertThat(output, is("Sorry, parking lot is full"));
    }

    @Test
    public void givenAvailableParkingSlots_whenDispatchWithExistingOccupant_thenRejectEntry() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-342-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ASD-343-657-QWT", PaintColor.RED));

        Space space = Space.parkingWithCapacity(5, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("park ASD-123-342-QWE White");
        assertThat(output, is("Sorry, car with registration number 'ASD-123-342-QWE', already inside"));
    }

    @Test
    public void givenAvailableParkingSlots_whenDispatchWithInvalidCommand_thenRejectEntry() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-342-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ASD-343-657-QWT", PaintColor.RED));

        Space space = Space.parkingWithCapacity(5, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("parks ASD-123-345-QWE White");
        assertThat(output, is("No command translator : parks ASD-123-345-QWE White"));
    }

    @Test
    public void givenAvailableParkingSlots_whenDispatchWithInvalidColor_thenRejectEntry() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-342-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ASD-343-657-QWT", PaintColor.RED));

        Space space = Space.parkingWithCapacity(5, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("park ASD-123-345-QWE Whites");
        assertThat(output, is("Command translation error : Given paint color value 'Whites' is invalid, valid value are [RED, GREEN, BLUE, BLACK, WHITE]"));
    }


    @Test
    public void givenAvailableParkingSlots_whenDispatchWithQuotedRegisterNumber_thenSlotAllocated() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-342-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ASD-343-657-QWT", PaintColor.RED));

        Space space = Space.parkingWithCapacity(5, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new ParkCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new ParkResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String output = dispatcher.dispatch("park 'B 4030 BCM' Red");
        assertThat(output, is("Allocated slot number: 3"));

        Slot occupied = space.slotRegistry().occupied().stream()
                .filter(slot -> slot.index() == 3).findFirst()
                .get();
        assertThat(occupied.occupant().registerNumber(), is("B 4030 BCM"));
        assertThat(((CarInfo) occupied.occupant()).paintColor(), is(PaintColor.RED));
    }
}
