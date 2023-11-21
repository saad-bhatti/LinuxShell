package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import shell.Output;
import shell.Parse;

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
 * This JUnit Test tests the methods of Parse Class (excluding the setters and getter)
 */
public class ParseTest {

  /**
   * The parse object. The methods of this class will be tested.
   */
  private Parse parse;
  /**
   * The output object. This object is a parameter for parse's method
   */
  private Output output;

  /**
   * Initializes the the variables
   */
  @Before
  public void setUp() {
    parse = new Parse();
    output = new Output();
  }

  /**
   * This test checks the behavior of Parse when the user inputs an empty string. The expected
   * output is an error message.
   */
  @Test
  public void testParseEmptyInput() {
    // Call the method
    String message = parse.parseInput("", output);

    // Check that the correct error message was returned
    assertEquals(message, "!&!The user input was empty!&!");
  }

  /**
   * This test checks the behavior of Parse when provided an input without any redirection. The
   * expected output is an empty string and the command and parameters variable to have been
   * assigned values.
   */
  @Test
  public void testParseInputWithNoRedirection() {
    // Call the method
    String message = parse.parseInput("mkdir QUICK MATHS", output);

    // See that an empty string was returned
    assertEquals("", message);

    // Check that the command variable was assigned the correct value
    assertEquals("mkdir", parse.getCommand());

    // Check that the parameters were correctly assigned
    assertEquals("[QUICK, MATHS]", parse.getParameters().toString());
  }

  /**
   * This test checks the behavior of Parse when provided an input without any redirection but ends
   * with an angled bracket. The expected output is an empty string and the command and parameters
   * variable to have been assigned values and the output variables to be as their default values.
   */
  @Test
  public void testParseInputEndingWithSymbol() {
    // Call the method
    String message = parse.parseInput("mkdir dir1 dir2>", output);

    // See that an empty string was returned
    assertEquals("", message);

    // Check that the command variable was assigned the correct value
    assertEquals("mkdir", parse.getCommand());

    // Check that the parameters were correctly assigned
    assertEquals("[dir1, dir2>]", parse.getParameters().toString());
    
    // Check that the output variables are still at their default values
    assertEquals(-1, output.getTypeOfRedirect());
    assert(output.getRedirectionParameters().isEmpty());
  }

  /**
   * This test checks the behavior of Parse when provided an input with redirection that will
   * overwrite. The expected output is an empty string. The expected behavior is the command and
   * parameters variable to have been assigned values, and the output variable to be set correctly
   * as well.
   */
  @Test
  public void testParseInputForOverwriteRedirection() {
    // Call the method
    String message = parse.parseInput("cat file1 file2>fileOutput", output);

    // See that an empty string was returned
    assertEquals("", message);

    // Check that the command variable was assigned the correct value
    assertEquals("cat", parse.getCommand());

    // Check that the parameters were correctly assigned
    assertEquals("[file1, file2]", parse.getParameters().toString());

    // Check that the output variables were correctly set
    assertEquals(1, output.getTypeOfRedirect());
    assertEquals("[fileOutput]", output.getRedirectionParameters().toString());
  }

  /**
   * This test checks the behavior of Parse when provided an input with redirection that will
   * append. The expected output is an empty string. The expected behavior is the command and
   * parameters variable to have been assigned values, and the output variable to be set correctly
   * as well.
   */
  @Test
  public void testParseInputForAppendRedirection() {
    // Call the method
    String message = parse.parseInput("cat file1 file2 >> fileOutput", output);

    // See that an empty string was returned
    assertEquals("", message);

    // Check that the command variable was assigned the correct value
    assertEquals("cat", parse.getCommand());

    // Check that the parameters were correctly assigned
    assertEquals("[file1, file2]", parse.getParameters().toString());

    // Check that the output variables were correctly set
    assertEquals(2, output.getTypeOfRedirect());
    assertEquals("[fileOutput]", output.getRedirectionParameters().toString());
  }

  /**
   * This test checks the behavior of Parse when provided an input with multiple redirections. The
   * expected output is an empty string. The expected behavior is the command and parameters
   * variable to have been assigned values, and the output variables to be set correctly as well.
   */
  @Test
  public void testParseInputForMultipleRedirection() {
    // Call the method
    String message = parse.parseInput("cat file1 file2 >> fileOutput>error >>file", output);

    // See that an empty string was returned
    assertEquals("", message);

    // Check that the command variable was assigned the correct value
    assertEquals("cat", parse.getCommand());

    // Check that the parameters were correctly assigned
    assertEquals("[file1, file2]", parse.getParameters().toString());

    // Check that the output variables were correctly set
    assertEquals(2, output.getTypeOfRedirect());
    assertEquals("[fileOutput>error, >>file]", output.getRedirectionParameters().toString());
  }
}
