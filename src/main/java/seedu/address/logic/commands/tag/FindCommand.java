package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import java.util.List;

public class FindCommand extends Command {
    public static final String COMMAND_WORD = "tag find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all the contacts or sales items associated with this tag. "
            + "Note that all contacts or sales associated with this tag "
            + "will be updated automatically with the updated tag.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public FindCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tag> contactTagList = model.getAddressBook().getContactTagList();
        List<Tag> saleTagList = model.getAddressBook().getSaleTagList();

        return null;
    }
}
