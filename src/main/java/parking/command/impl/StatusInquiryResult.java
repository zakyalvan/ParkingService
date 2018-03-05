package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.CarInfo;
import parking.space.Slot;
import parking.space.Space;
import parking.space.SpaceClosedException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zakyalvan
 */
public class StatusInquiryResult extends AbstractResult {
    private final Collection<Slot> occupiedSlots;

    public StatusInquiryResult(Command command, Collection<Slot> occupiedSlots) {
        super(command);
        this.occupiedSlots = occupiedSlots;
    }

    public StatusInquiryResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.occupiedSlots = null;
    }

    public Collection<Slot> occupiedSlots() {
        return occupiedSlots;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof StatusInquiryCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                StringBuilder builder = new StringBuilder();

                builder.append("Slot No").append("\t").append("Registration No.").append("\t").append("Colour").append("\n\r");
                int loopIndex = 1;
                for(Slot occupiedSlot : ((StatusInquiryResult) result).occupiedSlots()) {
                    builder.append(occupiedSlot.index()).append("\t").append(occupiedSlot.occupant().registerNumber()).append("\t").append(((CarInfo)occupiedSlot.occupant()).paintColor());
                    if(loopIndex != ((StatusInquiryResult) result).occupiedSlots().size()) {
                        builder.append("\n\r");
                    }
                    loopIndex++;
                }
                return builder.toString();
            }
            else {
                if(result.exception() instanceof SpaceClosedException) {
                    return "Sorry, parking space is closed currently";
                }
                else {
                    result.exception().printStackTrace();
                    return "Sorry, can not serve leave command";
                }
            }
        }
    }
}
