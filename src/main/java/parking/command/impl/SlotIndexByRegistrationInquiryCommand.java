package parking.command.impl;

import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartCommandTranslator;
import parking.space.CurrentSpaceHolder;
import parking.space.PaintColor;
import parking.space.Slot;
import parking.space.Space;

import java.util.Optional;

/**
 * @author zakyalvan
 */
public class SlotIndexByRegistrationInquiryCommand implements Command {
    private final Space targetSpace;
    private final String registerNumber;

    SlotIndexByRegistrationInquiryCommand(Space targetSpace, String registerNumber) {
        if(targetSpace == null) {
            throw new IllegalArgumentException("Target space for slot index by registration number inquiry command must be provided");
        }

        this.targetSpace = targetSpace;
        this.registerNumber = registerNumber;
    }

    public Space targetSpace() {
        return targetSpace;
    }

    public String registerNumber() {
        return registerNumber;
    }

    @Override
    public Result execute() {
        try {
            Optional<Slot> occupiedSlot = targetSpace.slotRegistry().occupied()
                    .stream().filter(slot -> slot.occupant().registerNumber().equalsIgnoreCase(registerNumber()))
                    .findFirst();

            return new SlotIndexByRegistrationInquiryResult(this, occupiedSlot);
        }
        catch (Exception e) {
            return new SlotIndexByRegistrationInquiryResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "slot_number_for_registration_number ";

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

            String registerNumber = inputParameters[0].trim();

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(commandIdentifier.trim());
            }

            SlotIndexByRegistrationInquiryCommand command = new SlotIndexByRegistrationInquiryCommand(spaceHolder.get(), registerNumber);
            return command;
        }
    }
}
