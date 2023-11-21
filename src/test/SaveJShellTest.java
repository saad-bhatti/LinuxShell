package test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.SaveJShell;
import fileSystem.FileSystem;
import fileSystem.Log;
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
 * Creates a file on the user computer and save contents that were passed by the JShell to save its
 * current status
 */

public class SaveJShellTest {

  /**
   * Have the SaveJShell command to save the content to a file
   */
  private SaveJShell save;
  /**
   * Have the log of the current session
   */
  private Log log;

  /**
   * Have the Stack of the current session
   */
  private Stack stk;

  /**
   * Have the Mock fileSystem of the current session
   */
  private FileSystem fs;

  /**
   * To pass in the argument
   */
  private ArrayList<Object> args;
  /**
   * To hold the string representation of file name
   */
  String fileName;

  /**
   * Set up all the instance variable and initializes them
   */
  @Before
  public void setUp() {
    save = new SaveJShell();
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    log = new Log();
    stk = new Stack();
    args = new ArrayList<>();

    // file name is test1 at the root folder
    fileName = "saveTest";
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
   * Create a file since it does not exist and then write to it
   */
  @Test
  public void test1CreateAFileAndWrite() {
    args.add(log);
    args.add(stk);
    args.add(fileName);
    assertEquals("", save.performOperation(fs, args));
  }

}

