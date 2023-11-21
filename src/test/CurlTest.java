package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Curl;
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
 * This is a JUnit Test class for the class Curl
 */
public class CurlTest {
  /**
   * Represents the FileSystem in JShell
   */
  private FileSystem fs;
  /**
   * Represents the Curl object in this JUnit Test class, enables the Test class to use Curl
   * functions
   */
  private Curl curl;
  /**
   * Represents the arguments provided by the user to the curl class
   */
  private ArrayList<Object> arguments;

  /**
   * This method initialize all instance variables we need in this JUnit Test case
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    curl = new Curl();
    arguments = new ArrayList<Object>();
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
   * This method tests that a file is created with the content from a valid URL that is provided.
   * The expected output is an empty string.
   */
  @Test
  public void testValidURL() {
    // Call the command
    arguments.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    String output = curl.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the file is created in the file system
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "073txt");
    assertEquals(true, exists);

    // Check that the files content is not empty
    String content = MockFileSystem.getFileContents(fs, "073txt");
    assertNotEquals("", content);
  }

  /**
   * This method tests Curl when a file in the current directory with the same name as the file to
   * be created already exists. The expected output is an error message describing this error.
   */
  @Test
  public void testExistedFile() {
    // Create the first copy of the file
    arguments.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    curl.performOperation(fs, arguments);

    // Now try to create the file again from the URL
    String output = curl.performOperation(fs, arguments);
    output = curl.performOperation(fs, arguments);

    // Check that the correct error message is returned
    assertEquals("!&!The file 073txt already exists!&!", output);
  }

}
