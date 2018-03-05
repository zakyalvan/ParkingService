package parking.command.core;

import parking.command.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author zakyalvan
 */
public class CommandDispatcher {
    private DelegatingCommandTranslator commandTranslators;
    private DelegatingResultFormatter resultFormats;

    public CommandDispatcher(Consumer<List<SmartCommandTranslator>> translatorsCustomizer, Consumer<List<SmartResultFormatter>> formatsCustomizer) {
        if(translatorsCustomizer != null) {
            List<SmartCommandTranslator> delegateTranslators = new ArrayList<>();
            translatorsCustomizer.accept(delegateTranslators);
            this.commandTranslators = new DelegatingCommandTranslator(delegateTranslators);
        }
        if(formatsCustomizer != null) {
            List<SmartResultFormatter> delegateFormats = new ArrayList<>();
            formatsCustomizer.accept(delegateFormats);
            this.resultFormats = new DelegatingResultFormatter(delegateFormats);
        }
    }

    public String dispatch(String input) {
        try {
            Command command = commandTranslators.translate(input);
            Result result = command.execute();
            return resultFormats.format(result);
        }
        catch(TranslatorUnavailableException e) {
            return "No command translator : ".concat(e.commandInput());
        }
        catch(CommandTranslationException e) {
            return "Command translation error : ".concat(e.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
            return "Unhandled error, see stack traces above";
        }
    }

    /**
     * Convenience factory method.
     *
     * Create command dispatcher with default {@link CommandTranslator} and {@link ResultFormatter}
     * for all defined command.
     *
     * @return
     */
    public static CommandDispatcher defaultInstance() {
        Consumer<List<SmartCommandTranslator>> translateCustomizer = translators -> {
            translators.add(new CreateSpaceCommand.Translator());
            translators.add(new ParkCommand.Translator());
            translators.add(new LeaveCommand.Translator());
            translators.add(new RegistrationByColorInquiryCommand.Translator());
            translators.add(new SlotIndexByColorInquiryCommand.Translator());
            translators.add(new SlotIndexByRegistrationInquiryCommand.Translator());
            translators.add(new StatusInquiryCommand.Translator());
        };

        Consumer<List<SmartResultFormatter>> formatCustomizers = formats -> {
            formats.add(new CreateSpaceResult.Formatter());
            formats.add(new ParkResult.Formatter());
            formats.add(new LeaveResult.Formatter());
            formats.add(new RegistrationByColorInquiryResult.Formatter());
            formats.add(new SlotIndexByColorInquiryResult.Formatter());
            formats.add(new SlotIndexByRegistrationInquiryResult.Formatter());
            formats.add(new StatusInquiryResult.Formatter());
        };

        return new CommandDispatcher(translateCustomizer, formatCustomizers);
    }
}