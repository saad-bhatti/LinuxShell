package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Tree;
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

public class TreeTest {

  /**
   * Represents the FileSystem. Directories that are created will be added here.
   */
  private FileSystem fs;
  /**
   * Tree object. The method of this object will be tested.
   */
  private Tree tree;
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
    tree = new Tree();
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
   * This method test that the correct output is returned when only the root exists in the file
   * system.
   */
  @Test
  public void testRoot() {
    // Call the command
    String output = tree.performOperation(fs, arguments);

    // Check that the correct output is returned
    assertEquals("/", output);
  }

  /**
   * This method tests that the correct output is returned when the tree command is called on a file
   * system consisting of multiple directories and files.
   */
  @Test
  public void testMockFileSystem() {
    // Call the command
    MockFileSystem.setUpMockFileSystem(fs);
    String output = tree.performOperation(fs, arguments);

    // Check that the correct output is returned
    String expected = "/\n\tfileRepeat\n\tdir1_1\n\t\tdir2_1\n\t\t\tfileRepeat"
        + "\n\t\t\tdirRepeat\n\tdirRepeat\n\t\tfile2_1";
    assertEquals(expected, output);
  }
}
