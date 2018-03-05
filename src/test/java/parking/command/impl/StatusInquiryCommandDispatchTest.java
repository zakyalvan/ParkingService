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
public class StatusInquiryCommandDispatchTest {
    @Test
    public void givenInitializedSpace_whenDispatchWithStatusInquiryCommandInput_thenStatusPrinted() {
        Consumer<SpaceInitializer> initCustomizer = initializer -> initializer
                .reserveSlot(1, Occupant.carInfo("1234-234", PaintColor.WHITE))
                .reserveSlot(2, Occupant.carInfo("1234-235", PaintColor.RED));

        Space space = Space.parkingWithCapacity(10, initCustomizer);
        space.openSpace();

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new StatusInquiryCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new StatusInquiryResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String dispatchOutput = dispatcher.dispatch("status");
        String expectedOutput = CurrentOccupiedFormatHelper.format();

        assertThat(dispatchOutput, is(expectedOutput));
        assertThat(CurrentSpaceHolder.instance().get().slotRegistry().available().size(), is(8));
        assertThat(CurrentSpaceHolder.instance().get().slotRegistry().occupied().size(), is(2));
    }

    public static class CurrentOccupiedFormatHelper {
        public static String format() {
            Space space = CurrentSpaceHolder.instance().get();

            StringBuilder builder = new StringBuilder();

            builder.append("Slot No").append("\t").append("Registration No.").append("\t").append("Colour").append("\n\r");
            int loopIndex = 1;
            for(Slot occupiedSlot : space.slotRegistry().occupied()) {
                builder.append(occupiedSlot.index()).append("\t").append(occupiedSlot.occupant().registerNumber()).append("\t").append(((CarInfo)occupiedSlot.occupant()).paintColor());
                if(loopIndex != space.slotRegistry().occupied().size()) {
                    builder.append("\n\r");
                }
                loopIndex++;
            }
            return builder.toString();
        }
    }
}
