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
 * Represents the change directory command. Changes the current working directory and to the
 * specified directory provided by the user
 */

public class ChangeDirectory extends CommandHandler {

  /**
   * A instance Directory type variable that represents the directory going into next
   */
  private Directory nextDirectory;

  /**
   * Takes in no arguments and call the super constructor which initializes the fields of the super
   * class constructor.
   */
  public ChangeDirectory() {}

  /**
   * {@inheritDoc} The perform Operation method changes the current directory to the directory
   * specified in the ArrayList. If there is no error encountered, it returns "". If an error
   * occurs, then return the specified error message
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass.
   * @return "" if the program execute properly without error, error message otherwise
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // The path given by user is stored in index 0 in the ArrayList
    String givenPath = (String) arguments.get(0);
    // Track the original current directory of the file system
    final Directory currentDirHolder = fileSystem.getCurrentDir();
    // Call to a super method to split the given path with regex /
    super.splitWithBackSlash(givenPath);

    // Traverse the specified path, changing the current directory at each element
    for (String nameFromPath : super.pathObject.getCurrentPath()) {
      // Changing to root directory
      if (nameFromPath.contentEquals("/"))
        fileSystem.setCurrentDir(fileSystem.getRoot());
      // Remain in current directory
      else if (nameFromPath.contentEquals("."))
        continue;
      // Changing to the parent directory of the current directory
      else if (nameFromPath.contentEquals("..")) {
        // Assign the parent of current directory to nextDirectory
        nextDirectory = fileSystem.getCurrentDir().getParentDirectory();
        // If the parent of current directory is null, then return an error message
        if (nextDirectory == null) {
          fileSystem.setCurrentDir(currentDirHolder);
          return "!&!The root does not have a parent directory!&!";
        }
        // If the parent of current directory does exist, then change current directory to it
        fileSystem.setCurrentDir(nextDirectory);
      }
      // Changing to a specified directory in the children of the current directory
      else {
        // Call to super
        super.searchForDirectory(fileSystem.getCurrentDir(), nameFromPath);
        // If the specified directory was not found, then return an error message
        if (super.directoryObject == null) {
          fileSystem.setCurrentDir(currentDirHolder);
          return "!&!The directory " + nameFromPath + " does not exist!&!";
        }
        // If the specified directory was found, change current directory to it
        fileSystem.setCurrentDir(super.directoryObject);
      }
    }
    // If this line is reached, then successfully changed directory according to the specified path
    return "";
  }
}
