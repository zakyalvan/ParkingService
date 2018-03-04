package parking.command.impl;

import parking.command.core.*;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class ParkCommand implements Command {
    private final Space parkingSpace;
    private final Occupant occupant;

    public ParkCommand(Space parkingSpace, Occupant occupant) {
        this.parkingSpace = parkingSpace;
        this.occupant = occupant;
    }

    public Occupant occupant() {
        return this.occupant;
    }

    @Override
    public Result execute() {
        try {
            Slot occupiedSlot = parkingSpace.entranceGate().enter(occupant);
            return new ParkResult(this, occupiedSlot);
        }
        catch (Exception e) {
            return new ParkResult(this, e);
        }
    }

    public static class InputTranslator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "park ";

        private final Space parkingSpace;
        private final String commandIdentifier;

        public InputTranslator(Space parkingSpace) {
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

            if(inputParameters.length != 2) {
                throw new InvalidArgumentSizeException(commandIdentifier, inputParameters.length, 2, inputParameters);
            }

            String registrationNumber = inputParameters[0];
            PaintColor paintColor = null;
            try {
                paintColor = PaintColor.valueOf(inputParameters[1]);
            }
            catch (IllegalArgumentException e) {
                throw new InvalidColorArgumentException(inputParameters[1]);
            }

            Occupant occupant = CarInfo.withDetails(registrationNumber, paintColor);

            ParkCommand command = new ParkCommand(parkingSpace, occupant);
            return command;
        }
    }
}
