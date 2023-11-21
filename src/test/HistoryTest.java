package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.History;
import fileSystem.Log;

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
 * This class is a JUnit Test which tests the public methods of the History class
 */
public class HistoryTest {

  /**
   * Represents the History object in this JUnit Test class, enables the Test class to use History
   * functions
   */
  private History history;
  /**
   * Represents the arguments provided by the user to the history class
   */
  private ArrayList<Object> arguments;

  /**
   * This method initializes the instance variables and builds a mock file system.
   */
  @Before
  public void setUp() {
    history = new History();
    arguments = new ArrayList<Object>();
  }

  /**
   * This test tests the case when there is no integer provided to the history command. The expected
   * output should be every user input recorded in the Log with history included.
   */
  @Test
  public void testNoInteger() {
    Log l = new Log();
    l.addToRecords("qwer");
    l.addToRecords("pwd");
    l.addToRecords("history");
    arguments.add(l);
    String output = history.performOperation(null, arguments);
    String expected = "1. qwer\n2. pwd\n3. history";
    assertEquals(expected, output);
  }

  /**
   * This test tests the case when there is integer provided to the history command. The integer
   * should be larger than the total number of lines stored in log. The expected output should be
   * every user input recorded in the Log with history included.
   */
  @Test
  public void testIntegerLargerThanTotal() {
    Log l = new Log();
    l.addToRecords("history");
    l.addToRecords("pwd");
    l.addToRecords("cd ..");
    l.addToRecords("history 10");
    arguments.add(l);
    arguments.add("10");
    String output = history.performOperation(null, arguments);
    String expected = "1. history\n2. pwd\n3. cd ..\n4. history 10";
    assertEquals(expected, output);
  }

  /**
   * This test tests the case when there is integer provided to the history command. The integer
   * should be smaller than the total number of lines stored in log. The expected output should be
   * the last integer number of user inputs recorded in the Log with history included.
   */
  @Test
  public void testIntegerSmallerThanTotal() {
    Log l = new Log();
    l.addToRecords("history");
    l.addToRecords("pwd");
    l.addToRecords("cd ..");
    l.addToRecords("history 2");
    arguments.add(l);
    arguments.add("2");
    String output = history.performOperation(null, arguments);
    String expected = "3. cd ..\n4. history 2";
    assertEquals(expected, output);
  }
}
