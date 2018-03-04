package parking.command.impl;

import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartCommandTranslator;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class LeaveCommand implements Command {
    private final Space parkingSpace;
    private final Integer slotIndex;

    public LeaveCommand(Space parkingSpace, Integer slotIndex) {
        this.parkingSpace = parkingSpace;
        this.slotIndex = slotIndex;
    }

    public Integer slotNumber() {
        return this.slotIndex;
    }

    @Override
    public Result execute() {
        try {
            Slot leavedSlot = parkingSpace.exitGate().leave(slotIndex);
            return new LeaveResult(this, leavedSlot);
        }
        catch (Exception e) {
            return new LeaveResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "leave ";

        private final Space parkingSpace;
        private final String commandIdentifier;

        public Translator(Space parkingSpace) {
            this.parkingSpace = parkingSpace;
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

            Integer slotIndex = null;
            try {
                slotIndex = Integer.parseInt(inputParameters[0]);
            }
            catch (NumberFormatException e) {
                throw new InvalidArgumentTypeException(commandIdentifier, inputParameters[1], Integer.class);
            }

            LeaveCommand command = new LeaveCommand(parkingSpace, slotIndex);
            return command;
        }
    }
}
