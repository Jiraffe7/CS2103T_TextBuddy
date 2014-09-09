/**
 * TextBuddy by Jireh Tan
 *
 * This program stores text in a list and allows users to manage the list.
 *
 */
package textbuddy;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextBuddy {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName;
        ArrayList<String> tempStore = new ArrayList<>();

        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("No file specified!");
            return;
        }
        fileOpen(fileName);
        fileRead(fileName, tempStore);
        introduction();
        commands(sc, fileName, tempStore);
    }

    private static void introduction() {
        System.out.println("List of commands:");
        System.out.println("add : adds text to list.");
        System.out.println("delete # : removes item specified by # from list.");
        System.out.println("clear : removes all items from list.");
        System.out.println("display : displays all items in list.");
        System.out.println("save : saves all items in list.");
        System.out.println("exit : saves and exits TextBuddy.");
        System.out.println();
    }

    // Determines the command input by user and executes the command.
    private static void commands(Scanner sc, String fileName, ArrayList<String> tempStore) {
        boolean repeat = true;

        while (repeat) {
            System.out.println("Command: ");
            String command = sc.next().toLowerCase();

            switch (command) {
                case "add":
                    String inputString = sc.nextLine().trim();
                    if (inputString.length() > 0) {
                        tempStore.add(inputString);
                        System.out.printf("Added to %s: \"%s\".\n\n", fileName, tempStore.get(tempStore.size() - 1));
                    } else {
                        System.out.printf("No input!\n\n");
                    }
                    break;
                case "delete":
                    int index = sc.nextInt() - 1;
                    if (index >= 0 && index < tempStore.size()) {
                        System.out.printf("Deleted from %s: \"%s\".\n\n", fileName, tempStore.remove(index));
                    } else {
                        System.out.printf("Item does not exist!\n\n");
                    }
                    break;
                case "clear":
                    tempStore.clear();
                    System.out.printf("All content deleted from %s.\n\n", fileName);
                    break;
                case "display":
                    if (tempStore.isEmpty()) {
                        System.out.println(fileName + " is empty.");
                        System.out.println();
                    } else {
                        for (int i = 1; i < tempStore.size() + 1; i++) {
                            System.out.printf("%d. %s\n", i, tempStore.get(i - 1));
                        }
                        System.out.println();
                    }
                    break;
                case "save":
                    System.out.printf("%s saved.", fileName);
                    break;
                case "exit":
                    repeat = false;
                    fileWrite(fileName, tempStore);
                    break;
                default:
                    System.out.printf("No command entered! Enter a command!\n\n");
            }
            fileWrite(fileName, tempStore);
        }
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
