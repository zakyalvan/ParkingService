package parking.command.impl;

import parking.command.core.CommandIdentifier;
import parking.command.core.CommandTranslationException;

import java.util.Arrays;
import java.util.List;

/**
 * @author zakyalvan
 */
public class InvalidArgumentSizeException extends CommandTranslationException {
    private final CommandIdentifier commandIdentifier;
    private final Integer givenNumber;
    private final Integer requiredNumber;
    private final List<String> givenArguments;

    public InvalidArgumentSizeException(CommandIdentifier commandIdentifier, Integer givenNumber, Integer requiredNumber, List<String> givenArguments) {
        super(String.format("Command '%s' is requiring %d number of arguments but %d given (%s)", commandIdentifier.toString(), requiredNumber, givenNumber, Arrays.asList(givenArguments).toString()));
        this.commandIdentifier = commandIdentifier;
        this.givenNumber = givenNumber;
        this.requiredNumber = requiredNumber;
        this.givenArguments = givenArguments;
    }
}
