package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Concatenate;
import fileSystem.FileSystem;

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
 * This class is a JUnit Test which tests the public methods of the Concatenate class
 */
public class ConcatenateTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * Concatenate object. The method of this object will be tested.
   */
  private Concatenate cat;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    cat = new Concatenate();
    arguments = new ArrayList<>();
  }

  /**
   * This method deletes the mock file system.
   * 
   * @throws Exception. If the field is not belonging to FileSystem, throw the exception
   */
  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("createdFileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  /**
   * This test checks the behavior of Concatenate when getting the content of a file provided by a
   * relative path. The expected output is the content of the file.
   */
  @Test
  public void testRelativePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1/fileRepeat");
    String output = cat.performOperation(fs, arguments);

    // Check that the content of the file was returned
    assertEquals("This is the content of fileRepeat in dir2_1", output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of Concatenate when getting the content of a file provided by an
   * absolute path. The expected output is the content of the file.
   */
  @Test
  public void testAbsolutePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("/dirRepeat/file2_1");
    String output = cat.performOperation(fs, arguments);

    // Check that the content of the file was returned
    assertEquals("This is the content of file2_1", output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of Concatenate when getting the content of multiple files
   * provided by multiple paths. The expected output is the content of the files.
   */
  @Test
  public void testMultipleValidPaths() {
    // Call the command
    arguments.add("fileRepeat&/dirRepeat/file2_1&/dir1_1/dir2_1/fileRepeat");
    String output = cat.performOperation(fs, arguments);

    // Check that the content of the files was returned
    String expected =
        "This is the content of fileRepeat in /" + "\n\n\n" + "This is the content of file2_1"
            + "\n\n\n" + "This is the content of fileRepeat in dir2_1";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of Concatenate when getting the content of a file that does not
   * exist. The expected output is an error message.
   */
  @Test
  public void testNonExistantFile() {
    // Call the command
    arguments.add("dir1_1/nonExistant");
    String output = cat.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The file nonExistant does not exist!&!", output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of Concatenate when getting the content of multiple valid files
   * with a file that does not exist in between. The expected output is the content of the files
   * that come before the invalid file, then the error message describing the error.
   */
  @Test
  public void testMultiplePathsWithNonExistantFile() {
    // Call the command
    arguments.add("fileRepeat&dir1_1/nonExistant&dirRepeat/file2_1");
    String output = cat.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "This is the content of fileRepeat in /" + "\n\n\n"
        + "!&!The file nonExistant does not exist!&!";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }
  
  /**
   * This test checks the behavior of Concatenate when provided an invalid path, The expected output
   * is an error message describing this error.
   */
  @Test
  public void testInvalidPath() {
    // Call the command
    arguments.add("dir1_1/invalidDirectory/nonExistant");
    String output = cat.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The directory invalidDirectory does not exist!&!", output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }
}
