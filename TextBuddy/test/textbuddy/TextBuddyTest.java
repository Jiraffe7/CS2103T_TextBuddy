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
        testOneCommand("adding text", "Added to null: \"1\".\n\n", "add 1");
        testOneCommand("adding text", "Added to null: \"2\".\n\n", "add 2");
        testOneCommand("adding text", "Added to null: \"3\".\n\n", "add 3");
        testOneCommand("displaying", "1. test\n2. 1\n3. 2\n4. 3\n\n", "display");
    }

    private void testOneCommand(String description, String expected, String command) {
        Assert.assertEquals(description, expected, TextBuddy.determineCommand(command));
    }
}
