package parking.command.impl;

import parking.command.core.AbstractResult;
import parking.command.core.Command;
import parking.command.core.Result;
import parking.command.core.SmartResultFormatter;
import parking.space.*;

import java.util.List;

/**
 * @author zakyalvan
 */
public class RegistrationByColorInquiryResult extends AbstractResult {
    private final List<CarInfo> colorOccupants;

    RegistrationByColorInquiryResult(Command command, List<CarInfo> colorOccupants) {
        super(command);
        this.colorOccupants = colorOccupants;
    }

    public RegistrationByColorInquiryResult(Command command, Throwable throwable) {
        super(command, throwable);
        this.colorOccupants = null;
    }

    public List<CarInfo> colorOccupants() {
        return colorOccupants;
    }

    public static class Formatter implements SmartResultFormatter {
        @Override
        public boolean supports(Result result) {
            return result.command() instanceof RegistrationByColorInquiryCommand;
        }

        @Override
        public String format(Result result) {
            if(result.success()) {
                RegistrationByColorInquiryResult inquiryResult = (RegistrationByColorInquiryResult) result;

                StringBuilder outputBuilder = new StringBuilder();
                int loopIndex = 1;
                for (CarInfo colorOccupant : inquiryResult.colorOccupants()) {
                    outputBuilder.append(colorOccupant.registerNumber());
                    if(loopIndex != inquiryResult.colorOccupants().size()) {
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
