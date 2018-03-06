package parking.command.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import parking.command.core.CommandIdentifier;
import parking.command.core.Input;
import parking.command.core.InputParser;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author zakyalvan
 */
public class DefaultInputParserTest {
    private InputParser inputParser;

    @BeforeClass
    public void createParser() {
        this.inputParser = InputParser.defaultParser();
    }

    @Test
    public void givenDefaultInputParser_whenParseSingleSpaceSeparatedCommand_thenReturnInput() {
        String testInput = "sample_command first second";
        Input parsedInput = inputParser.parse(testInput);
        assertThat(parsedInput.identifier(), is(CommandIdentifier.by("SAMPLE_COMMAND")));
        assertThat(parsedInput.arguments().size(), is(2));
    }

    @Test
    public void givenDefaultInputParser_whenParseInconsistenceSpaceSeparatedCommand_thenReturnInput() {
        String testInput = "  sample_command   first second ";
        Input parsedInput = inputParser.parse(testInput);
        assertThat(parsedInput.identifier(), is(CommandIdentifier.by("SAMPLE_COMMAND")));
        assertThat(parsedInput.arguments().size(), is(2));
    }

    @Test
    public void givenDefaultInputParser_whenParseNoArgumentCommandInput_thenReturnInput() {
        String testInput = "    command_without_arguments ";
        Input parsedInput = inputParser.parse(testInput);
        assertThat(parsedInput.identifier(), is(CommandIdentifier.by("command_without_arguments")));
        assertThat(parsedInput.arguments(), is(notNullValue()));
        assertThat(parsedInput.arguments().size(), is(0));
    }

    @Test
    public void givenDefaultInputParser_whenParseTwoIdenticRawInput_thenAllInputMustBeEquals() {
        Input firstInput = inputParser.parse("sample_command first second");
        assertThat(firstInput.identifier(), is(CommandIdentifier.by("SAMPLE_COMMAND")));
        assertThat(firstInput.arguments().size(), is(2));

        Input secondInput = inputParser.parse("sample_command first second");
        assertThat(secondInput.identifier(), is(CommandIdentifier.by("SAMPLE_COMMAND")));
        assertThat(secondInput.arguments().size(), is(2));

        assertThat(firstInput, is(equalTo(secondInput)));
    }

    @Test
    public void givenDefaultInputParser_whenParseInputCommandWithQuotedWords_thenMustSuccess() {
        Input firstInput = inputParser.parse("  sample_command  'one argument' second 'and this third' \"and fourth\" ");
        assertThat(firstInput.identifier(), is(CommandIdentifier.by("SAMPLE_COMMAND")));
        assertThat(firstInput.arguments().size(), is(4));
    }
}
