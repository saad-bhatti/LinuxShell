package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Search;
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
 * This class is a JUnit Test which tests the public methods of the Search class
 */
public class SearchTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * Search object. The method of this object will be tested.
   */
  private Search search;
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
    search = new Search();
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
   * This test checks the behavior of search when searching a directory within a directory provided
   * by a single path. The expected output is the full paths of all found directories.
   */
  @Test
  public void testSinglePathDirectory() {
    // Call the command
    arguments.add("/");
    arguments.add("d");
    arguments.add("dirRepeat");
    String output = search.performOperation(fs, arguments);

    // Check that the correct output is given
    assertEquals("/dir1_1/dir2_1/dirRepeat\n" + "/dirRepeat", output);
  }

  /**
   * This test checks the behavior of search when searching a file within a directory provided by a
   * single path. The expected output is the full paths of all found files.
   */
  @Test
  public void testSinglePathFile() {
    // Call the command
    arguments.add("/");
    arguments.add("f");
    arguments.add("fileRepeat");
    String output = search.performOperation(fs, arguments);

    // Check that the correct output is given
    assertEquals("/fileRepeat\n" + "/dir1_1/dir2_1/fileRepeat", output);
  }

  /**
   * This test checks the behavior of search when searching a directory within a multiple
   * directories provided by multiple paths. The expected output is the full paths of all found
   * directories.
   */
  @Test
  public void testMultiplePathDirectory() {
    // Call the command
    arguments.add("/&/dir1_1/dir2_1");
    arguments.add("d");
    arguments.add("dirRepeat");
    String output = search.performOperation(fs, arguments);

    // Check that the correct output is given
    assertEquals("/dir1_1/dir2_1/dirRepeat\n" + "/dirRepeat\n" + "/dir1_1/dir2_1/dirRepeat",
        output);
  }

  /**
   * This test checks the behavior of search when searching a file within a multiple directories
   * provided by multiple paths. The expected output is the full paths of all found files.
   */
  @Test
  public void testMultiplePathFile() {
    // Call the command
    arguments.add("/&/dir1_1/dir2_1");
    arguments.add("f");
    arguments.add("fileRepeat");
    String output = search.performOperation(fs, arguments);

    // Check that the correct output is given
    assertEquals("/fileRepeat\n" + "/dir1_1/dir2_1/fileRepeat\n" + "/dir1_1/dir2_1/fileRepeat",
        output);
  }

  /**
   * This test checks the behavior of search when searching an item that does not exist within the
   * within the mock file system. The expected output is an error message.
   */
  @Test
  public void testItemDoesNotExist() {
    // Call the command
    arguments.add("/");
    arguments.add("f");
    arguments.add("randomFile");
    String output = search.performOperation(fs, arguments);

    // Check that the correct error message is given
    assertEquals("No item of name 'randomFile' was found", output);
  }

  /**
   * This test checks the behavior of search when searching an invalid path is provided.The expected
   * output is an error message describing the error.
   */
  @Test
  public void testInvalidPath() {
    // Call the command
    arguments.add("/dir1_1/randomDir&/");
    arguments.add("d");
    arguments.add("dirRepeat");
    String output = search.performOperation(fs, arguments);

    // Check that the correct error message is given
    assertEquals("!&!The directory randomDir does not exist!&!", output);
  }
}
