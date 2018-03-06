package parking.command.impl;

import parking.command.core.*;
import parking.space.CurrentSpaceHolder;
import parking.space.Slot;
import parking.space.Space;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zakyalvan
 */
public class StatusInquiryCommand implements Command {
    private final Space targetSpace;

    StatusInquiryCommand(Space targetSpace) {
        if(targetSpace == null) {
            throw new IllegalArgumentException("Target space for status inquiry command must be provided");
        }
        this.targetSpace = targetSpace;
    }

    public Space targetSpace() {
        return targetSpace;
    }

    @Override
    public Result execute() {
        try {
            Collection<Slot> occupiedSlots = targetSpace.slotRegistry().occupied();
            return new StatusInquiryResult(this, occupiedSlots);
        }
        catch (Exception e) {
            return new StatusInquiryResult(this, e);
        }
    }

    public static class Translator implements SmartCommandTranslator {
        private static final String DEFAULT_COMMAND_IDENTIFIER = "status";

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
            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(identifier);
            }
            return new StatusInquiryCommand(spaceHolder.get());
        }
    }
}
