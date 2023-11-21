package test;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.FileSystem;
import shell.Output;

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
 * This class is a JUnit Test which tests the public method of the Output class
 */
public class OutputTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * Output object. The method of this object will be tested.
   */
  private Output output;
  /**
   * Responsible for containing the parameters for redirection
   */
  private ArrayList<String> redirParam;
  /**
   * Responsible for getting the actual content that is printed on the screen during the test
   */
  private final ByteArrayOutputStream actualContent = new ByteArrayOutputStream();
  /**
   * Responsible for getting the expected content that is printed on the screen during the test
   */
  private final ByteArrayOutputStream expectedContent = new ByteArrayOutputStream();
  /**
   * Responsible for tracking where the System will print the content
   */
  private final PrintStream originalDestination = System.out;

  /**
   * This method initializes the instance variables.
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setCurrentDirectory(fs, "/");
    output = new Output();
    redirParam = new ArrayList<>();
    System.setOut(new PrintStream(actualContent));
  }

  /**
   * This method deletes the mock file system. Resets the system to print the screen.
   * 
   * @throws Exception. If the field is not belonging to FileSystem, throw the exception
   */
  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("createdFileSystem");
    field.setAccessible(true);
    field.set(null, null);
    System.setOut(originalDestination);
  }

  /**
   * Tests that nothing is printed on the screen when an empty string is given as input and
   * redirection is not occurring.
   */
  @Test
  public void testNoRedirectionEmpty() {
    // Set redirection to false
    output.setRedirectionOccuring(false);

    // Call the method
    output.printOutput(fs, "");

    // Check that nothing was printed
    assertEquals("", actualContent.toString());
  }

  /**
   * Tests that the correct string is printed when the input consists of only successful input and
   * redirection is not occurring.
   */
  @Test
  public void testNoRedirectionAllSuccess() {
    // Set redirection to false
    output.setRedirectionOccuring(false);

    // Call the method
    output.printOutput(fs, "Successful output to be printed");
    String actual = actualContent.toString();

    // Print out the expected output and retrieve it
    System.setOut(new PrintStream(expectedContent));
    System.out.println("Successful output to be printed");
    String expected = expectedContent.toString();

    // Check that the correct output was printed
    assertEquals(expected, actual);
  }

  /**
   * Tests that the correct string is printed when the input consists of only an error message and
   * redirection is not occurring.
   */
  @Test
  public void testNoRedirectionAllError() {
    // Set redirection to false
    output.setRedirectionOccuring(false);

    // Call the method
    output.printOutput(fs, "!&!Error Message 101!&!");
    String actual = actualContent.toString();

    // Print out the expected output and retrieve it
    System.setOut(new PrintStream(expectedContent));
    System.out.println("Error Message 101");
    String expected = expectedContent.toString();

    // Check that the correct output was printed
    assertEquals(expected, actual);
  }

  /**
   * Tests that the correct string is printed when the input consists of both successful input and
   * an error message and redirection is not occurring.
   */
  @Test
  public void testNoRedirectionSuccessAndError() {
    // Set redirection to false
    output.setRedirectionOccuring(false);

    // Call the method
    output.printOutput(fs, "Successful output is here\n!&!Error Message 101!&!");
    String actual = actualContent.toString();

    // Print out the expected output and retrieve it
    System.setOut(new PrintStream(expectedContent));
    System.out.println("Successful output is here\nError Message 101");
    String expected = expectedContent.toString();

    // Check that the correct output was printed
    assertEquals(expected, actual);
  }

  /**
   * Tests that nothing is printed on the screen when an empty string is given as input and
   * redirection is occurring.
   */
  @Test
  public void testRedirectionEmpty() {
    // Set redirection to true, set a valid file name, set to overwrite
    output.setRedirectionOccuring(true);
    redirParam.add("newFile");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(1);

    // Call the method
    output.printOutput(fs, "");

    // Check that nothing was printed
    assertEquals("", actualContent.toString());

    // Check that the file was created
    boolean created = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, created);

    // Check that the file has empty content
    String contents = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("", contents);
  }

  /**
   * Tests that nothing is printed on the screen when redirecting the output with a relative path
   * for the file.
   */
  @Test
  public void testRedirectionRelativePath() {
    MockFileSystem.setUpMockFileSystem(fs);
    // Set redirection to true, set a valid file name, set to overwrite
    output.setRedirectionOccuring(true);
    redirParam.add("/dir1_1/dir2_1/newFile");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(1);

    // Call the method
    output.printOutput(fs, "You've been gnomed!");

    // Check that nothing was printed
    assertEquals("", actualContent.toString());

    // Check that the file was created
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    boolean created = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, created);

    // Check that the files content has been set
    String contents = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("You've been gnomed!", contents);
  }

  /**
   * Tests that nothing is printed on the screen when redirecting the output, and that existing
   * file's content is overwritten by the methods input.
   */
  @Test
  public void testRedirectionOverWriteExistingFile() {
    MockFileSystem.setUpMockFileSystem(fs);
    // Set redirection to true, set an existing file name, set to overwrite
    output.setRedirectionOccuring(true);
    redirParam.add("fileRepeat");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(1);

    // Call the method
    output.printOutput(fs, "In with the new, out with the old");

    // Check that nothing was printed
    assertEquals("", actualContent.toString());

    // Check that the file has empty content
    String contents = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("In with the new, out with the old", contents);
  }

  /**
   * Tests that nothing is printed on the screen when redirecting the output, and methods input has
   * been appended to the existing file's content.
   */
  @Test
  public void testRedirectionAppendExistingFile() {
    MockFileSystem.setUpMockFileSystem(fs);
    // Set redirection to true, set an existing file name, set to append
    output.setRedirectionOccuring(true);
    redirParam.add("fileRepeat");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(2);

    // Call the method
    output.printOutput(fs, "WHO LET THE DOGS OUT!!!");

    // Check that nothing was printed
    assertEquals("", actualContent.toString());

    // Check that the file has empty content
    String contents = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in /\nWHO LET THE DOGS OUT!!!", contents);
  }

  /**
   * Tests that the correct string is printed when the input consists of only an error message and
   * redirection is occurring.
   */
  @Test
  public void testRedirectionAllError() {
    // Set redirection to true, set a valid file name, set to overwrite
    output.setRedirectionOccuring(true);
    redirParam.add("newFile");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(1);

    // Call the method
    output.printOutput(fs, "!&!Error 404 Not Found!&!");
    String actual = actualContent.toString();

    // Print out the expected output and retrieve it
    System.setOut(new PrintStream(expectedContent));
    System.out.println("Error 404 Not Found");
    String expected = expectedContent.toString();

    // Check that the correct output was printed
    assertEquals(expected, actual);
  }

  /**
   * Tests that the correct string is printed when the input consists of both successful output and
   * an error message and redirection is occurring.
   */
  @Test
  public void testRedirectionSuccessAndError() {
    // Set redirection to true, set a valid file name, set to overwrite
    output.setRedirectionOccuring(true);
    redirParam.add("newFile");
    output.setRedirectionParameters(redirParam);
    output.setTypeOfRedirect(1);

    // Call the method
    output.printOutput(fs, "Never gonna give you up\n!&!Never gonna let you down!&!");
    String actual = actualContent.toString();

    // Print out the expected output and retrieve it
    System.setOut(new PrintStream(expectedContent));
    System.out.println("Never gonna let you down");
    String expected = expectedContent.toString();

    // Check that the correct output was printed
    assertEquals(expected, actual);

    // Check that the file was created
    boolean created = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, created);

    // Check that the files content only contains the successful output
    String contents = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("Never gonna give you up", contents);
  }
}
