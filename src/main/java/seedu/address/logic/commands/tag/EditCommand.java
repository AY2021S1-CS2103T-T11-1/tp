package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Edits a tag based on its displayed index in the tag list and updates all
 * items associated with this tag.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "tag edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tag identified by the index number used in the displayed tag list. "
            + "Note that all contacts or sales associated with this tag "
            + "will be updated automatically with the updated tag.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in StonksBook.";

    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited tag %s to: %s";

    private final Index targetIndex;
    private final EditTagDescriptor editTagDescriptor;

    /**
     *
     * @param targetIndex of the tag in the tag list to edit.
     * @param editTagDescriptor details to edit the tag with.
     */
    public EditCommand(Index targetIndex, EditTagDescriptor editTagDescriptor) {
        this.targetIndex = targetIndex;
        this.editTagDescriptor = editTagDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tag> contactTagList = model.getAddressBook().getContactTagList();
        List<Tag> saleTagList = model.getAddressBook().getSaleTagList();

        if (targetIndex.getOneBased() > contactTagList.size() + saleTagList.size() || targetIndex.getOneBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        Tag tagToEdit;
        Tag editedTag;
        if (targetIndex.getOneBased() > contactTagList.size()) {
            tagToEdit = saleTagList.get(targetIndex.getZeroBased());
            editedTag = createEditedTag(tagToEdit, editTagDescriptor);

            if (!tagToEdit.isSameTag(editedTag) && model.hasSaleTag(editedTag)) {
                throw new CommandException(MESSAGE_DUPLICATE_TAG);
            }
            model.editSaleTag(tagToEdit, editedTag);
        } else {
            tagToEdit = contactTagList.get(targetIndex.getZeroBased());
            editedTag = createEditedTag(tagToEdit, editTagDescriptor);

            if (!tagToEdit.isSameTag(editedTag) && model.hasContactTag(editedTag)) {
                throw new CommandException(MESSAGE_DUPLICATE_TAG);
            }
            model.editContactTag(tagToEdit, editedTag);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, tagToEdit, editedTag));
    }

    /**
     * Creates and returns a {@code Tag} with the details of {@code tagToEdit}
     * edited with {@code editTagDescriptor}.
     */
    private static Tag createEditedTag(Tag tagToEdit,
                                       EditTagDescriptor editTagDescriptor) {
        assert tagToEdit != null;

        String updatedTagName = editTagDescriptor.getTagName().orElse(tagToEdit.getTagName());

        return new Tag(updatedTagName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editTagDescriptor.equals(e.editTagDescriptor);
    }

    /**
     * Stores the details to edit the tag with.
     */
    public static class EditTagDescriptor {
        private String tagName;

        public EditTagDescriptor() {}

        /**
         * Instantiates a EditTagDescriptor object by copying another EditTagDescriptor {@code toCopy}.
         */
        public EditTagDescriptor(EditTagDescriptor toCopy) {
            setTagName(toCopy.tagName);
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public Optional<String> getTagName() {
            return Optional.ofNullable(tagName);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTagDescriptor)) {
                return false;
            }

            // state check
            EditTagDescriptor e =
                    (EditTagDescriptor) other;

            return getTagName().equals(e.getTagName());
        }
    }
}
