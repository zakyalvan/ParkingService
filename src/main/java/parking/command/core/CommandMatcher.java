package parking.command.core;

/**
 * @author zakyalvan
 */
public class CommandMatcher {
    private String commandIdentifier;
    private boolean checkIdentifier;

    public void requiredIdentifier(String commandIdentifier) {
        this.commandIdentifier = commandIdentifier;
    }
    public String requiredIdentifier() {
        return commandIdentifier;
    }

    public void checkIdentifier(boolean checkIdentifier) {
        this.checkIdentifier = checkIdentifier;
    }
    public boolean checkIdentifier() {
        return checkIdentifier;
    }

    /**
     *
      * @param input
     * @return
     */
    public boolean match(Input input) {
        return false;
    }
}
