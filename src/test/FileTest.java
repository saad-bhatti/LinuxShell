package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import fileSystem.File;

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
 * This JUnit Test tests the methods of File Class (excluding setters and getters)
 */
public class FileTest {

  /**
   * File object. The methods of this object will be tested
   */
  private File file;

  /**
   * Initializes the file object
   */
  @Before
  public void setUp() {
    file = new File();
  }

  /**
   * Tests the constructor with one parameter, ensuring that the file name is set
   */
  @Test
  public void testOneParameterConstructor() {
    // Call the constructor
    file = new File("zumran");

    // Check that the file name is correct
    assertEquals("zumran", file.getName());
  }

  /**
   * Tests the constructor with two parameters, ensuring that the file name and content are set
   */
  @Test
  public void testTwoParameterConstructor() {
    // Call the constructor
    file = new File("zumran", "nain");

    // Check that the file name was set
    assertEquals("zumran", file.getName());

    // Check that the content of the file was set
    assertEquals("nain", file.getContent());
  }
}
