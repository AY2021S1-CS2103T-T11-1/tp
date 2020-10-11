package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag based on its displayed index in the tag list and updates all
 * items associated with this tag.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "tag delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the index number used in the displayed reminder list. "
            + "Note that all associations with this tag will be cleared.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Contact tags will be displayed in front of Sale tags.
        List<Tag> contactTagList = model.getAddressBook().getContactTagList();
        List<Tag> saleTagList = model.getAddressBook().getSaleTagList();

        if (targetIndex.getOneBased() > contactTagList.size() + saleTagList.size() || targetIndex.getOneBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        Tag tagToDelete;
        if (targetIndex.getOneBased() > contactTagList.size()) {
            tagToDelete = saleTagList.get(targetIndex.getOneBased());
            model.deleteSaleTag(tagToDelete);
        } else {
            tagToDelete = contactTagList.get(targetIndex.getZeroBased());
            model.deleteContactTag(tagToDelete);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        return (obj instanceof DeleteCommand) && targetIndex.equals(((DeleteCommand) obj).targetIndex);
    }
}
