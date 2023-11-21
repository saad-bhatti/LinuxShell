package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Exit;

/**
 * This class is a JUnit Test which tests the public methods of the Exit class
 */
public class ExitTest {
  /**
   * Exit object. The method of this object will be tested.
   */
  private Exit exit;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;
  /**
   * ArrayList of type String. The method will modify this variable.
   */
  private ArrayList<String> exitArray;

  /**
   * This method initializes the instance variables
   */
  @Before
  public void setUp() {
    exit = new Exit();
    arguments = new ArrayList<>();
    exitArray = new ArrayList<String>();
  }

  /**
   * This test checks the behavior of Exit when adding provided an array list of type string. The
   * expected output is an empty string and the arraylist to not be empty.
   */
  @Test
  public void testExitCommand() {
    // Call the command
    arguments.add(exitArray);
    String output = exit.performOperation(null, arguments);
    
    // Check that an empty string was returned
    assertEquals("", output);
    
    // Check that the array list input now has one element
    assertEquals(false, exitArray.isEmpty());
  }
}
