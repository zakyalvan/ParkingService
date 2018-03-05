package parking.command.impl;

import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartCommandTranslator;
import parking.space.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zakyalvan
 */
public class RegistrationByColorInquiryCommand implements Command {
    private final Space targetSpace;
    private final PaintColor paintColor;

    public RegistrationByColorInquiryCommand(Space targetSpace, PaintColor paintColor) {
        if(targetSpace == null) {
            throw new IllegalArgumentException("Target space for registration by color inquiry command must be provided");
        }

        this.targetSpace = targetSpace;
        this.paintColor = paintColor;
    }

    public Space targetSpace() {
        return targetSpace;
    }

    public PaintColor paintColor() {
        return paintColor;
    }

    @Override
    public Result execute() {
        try {
            List<CarInfo> colorOccupants = targetSpace.slotRegistry().occupied()
                    .stream().map(slot -> (CarInfo) slot.occupant())
                    .filter(occupant -> occupant.paintColor().equals(paintColor))
                    .collect(Collectors.toList());

            return new RegistrationByColorInquiryResult(this, colorOccupants);
        }
        catch (Exception e) {
            return new RegistrationByColorInquiryResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "registration_numbers_for_cars_with_colour ";

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

            PaintColor paintColor = null;
            try {
                paintColor = PaintColor.valueOf(inputParameters[0]);
            }
            catch (IllegalArgumentException e) {
                throw new InvalidColorArgumentException(inputParameters[0]);
            }

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(commandIdentifier.trim());
            }

            RegistrationByColorInquiryCommand command = new RegistrationByColorInquiryCommand(spaceHolder.get(), paintColor);
            return command;
        }
    }
}
