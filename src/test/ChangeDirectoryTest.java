package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ChangeDirectory;
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
 * This class is a JUnit Test which tests the public methods of the ChangeDirectory class
 */
public class ChangeDirectoryTest {
  /**
   * ChangeDirectory object. This objects methods will be tested.
   */
  private ChangeDirectory cd;
  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setup() {
    cd = new ChangeDirectory();
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
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
   * This test checks the behavior of ChangeDirectory when changing to the root directory. The
   * expected output is an empty string and the current directory to be root.
   */
  @Test
  public void testChangeToRootDirectory() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("/");
    String output = cd.performOperation(fs, arguments);

    // Check that an empty string was returned
    assertEquals("", output);

    // Check the name of the current directory is '/'
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ChangeDirectory when changing to a parent directory. The
   * expected output is an empty string and the current directory to be the current directory's
   * parent.
   */
  @Test
  public void testChangeToParentDirectory() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("..");
    String output = cd.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the name of the current directory is the parent's name
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ChangeDirectory when provided a relative path. The expected
   * output is an empty string and the current directory to be changed.
   */
  @Test
  public void testChangeWithRelativePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1/dirRepeat");
    String output = cd.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the correct directory is now the current directory
    assertEquals("dirRepeat", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ChangeDirectory when provided an absolute path. The expected
   * output is an empty string and the current directory to be changed.
   */
  @Test
  public void testChangeWithFullPath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("/dirRepeat");
    String output = cd.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the correct directory is the current directory
    assertEquals("dirRepeat", fs.getCurrentDir().getName());
  }


  /**
   * This test checks the behavior of ChangeDirectory when provided an invalid path. The expected
   * output is an error message and the current directory not to be changed.
   */
  @Test
  public void testChangeWithInvalidPath() {
    // Call the command
    arguments.add("dir1_1/nonExistant");
    String output = cd.performOperation(fs, arguments);

    // Check that the correct error message is returned
    assertEquals("!&!The directory nonExistant does not exist!&!", output);

    // Check that the current directory is not changed
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ChangeDirectory when attempting to change to the root of the
   * file system's parent. The expected output is an error message and the current directory not to
   * be changed.
   */
  @Test
  public void testChangeToRootsParent() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("/..");
    String output = cd.performOperation(fs, arguments);

    // Check that the correct error message is returned
    assertEquals("!&!The root does not have a parent directory!&!", output);

    // Check that the current directory is not changed
    assertEquals("dir2_1", fs.getCurrentDir().getName());
  }
}
