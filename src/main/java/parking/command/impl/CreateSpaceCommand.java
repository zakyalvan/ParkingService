package parking.command.impl;

import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartCommandTranslator;
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
        private static final String DEFAULT_COMMAND_IDENTIFIER = "create_parking_lot ";

        private final String commandIdentifier;

        public Translator() {
            this.commandIdentifier = DEFAULT_COMMAND_IDENTIFIER;
        }

        @Override
        public boolean supports(String input) {
            return input.trim().toUpperCase().startsWith(commandIdentifier.toUpperCase());
        }

        @Override
        public Command translate(String input) {
            String[] inputParameters = input.trim().toUpperCase().replaceFirst(commandIdentifier.toUpperCase(), "")
                    .trim().split("\\s{1,}");

            if(inputParameters.length != 1) {
                throw new InvalidArgumentSizeException(commandIdentifier, inputParameters.length, 1, inputParameters);
            }

            Integer spaceCapacity = null;
            try {
                spaceCapacity = Integer.parseInt(inputParameters[0]);
            }
            catch (NumberFormatException e) {
                throw new InvalidArgumentTypeException(commandIdentifier, inputParameters[0], Integer.class);
            }

            CreateSpaceCommand command = new CreateSpaceCommand(spaceCapacity);
            return command;
        }
    }
}
