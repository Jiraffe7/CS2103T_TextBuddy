package textbuddy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class contains the text editing methods of TextBuddy.
 * 
 * @author Jireh
 */
public class TextBuddyLogic {

    private static ArrayList<String> tempStore = new ArrayList<>();

    private static final String MESSAGE_ADD = "Added to %s: \"%s\".\n\n";
    private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %s is ready for use.\n\n";
    private static final String MESSAGE_FOUND = "\nText found:\n";
    private static final String MESSAGE_CLEAR_ALL = "All items deleted from %s.\n\n";
    private static final String MESSAGE_INVALID_INDEX = "Enter integer index!\n\n";
    private static final String MESSAGE_DELETE_INVALID = "Item does not exist!\n\n";
    private static final String MESSAGE_DELETE = "Deleted from %s: \"%s\".\n\n";
    private static final String MESSAGE_NOT_FOUND = "\nText not found.\n\n";
    private static final String MESSAGE_ADD_INVALID = "No input!\n\n";
    private static final String MESSAGE_EMPTY = "%s is empty!\n\n";
    private static final String MESSAGE_ENTER_INDEX = "Enter index!\n\n";
    private static final int INPUT_ARGUMENT = 1;

    /**
     * Adds text to the file.
     *
     * @param commands
     * text to be added must be found in commands[1]
     */
    static String addText(String[] commands) {
        if (commands.length > 1) {
            String inputString = commands[INPUT_ARGUMENT];
            tempStore.add(inputString);
            return String.format(MESSAGE_ADD, TextBuddy.fileName, tempStore.get(tempStore.size() - 1));
        } else {
            return MESSAGE_ADD_INVALID;
        }
    }

    /**
     * Displays a list of text stored in the file.
     */
    static String displayText() {
        if (TextBuddyLogic.tempStore.isEmpty()) {
            return String.format(TextBuddyLogic.MESSAGE_EMPTY, TextBuddy.fileName);
        } else {
            String displayString = "";
            for (int i = 1; i < TextBuddyLogic.tempStore.size() + 1; i++) {
                displayString = displayString.concat(String.format("%d. %s\n", i, TextBuddyLogic.tempStore.get(i - 1)));
            }
            displayString = displayString.concat("\n");
            return displayString;
        }
    }

    /**
     * Sorts the list of text alphabetically.
     */
    static String sortAlphaText() {
        if (!TextBuddyLogic.tempStore.isEmpty()) {
            Collections.sort(TextBuddyLogic.tempStore);
            return "List sorted!\n\n";
        } else {
            return String.format(TextBuddyLogic.MESSAGE_EMPTY, TextBuddy.fileName);
        }
    }

    /**
     * Deletes text of a given index from the file.
     *
     * @param commands
     * index must be found in commands[1]
     */
    static String deleteText(String[] commands) {
        int index;
        if (commands.length > 1) {
            try {
                index = Integer.parseInt(commands[TextBuddyLogic.INPUT_ARGUMENT]) - 1;
            } catch (NumberFormatException ex) {
                return TextBuddyLogic.MESSAGE_INVALID_INDEX;
            }
        } else {
            return TextBuddyLogic.MESSAGE_ENTER_INDEX;
        }
        if (index >= 0 && index < TextBuddyLogic.tempStore.size()) {
            return String.format(TextBuddyLogic.MESSAGE_DELETE, TextBuddy.fileName, TextBuddyLogic.tempStore.remove(index));
        } else {
            return TextBuddyLogic.MESSAGE_DELETE_INVALID;
        }
    }

    /**
     * Searches for given text.
     *
     * @param commands
     *
     * @return
     * list of text with full or partial hits with corresponding index in list.
     */
    static String searchText(String[] commands) {
        String searchTerm = commands[1];
        Iterator<String> iterator = TextBuddyLogic.tempStore.iterator();
        int index = 0;
        String foundString = "";
        while (iterator.hasNext()) {
            String currentString = iterator.next();
            if (currentString.contains(searchTerm)) {
                foundString = foundString.concat(String.format("%d. %s\n", index + 1, currentString));
            }
            index++;
        }
        if (!foundString.isEmpty()) {
            return TextBuddyLogic.MESSAGE_FOUND.concat(foundString).concat("\n");
        } else {
            return TextBuddyLogic.MESSAGE_NOT_FOUND;
        }
    }

    /**
     * Clears all text stored in the file.
     */
    static String clearText() {
        TextBuddyLogic.tempStore.clear();
        return String.format(TextBuddyLogic.MESSAGE_CLEAR_ALL, TextBuddy.fileName);
    }

    /**
     * @return the tempStore
     */
    public static ArrayList<String> getTempStore() {
        return tempStore;
    }

}
