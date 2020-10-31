package seedu.address.logic.commands.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_CALL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_CALL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookInReverse;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.person.TypicalPersons.GEORGE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PurgeCommand;
import seedu.address.logic.commands.meeting.EditCommand.EditMeetingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Message;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.TypicalDates;
import seedu.address.testutil.TypicalDurations;
import seedu.address.testutil.meeting.EditMeetingDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookInReverse(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        Meeting editedMeeting = new Meeting(GEORGE, new Message(VALID_MESSAGE_CALL_AMY), TypicalDates.TYPICAL_DATE_3,
                TypicalDurations.TYPICAL_DURATION_ONE_HOUR);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                // George appears as the 6th item
                .withContactIndex(Index.fromZeroBased(6))
                .withMessage(VALID_MESSAGE_CALL_AMY)
                .withStartDate(TypicalDates.TYPICAL_DATE_3)
                .withDuration(TypicalDurations.TYPICAL_DURATION_ONE_HOUR)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        expectedModel.setMeeting(model.getSortedMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        Index indexLastMeeting = Index.fromOneBased(model.getSortedMeetingList().size());
        Meeting lastMeeting = model.getSortedMeetingList().get(indexLastMeeting.getZeroBased());

        Meeting editedMeeting =
                new Meeting(lastMeeting.getPerson(), lastMeeting.getMessage(), TypicalDates.TYPICAL_DATE_3,
                        TypicalDurations.TYPICAL_DURATION_TWO_DAYS);

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withStartDate(TypicalDates.TYPICAL_DATE_3)
                .withDuration(TypicalDurations.TYPICAL_DURATION_TWO_DAYS)
                .build();

        EditCommand editCommand = new EditCommand(indexLastMeeting, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        expectedModel.setMeeting(lastMeeting, editedMeeting);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        EditCommand editCommand = new EditCommand(INDEX_FIRST_ITEM, new EditMeetingDescriptor());
        Meeting editedMeeting = model.getSortedMeetingList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMeetingUnfilteredList_failure() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        Meeting firstMeeting = model.getSortedMeetingList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withContactIndex(Index.fromZeroBased(model.getSortedPersonList().indexOf(firstMeeting.getPerson())))
                .withStartDate(firstMeeting.getStartDate())
                .withMessage(firstMeeting.getMessage().message)
                .withDuration(firstMeeting.getDuration())
                .build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_invalidMeetingIndexUnfilteredList_failure() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        Index outOfBoundIndex = Index.fromOneBased(model.getSortedMeetingList().size() + 1);
        EditMeetingDescriptor descriptor =
                new EditMeetingDescriptorBuilder().withMessage(VALID_MESSAGE_CALL_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditMeetingDescriptor descriptor =
                new EditMeetingDescriptorBuilder()
                        .withMessage(VALID_MESSAGE_CALL_AMY)
                        .withContactIndex(INDEX_SECOND_ITEM)
                        .withStartDate(TypicalDates.TYPICAL_DATE_3)
                        .withDuration(TypicalDurations.TYPICAL_DURATION_ONE_HOUR)
                        .build();
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ITEM, descriptor);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptor(descriptor);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new PurgeCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ITEM, descriptor)));

        // different descriptor -> returns false
        EditMeetingDescriptor diffDescriptor =
                new EditMeetingDescriptorBuilder()
                        .withStartDate(TypicalDates.TYPICAL_DATE_2)
                        .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ITEM, diffDescriptor)));
    }
}
