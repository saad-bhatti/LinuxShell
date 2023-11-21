/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.PrintWorkingDirectory;
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

public class PrintWorkingDirectoryTest {

  // Instance Variables
  private FileSystem filesystem;
  private ArrayList<Object> arguments;
  private PrintWorkingDirectory pwd;

  @Before
  public void setUp() {
    this.filesystem = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(filesystem);
    MockFileSystem.setCurrentDirectory(filesystem, "/");
    this.pwd = new PrintWorkingDirectory();
    this.arguments = new ArrayList<>();
  }

  /**
   * This tests that the absolute path of the root is returned.
   */
  @Test
  public void testCurrentIsRoot() {
    assertEquals("/", pwd.performOperation(filesystem, arguments));
  }

  /**
   * This tests that the absolute path of the current directory, that is not the root, is returned.
   */
  @Test
  public void testCurrentIsNotRoot() {
    MockFileSystem.setCurrentDirectory(filesystem, "/dir1_1/dir2_1");
    assertEquals("/dir1_1/dir2_1", pwd.performOperation(filesystem, arguments));
  }

}
