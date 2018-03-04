package parking.command.impl;

import parking.command.core.*;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class ParkResult extends AbstractResult {
    private final Slot occupied;

    public ParkResult(Command command, Slot occupied) {
        super(command);
        this.occupied = occupied;
    }

    public ParkResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.occupied = null;
    }

    public Slot occupied() {
        return occupied;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof ParkCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                return String.format("Allocated slot number: %d", ((ParkResult) result).occupied().index());
            }
            else {
                if(result.exception() instanceof GateClosedException) {
                    return "Sorry, entrance gate is closed currently";
                }
                else if(result.exception() instanceof SpaceClosedException) {
                    return "Sorry, parking space is closed currently";
                }
                else if(result.exception() instanceof FullyOccupiedException) {
                    return "Sorry, parking lot is full";
                }
                else if(result.exception() instanceof AlreadyInsideException) {
                    return String.format("Sorry, car with registration number '%s', already inside", ((ParkCommand) result.command()).occupant().registerNumber());
                }
                else {
                    result.exception().printStackTrace();
                    return "Sorry, can not serve park command";
                }
            }
        }
    }
}
