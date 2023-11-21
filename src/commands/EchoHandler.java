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
 * Represents the simple echo command
 *
 */
public class EchoHandler extends CommandHandler {

  /**
   * Default Constructor of EchoHandler class
   */
  public EchoHandler() {}

  /**
   * {@inheritDoc} This method prints the user input givenText where no text file name is specified
   * 
   * @param fileSystem is the current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments is a container that contains the specific arguments required for the method of
   *        the subclass.
   * 
   * @return A string that was contained within the arguments array list
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Text to print is at index 0 of the arguments
    String givenText = (String) arguments.get(0);

    // Return the text
    return givenText;
  }
}
