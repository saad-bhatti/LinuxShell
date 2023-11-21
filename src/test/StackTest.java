package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Stack;

// **********************************************************
// Assignment2:
// Student1: Saad Mohy-Uddin Bhatti
//
// Student2: Awais Aziz
//
// Student3: Haowen Chang
//
// Student4: Zumran Nain
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

/**
 * This JUnit Test tests the methods of Stack Class
 */
public class StackTest {

  /**
   * The Stack object. The methods of this object will be tested
   */
  private Stack stack;

  /**
   * Initializes the stack object
   */
  @Before
  public void setUp() {
    stack = new Stack();
  }

  /**
   * Tests the method setTopString()
   */
  @Test
  public void testSetTopString() {
    // Call the method
    stack.setTopString("Top String???");

    // Check that the string was added
    assertEquals("Top String???", stack.getTopString());
  }

  /**
   * Tests the method getTopString(). The top string should be returned and the removed from the
   * stack.
   */
  @Test
  public void testGetTopString() {
    // Call the method
    stack.setTopString("Input 01");
    stack.setTopString("Input 02");
    String output = stack.getTopString();

    // Check that the top string was returned
    assertEquals("Input 02", output);

    // Check that the top string was removed from the stack
    assertEquals("Input 01", stack.getTopString());
  }
}
