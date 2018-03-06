package parking.command.impl;

import parking.command.core.*;
import parking.space.Space;

/**
 * @author zakyalvan
 */
public class CreateSpaceCommand implements Command {
    private final Integer spaceCapacity;

    CreateSpaceCommand(Integer spaceCapacity) {
        this.spaceCapacity = spaceCapacity;
    }

    public Integer spaceCapacity() {
        return spaceCapacity;
    }

    @Override
    public Result execute() {
        try {
            Space createdSpace = Space.parkingWithCapacity(spaceCapacity);
            createdSpace.openSpace();
            return new CreateSpaceResult(this, createdSpace);
        }
        catch (Exception e) {
            return new CreateSpaceResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "create_parking_lot";

        private final CommandIdentifier identifier;

        public Translator() {
            this.identifier = CommandIdentifier.by(DEFAULT_COMMAND_IDENTIFIER);
        }

        @Override
        public boolean supports(Input input) {
            return identifier.equals(input.identifier());
        }

        @Override
        public Command translate(Input input) {
            if(input.arguments().size() != 1) {
                throw new InvalidArgumentSizeException(identifier, input.arguments().size(), 1, input.arguments());
            }

            Integer spaceCapacity = null;
            try {
                spaceCapacity = Integer.parseInt(input.argument(0));
            }
            catch (NumberFormatException e) {
                throw new InvalidArgumentTypeException(identifier, input.argument(0), Integer.class);
            }

            CreateSpaceCommand command = new CreateSpaceCommand(spaceCapacity);
            return command;
        }
    }
}
