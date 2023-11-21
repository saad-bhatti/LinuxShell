package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.EchoHandler;
import fileSystem.FileSystem;

/**
 * This class is a JUnit Test which tests the public methods of the EchoHandler class
 */
public class EchoHandlerTest {
  /**
   * EchoHandler object. The method of this will be tested.
   */
  private EchoHandler echo;
  /**
   * FileSystem object.
   */
  private FileSystem fs;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;

  /**
   * Initializes the variables.
   */
  @Before
  public void setUp() {
    echo = new EchoHandler();
    arguments = new ArrayList<>();
  }

  /**
   * This test checks the behavior of EchoHandler when provided a string that does not contain any
   * special symbols. The expected output is the string given in the arguments.
   */
  @Test
  public void testStringWithoutSpecialSymbol() {
    // Call the command
    arguments.add("This string is supposed to be returned");
    String output = echo.performOperation(fs, arguments);

    // Check that a correct output is returned
    assertEquals("This string is supposed to be returned", output);
  }

  /**
   * This test checks the behavior of EchoHandler when provided a string that contains special
   * symbols. The expected output is the string given in the arguments.
   */
  @Test
  public void testStringWithSpecialSymbol() {
    // Call the command
    arguments.add("Ooo, these are special symbols: !@#$%^");
    String output = echo.performOperation(fs, arguments);

    // Check that a correct output is returned
    assertEquals("Ooo, these are special symbols: !@#$%^", output);
  }
}
