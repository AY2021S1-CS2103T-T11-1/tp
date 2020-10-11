package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook.
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a contact tag with the same name {@code tag} exists in StonksBook.
     */
    boolean hasContactTag(Tag tag);

    /**
     * Returns true if a sale tag with the same name {@code tag} exists in StonksBook.
     */
    boolean hasSaleTag(Tag tag);

    /**
     * Adds the given tag.
     * {@code tag} must not already exist in StonksBook.
     */
    void addContactTag(Tag tag);

    /**
     * Replaces the given {@code tag} with {@code editedTag}.
     * {@code target} must exist in the contact tag list.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in StonksBook.
     */
    void editContactTag(Tag target, Tag editedTag);

    /**
     * Replaces the given {@code tag} with {@code editedTag}.
     * {@code target} must exist in the sale tag list.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in StonksBook.
     */
    void editSaleTag(Tag target, Tag editedTag);

    /**
     * Deletes the given tag from the contact tag list.
     * The tag must exist in StonksBook.
     */
    void deleteContactTag(Tag target);

    /**
     * Deletes the given tag from the sale tag list.
     * The tag must exist in StonksBook.
     */
    void deleteSaleTag(Tag target);

    /**
     * Lists all existing tags.
     */
    String listTags();

    /**
     * Lists all contacts associated with {@code target} tag.
     */
    int findByContactTag(Tag target);

    /**
     * Lists all sales associated with {@code target} tag.
     */
    int findBySaleTag(Tag target);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
    .
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the sorted person list.
     */
    ObservableList<Person> getSortedPersonList();

    /**
     * Updates the comparator of the sorted person list to sort by the given {@code comparator}.
     */
    void updateSortedPersonList(Comparator<Person> comparator);

    /**
     * Returns true if an appointment with same fields as {@code appointment} exists in StonksBook.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Deletes the given appointment.
     * {@code appointment} must exist in StonksBook.
     */
    void deleteAppointment(Appointment target);

    /**
     * Adds the given appointment.
     * {@code appointment} must not already exist in StonksBook.
     */
    void addAppointment(Appointment appointment);

    /**
     * Returns true if a reminder with the same fields {@code reminder} exists in StonksBook.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Deletes the given reminder.
     * The reminder must exist in StonksBook.
     */
    void deleteReminder(Reminder target);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in StonksBook.
     */
    void addReminder(Reminder reminder);
}
