package textbuddy;

import org.junit.Assert;
import org.junit.Test;

/**
 * jUnit test for TextBuddy
 *
 * @author Jireh
 */
public class TextBuddyTest {

    /**
     * Test of performCommand method, of class TextBuddy.
     */
    @Test
    public void testPerformCommands() {

        testOneCommand("no command input", "No command entered! Enter a command!\n\n", "");

        //testing add function
        testOneCommand("add command without text", "No input!\n\n", "add");
        testOneCommand("adding text", "Added to null: \"test\".\n\n", "add test");
        testOneCommand("adding text", "Added to null: \"test 1 adding more text\".\n\n", "add test 1 adding more text");
        testOneCommand("adding text", "Added to null: \"test 2 adding even more text\".\n\n", "add test 2 adding even more text");
        testOneCommand("adding text", "Added to null: \"test 3 this is the final test\".\n\n", "add test 3 this is the final test");
        testOneCommand("displaying after adding", "1. test\n2. test 1 adding more text\n3. test 2 adding even more text\n4. test 3 this is the final test\n\n", "display");

        //testing delete function
        testOneCommand("delete command with no index", "Enter index!\n\n", "delete");
        testOneCommand("delete command with non integer value input for index", "Enter integer index!\n\n", "delete hello");
        testOneCommand("delete item that does not exist", "Item does not exist!\n\n", "delete 5");
        testOneCommand("delete item", "Deleted from null: \"test 3 this is the final test\".\n\n", "delete 4");
        testOneCommand("delete item that does not exist", "Item does not exist!\n\n", "delete 4");
        testOneCommand("displaying after deletion", "1. test\n2. test 1 adding more text\n3. test 2 adding even more text\n\n", "display");

        //testing clear function
        testOneCommand("clear items", "All items deleted from null.\n\n", "clear");
        testOneCommand("displaying after clear", "null is empty.\n\n", "display");

        //testing sort function
        TextBuddy.performCommand("add lemon");
        TextBuddy.performCommand("add durian");
        TextBuddy.performCommand("add banana");
        TextBuddy.performCommand("add apple");
        TextBuddy.performCommand("add orange");
        TextBuddy.performCommand("add pear");
        TextBuddy.performCommand("add kiwi");
        TextBuddy.performCommand("add mango");
        testOneCommand("sorting according to alphabetical order", "List sorted!\n\n", "sort");
        testOneCommand("displaying after sorting", "1. apple\n2. banana\n3. durian\n4. kiwi\n5. lemon\n6. mango\n7. orange\n8. pear\n\n", "display");
    }

    private void testOneCommand(String description, String expected, String command) {
        Assert.assertEquals(description, expected, TextBuddy.performCommand(command));
    }
}
