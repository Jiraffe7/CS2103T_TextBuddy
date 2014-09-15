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
import java.util.Scanner;

/**
 * This program stores text in a list and allows users to manage the list.
 *
 * @author Jireh Tan
 */
public class TextBuddy {

    private static Scanner sc = new Scanner(System.in);
    private static BufferedReader reader;
    private static BufferedWriter writer;

    static String fileName;

    /**
     * These are the locations of command and command argument
     * stored in the commands[] array.
     */
    enum COMMAND_TYPE {

        ADD, DELETE, CLEAR, DISPLAY, EXIT, INVALID, SORT, SEARCH
    };

    private static final int INPUT_COMMAND = 0;
    private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %s is ready for use.\n\n";
    private static final String MESSAGE_NO_COMMAND = "No command entered! Enter a command!\n\n";

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
                return TextBuddyLogic.addText(commands);
            case DELETE:
                return TextBuddyLogic.deleteText(commands);
            case CLEAR:
                return TextBuddyLogic.clearText();
            case DISPLAY:
                return TextBuddyLogic.displayText();
            case EXIT:
                System.exit(0);
                break;
            case SORT:
                return TextBuddyLogic.sortAlphaText();
            case SEARCH:
                return TextBuddyLogic.searchText(commands);
            case INVALID:
                return MESSAGE_NO_COMMAND;
            default:
                return MESSAGE_NO_COMMAND;
        }
        return null;
    }

    /**
     * Determines if file name is valid, then creates file if it does not exist.
     */
    protected static void openFile() {
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
    protected static void readFile() {
        initialiseBufferedReader();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                TextBuddyLogic.getTempStore().add(line);
            }
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        } finally {
            terminateBufferedReader();
        }
    }

    /**
     * Saves items in list into specified file.
     */
    protected static void writeFile() {
        initialiseBufferedWriter();
        try {
            for (String line : TextBuddyLogic.getTempStore()) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        } finally {
            terminateBufferedWriter();
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
        } else if (commands[INPUT_COMMAND].equalsIgnoreCase("search")) {
            return COMMAND_TYPE.SEARCH;
        } else {
            return COMMAND_TYPE.INVALID;
        }
    }

    private static String[] splitCommandAndArguments(String line) {
        String[] commands;
        commands = line.trim().split("\\s+", 2);
        return commands;
    }

    private static void initialiseBufferedWriter() {
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }

    private static void terminateBufferedWriter() {
        try {
            writer.close();
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }

    private static void initialiseBufferedReader() {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException: " + ex.getMessage());
        }
    }

    private static void terminateBufferedReader() {
        try {
            reader.close();
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }

    private static void printIntroduction() {
        System.out.println("List of commands:");
        System.out.printf("%-10s : %s", "add", "adds text to list.\n");
        System.out.printf("%-10s : %s", "delete #", "removes item specified by # from list.\n");
        System.out.printf("%-10s : %s", "clear", "removes all items from list.\n");
        System.out.printf("%-10s : %s", "display", "displays all items in list.\n");
        System.out.printf("%-10s : %s", "sort", "sorts all items in list.\n");
        System.out.printf("%-10s : %s", "search", "searches for all occurences in list.\n");
        System.out.printf("%-10s : %s", "exit", "saves and exits TextBuddy.\n");
        System.out.println();
    }
}
