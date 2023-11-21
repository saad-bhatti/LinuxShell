package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.EchoAppend;
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
 * This class is a JUnit Test which tests the public methods of the EchoAppend class
 */
public class EchoAppendTest {

  /**
   * EchoAppend object. The method of this object will be tested.
   */
  private EchoAppend echoAppend;

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
  public void setUp() {
    echoAppend = new EchoAppend();
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
   * This test checks the behavior of EchoAppend when attempting to append the file starting at the
   * root directory. The expected output is the string "Append" appended to the contents.
   */
  @Test
  public void testAbsolutePathForFile() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/");
    arguments.add("fileRepeat");
    arguments.add("Append");
    String output = echoAppend.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are appended
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in /\nAppend", content);
  }

  /**
   * This test checks the behavior of EchoAppend when attempting to append the file starting at the
   * fileRepeat directory. The expected output is the string "Append" appended to the contents.
   */
  @Test
  public void testRelativePathForFile() {
    // Check that an empty string is returned
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add("fileRepeat");
    arguments.add("Append");
    String output = echoAppend.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are appended
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in dir2_1\nAppend", content);
  }

  /**
   * This test checks the behavior of EchoAppend when attempting to append to an existing file in
   * the directory. The expected output is the string "Append" appended to the contents.
   */
  @Test
  public void testFileAlreadyExistsBefore() {
    // Call the command
    arguments.add("fileRepeat");
    arguments.add("Append");
    String output = echoAppend.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are appended
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("This is the content of fileRepeat in /\nAppend", content);
  }

  /**
   * This test checks the behavior of EchoAppend when attempting to append to a file that did not
   * exists before in the directory. The expected output a new file created and the string "Append"
   * appended to the contents.
   */
  @Test
  public void testFileDidNotExistBefore() {
    // Call the command
    arguments.add("zumran");
    arguments.add("Append");
    String output = echoAppend.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals(output, "");

    // Check that the contents of the existing file are overwritten
    String content = MockFileSystem.getFileContents(fs, "zumran");
    assertEquals("Append", content);
  }

  /**
   * This test checks the behavior of EchoAppend when attempting to append to a directory with the
   * same name The expected output an error message.
   */
  @Test
  public void testDirectoryWithTheSameNameAlreadyExists() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    arguments.add(0, "dirRepeat");
    arguments.add(1, "Append");
    String output = echoAppend.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals(output, "!&!A directory with the name dirRepeat already exists!&!");

    // Check that the file was not created
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "dirRepeat");
    assertEquals(false, exists);
  }
}
