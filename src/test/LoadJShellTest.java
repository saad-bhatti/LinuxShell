package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.LoadJShell;
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
 * This class depends on saveJShell class to create a file first so that it can read it later.
 * Tests if the loadJShell can read a file
 */

public class LoadJShellTest {

  /**
   * Have the LoadJShell command to read a file on user computer created by saveJShell
   */
  private LoadJShell load;
  /**
   * Have the SaveJShell command to create a file on user computer
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
   * To pass in the argument like file name
   */
  private ArrayList<Object> args;
  /**
   * To test the expected result given by the function
   */
  private ArrayList<Object> expected;
  /**
   * To hold the string representation of file name
   */
  String fileName;


  @Before
  public void setUp() {
    load = new LoadJShell();
    save = new SaveJShell();
    fs = FileSystem.createFileSystemInstance();
    MockFileSystem.setUpMockFileSystem(fs);
    MockFileSystem.setCurrentDirectory(fs, "/");
    log = new Log();
    stk = new Stack();
    args = new ArrayList<>();
    expected = new ArrayList<>();

    // file name is test2 at the root folder of eclipse (path)
    fileName = "loadTest";
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
   * Create a file which was not saved by SaveJShell. Reads an improper formatted file and checks if
   * the exception get thrown by the function
   */
  @Test
  public void test1ReadImproperFormattedFile() {
    args.add(log);
    args.add(fileName);
    save.performOperation(fs, args);
    args.clear();
    args.add(fileName);
    expected.add("!&!File with improper format is being read. Cannot load the previous JShell!&!");
    assertEquals(expected, load.performOperation(args));
  }

  /**
   * Override the test2 file created in test1 and read the proper formatted file and load it
   */
  @Test
  public void test2ReadCorrectFormattedFile() {
    expected.add(log);
    expected.add(stk);
    expected.add(fileName);
    save.performOperation(fs, expected);
    expected.remove(fileName);
    expected.add(fs);
    args.add(fileName);
    args = load.performOperation(args);
    assertTrue(args.get(0) instanceof Log);
    assertTrue(args.get(1) instanceof Stack);
    assertTrue(args.get(2) instanceof FileSystem);
  }
}
