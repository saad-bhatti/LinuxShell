package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: bhatti97
// UT Student #: 1006448248
// Author: Saad Mohy-Uddin Bhatti
//
// Student2:
// UTORID user_name: azizawai
// UT Student #: 1006103681
// Author: Awais Aziz
//
// Student3:
// UTORID user_name: changh31
// UT Student #: 1006205394
// Author: Haowen Chang
//
// Student4:
// UTORID user_name: nainzumr
// UT Student #: 1005954139
// Author: Zumran Nain
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// **

/**
 * This JUnit Test tests the methods of Directory Class (excluding setters and getters)
 */
public class DirectoryTest {

  /**
   * Represents the file system
   */
  private FileSystem fs;

  /**
   * Initializes the file system
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
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
   * Tests the behavior of the method addFilesInCurrentDirectory. The expected behavior is for the
   * file to be added to the current directory
   */
  @Test
  public void testAddingFiles() {
    // Call the method
    File file = new File("newFile");
    fs.getCurrentDir().addFilesInCurrentDirectory(file);

    // Check that the file was added to the current directory
    boolean added = MockFileSystem.searchForFileInCurrent(fs, "newFile");
    assertEquals(true, added);
  }

  /**
   * Tests the behavior of the method addChildDirectory. The expected behavior is for the
   * directory to be added to the current directory
   */
  @Test
  public void testAddChildDirectory() {
    // Call the method
    Directory dir = new Directory("newDirectory");
    fs.getCurrentDir().addChildDirectory(dir);
    
    // Check that the directory was added to the current directory
    boolean exists = MockFileSystem.searchForDirectoryInCurrent(fs, "newDirectory");
    assertEquals(true, exists);
  }
}
