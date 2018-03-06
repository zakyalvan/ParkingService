package parking.command.impl;

import parking.command.core.*;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class LeaveCommand implements Command {
    private final Space targetSpace;
    private final Integer slotIndex;

    LeaveCommand(Space targetSpace, Integer slotIndex) {
        this.targetSpace = targetSpace;
        this.slotIndex = slotIndex;
    }

    public Space targetSpace() {
        return targetSpace;
    }

    public Integer slotNumber() {
        return this.slotIndex;
    }

    @Override
    public Result execute() {
        try {
            Slot leavedSlot = targetSpace.exitGate().leave(slotIndex);
            return new LeaveResult(this, leavedSlot);
        }
        catch (Exception e) {
            return new LeaveResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "leave";

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

            Integer slotIndex = null;
            try {
                slotIndex = Integer.parseInt(input.argument(0));
            }
            catch (NumberFormatException e) {
                throw new InvalidArgumentTypeException(identifier, input.argument(0), Integer.class);
            }

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(identifier);
            }

            LeaveCommand command = new LeaveCommand(spaceHolder.get(), slotIndex);
            return command;
        }
    }
}
