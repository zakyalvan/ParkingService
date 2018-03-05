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
public class RegisterByColorInquiryCommandDispatchTest {
    @Test
    public void givenInitializerParkingSpace_whenDispatchWithRegisterNumberByColorInquiryCommandInput_thenReturnAllRegisterNumber() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-234-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ACD-123-235-WWE", PaintColor.RED))
                .reserveSlot(3, Occupant.carInfo("ADD-123-237-CDE", PaintColor.WHITE));

        Space space = Space.parkingWithCapacity(10, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new RegistrationByColorInquiryCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new RegistrationByColorInquiryResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String dispatchOutput = dispatcher.dispatch("registration_numbers_for_cars_with_colour White");
        assertThat(dispatchOutput, is("ASD-123-234-QWE, ADD-123-237-CDE"));
    }

    @Test
    public void givenInitializerParkingSpace_whenDispatchWithInvalidColorValue_thenRejectInquiry() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("ASD-123-234-QWE", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("ACD-123-235-WWE", PaintColor.RED))
                .reserveSlot(3, Occupant.carInfo("ADD-123-237-CDE", PaintColor.WHITE));

        Space space = Space.parkingWithCapacity(10, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new RegistrationByColorInquiryCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new RegistrationByColorInquiryResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String dispatchOutput = dispatcher.dispatch("registration_numbers_for_cars_with_colour WhiteX");
    }
}
