package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.MakeDirectory;
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
 * This class is a JUnit Test tests the public methods of the MakeDirectory class
 */
public class MakeDirectoryTest {

  // Instance Variables
  /**
   * Represents the FileSystem. Directories that are created will be added here.
   */
  private FileSystem fs;
  /**
   * MakeDirectory object. The method of this object will be tested.
   */
  private MakeDirectory mkdir;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables.
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setCurrentDirectory(fs, "/");
    mkdir = new MakeDirectory();
    arguments = new ArrayList<>();
  }

  /**
   * This method deletes the existing file system.
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
   * This test checks the behavior of performOperation when all directories can be created. The
   * expected return value is an empty string.
   */
  @Test
  public void testAllCreated() {
    // Call the command
    arguments.add("dir1_1&dir1_2&dir1_3&dir1_1/dir2_1&/dir1_1/dir2_1/dir3_1");
    String returnMessage = mkdir.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", returnMessage);

    // Check that the directory was created
    MockFileSystem.setCurrentDirectory(fs, "dir1_1/dir2_1/dir3_1");
    assertEquals("dir3_1", fs.getCurrentDir().getName());
  }

  /*
   * This test checks the behavior of performOperation when an invalid path is given. The
   * directories that come before this invalid path will be created, but the directories after the
   * invalid path will not be made. The expected return value is an error message stating an invalid
   * path is given.
   */
  @Test
  public void testInvalidPathGiven() {
    // Call the command
    arguments.add("dir1_1&dir1_2/dir2_1&dir1_3");
    String returnMessage = mkdir.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The directory dir1_2 does not exist!&!", returnMessage);

    // Check that valid directory was created (that came before the error)
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir1_1");
    assertEquals(true, exists);
  }

  /**
   * This test checks the behavior of performOperation when a duplicate directory is attempted to be
   * made. The expected return value is an error message describing that a duplicate directory was
   * trying to be made.
   */
  @Test
  public void testCreateDuplicate() {
    // Call the command
    arguments.add("dir1&dir1");
    String returnMessage = mkdir.performOperation(fs, arguments);

    // Check that the correct error message was given
    assertEquals("!&!The directory dir1 already exists!&!", returnMessage);

    // Check that the first directory was created
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir1");
    assertEquals(true, exists);
  }

  /**
   * This test checks the behavior of performOperation when attempting to create directory in a
   * directory that contains a file of the same name. The expected return value is an error message
   * describing the error and that the directory should not be made.
   */
  @Test
  public void testFileWithSameNameAlreadyExists() {
    // Call the command
    MockFileSystem.setUpMockFileSystem(fs);
    arguments.add("fileRepeat");
    String returnMessage = mkdir.performOperation(fs, arguments);

    // Check that the correct error message was given
    assertEquals("!&!A file with the name fileRepeat already exists!&!", returnMessage);

    // Check that the first directory was created
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "fileRepeat");
    assertEquals(false, exists);
  }
}
