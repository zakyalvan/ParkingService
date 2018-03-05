package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.*;

/**
 * @author zakyalvan
 */
public class CreateSpaceResult extends AbstractResult {
    private final Space createdSpace;

    public CreateSpaceResult(Command command, Space createdSpace) {
        super(command);
        this.createdSpace = createdSpace;
    }

    public CreateSpaceResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.createdSpace = null;
    }

    public Space createdSpace() {
        return createdSpace;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof CreateSpaceCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                return String.format("Created a parking lot with %d slots", ((CreateSpaceResult) result).createdSpace().capacity());
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
