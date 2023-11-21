package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.PushDirectory;
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
 * This class is a JUnit Test which tests the public methods of the PushDirectory class
 */
public class PushDirectoryTest {

  /**
   * Represents the PushDirectory object in this JUnit Test class, enables the Test class to use
   * PushDirectory functions
   */
  private PushDirectory pushd;

  /**
   * Represents the FileSystem in JShell
   */
  private FileSystem fs;

  /**
   * Represents the arguments provided by the user to the PushDirectory class
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setup() {
    pushd = new PushDirectory();
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
   * This test checks the directory after pushd and path stored on the stack when a relative path is
   * provided
   */
  @Test
  public void testPushWithRelativePath() {
    Stack stack = new Stack();
    MockFileSystem.setCurrentDirectory(fs, "/");
    arguments.add(stack);
    arguments.add("dir1_1");
    pushd.performOperation(fs, arguments);
    StringBuilder actual = new StringBuilder();
    actual.append(fs.getCurrentDir().getName());
    actual.append(stack.getTopString());
    StringBuilder expected = new StringBuilder();
    expected.append("dir1_1");
    expected.append("/");
    assertEquals(expected.toString(), actual.toString());
  }

  /**
   * This test checks the directory after pushd and path stored on the stack when an absolute path
   * is provided
   */
  @Test
  public void testPushWithAbsolutePath() {
    Stack stack = new Stack();
    MockFileSystem.setCurrentDirectory(fs, "/");
    arguments.add(stack);
    arguments.add("/dir1_1/dir2_1");
    pushd.performOperation(fs, arguments);
    StringBuilder actual = new StringBuilder();
    actual.append(fs.getCurrentDir().getName());
    actual.append(stack.getTopString());
    StringBuilder expected = new StringBuilder();
    expected.append("dir2_1");
    expected.append("/");
    assertEquals(expected.toString(), actual.toString());
  }

  /**
   * This test checks the directory after pushd two times and the top path stored on the stack when
   * two absolute paths are provided
   */
  @Test
  public void testPushMultipleTimes() {
    Stack stack = new Stack();
    MockFileSystem.setCurrentDirectory(fs, "/");
    arguments.add(stack);
    arguments.add("/dir1_1/dir2_1");
    pushd.performOperation(fs, arguments);
    arguments.clear();
    arguments.add(stack);
    arguments.add("/dir1_1");
    pushd.performOperation(fs, arguments);
    StringBuilder actual = new StringBuilder();
    actual.append(fs.getCurrentDir().getName());
    actual.append(stack.getTopString());
    StringBuilder expected = new StringBuilder();
    expected.append("dir1_1");
    expected.append("//dir1_1/dir2_1");
    assertEquals(expected.toString(), actual.toString());
  }

}
