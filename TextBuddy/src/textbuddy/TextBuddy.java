package textbuddy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This program stores text in a list and allows users to manage the list.
 *
 * @author Jireh Tan
 */
public class TextBuddy {

    private static Scanner sc = new Scanner(System.in);

    private static String fileName;
    private static ArrayList<String> tempStore = new ArrayList<>();

    enum COMMAND_TYPE {

        ADD, DELETE, CLEAR, DISPLAY, EXIT, INVALID, SORT
    };

    private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %s is ready for use.\n\n";
    private static final String MESSAGE_DELETE_INVALID = "Item does not exist!\n\n";
    private static final String MESSAGE_DELETE = "Deleted from %s: \"%s\".\n\n";
    private static final String MESSAGE_ADD_INVALID = "No input!\n\n";
    private static final String MESSAGE_ADD = "Added to %s: \"%s\".\n\n";
    private static final String MESSAGE_NO_COMMAND = "No command entered! Enter a command!\n\n";
    private static final String MESSAGE_EMPTY = "%s is empty.\n\n";
    private static final String MESSAGE_ENTER_INDEX = "Enter index!\n\n";
    private static final String MESSAGE_INVALID_INDEX = "Enter integer index!\n\n";
    private static final String MESSAGE_CLEAR_ALL = "All items deleted from %s.\n\n";

    /**
     * These are the locations of command and command argument
     * stored in the commands[] array.
     */
    private static final int INPUT_COMMAND = 0;
    private static final int INPUT_ARGUMENT = 1;

    public static void main(String[] args) {

        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("No file specified!");
            return;
        }
        openFile();
        readFile();
        printIntroduction();

        while (true) {
            System.out.println("Command: ");
            System.out.println(performCommand(sc.nextLine()));
            writeFile();
        }
    }

    /**
     * Determines the command input by user and executes the command.
     *
     * @param line
     * is the full string entered by the user.
     */
    public static String performCommand(String line) {
        String[] commands;

        if (line.length() > 0) {
            commands = splitCommandAndArguments(line);
        } else {
            return MESSAGE_NO_COMMAND;
        }

        COMMAND_TYPE command = getCommand(commands);

        switch (command) {
            case ADD:
                return addText(commands);
            case DELETE:
                return deleteText(commands);
            case CLEAR:
                return clearText();
            case DISPLAY:
                return displayText();
            case EXIT:
                System.exit(0);
                break;
            case SORT:
                return sortAlphaText();
            case INVALID:
                return MESSAGE_NO_COMMAND;
            default:
                return MESSAGE_NO_COMMAND;
        }
        return null;
    }

    private static String sortAlphaText() {
        Collections.sort(tempStore);
        return "List sorted!\n\n";
    }

    /**
     * Displays a list of text stored in the file.
     */
    private static String displayText() {
        if (tempStore.isEmpty()) {
            return String.format(MESSAGE_EMPTY, fileName);
        } else {
            String displayString = "";
            for (int i = 1; i < tempStore.size() + 1; i++) {
                displayString = displayString.concat(String.format("%d. %s\n", i, tempStore.get(i - 1)));
            }
            displayString = displayString.concat("\n");
            return displayString;
        }
    }

    /**
     * Clears all text stored in the file.
     */
    private static String clearText() {
        tempStore.clear();
        return String.format(MESSAGE_CLEAR_ALL, fileName);
    }

    /**
     * Deletes text of a given index from the file.
     *
     * @param commands
     * index must be found in commands[1]
     */
    private static String deleteText(String[] commands) {
        int index;

        if (commands.length > 1) {
            try {
                index = Integer.parseInt(commands[INPUT_ARGUMENT]) - 1;
            } catch (NumberFormatException ex) {
                return MESSAGE_INVALID_INDEX;
            }
        } else {
            return MESSAGE_ENTER_INDEX;
        }
        if (index >= 0 && index < tempStore.size()) {
            return String.format(MESSAGE_DELETE, fileName, tempStore.remove(index));
        } else {
            return MESSAGE_DELETE_INVALID;
        }
    }

    /**
     * Adds text to the file.
     *
     * @param commands
     * text to be added must be found in commands[1]
     */
    private static String addText(String[] commands) {
        if (commands.length > 1) {
            String inputString = commands[INPUT_ARGUMENT];
            tempStore.add(inputString);
            return String.format(MESSAGE_ADD, fileName, tempStore.get(tempStore.size() - 1));
        } else {
            return MESSAGE_ADD_INVALID;
        }
    }

    /**
     * Saves items in list into specified file.
     */
    private static void writeFile() {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
        try {
            for (String line : tempStore) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                System.err.println("IOException: " + ex.getMessage());
            }
        }
    }

    /**
     * Determines if file name is valid, then creates file if it does not exist.
     */
    private static void openFile() {
        File f;
        Path currentDir = Paths.get(fileName);
        String currentDirString = currentDir.toAbsolutePath().toString();

        f = new File(currentDirString);
        if (f.exists() && !f.isDirectory()) {

        } else {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.err.println("IOException: " + ex.getMessage());
            }
        }
        System.out.printf(MESSAGE_WELCOME, fileName);
    }

    /**
     * Copies text from file into list.
     */
    private static void readFile() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException: " + ex.getMessage());
        }
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                tempStore.add(line);
            }
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.err.println("IOException: " + ex.getMessage());
            }
        }
    }

    private static COMMAND_TYPE getCommand(String[] commands) {

        if (commands[INPUT_COMMAND].equalsIgnoreCase("add")) {
            return COMMAND_TYPE.ADD;
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("delete")) {
            return COMMAND_TYPE.DELETE;
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("clear")) {
            return COMMAND_TYPE.CLEAR;
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("display")) {
            return COMMAND_TYPE.DISPLAY;
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("exit")) {
            return COMMAND_TYPE.EXIT;
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("sort")) {
            return COMMAND_TYPE.SORT;
        } else {
            return COMMAND_TYPE.INVALID;
        }
    }

    private static String[] splitCommandAndArguments(String line) {
        String[] commands;
        commands = line.trim().split("\\s+", 2);
        return commands;
    }

    private static void printIntroduction() {
        System.out.println("List of commands:");
        System.out.printf("%-10s : %s", "add", "adds text to list.\n");
        System.out.printf("%-10s : %s", "delete #", "removes item specified by # from list.\n");
        System.out.printf("%-10s : %s", "clear", "removes all items from list.\n");
        System.out.printf("%-10s : %s", "display", "displays all items in list.\n");
        System.out.printf("%-10s : %s", "save", "saves all items to list.\n");
        System.out.printf("%-10s : %s", "exit", "saves and exits TextBuddy.\n");
        System.out.println();
    }
}
