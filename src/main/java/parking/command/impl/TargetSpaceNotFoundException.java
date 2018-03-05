package parking.command.impl;

import parking.command.core.CommandTranslationException;

/**
 * @author zakyalvan
 */
public class TargetSpaceNotFoundException extends CommandTranslationException {
    private final String commandIdentifier;

    public TargetSpaceNotFoundException(String commandIdentifier) {
        super(String.format("Execution of command '%s' requiring a space to be created first", commandIdentifier));
        this.commandIdentifier = commandIdentifier;
    }

    public String commandIdentifier() {
        return commandIdentifier;
    }
}
