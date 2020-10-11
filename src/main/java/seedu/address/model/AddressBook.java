package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueContactTagList;
import seedu.address.model.tag.UniqueSaleTagList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson comparison).
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueContactTagList contactTags;
    private final UniqueSaleTagList saleTags;
    private final UniqueAppointmentList appointments;
    private final UniqueReminderList reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     *
     */
    {
        persons = new UniquePersonList();
        contactTags = new UniqueContactTagList();
        saleTags = new UniqueSaleTagList();
        appointments = new UniqueAppointmentList();
        reminders = new UniqueReminderList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the  contents of the tag list with {@code contactTags}.
     * {@code contactTags} must not contain duplicate contactTags.
     */
    public void setTags(List<Tag> contactTags) {
        this.contactTags.setTags(contactTags);
    }

    /**
     * Replaces the contents of the appointments list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Replaces the contents of the reminders list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTags(newData.getContactTagList());
        setAppointments(newData.getAppointmentList());
        setReminders(newData.getReminderList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        for (Tag t : p.getTags()) {
            contactTags.add(t);
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        for (Tag t : editedPerson.getTags()) {
            contactTags.add(t);
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// tag-level operations

    /**
     * Returns true if a contact tag with the same identity as {@code tag} exists in StonksBook.
     */
    public boolean hasContactTag(Tag tag) {
        requireNonNull(tag);
        return contactTags.contains(tag);
    }

    /**
     * Returns true if a sale tag with the same identity as {@code tag} exists in StonksBOok.
     */
    public boolean hasSaleTag(Tag tag) {
        requireNonNull(tag);
        return saleTags.contains(tag);
    }

    /**
     * Adds the specified contact tag to StonksBook.
     * If the tag already exists in the contact tag list, no action will be performed.
     */
    public void addContactTag(Tag tag) {
        contactTags.add(tag);
    }

    /**
     * Adds the specified sale tag to StonksBook.
     * If the tag already exists in the sale tag list, no action will be performed.
     */
    public void addSaleTag(Tag tag) {
        saleTags.add(tag);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     *
     */
    public void removeContactTag(Tag key) {
        contactTags.remove(key);
        persons.removeContactTag(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     *
     */
    public void removeSaleTag(Tag key) {
        saleTags.remove(key);
        persons.removeSaleTag(key);
    }

    /**
     * Replaces the given {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the contact tag list.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in StonksBook.
     */
    public void editContactTag(Tag target, Tag editedTag) {
        contactTags.setTag(target, editedTag);
        persons.setContactTag(target, editedTag);
    }

    /**
     * Replaces the given {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the sale tag list.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in StonksBook.
     */
    public void editSaleTag(Tag target, Tag editedTag) {
        saleTags.setTag(target, editedTag);
        // TODO: edit sale tag once sale model is implemented
    }

    /**
     * List all the existing tags in StonksBook.
     */
    public String listTags() {
        // TODO: append sale tags once sale model is implemented
        return contactTags.asUnmodifiableObservableList().toString();
    }

    /**
     * Re-order all the existing tags in StonksBook.
     */
    public void sortTags() {
        contactTags.sort();
    }

    //// appointment-level operations

    /**
     * Returns true if an equivalent appointment exists in the address book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Adds an appointment to the address book.
     * The appointment must not already exist in the address book.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    //// reminder-level operations

    /**
     * Returns true if an equivalent reminder exists in StonksBook.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a reminder to StonksBook.
     * The reminder must not already exist in StonksBook.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getContactTagList() {
        return contactTags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getSaleTagList() {
        return saleTags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;

        return persons.equals(otherAddressBook.persons) && reminders.equals(otherAddressBook.reminders)
                && appointments.equals(otherAddressBook.appointments)
                && contactTags.equals(((AddressBook) other).contactTags);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
