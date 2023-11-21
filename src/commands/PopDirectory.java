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
 * Represents popd command in JShell
 */
public class PopDirectory extends CommandHandler {

  /**
   * The instance variable of ChangeDirectory class, enables class to use methods from
   * ChangeDirectory class
   */
  ChangeDirectory cd;
  /**
   * The instance variable of Stack class, enables class to use methods from Stack class
   */
  Stack stack;

  /**
   * Constructs a new PopDirectory object. Initialize the cd to a new ChangeDirectory object.
   */
  public PopDirectory() {
    cd = new ChangeDirectory();
  }


  /**
   * {@inheritDoc} Change the directory to the most recent directory stored in Stack. Returns "" if
   * no error occurred, return specified error message otherwise.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass
   * @return "" if no error occurred, specified error message if error occurred.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Get the stack from arguments at index 0
    stack = (Stack) arguments.get(0);

    // If the stack is empty
    if (stack.getStack().isEmpty())
      return "!&!Cannot pop to a directory as there is no path saved in the stack!&!";

    // If the stack is not empty
    else {
      // Get the path saved at the top of the stack
      String topString = stack.getTopString();

      // Add the string to the commandArray of super
      super.commandArray.clear();
      super.commandArray.add(topString);

      // Change the directory specified by the path
      errorMessage = cd.performOperation(fileSystem, super.commandArray);

      // If unable to reach the directory, return the error message
      // (In assignment 2A, we will always reach this directory
      if (!errorMessage.isEmpty())
        return errorMessage;
    }
    // Return an empty string to indicate no error occurred
    return "";
  }
}
