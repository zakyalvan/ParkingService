package parking.command.impl;

import parking.command.core.CommandIdentifier;
import parking.command.core.CommandTranslationException;

/**
 * @author zakyalvan
 */
public class InvalidArgumentTypeException extends CommandTranslationException {
    public InvalidArgumentTypeException(CommandIdentifier commandIdentifier, String givenArgument, Class<?> requiredType) {
        super(String.format("Argument for command id '%s' must be an '%s' type, but %s given", commandIdentifier.toString(), givenArgument, requiredType.getName()));
    }
}
