package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.Slot;
import parking.space.SpaceClosedException;

import java.util.List;

/**
 * @author zakyalvan
 */
public class SlotIndexByColorInquiryResult extends AbstractResult {
    private final List<Slot> colorSlots;

    SlotIndexByColorInquiryResult(Command command, List<Slot> colorSlots) {
        super(command);
        this.colorSlots = colorSlots;
    }

    public SlotIndexByColorInquiryResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.colorSlots = null;
    }

    public List<Slot> colorSlots() {
        return colorSlots;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof SlotIndexByColorInquiryCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                SlotIndexByColorInquiryResult inquiryResult = (SlotIndexByColorInquiryResult) result;

                if(inquiryResult.colorSlots().isEmpty()) {
                    return "None";
                }

                StringBuilder outputBuilder = new StringBuilder();
                int loopIndex = 1;
                for (Slot colorSlot : inquiryResult.colorSlots()) {
                    outputBuilder.append(colorSlot.index());
                    if(loopIndex < inquiryResult.colorSlots().size()) {
                        outputBuilder.append(", ");
                    }
                    loopIndex++;
                }
                return outputBuilder.toString();
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
