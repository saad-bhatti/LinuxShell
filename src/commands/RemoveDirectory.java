package commands;

import java.util.ArrayList;
import fileSystem.FileSystem;

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
 * The RemoveDirectory class removes a specific directory provided by a path and its children
 * directories. This class has two fields whose types are ChangeDirectory and PrintWorkingDirectory.
 */
public class RemoveDirectory extends CommandHandler {

  /**
   * Responsible for changing the current directory to the specified path
   */
  private ChangeDirectory cd;
  /**
   * Responsible for getting the full path of the current directory
   */
  private PrintWorkingDirectory pwd;

  /**
   * Constructor that initializes the two fields of RemoveDirectory
   */
  public RemoveDirectory() {
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
  }

  /**
   * This method allows us to remove the directory from the current directory. If no error occurs,
   * returns an empty string. If the directory to be removed does not exist in the current
   * directory, returns an error message describing this.
   * 
   * @param fileSystem The file system whose directory will be removed
   * @param dirName of type String which contains the name of the directory to be removed
   * @return A String that is an empty string or contains an error message
   */
  private String removeDirInCurrent(FileSystem fs, String dirName) {
    // Search for the directory in the current children
    for (int i = 0; i < fs.getCurrentDir().getChildDirectory().size(); i++) {

      // If the directory with dirName is found, remove it from the file system
      if (fs.getCurrentDir().getChildDirectory().get(i).getName().contentEquals(dirName)) {
        fs.getCurrentDir().getChildDirectory().remove(i);
        return "";
      }
    }
    // If not found, return an error message
    return "!&!The directory '" + dirName + "' was not found!&!";
  }

  /**
   * {@inheritDoc} This method provides implementation of performOperation. This method removes a
   * directory from the file system if no error is encountered, and returns empty String to indicate
   * it was successful. If the directory cannot be removed from the file system, then a specific
   * error message is returned.
   * 
   * @param fileSystem The file system whose directory will be removed
   * @param arguments An ArrayList of type Object containing the name of the directory to be removed
   *        at index 0
   * @return A String that is an empty string or contains an error message
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Get the path of the directory to be removed
    String path = (String) arguments.get(0);
    errorMessage = "";

    // If a directory in current directory is being removed
    if (!path.contains("/")) {
      return this.removeDirInCurrent(fileSystem, path);
    }

    // A directory provided by a path is being deleted
    else {
      // Check if a parent directory or current directory is being deleted
      if (pwd.performOperation(fileSystem, commandArray).startsWith(path))
        return "!&!A parent directory or the current directory cannot be removed!&!";

      // Get the name of the directory to be removed
      String nameOfDir = super.splitAndGetLast(path);

      // Save the current directory of the file system
      super.directoryObject = fileSystem.getCurrentDir();

      // Change the current directory to the parent directory of the directory to be removed
      String pathToParent = super.pathObject.toString();
      super.commandArray.add(pathToParent);
      errorMessage = cd.performOperation(fileSystem, commandArray);

      // If the path given was invalid
      if (!errorMessage.isEmpty())
        return errorMessage;
      // Successfully changed the current directory to the parent directory. Call method.
      errorMessage = this.removeDirInCurrent(fileSystem, nameOfDir);

      // Set current directory back to original and return the errorMessage
      fileSystem.setCurrentDir(directoryObject);
      return errorMessage;
    }
  }
}
