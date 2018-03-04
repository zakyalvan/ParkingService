package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class LeaveResult extends AbstractResult {
    private final Slot leavedSlot;

    public LeaveResult(Command command, Slot leavedSlot) {
        super(command);
        this.leavedSlot = leavedSlot;
    }

    public LeaveResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.leavedSlot = null;
    }

    public Slot leavedSlot() {
        return leavedSlot;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof LeaveCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                return String.format("Slot number %d is free", ((LeaveResult) result).leavedSlot().index());
            }
            else {
                if(result.exception() instanceof GateClosedException) {
                    return "Sorry, exit gate is closed currently";
                }
                else if(result.exception() instanceof SpaceClosedException) {
                    return "Sorry, parking space is closed currently";
                }
                else if (result.exception() instanceof InvalidIndexException) {
                    return "";
                }
                else if(result.exception() instanceof SlotUnoccupiedException) {
                    return "";
                }
                else {
                    result.exception().printStackTrace();
                    return "Sorry, can not serve leave command";
                }
            }
        }
    }
}
