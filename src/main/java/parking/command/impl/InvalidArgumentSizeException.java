package parking.command.impl;

import parking.command.core.CommandTranslationException;

import java.util.Arrays;

/**
 * @author zakyalvan
 */
public class InvalidArgumentSizeException extends CommandTranslationException {
    private final String commandIdentifier;
    private final Integer givenNumber;
    private final Integer requiredNumber;
    private final String[] givenArguments;

    public InvalidArgumentSizeException(String commandIdentifier, Integer givenNumber, Integer requiredNumber, String... givenArguments) {
        super(String.format("Command '%s' is requiring %d number of arguments but %d given (%s)", commandIdentifier, requiredNumber, givenNumber, Arrays.asList(givenArguments).toString()));
        this.commandIdentifier = commandIdentifier;
        this.givenNumber = givenNumber;
        this.requiredNumber = requiredNumber;
        this.givenArguments = givenArguments;
    }
}
