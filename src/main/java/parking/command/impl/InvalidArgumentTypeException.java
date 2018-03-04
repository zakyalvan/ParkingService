package parking.command.impl;

import parking.command.core.CommandTranslationException;

/**
 * @author zakyalvan
 */
public class InvalidArgumentTypeException extends CommandTranslationException {
    public InvalidArgumentTypeException(String commandIdentifier, String givenArgument, Class<?> requiredType) {
        super(String.format("Argument for command id '%s' must be an '%s' type, but %s given", commandIdentifier, givenArgument, requiredType.getName()));
    }
}
