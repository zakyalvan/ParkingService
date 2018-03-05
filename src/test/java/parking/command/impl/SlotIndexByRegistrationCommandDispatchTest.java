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
public class SlotIndexByRegistrationCommandDispatchTest {
    @Test
    public void givenInitializerParkingSpace_whenDispatchWithSlotIndexByColorInquiryCommandInput_thenReturnAllSlotNumberOccupied() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-234-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ACD-123-235-WWE", PaintColor.RED))
                .reserveSlot(4, Occupant.carInfo("ADD-123-237-CDE", PaintColor.WHITE));

        Space space = Space.parkingWithCapacity(10, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new SlotIndexByRegistrationInquiryCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new SlotIndexByRegistrationInquiryResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String firstOutput = dispatcher.dispatch("slot_number_for_registration_number ASD-123-234-QWE");
        assertThat(firstOutput, is("1"));

        String secondOutput = dispatcher.dispatch("slot_number_for_registration_number ACD-123-235-WWE");
        assertThat(secondOutput, is("2"));

        String thirdOutput = dispatcher.dispatch("slot_number_for_registration_number ACD-123-235-WWC");
        assertThat(thirdOutput, is("Not found"));
    }
}
