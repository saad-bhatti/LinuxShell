package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Log;

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
 * This JUnit Test tests the methods of Log Class
 */
public class LogTest {

  /**
   * Log object. The methods of this object will be tested
   */
  private Log log;

  /**
   * Initializes the log object
   */
  @Before
  public void setUp() {
    log = new Log();
  }

  /**
   * Tests the addToRecords method when providing an empty string. The expected behavior is for the
   * log to remain empty
   */
  @Test
  public void testAddNoInput() {
    // Call the method
    log.addToRecords("");

    // Check that the log is still empty
    assertTrue(log.getRecords().isEmpty());
  }

  /**
   * Tests the addToRecords method when providing an one input. The expected behavior is for the log
   * to contain the single string
   */
  @Test
  public void testAddToRecords() {
    // Call the method
    log.addToRecords("First User Input");

    // Check that the log contains the input
    ArrayList<String> output = log.getRecords();
    assertEquals("[First User Input]", output.toString());

  }

  /**
   * Tests the addToRecords method when providing an multiple inputs. The expected behavior is for
   * the log to contain all of the inputs with the most recent input at the end.
   */
  @Test
  public void TestAddMultipleRecords() {
    // Call the methods
    log.addToRecords("1st Input");
    log.addToRecords("2nd Input");
    log.addToRecords("3rd Input");

    // Check that the log contains the input
    ArrayList<String> output = log.getRecords();
    assertEquals("[1st Input, 2nd Input, 3rd Input]", output.toString());
  }
}
