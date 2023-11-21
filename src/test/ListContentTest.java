package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ListContent;
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
 * This class is a JUnit Test which tests the public methods of the ListContent class
 */
public class ListContentTest {

  /**
   * ListContent object. This objects methods will be tested.
   */
  private ListContent ls;
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
    ls = new ListContent();
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
   * This test checks the behavior of ListContent when provided no parameters. The expected output
   * is the name of all the content within the current directory.
   */
  @Test
  public void testListContentWithNoInput() {
    // Call the command
    arguments.add("");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "dir1_1\n" + "dirRepeat\n" + "fileRepeat";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of a directory provided
   * by a relative path. The expected output is the name of all the content within that directory.
   */
  @Test
  public void testListContentDirectoryWithRelativePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "dirRepeat\n" + "fileRepeat";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of a directory provided
   * by an absolute path. The expected output is the name of all the content within that directory.
   */
  @Test
  public void testListContentDirectoryWithAbsolutePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dirRepeat");
    arguments.add("/dir1_1/dir2_1");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "dirRepeat\n" + "fileRepeat";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dirRepeat", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of a file provided by a
   * relative path. The expected output is the relative path of that file.
   */
  @Test
  public void testListContentFileWithRelativePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1/fileRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "dir2_1/fileRepeat";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of a file provided by an
   * absolute path. The expected output is the absolute path of that file.
   */
  @Test
  public void testListContentFileWithAbsolutePath() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dirRepeat");
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "/dir1_1/dir2_1/fileRepeat";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dirRepeat", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of multiple items of
   * different types. The expected output is list content of each of those individual items.
   */
  @Test
  public void testListContentOfMultipleItems() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("/&/dir1_1/dir2_1/fileRepeat&.&/dirRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected =
        // The root
        "dir1_1\n" + "dirRepeat\n" + "fileRepeat\n"
        // Absolute path to fileRepeat
            + "/dir1_1/dir2_1/fileRepeat\n"
            // Content of the current directory
            + "dir2_1\n"
            // Content of dirRepeat
            + "file2_1";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of an item that does not
   * exist. The expected output is an error message.
   */
  @Test
  public void testListContentNonExistantItem() {
    // Call the command
    arguments.add("nonExistant");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!nonExistant does not exist!&!", output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when provided an invalid path. The expected output
   * is an error message.
   */
  @Test
  public void testListContentInvalidPath() {
    // Call the command
    arguments.add("/dir1_1/dirInvalid/fileRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The directory dirInvalid does not exist!&!", output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent when listing the content of multiple items of
   * different types with an invalid entry in between. The expected output is list content of each
   * of those individual items that come before the invalid item, and then the error message
   * describing the error.
   */
  @Test
  public void testListContentOfMultipleItemsWithInvalid() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("/&/dir1_1/dir2_1/fileRepeat&invalidItem&/dirRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected =
        // The root
        "dir1_1\n" + "dirRepeat\n" + "fileRepeat\n"
        // Absolute path to fileRepeat
            + "/dir1_1/dir2_1/fileRepeat\n"
            // Error message
            + "!&!invalidItem does not exist!&!";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("dir1_1", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent it is recursively called and no parameters are
   * provided. The expected output is the name of all the content within the current directory and
   * the content of its children.
   */
  @Test
  public void testRecursivelyListContentWithNoInput() {
    // Call the command
    arguments.add("-R");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected =
        "Key: . is equal to the directory name /\n\n" + ".:\n" + "dir1_1\ndirRepeat\nfileRepeat\n"
            + "\n" + "./dir1_1:\ndir2_1\n" + "\n" + "./dir1_1/dir2_1:\n" + "dirRepeat\nfileRepeat\n"
            + "\n" + "./dir1_1/dir2_1/dirRepeat:\n" + "\n" + "./dirRepeat:\n" + "file2_1";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent it is recursively called and a single parameter is
   * provided. The expected output is the name of all the content within the current directory and
   * the content of its children.
   */
  @Test
  public void testRecursivelyListContentWithSingleInput() {
    // Call the command
    arguments.add("-R&dir1_1");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "Key: . is equal to the directory name dir1_1\n" + "\n" + ".:\n" + "dir2_1\n"
        + "\n" + "./dir2_1:\n" + "dirRepeat\n" + "fileRepeat\n" + "\n" + "./dir2_1/dirRepeat:";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }

  /**
   * This test checks the behavior of ListContent it is recursively called and multiple parameters
   * are provided. The expected output is the name of all the content within the current directory
   * and the content of its children.
   */
  @Test
  public void testRecursivelyListContentWithMultipleInput() {
    arguments.add("-R&dir1_1&dirRepeat");
    String output = ls.performOperation(fs, arguments);

    // Check that the correct output was returned
    String expected = "Key: . is equal to the directory name dir1_1\n" + "\n" + ".:\n" + "dir2_1\n"
        + "\n" + "./dir2_1:\n" + "dirRepeat\n" + "fileRepeat\n" + "\n" + "./dir2_1/dirRepeat:\n"
        + "\n\nKey: . is equal to the directory name dirRepeat\n\n" + ".:\n" + "file2_1";
    assertEquals(expected, output);

    // Check that the current directory did not change
    assertEquals("/", fs.getCurrentDir().getName());
  }
}
