package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.EchoOverwrite;
import fileSystem.FileSystem;

/**
 * This class is a JUnit Test which tests the public methods of the EchoOverwrite class
 */
public class EchoOverwriteTest {

  /**
   * EchoOverwrite object. The method of this object will be tested.
   */
  private EchoOverwrite echo;
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
    echo = new EchoOverwrite();
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
   * This test checks the behavior of EchoOverwrite when attempting to overwrite a file provided by
   * an absolute path. The expected output is an empty string and for the file's contents to be
   * overwritten.
   */
  @Test
  public void testAbsolutePathForFile() {
    // Call the command
    arguments.add("/dir1_1/dir2_1/fileRepeat");
    arguments.add("Overwrite");
    String output = echo.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are overwritten
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("Overwrite", content);
  }

  /**
   * This test checks the behavior of EchoOverwrite when attempting to overwrite a file provided by
   * a relative path. The expected output is an empty string and for the file's contents to be
   * overwritten.
   */
  @Test
  public void testRelativePathForFile() {
    // Call the command
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1");
    arguments.add("dir2_1/fileRepeat");
    arguments.add("Overwrite");
    String output = echo.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are overwritten
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("Overwrite", content);
  }

  /**
   * This test checks the behavior of EchoOverwrite when attempting to overwrite a file that already
   * exists. The expected output is an empty string and for the file's contents to be overwritten.
   */
  @Test
  public void testFileAlreadyExistsBefore() {
    // Call the command
    arguments.add("fileRepeat");
    arguments.add("Overwrite");
    String output = echo.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals("", output);

    // Check that the contents of the existing file are overwritten
    String content = MockFileSystem.getFileContents(fs, "fileRepeat");
    assertEquals("Overwrite", content);
  }

  /**
   * This test checks the behavior of EchoOverwrite when attempting to overwrite a file that does
   * not exist. The expected output is an empty string and for the file to be created and its
   * contents to be set.
   */
  @Test
  public void testFileDidNotExistBefore() {
    // Call the command
    arguments.add(0, "dir1_1/dir2_1/newFile");
    arguments.add(1, "New file's content");
    String output = echo.performOperation(fs, arguments);

    // Check that an empty string is returned
    assertEquals(output, "");

    // Check that the new file was created
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, exists);

    // Check that the content of the new file was added
    String content = MockFileSystem.getFileContents(fs, "newFile");
    assertEquals("New file's content", content);
  }

  /**
   * This test checks the behavior of EchoOverwrite when attempting to overwrite a file that does
   * not exist but a directory with the same name already exists. The expected output is an error
   * message and for the file to not be created.
   */
  @Test
  public void testDirectoryWithTheSameNameAlreadyExists() {
    // Call the command
    arguments.add("dirRepeat");
    arguments.add("Random file content!");
    String output = echo.performOperation(fs, arguments);

    // Check that the correct error message was returned
    assertEquals("!&!A directory with the name dirRepeat already exists!&!", output);

    // Check that file was not created
    boolean exists = MockFileSystem.searchForFileInCurrent(fs, "dirRepeat");
    assertEquals(false, exists);
  }
}
