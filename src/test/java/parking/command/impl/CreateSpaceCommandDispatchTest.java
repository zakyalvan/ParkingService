package parking.command.impl;

import org.testng.annotations.Test;
import parking.command.core.CommandDispatcher;
import parking.command.core.SmartCommandTranslator;
import parking.command.core.SmartResultFormatter;
import parking.space.CurrentSpaceHolder;

import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zakyalvan
 */
public class CreateSpaceCommandDispatchTest {
    @Test
    public void givenNumberOfSpaceCapacity_whenDispatchWithCreateSpaceCommantInput_thenSlotsAllocated() {
        Integer spaceCapacity = 10;

        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators ->
                translators.add(new CreateSpaceCommand.Translator());
        Consumer<List<SmartResultFormatter>> formatCustomizers = formats ->
                formats.add(new CreateSpaceResult.Formatter());

        CommandDispatcher dispatcher = new CommandDispatcher(translateCustomizer, formatCustomizers);

        String dispatchOutput = dispatcher.dispatch("create_parking_lot " + spaceCapacity);
        assertThat(dispatchOutput, is(String.format("Created a parking lot with %d slots", spaceCapacity)));
        assertThat(CurrentSpaceHolder.instance().get().capacity(), is(spaceCapacity));
        assertThat(CurrentSpaceHolder.instance().get().slotRegistry().available().size(), is(spaceCapacity));
        assertThat(CurrentSpaceHolder.instance().get().slotRegistry().occupied().size(), is(0));
    }
}
