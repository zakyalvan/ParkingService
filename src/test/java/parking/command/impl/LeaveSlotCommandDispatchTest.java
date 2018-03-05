package parking.command.impl;

import org.testng.annotations.Test;
import parking.command.core.CommandDispatcher;
import parking.command.core.SmartCommandTranslator;
import parking.command.core.SmartResultFormatter;
import parking.space.Occupant;
import parking.space.PaintColor;
import parking.space.Space;
import parking.space.SpaceInitializer;

import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zakyalvan
 */
public class LeaveSlotCommandDispatchTest {
    @Test
    public void givenEmptyParkingSpace_whenDispatchWithParkCommandInput_thenAllocateOneSlot() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(10, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new LeaveCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new LeaveResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String firstOutput = dispatcher.dispatch("leave 1");
        assertThat(firstOutput, is("Slot number 1 is free"));

        String secondOutput = dispatcher.dispatch("Leave 2");
        assertThat(secondOutput, is("Slot number 2 is free"));
    }
}
