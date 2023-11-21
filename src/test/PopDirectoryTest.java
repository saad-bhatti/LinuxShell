package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.PopDirectory;
import fileSystem.FileSystem;
import fileSystem.Stack;
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
 * This class is a JUnit Test which tests the public methods of the PopDirectory class
 */
public class PopDirectoryTest {

  /**
   * Represents the PopDirectory object in this JUnit Test class, enables the Test class to use
   * PopDirectory functions
   */
  private PopDirectory popd;

  /**
   * Represents the FileSystem in JShell
   */
  private FileSystem fs;

  /**
   * Represents the arguments provided by the user to the PopDirectory class
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setup() {
    popd = new PopDirectory();
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
   * This test tests the PopDirectory gives the proper error message when the empty stack is given.
   */
  @Test
  public void testEmptyStack() {
    Stack stack = new Stack();
    arguments.add(stack);
    String output = popd.performOperation(fs, arguments);
    String expected = "!&!Cannot pop to a directory as there is no path saved in the stack!&!";
    assertEquals(expected, output);
  }

  /**
   * This test checks whether the PopDirectory goes to the correct directory after one pop with an
   * one-path stack given
   */
  @Test
  public void testStackWithOnePath() {
    Stack stack = new Stack();
    stack.setTopString("/dir1_1");
    arguments.add(stack);
    popd.performOperation(fs, arguments);
    String output = fs.getCurrentDir().getName();
    String expected = "dir1_1";
    assertEquals(expected, output);
  }

  /**
   * This test checks whether the PopDirectory goes to the correct directory after one pop with a
   * multiple-paths stack given
   */
  @Test
  public void testStackWithMultiplePaths() {
    Stack stack = new Stack();
    stack.setTopString("/");
    stack.setTopString("/dir1_1");
    arguments.add(stack);
    popd.performOperation(fs, arguments);
    String output = fs.getCurrentDir().getName();
    String expected = "dir1_1";
    assertEquals(expected, output);
  }

  /**
   * This test checks whether the PopDirectory goes to the right directory after two popd with a
   * multiple-paths stack given
   */
  @Test
  public void testPopMultipleTimes() {
    Stack stack = new Stack();
    stack.setTopString("/dirRepeat");
    stack.setTopString("/dir1_1");
    arguments.add(stack);
    popd.performOperation(fs, arguments);
    popd.performOperation(fs, arguments);
    String output = fs.getCurrentDir().getName();
    String expected = "dirRepeat";
    assertEquals(expected, output);
  }

}
