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
 * Creates a directory inside the current directory or absolute directory and pass back an error
 * message if directory or file with same name already exist.
 */
public class MakeDirectory extends CommandHandler {

  // Instance Variables
  /**
   * used to create a new directory and put it inside the specified directory
   */
  Directory newDirectory;
  /**
   * used to traverse the directory if absolute path class
   */
  private ChangeDirectory cd;

  /**
   * Constructor initializes the instance variable
   */
  public MakeDirectory() {
    newDirectory = null;
    cd = new ChangeDirectory();
  }

  /**
   * This method stores the new directory in the current directory. If no error occurs, return "".
   * If a directory with the same name exists, return an errorMessage
   */
  private String makeDirectoryInCurrent(FileSystem fileSystem, String givenName) {

    // Search if a directory with the same name already exist
    super.searchForDirectory(fileSystem.getCurrentDir(), givenName);

    // If a directory with the same name does exist, return an error message
    if (super.directoryObject != null)
      return "!&!The directory " + givenName + " already exists!&!";

    // Search of a file with the same name already exists
    super.searchForFile(fileSystem.getCurrentDir(), givenName);

    // If a file with the same name does exist, return an error message
    if (super.fileObject != null)
      return "!&!A file with the name " + givenName + " already exists!&!";

    // If a directory does not exist, create the new directory
    newDirectory = new Directory(givenName);

    // Add newDirectory to the childrenDirectories of currentDirectory
    fileSystem.getCurrentDir().addChildDirectory(newDirectory);

    // Set currentDirectory as the parent of newDirectory
    newDirectory.setParentDirectory(fileSystem.getCurrentDir());

    // Return "" to indicate directory was created successfully
    return "";
  }

  /**
   * This method stores the new directory in the directory specified by a path. If an error occurs,
   * return an error message. If no error occurs, return by creating the directory in the new
   * current directory.
   */
  private String makeDirectoryWithPath(FileSystem fileSystem, String givenName, String path) {

    // Attempt to change the current directory to the parent directory of the new directory
    // being made.
    super.commandArray.clear();
    super.commandArray.add((Object) path);
    errorMessage = cd.performOperation(fileSystem, super.commandArray);

    // If the parent directory could not be found, return errorMessage
    if (!errorMessage.isEmpty())
      return errorMessage;

    // If this line reached, successfully changed current directory to the parent directory.
    // Now storing the new directory in the current directory.
    return this.makeDirectoryInCurrent(fileSystem, givenName);
  }

  /**
   * {@inheritDoc} This method provides implementation of performOperation. This method creates new
   * directories if no error is encountered, and returns empty String ("") to indicate it was
   * successful. Directories will be made until all directories given are made or an error is
   * reached, in which a specific error message is returned.
   * 
   * @param fileSystem of type FileSystem contains current working directory
   * @param arguments An array list contains the specified paths and directory names which will be
   *        created
   * @return An empty String if operation is successful. Otherwise, returns a specific error message
   */
  @Override

  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    errorMessage = "";
    // The path given by the user is stored in index 0 of the ArrayList
    String givenPath = (String) arguments.get(0);
    // Track the original current directory of the file system
    final Directory currentDirHolder = fileSystem.getCurrentDir();
    // Call to a super method to split the given path with regex &
    String[] paths = super.splitWithAndSymbol(givenPath);
    // Variables needed in the for loop
    String name, pathToParent;

    // Attempt to create each directory individually
    for (String path : paths) {
      if (path.contentEquals("The name of a directory contains an invalid symbol"))
        return "!&!" + path + "!&!";
      // Get name of the directory to be created and remove it from the path
      name = super.splitAndGetLast(path);
      // Storing the new directory in the current directory.
      if (super.pathObject.isPathEmpty()) {
        // Call the private method.
        errorMessage = this.makeDirectoryInCurrent(fileSystem, name);
        // If error occurs while creating the new directory, return errorMessage
        if (!errorMessage.isEmpty())
          return errorMessage;
      }
      // Not storing the new directory in the current directory.
      else {
        // Get the path to parent as a string
        pathToParent = super.pathObject.toString();
        // Call the private method.
        errorMessage = this.makeDirectoryWithPath(fileSystem, name, pathToParent);
        // Set the current directory back to the initial directory
        fileSystem.setCurrentDir(currentDirHolder);
        // If the error occurred while changing to the parent directory, return errorMessage
        if (!errorMessage.isEmpty())
          return errorMessage;
      }
    }
    // If this line is reached, then successfully created both directories.
    return "";
  }
}
