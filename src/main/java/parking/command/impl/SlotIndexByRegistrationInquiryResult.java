package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.Slot;
import parking.space.SpaceClosedException;

import java.util.Optional;

/**
 * @author zakyalvan
 */
public class SlotIndexByRegistrationInquiryResult extends AbstractResult {
    private final Optional<Slot> occupiedSlot;

    SlotIndexByRegistrationInquiryResult(Command command, Optional<Slot> occupiedSlot) {
        super(command);
        this.occupiedSlot = occupiedSlot;
    }

    public SlotIndexByRegistrationInquiryResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.occupiedSlot = null;
    }

    public Optional<Slot> occupiedSlot() {
        return occupiedSlot;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof SlotIndexByRegistrationInquiryCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                SlotIndexByRegistrationInquiryResult inquiryResult = (SlotIndexByRegistrationInquiryResult) result;
                if(inquiryResult.occupiedSlot().isPresent()) {
                    return String.format("%d", inquiryResult.occupiedSlot.get().index());
                }
                else {
                    return "Not found";
                }
            }
            else {
                if(result.exception() instanceof SpaceClosedException) {
                    return "Sorry, parking space is closed currently";
                }
                else {
                    result.exception().printStackTrace();
                    return "Sorry, can not serve park command";
                }
            }
        }
    }
}
