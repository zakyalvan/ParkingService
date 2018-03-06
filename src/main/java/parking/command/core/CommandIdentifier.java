package parking.command.core;

import java.util.Objects;

/**
 * Type for identifying a {@link Command}.
 *
 * @author zakyalvan
 */
public final class CommandIdentifier {
    private final String identifier;

    private CommandIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean equalsIgnoreCase(String other) {
        if(other == null || other.trim().isEmpty()) {
            return false;
        }
        return this.identifier.equalsIgnoreCase(other.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandIdentifier that = (CommandIdentifier) o;
        return Objects.equals(identifier.toUpperCase(), that.identifier.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier.toUpperCase());
    }

    @Override
    public String toString() {
        return identifier.toUpperCase();
    }

    /**
     *
     * @param identifier
     * @return
     */
    public static CommandIdentifier by(String identifier) {
        if(identifier == null || identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("Command can not identified by null or empty string");
        }
        return new CommandIdentifier(identifier);
    }
}
