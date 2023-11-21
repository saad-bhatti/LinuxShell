package test;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Directory;
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
 * This JUnit Test tests the methods of FileSystem Class (excluding setters and getters)
 */
public class FileSystemTest {

  /**
   * FileSystem Object. The methods of this command will be tested.
   */
  private FileSystem fs;

  /**
   * Sets up the mock file system
   */
  @Before
  public void setUp() {
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
  }

  /**
   * Tests the Singleton Design of the file system. The expected behavior is for the second file
   * system object to be pointing to the same file system.
   */
  @Test
  public void testCreateFileSystemInstance() {
    // Create the new file system object
    FileSystem newFS = FileSystem.createFileSystemInstance();

    // Check that the new file system object is pointing to the existing file system
    assertEquals(newFS, fs);
  }

  /**
   * Tests the iterator of the file system.
   */
  @Test
  public void testIterator() {
    // Create the iterator object
    Iterator<Directory> iterator = fs.iterator();
    Directory current;

    // Check that the iterator's next is the root
    current = iterator.next();
    assertEquals("/", current.getName());

    // Check that the iterator's next is dir1_1
    current = iterator.next();
    assertEquals("dir1_1", current.getName());

    // Check that the iterator's next is dir2_1
    current = iterator.next();
    assertEquals("dir2_1", current.getName());
  }
}
