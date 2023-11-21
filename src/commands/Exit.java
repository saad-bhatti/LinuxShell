package commands;

import java.util.ArrayList;
import fileSystem.*;

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
 * Represents the Exit command in JShell
 */
public class Exit extends CommandHandler {

  /**
   * Default Constructor of Exit class
   */
  public Exit() {

  }

  /**
   * {@inheritDoc} This method stops the Jshell class from running by adding 1 to the ArrayList
   * running
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass.
   * @return "" if the method runs properly
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Get the ArrayList that is stored at index 0
    @SuppressWarnings("unchecked")
    ArrayList<String> running = (ArrayList<String>) arguments.get(0);

    // Add an element to running to change loop condition
    running.add("1");

    // Return empty string to indicate success
    return "";
  }
}
