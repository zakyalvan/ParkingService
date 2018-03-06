package parking.command.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of {@link InputParser}. This implementation inspired by this SO thread
 * https://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double/366532#366532
 *
 * @author zakyalvan
 */
class DefaultInputParser implements InputParser {
    private static final String NORMALIZING_PATTERN = "\\s{1,}";

    private final Pattern matchingRegex;

    DefaultInputParser() {
        matchingRegex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
    }

    public Pattern matchingRegex() {
        return matchingRegex;
    }

    @Override
    public Input parse(String input) {
        if(input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Can not parse null or empty input string");
        }

        String normalizedInput = input.trim().replaceAll(NORMALIZING_PATTERN," ");

        List<String> matchedList = new ArrayList<>();
        Matcher regexMatcher = matchingRegex.matcher(normalizedInput);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                matchedList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                matchedList.add(regexMatcher.group(2));
            } else {
                // Add unquoted word
                matchedList.add(regexMatcher.group());
            }
        }

        if(matchedList.size() < 1) {
            throw new IllegalArgumentException("Invalid parsed length");
        }

        String commandIdentifier = matchedList.get(0).toUpperCase();
        matchedList.remove(0);

        List<String> argumentList = Collections.unmodifiableList(matchedList);

        return new Input(commandIdentifier, argumentList, input, normalizedInput);
    }
}
