package seedu.address.logic.parser.tag;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.tag.DeleteCommand;
import seedu.address.logic.commands.tag.EditCommand;
import seedu.address.logic.commands.tag.FindCommand;
import seedu.address.logic.commands.tag.ListCommand;
import seedu.address.logic.parser.GroupCommandsParser;
import seedu.address.logic.parser.exceptions.ParseException;

public class TagCommandsParser implements GroupCommandsParser {

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
