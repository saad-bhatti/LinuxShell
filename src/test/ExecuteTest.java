package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.FileSystem;
import fileSystem.Log;
import fileSystem.Stack;
import java.lang.reflect.Field;
import java.util.ArrayList;
import shell.Execute;
import static org.junit.Assert.assertEquals;

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
 * This class is a JUnit Test which tests the public methods of the Execute class. Mainly focused on
 * if it can call instantiate other class object
 */
public class ExecuteTest {
  /**
   * FileSystem object. The mock file system will be based around this.
   */
  private FileSystem fs;
  /**
   * ArrayList of type Object. It will contain the arguments for the methods that are being tested.
   */
  private ArrayList<Object> arguments;
  /**
   * Execute of object. It will instantiate other class to execute command
   */
  private Execute exe;
  /**
   * To hold the error message
   */
  String output;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setup() {
    exe = new Execute();
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    arguments = new ArrayList<>();
    output = "";
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
   * This method deletes the existing file system.
   * 
   * @throws Exception. If the field is not belonging to FileSystem, throw the exception
   */
  // @After
  // public void tearDown() throws Exception {
  // Field field = (fs.getClass()).getDeclaredField("createdFileSystem");
  // field.setAccessible(true);
  // field.set(null, null);
  // }

  /**
   * This test checks the behavior of Exit class when this command is called by the user. It gets
   * created at runtime. produces the right expected output which is empty string.
   */
  @Test
  public void testExitClass() {
    ArrayList<String> exit = new ArrayList<>();
    arguments.add(exit);
    assertEquals("", exe.executeCommand(fs, "exit", arguments));
  }

  /**
   * This test checks the behavior of PrintWorkingDirectory class when this command is called by the
   * user. It gets created at runtime. The expected output is absolute path from root to current
   * directory
   */
  @Test
  public void testPathRootToCurrent() {
    MockFileSystem.setCurrentDirectory(fs, "/dir1_1/dir2_1");
    assertEquals("/dir1_1/dir2_1", exe.executeCommand(fs, "pwd", arguments));
  }

  /**
   * This test checks the behavior of Manual class when this command is called by the user. It gets
   * created at runtime. The expected output is error message.
   */
  @Test
  public void testManualErrorMsg() {
    String output = "!&!Command test does not exist!&!";
    arguments.add("test");
    assertEquals(output, exe.executeCommand(fs, "man", arguments));
  }

  /**
   * This test checks the behavior of Pop Directory class when this command is called by the user.
   * It gets created at runtime. The expected output is error message.
   */
  @Test
  public void testPopDirectoryErrorMsg() {
    Stack stack = new Stack();
    arguments.add(stack);
    String output = exe.executeCommand(fs, "popd", arguments);
    String expected = "!&!Cannot pop to a directory as there is no path saved in the stack!&!";
    assertEquals(expected, output);
  }

  /**
   * This test checks the behavior of History class when this command is called by the user. It gets
   * created at runtime. The expected output is the log of all the commands that were passed in by
   * user.
   */
  @Test
  public void testHistoryOutput() {
    Log l = new Log();
    l.addToRecords("history 121");
    l.addToRecords("pwd");
    l.addToRecords("exit");
    arguments.add(l);
    arguments.add("10");
    String output = exe.executeCommand(fs, "history", arguments);
    String expected = "1. history 121\n2. pwd\n3. exit";
    assertEquals(expected, output);
  }

  /**
   * This test checks the behavior of Concatenate class when this command is called by the user. It
   * gets created at runtime. The expected output is the error message.
   */
  @Test
  public void testConcatenateErrorMsg() {
    // Call the command
    arguments.add("nonExistant");
    String output = exe.executeCommand(fs, "cat", arguments);

    // Check that the correct error message was returned
    assertEquals("!&!The file nonExistant does not exist!&!", output);
  }
  
  /**
   * This test checks the behavior of Change Directory class when this command is called by the user. It
   * gets created at runtime. The expected output is the error message.
   */
  @Test
  public void testChangeWithInvalidPath() {
    // Call the command
    arguments.add("Existant");
    String output = exe.executeCommand(fs, "cd", arguments);

    // Check that the correct error message is returned
    assertEquals("!&!The directory Existant does not exist!&!", output);
  }

}
