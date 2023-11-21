package fileSystem;

import java.io.Serializable;
import java.util.ArrayList;

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
 * Represents the Log in JShell that stores all user inputs
 */
public class Log implements Serializable {
  /**
   * Log serialVersionUID value for Serial purpose
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * The record that contains all user inputs
   */
  private ArrayList<String> records;

  /**
   * Constructs a new Log object and initialize the records variable
   */
  public Log() {
    // intialize the records to a ArrayList of type String
    this.records = new ArrayList<String>();
  }

  /**
   * Adds userInput to records. Does not throw any exceptions
   * 
   * @param userInput The user input in the JShell
   */

  public void addToRecords(String userInput) {
    if (!userInput.isEmpty()) {
      this.records.add(userInput);
    }
  }

  /**
   * Getter for Records. Does not throw any exceptions
   * 
   * @return The array list records that contains all user inputs
   */

  public ArrayList<String> getRecords() {
    return this.records;
  }
}
