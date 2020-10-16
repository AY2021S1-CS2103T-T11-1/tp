package seedu.address.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import seedu.address.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    // The shae of red that is used to indicate overdue reminders
    private static final String RED = "#ff0266";

    private static final String OVERDUE_DATE_STYLE_CLASS = "overdue-date";
    private static final String FXML = "ReminderListCard.fxml";

    public final Reminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label message;
    @FXML
    private Label scheduledDate;
    @FXML
    private Label personName;
    @FXML
    private FontIcon calendarIcon;

    /**
     * Creates a {@code ReminderCard} with the given {@code Reminder} and index to display.
     */
    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        message.setText(reminder.getMessage());
        scheduledDate.setText(reminder.getFormattedScheduledDate());
        personName.setText(reminder.getPerson().getName().fullName);

        //  TODO: Test that the colour updates properly when a reminder is updated
        if (reminder.isOverdue()) {
            setStyleToIndicateOverdue();
        }
    }

    /**
     * Sets the reminder card style to indicate an overdue reminder.
     */
    private void setStyleToIndicateOverdue() {
        calendarIcon.setIconColor(Paint.valueOf(RED));

        ObservableList<String> scheduledDateStyleClass = scheduledDate.getStyleClass();
        if (!scheduledDateStyleClass.contains(OVERDUE_DATE_STYLE_CLASS)) {
            scheduledDateStyleClass.add(OVERDUE_DATE_STYLE_CLASS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}
