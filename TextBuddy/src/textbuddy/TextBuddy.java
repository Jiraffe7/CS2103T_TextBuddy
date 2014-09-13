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

        ADD, DELETE, CLEAR, DISPLAY, EXIT, INVALID
    };

    public static void main(String[] args) {

        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("No file specified!");
            return;
        }
        fileOpen(fileName);
        fileRead(fileName, tempStore);
        printIntroduction();

        while (true) {
            System.out.println("Command: ");
            System.out.println(performCommand(sc.nextLine()));
            fileWrite(fileName, tempStore);
        }
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

    // Determines the command input by user and executes the command.
    public static String performCommand(String line) {
        String[] commands;

        if (line.length() > 0) {
            commands = splitCommandAndArguments(line);
        } else {
            return "No command entered! Enter a command!\n\n";
        }

        COMMAND_TYPE command = getCommand(commands);

        switch (command) {
            case ADD:
                if (commands.length > 1) {
                    String inputString = commands[1];
                    tempStore.add(inputString);
                    return String.format("Added to %s: \"%s\".\n\n", fileName, tempStore.get(tempStore.size() - 1));
                } else {
                    return "No input!\n\n";
                }
            case DELETE:
                int index;

                if (commands.length > 1) {
                    try {
                        index = Integer.parseInt(commands[1]) - 1;
                    } catch (NumberFormatException ex) {
                        return "Enter integer index!\n\n";
                    }
                } else {
                    return "Enter index!\n\n";
                }
                if (index >= 0 && index < tempStore.size()) {
                    return String.format("Deleted from %s: \"%s\".\n\n", fileName, tempStore.remove(index));
                } else {
                    return "Item does not exist!\n\n";
                }
            case CLEAR:
                tempStore.clear();
                return String.format("All items deleted from %s.\n\n", fileName);
            case DISPLAY:
                if (tempStore.isEmpty()) {
                    return String.format("%s is empty.\n\n", fileName);
                } else {
                    String displayString = "";
                    for (int i = 1; i < tempStore.size() + 1; i++) {
                        displayString = displayString.concat(String.format("%d. %s\n", i, tempStore.get(i - 1)));
                    }
                    displayString = displayString.concat("\n");
                    return displayString;
                }
            case EXIT:
                fileWrite(fileName, tempStore);
                System.exit(0);
                break;
            default:
                return "No command entered! Enter a command!\n\n";
        }
        return null;
    }

    private static COMMAND_TYPE getCommand(String[] commands) {

        if (commands[0].equalsIgnoreCase("add")) {
            return COMMAND_TYPE.ADD;
        } else if (commands[0].equalsIgnoreCase("delete")) {
            return COMMAND_TYPE.DELETE;
        } else if (commands[0].equalsIgnoreCase("clear")) {
            return COMMAND_TYPE.CLEAR;
        } else if (commands[0].equalsIgnoreCase("display")) {
            return COMMAND_TYPE.DISPLAY;
        } else if (commands[0].equalsIgnoreCase("exit")) {
            return COMMAND_TYPE.EXIT;
        } else {
            return COMMAND_TYPE.INVALID;
        }
    }

    private static String[] splitCommandAndArguments(String line) {
        String[] commands;
        commands = line.trim().split("\\s+", 2);
        return commands;
    }

    // Saves items in list into specified file.
    private static void fileWrite(String fileName, ArrayList<String> tempStore) {
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

    // Determines if file name is valid, then creates file if it does not exist.
    private static void fileOpen(String fileName) {
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
        System.out.printf("\nWelcome to TextBuddy. %s is ready for use.\n\n", fileName);
    }

    // Copies text from file into list.
    private static void fileRead(String fileName, ArrayList<String> tempStore) {
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
}
