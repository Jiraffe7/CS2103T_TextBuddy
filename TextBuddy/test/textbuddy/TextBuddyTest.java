/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textbuddy;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Jireh
 */
public class TextBuddyTest {

    /**
     * Test of determineCommand method, of class TextBuddy.
     */
    @Test
    public void testDetermineCommands() {
        testOneCommand("adding text", "Added to null: \"test\".\n\n", "add test");
        testOneCommand("adding text", "Added to null: \"test 1 adding more text\".\n\n", "add test 1 adding more text");
        testOneCommand("adding text", "Added to null: \"test 2 adding even more text\".\n\n", "add test 2 adding even more text");
        testOneCommand("adding text", "Added to null: \"test 3 this is the final test\".\n\n", "add test 3 this is the final test");
        testOneCommand("displaying", "1. test\n2. test 1 adding more text\n3. test 2 adding even more text\n4. test 3 this is the final test\n\n", "display");
    }

    private void testOneCommand(String description, String expected, String command) {
        Assert.assertEquals(description, expected, TextBuddy.determineCommand(command));
    }
}
