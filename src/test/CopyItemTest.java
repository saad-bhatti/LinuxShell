package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.CopyItem;
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
 * This class is a JUnit Test which tests the public methods of the CopyItem class
 */
public class CopyItemTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * CopyItem object. The method of this object will be tested.
   */
  private CopyItem cp;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setUp() throws Exception {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    cp = new CopyItem();
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
   * This test checks the behavior of CopyItem when copying a file to an existing file. The expected
   * output is an empty string and the expected behavior is that the existing file's content should
   * be overwritten with the content of the copied file.
   */
  @Test
  public void testCopyFileToExistingFile() {
    // Call the command
    arguments.add("/dirRepeat/file2_1");
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    String output = cp.performOperation(fs, arguments);

    // Check that empty string returned
    assertEquals("", output);

    // Check that the contents of the file have been copied
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    String contents = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of file2_1", contents);
  }

  /**
   * This test checks the behavior of CopyItem when copying a file to a file that does not exist.
   * The expected output is an empty string and the expected behavior is that a new file will be
   * created with the content of the copied file.
   */
  @Test
  public void testCopyFileToNewFile() {
    // Call the command
    arguments.add("/dirRepeat/file2_1");
    arguments.add("/newFile");
    String output = cp.performOperation(fs, arguments);

    // Check that empty string returned
    assertEquals("", output);

    // Check that the file has been created
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, exists);

    // Check that the contents of the file have been copied
    String contents = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("This is the content of file2_1", contents);
  }

  /**
   * This test checks the behavior of CopyItem when copying a file to a directory. The expected
   * output is an empty string and the expected behavior is that a new file will be created with the
   * name and content of the copied file, and will be added to the directory.
   */
  @Test
  public void testCopyFileToDirectoryWithNoFileOfSameName() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1/fileRepeat");
    arguments.add("/dir1_1");

    // Check that empty string returned
    String output = cp.performOperation(fs, arguments);
    assertEquals("", output);

    // Check that the file has been created
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(true, exists);

    // Check that the contents of the file have been copied
    String contents = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1", contents);
  }

  /**
   * This test checks the behavior of CopyItem when copying a file to a directory. The expected
   * output is an empty string and the expected behavior is that a new file will be created with the
   * name and content of the copied file, and will be added to the directory.
   */
  @Test
  public void testCopyFileToDirectoryWithFileOfSameName() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("fileRepeat");
    arguments.add("/");
    String output = cp.performOperation(fs, arguments);

    // Check that empty string returned
    assertEquals("", output);

    // Check that the contents of the file have been copied
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    String contents = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1", contents);
  }

  /**
   * This test checks the behavior of CopyItem when copying a directory to a directory that does not
   * have a child directory of the same name. The expected output is an empty string and the
   * expected behavior is that a copy of the directory and its children will be added to the
   * destination.
   */
  @Test
  public void testCopyDirectoryToDirectoryWithNoChild() {
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1");
    arguments.add("/dirRepeat");
    String output = cp.performOperation(fs, arguments);
    assertEquals("", output);
  }

  /**
   * This test checks the behavior of CopyItem when copying a directory to a directory that already
   * contains a child directory of the same name. The expected output is an empty string and the
   * expected behavior is that a copy of the directory and its children will overwrite the existing
   * child directory and its contents of the same name.
   */
  @Test
  public void testCopyDirectoryToDirectoryWithChild() {
    // Call the command
    arguments.add("dirRepeat");
    arguments.add("/dir1_1/dir2_1");
    String output = cp.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check the existing directory has been overwritten (by checking if it contains file2_1)
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1/dirRepeat");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "file2_1");
    assertEquals(true, exists);
  }

  /**
   * This test checks the behavior of CopyItem when copying a directory to a file. The expected
   * output is an error message describing that a directory cannot be copied to a file.
   */
  @Test
  public void testCopyDirectoryToFile() {
    // Call the command
    arguments.add("dirRepeat");
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    String output = cp.performOperation(fs, arguments);

    // Check that the proper error message is returned
    assertEquals("!&!Cannot copy a directory to a file!&!", output);
  }

  /**
   * This test checks the behavior of CopyItem when copying a parent directory to one of its
   * children directories. The expected output is an error message describing that a parent
   * directory cannot be copied to a its child.
   */
  @Test
  public void testCopyParentToChild() {
    // Call the command
    arguments.add("dir1_1");
    arguments.add("/dir1_1/dir2_1");
    String output = cp.performOperation(fs, arguments);

    // Check that the proper error message is returned
    assertEquals("!&!Cannot copy a parent directory to a child directory!&!", output);
  }

  /**
   * This test checks the behavior of CopyItem when copying an item that does not exist.The expected
   * output is an error message describing that the item to be copied does not exist.
   */
  @Test
  public void testCopyNonExistantItem() {
    // Call the command
    arguments.add("dir1_1/nonExistant");
    arguments.add("/dirRepeat");
    String output = cp.performOperation(fs, arguments);

    // Check that the proper error message is returned
    assertEquals("!&!The item 'nonExistant' does not exist!&!", output);
  }

  /**
   * This test checks the behavior of CopyItem when copying to a directory item that does not exist.
   * The expected output is an error message describing that the directory being copied to does not
   * exist.
   */
  @Test
  public void testCopyNonExistantDestination() {
    // Call the command
    arguments.add("dir1_1/dir2_1");
    arguments.add("/nonExistant");
    String output = cp.performOperation(fs, arguments);

    // Check that the proper error message is returned
    assertEquals("!&!The item 'nonExistant' does not exist!&!", output);
  }
}
