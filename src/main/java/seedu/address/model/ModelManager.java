package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;

    private final UserPrefs userPrefs;

    private final FilteredList<Person> filteredPersons;

    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.sortedPersons = new SortedList<>(this.filteredPersons);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return this.userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return this.userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.addressBook.hasPerson(person);
    }

    @Override
    public boolean hasContactTag(Tag tag) {
        requireNonNull(tag);
        return addressBook.hasContactTag(tag);
    }

    @Override
    public boolean hasSaleTag(Tag tag) {
        requireNonNull(tag);
        return addressBook.hasSaleTag(tag);
    }

    @Override
    public void addContactTag(Tag tag) {
        addressBook.addContactTag(tag);
    }

    @Override
    public void editContactTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        addressBook.editContactTag(target, editedTag);
    }

    @Override
    public void editSaleTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        addressBook.editSaleTag(target, editedTag);
    }

    @Override
    public void deleteContactTag(Tag target) {
        requireNonNull(target);
        addressBook.removeContactTag(target);
    }

    @Override
    public void deleteSaleTag(Tag target) {
        requireNonNull(target);
        addressBook.removeSaleTag(target);
    }

    @Override
    public void deletePerson(Person target) {
        this.addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        this.addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        this.addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return addressBook.hasAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        addressBook.removeAppointment(target);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        addressBook.addAppointment(appointment);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return addressBook.hasReminder(reminder);
    }

    @Override
    public void deleteReminder(Reminder target) {
        addressBook.removeReminder(target);
    }

    @Override
    public void addReminder(Reminder reminder) {
        addressBook.addReminder(reminder);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}.
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return this.filteredPersons;
    }

    /**
     * Updates the predicate used to filter person list and
     * set comparator for sorted list to be null.
     *
     * @param predicate predicate to filter person list
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        this.filteredPersons.setPredicate(predicate);
        this.sortedPersons.setComparator(null);
    }


    //=========== Sorted Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return this.sortedPersons;
    }

    /**
     * Updates the comparator to sort the person list.
     *
     * @param comparator comparator for sorting the person list
     */
    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        this.sortedPersons.setComparator(comparator);
    }


    @Override
    public String listTags() {
        return addressBook.listTags();
    }

    @Override
    public void findByContactTag(Tag target) {

    }

    @Override
    public void findBySaleTag(Tag target) {

    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return this.addressBook.equals(other.addressBook)
            && this.userPrefs.equals(other.userPrefs)
            && this.filteredPersons.equals(other.filteredPersons)
                && this.sortedPersons.equals(other.sortedPersons);
    }

}
