package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.MoveItem;
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
 * This class is a JUnit Test which tests the public methods of the MoveItem class
 */
public class MoveItemTest {

  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * MoveItem object. The method of this object will be tested.
   */
  private MoveItem mv;
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
    mv = new MoveItem();
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
   * This test checks the behavior of MoveItem when moving a file to an existing file. The expected
   * output is an empty string and the expected behavior is that the existing file's content should
   * be overwritten with the content of the moved file and the moved file deleted.
   */
  @Test
  public void testMoveFileToExistingFile() {
    // Call the command
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    arguments.add("/");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are overwritten
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1", content);

    // Check that the file that was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a file to a file that does not exist. The
   * expected output is an empty string and the expected behavior is that a new file will be created
   * with the content of the moved file and the moved file deleted.
   */
  @Test
  public void testMoveFileToNewFile() {
    // Call the command
    arguments.add("fileRepeat");
    arguments.add("/dir1_1/newFile");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the new file was created
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, exists);

    // Check that the content of the new file were set
    String content = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("This is the content of fileRepeat in /", content);

    // Check that the file was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/");
    exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a file to a directory. The expected
   * output is an empty string and the expected behavior is that a the file will be moved here.
   */
  @Test
  public void testMoveFileToDirectoryWithNoFileOfSameName() {
    // Call the command
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    arguments.add("dir1_1");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string was returned
    assertEquals("", output);

    // Check that the file was added
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(true, exists);

    // Check that the content of the new file were set
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1", content);

    // Check that the file was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a file to a directory. The expected
   * output is an empty string and the expected behavior is that the moved file will overwrite the
   * existing file in the directory.
   */
  @Test
  public void testMoveFileToDirectoryWithFileOfSameName() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("fileRepeat");
    arguments.add("/");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string was returned
    assertEquals("", output);

    // Check that the content of the existing file were overwritten
    MockFileSystem.setCurrentDirectory(fs, "/");
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1", content);

    // Check that the file was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a directory to a directory that already
   * exists. The expected output is an empty string and the expected behavior is that a existing
   * directory will be overwritten by the moved directory.
   */
  @Test
  public void testMoveDirectoryToExistingDirectory() {
    // Call the command
    arguments.add("/dir1_1/dir2_1/dirRepeat");
    arguments.add("/");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string was returned
    assertEquals("", output);

    // Check the existing was overwritten by checking that it does not contain file2_1
    MockFileSystem.setCurrentDirectory(fs, "/dirRepeat");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "file2_1");
    assertEquals(false, exists);

    // Check that the directory was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dirRepeat");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a directory to a directory that does not
   * exist. The expected output is an empty string and the expected behavior is that a moved
   * directory will be renamed moved here.
   */
  @Test
  public void testMoveDirectoryToNonExistingDirectory() {
    // Call the command
    arguments.add("/dir1_1/dir2_1");
    arguments.add("/");
    String output = mv.performOperation(fs, arguments);

    // Check that an empty string was returned
    assertEquals("", output);

    // Check that the directory was added
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir2_1");
    assertEquals(true, exists);

    // Check that its contents were added
    MockFileSystem.setCurrentDirectory(fs, "/dir2_1");
    exists = MockFileSystem.searchForFileInCurrent(fs, "fileRepeat");
    assertEquals(true, exists);

    // Check that the directory was deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir2_1");
    assertEquals(false, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a directory to a file. The expected
   * output is an error message describing that a directory cannot be moved to a file.
   */
  @Test
  public void testCopyDirectoryToFile() {
    // Call the command
    arguments.add("/dir1_1/dir2_1");
    arguments.add("/fileRepeat");
    String output = mv.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!Cannot move a directory to a file!&!", output);

    // Check that the directory was not deleted from its original location
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir2_1");
    assertEquals(true, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving a parent directory to one of its children
   * directories. The expected output is an error message describing that a parent directory cannot
   * be moved to a its child.
   */
  @Test
  public void testMoveParentToChild() {
    // Call the command
    arguments.add("dir1_1");
    arguments.add("/dir1_1/dir2_1");
    String output = mv.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!Cannot move a parent directory to a child directory!&!", output);

    // Check that the directory was not deleted from its original location
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "dir1_1");
    assertEquals(true, exists);
  }

  /**
   * This test checks the behavior of MoveItem when moving an item that does not exist.The expected
   * output is an error message describing that the item to be moved does not exist.
   */
  @Test
  public void testCopyNonExistantItem() {
    // Call the command
    arguments.add("dir1_1/nonExistant");
    arguments.add("/dirRepeat");
    String output = mv.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The item 'nonExistant' does not exist!&!", output);
  }
}
