package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Path;

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
 * This JUnit Test tests the methods of Path Class (excluding the setters and getter)
 */
public class PathTest {

  /**
   * The Path object. The methods of this object will be tested
   */
  private Path<String> path;

  /**
   * Initializes the path object
   */
  @Before
  public void setUp() {
    path = new Path<>();
  }

  /**
   * Tests the method GetLastAndRemove when the path object is empty
   */
  @Test
  public void testGetLastAndRemoveOnEmpty() {
    // Call the method
    String output = path.getLastAndRemoveIt();

    // Check that null was returned
    assertEquals(null, output);
  }

  /**
   * Tests the method GetLastAndRemove when the path object is contains a string
   */
  @Test
  public void testGetLastAndRemoveNonEmpty() {
    // Call the method
    path.addToPath("Path123");
    path.addToPath("Path456");
    String output = path.getLastAndRemoveIt();

    // Check that the correct string was returned
    assertEquals("Path456", output);
  }

  /**
   * Tests the toString method of the path object to see if it returns the correct output
   */
  @Test
  public void testToStringMethod() {
    // Call the method
    path.addToPath("Path123");
    path.addToPath("Path456");
    String output = path.toString();

    // Check that the correct string was returned
    assertEquals("Path123/Path456/", output);
  }
}
