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

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "park";

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
            if(input.arguments().size() != 2) {
                throw new InvalidArgumentSizeException(identifier, input.arguments().size(), 2, input.arguments());
            }

            String registrationNumber = input.argument(0);
            PaintColor paintColor = null;
            try {
                paintColor = PaintColor.valueOf(input.argument(1).toUpperCase());
            }
            catch (IllegalArgumentException e) {
                throw new InvalidColorArgumentException(input.argument(1));
            }

            Occupant occupant = CarInfo.withDetails(registrationNumber, paintColor);

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(identifier);
            }

            ParkCommand command = new ParkCommand(spaceHolder.get(), occupant);
            return command;
        }
    }
}
