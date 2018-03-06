package parking.command.impl;

import parking.command.core.*;
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
        private static final String DEFAULT_COMMAND_IDENTIFIER = "slot_number_for_registration_number";

        private final CommandIdentifier identifier;

        public Translator() {
            identifier = CommandIdentifier.by(DEFAULT_COMMAND_IDENTIFIER);
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

            String registerNumber = input.argument(0);

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(identifier);
            }

            return new SlotIndexByRegistrationInquiryCommand(spaceHolder.get(), registerNumber);
        }
    }
}
