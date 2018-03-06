package parking.command.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Type represent parsed string input.
 *
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public final class Input implements Serializable {
    private final CommandIdentifier identifier;
    private final List<String> arguments;
    private final String original;
    private final String normalized;

    Input(String identifier, List<String> arguments, String original, String normalized) {
        this.identifier = CommandIdentifier.by(identifier);
        this.arguments = Collections.unmodifiableList(arguments);
        this.original = original;
        this.normalized = normalized;
    }

    public CommandIdentifier identifier() {
        return identifier;
    }

    public List<String> arguments() {
        return arguments;
    }

    public String argument(Integer index) {
        return arguments.get(index);
    }

    public String original() {
        return original;
    }

    public String normalized() {
        return normalized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return Objects.equals(identifier, input.identifier) &&
                Objects.equals(normalized, input.normalized);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, normalized);
    }

    @Override
    public String toString() {
        return "Input{" +
                "identifier='" + identifier + '\'' +
                ", arguments=" + arguments +
                ", original='" + original + '\'' +
                ", normalized='" + normalized + '\'' +
                '}';
    }
}
