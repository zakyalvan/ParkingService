package parking.command.impl;

import parking.command.core.CommandIdentifier;
import parking.command.core.CommandTranslationException;

/**
 * @author zakyalvan
 */
public class TargetSpaceNotFoundException extends CommandTranslationException {
    private final CommandIdentifier commandIdentifier;

    public TargetSpaceNotFoundException(CommandIdentifier commandIdentifier) {
        super(String.format("Execution of command '%s' requiring a space to be created first", commandIdentifier));
        this.commandIdentifier = commandIdentifier;
    }

    public CommandIdentifier commandIdentifier() {
        return commandIdentifier;
    }
}
