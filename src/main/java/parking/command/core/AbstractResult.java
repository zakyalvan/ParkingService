package parking.command.core;

/**
 * @author zakyalvan
 */
public abstract class AbstractResult implements Result {
    private final Command command;
    private final Throwable throwable;

    public AbstractResult(Command command) {
        this.command = command;
        this.throwable = null;
    }
    public AbstractResult(Command command, Throwable throwable) {
        this.command = command;
        this.throwable = throwable;
    }

    @Override
    public Command command() {
        return command;
    }

    @Override
    public boolean success() {
        return throwable == null;
    }

    @Override
    public Throwable exception() {
        return throwable;
    }
}
