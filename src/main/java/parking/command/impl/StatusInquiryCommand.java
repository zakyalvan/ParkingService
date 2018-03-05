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

            CurrentSpaceHolder spaceHolder = CurrentSpaceHolder.instance();
            if(!spaceHolder.exists()) {
                throw new TargetSpaceNotFoundException(commandIdentifier.trim());
            }

            StatusInquiryCommand command = new StatusInquiryCommand(spaceHolder.get());
            return command;
        }
    }
}
