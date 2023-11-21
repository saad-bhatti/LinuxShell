package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.RemoveDirectory;
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
 * This class is a JUnit Test which tests the public methods of the RemoveDirectory class
 */
public class RemoveDirectoryTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * Search object. The method of this object will be tested.
   */
  private RemoveDirectory rm;
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
    rm = new RemoveDirectory();
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
   * This test checks the behavior of RemoveDirectory when removing a directory in the current
   * directory. The expected output is an empty string.
   */
  @Test
  public void testRemoveInCurrent() {
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1");
    String output = rm.performOperation(fs, arguments);
    assertEquals("", output);
    boolean stillExists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir2_1");
    assertEquals(false, stillExists);
  }

  /**
   * This test checks the behavior of RemoveDirectory when removing a directory provided by a valid
   * path. The expected output is an empty string.
   */
  @Test
  public void testRemoveByPath() {
    arguments.add("dir1_1/dir2_1");
    String output = rm.performOperation(fs, arguments);
    assertEquals("", output);
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    boolean stillExists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir2_1");
    assertEquals(false, stillExists);
  }

  /**
   * This test checks the behavior of RemoveDirectory when removing a directory that is a parent to
   * the current directory or the current directory itself. The expected output is an error message
   * describing the error.
   */
  @Test
  public void testRemoveParent() {
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("/dir1_1");
    String output = rm.performOperation(fs, arguments);
    assertEquals("!&!A parent directory or the current directory cannot be removed!&!", output);
    MockFileSystem.setCurrentDirectory(fs, "/");
    boolean stillExists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir1_1");
    assertEquals(true, stillExists);
  }

  /**
   * This test checks the behavior of RemoveDirectory when removing a directory that does not exist.
   * The expected output is an error message describing the error.
   */
  @Test
  public void testNonExistantDirectory() {
    arguments.add("/dir1_1/nonExistantDir");
    String output = rm.performOperation(fs, arguments);
    assertEquals("!&!The directory 'nonExistantDir' was not found!&!", output);
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of RemoveDirectory when removing a directory that is a parent to
   * the current directory or the current directory itself. The expected output is an error message
   * describing the error.
   */
  @Test
  public void testInvalidPath() {
    arguments.add("/nonExistantDir/dir2_1");
    String output = rm.performOperation(fs, arguments);
    assertEquals("!&!The directory nonExistantDir does not exist!&!", output);
    assertEquals("/", fs.getCurrentDir().getName());
  }
}
