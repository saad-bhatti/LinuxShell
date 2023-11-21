package commands;

import java.util.ArrayList;
import java.util.Iterator;
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
 * Represents the ls command in JShell
 */
public class ListContent extends CommandHandler {

  /**
   * Responsible for changing directories when a path is provided
   */
  private ChangeDirectory cd;
  /**
   * Responsible for getting the full path of the current directory of the file system
   */
  private PrintWorkingDirectory pwd;
  /**
   * True if recursion is occurring. Otherwise false.
   */
  boolean recursion;
  /**
   * Responsible for containing the output of the command
   */
  private StringBuilder output;

  /**
   * Constructor of the ListContent object. Initializes the two fields.
   */
  public ListContent() {
    output = new StringBuilder();
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
  }

  ///////////////////////////////////// Appending Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Appends the names of all directories within the current directory to the output. If the current
   * directory contains no directories, then append nothing.
   * 
   * @param currentDirectory The directory whose directories' names are being appended
   */
  private void printAllDirectoryNames(Directory currentDirectory) {

    // Traverse through the ArrayList of all directories within the current directory
    for (Directory dir : currentDirectory.getChildDirectory())
      // Append the name of the directory to the output
      output.append(dir.getName() + "\n");
  }


  /**
   * Appends the names of all files within the current directory to the output. If there are no
   * files within current directory, then append nothing.
   * 
   * @param currentDirectory The directory whose files' names are being appended
   */
  private void printAllFileNames(Directory currentDirectory) {

    // Traverse through the ArrayList of all files within the current directory
    for (File file : currentDirectory.getFilesInCurrentDirectory())
      // Append the name of the directory to the output
      output.append(file.getName() + "\n");
  }

  ///////////////////////////////// List Content in Current \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Appends the names of all directories and files within the current directory to the output.
   * Append nothing if there are no items within the current directory.
   * 
   * @param currentDirectory The current directory whose items' names are being appended
   */
  private void listContentInCurrent(Directory currentDirectory) {

    // Print all of the names of the directories within current directory, if there are any
    this.printAllDirectoryNames(currentDirectory);

    // Print all of the names of the directories within current directory, if there are any
    this.printAllFileNames(currentDirectory);
  }

  /**
   * Recursively appends all of the content of the current directory and its children to the output.
   * 
   * @param fs The FileSystem to which dir belongs to
   * @param dir The directory whose content will be recursively listed
   */
  private void recursivelyListContentInCurrent(FileSystem fs, Directory dir) {

    // Get the current directory of the file system
    Directory cwdHolder = fs.getCurrentDir();

    // List content of the current directory
    fs.setCurrentDir(dir);
    String pathOfHead = pwd.performOperation(fs, null);

    // Create the iterator
    Iterator<Directory> iter = fs.iterator(dir);

    // Append the content of each child directory to the output
    String currentPath;
    boolean counter = false;
    if (output.length() != 0)
      output.append("\n\n");
    output.append("Key: . is equal to the directory name " + dir.getName() + "\n\n");

    while (iter.hasNext()) {
      // Set the current directory to the iterator
      fs.setCurrentDir(iter.next());

      // Not the first time appending to the output
      if (counter) {
        if (!pathOfHead.contentEquals("/"))
          currentPath = pwd.performOperation(fs, null).replace(pathOfHead + "/", "./");
        else
          currentPath = pwd.performOperation(fs, null).replaceFirst("/", "./");
        output.append("\n" + currentPath + ":\n");
      }

      // First time appending to the output
      else {
        output.append(".:\n");
        counter = true;
      }

      // Append the content of the directory
      this.listContentInCurrent(fs.getCurrentDir());
    }

    // Set the previous current directory
    fs.setCurrentDir(cwdHolder);
  }

  ////////////////////////// List Content For Single Object in Current \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * If only one path is provided by the user, if the path is pointed to a file, then print the path
   * to the file. If the path is a directory, then print everything under that directory. Returns ""
   * if the method runs correctly, returns specified error message otherwise.
   * 
   * @param fs The current file system in the JShell and contains a reference to the root and
   *        current directory.
   * @param objectName The name of the object specified by the user
   * @param path The path entered by the user
   * @return "" if runs correctly, specified error message otherwise.
   */
  private void listContentForSingleObject(FileSystem fs, String objectName, String path) {

    // Use super method to search if the object is specifying a file
    super.searchForFile(fs.getCurrentDir(), objectName);

    // If the object is a file, append the file's name
    if (super.fileObject != null) {
      if (path.isEmpty())
        output.append(objectName + "\n");
      else
        output.append(path + objectName + "\n");
      return;
    }

    // Use super method to search if the object is specifying a directory
    super.searchForDirectory(fs.getCurrentDir(), objectName);

    // If the object is a directory, return by calling private method
    if (super.directoryObject != null) {

      // If recursion is not occurring
      if (!recursion)
        this.listContentInCurrent(super.directoryObject);

      // If recursion is occurring
      else
        this.recursivelyListContentInCurrent(fs, directoryObject);

      return;
    }

    // If this line is reached, then the object does not exist. Append an error message.
    output.append("!&!" + objectName + " does not exist!&!" + "\n");
    return;
  }

  /////////////////////////////// List Content With Path \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method is responsible for changing the current directory to the directory specified by the
   * path. If changing the directory is successful, then return by calling the method
   * ListContentForSingleObject. If changing the directory was unsuccessful, append the specified
   * error message.
   * 
   * @param fs The current file system in the JShell and contains a reference to the root and
   *        current directory.
   * @param objectName The name of the object specified by the user
   * @param path The path entered by the user
   */
  private void changeToParent(FileSystem fs, String objectName, String stringPath) {

    // Attempt to change the current directory to the directory specified by the path.
    super.commandArray.clear();
    super.commandArray.add((Object) stringPath);
    errorMessage = cd.performOperation(fs, super.commandArray);

    // If the directory could not be found, append errorMessage
    if (!super.errorMessage.isEmpty()) {
      output.append(errorMessage + "\n");
      return;
    }
    // If this line reached, successfully changed current directory to the parent directory.
    this.listContentForSingleObject(fs, objectName, stringPath);
  }

  /**
   * If the object to list the content for is a special case, then calls the proper method to list
   * its content and returns true. Otherwise, this method returns false.
   * 
   * @param fs The current file system in the JShell and contains a reference to the root and
   *        current directory.
   * @param objectName String containing the name of the item that will be listed
   * @param dir The original current directory of the file system
   * @return Boolean true if object is a special case. Otherwise false
   */
  private boolean specialCases(FileSystem fs, String objectName, Directory dir) {

    // Special case 1: objectName == '.'
    if (objectName.contentEquals(".")) {
      // If recursion is not happening
      if (!recursion)
        this.listContentInCurrent(fs.getCurrentDir());
      // If recursion is happening
      else
        this.recursivelyListContentInCurrent(fs, fs.getCurrentDir());
      return true;
    }

    // Special case 2: objectName == '/'
    else if (objectName.contentEquals("/")) {
      // If recursion is not happening
      if (!recursion)
        this.listContentInCurrent(fs.getRoot());
      // If recursion is happening
      else
        this.recursivelyListContentInCurrent(fs, fs.getRoot());
      return true;
    }

    // Special case 3: objectName == '..'
    else if (objectName.contains("..")) {
      // Not currently in the root
      if (fs.getCurrentDir().getParentDirectory() != null) {
        // If recursion is not happening
        if (!recursion)
          this.listContentInCurrent(fs.getCurrentDir().getParentDirectory());
        // If recursion is happening
        else
          this.recursivelyListContentInCurrent(fs, fs.getCurrentDir().getParentDirectory());
      }
      // Currently in the root
      else
        output.append("!&!The root does not have a parent directory!&!" + "\n");
      return true;
    }
    // Not a special case
    return false;
  }

  /**
   * Lists the content of a directory or appends the path of a file. If unsuccessful, it appends the
   * error message to the output.
   * 
   * @param fs The current file system in the JShell and contains a reference to the root and
   *        current directory.
   * @param givenPath The paths entered by the user
   * @param currentDirHolder The directory object that holds the current directory when using this
   *        function
   */
  private void listContentWithPath(FileSystem fs, String givenPath, Directory cwdHolder) {

    String[] paths = super.splitWithAndSymbol(givenPath);
    String objectName, pathToParent;
    // For each individual path given
    for (String path : paths) {

      // Get name of the object and remove it from the path
      objectName = super.splitAndGetLast(path);

      // Check if this path is a special case
      boolean completed = this.specialCases(fs, objectName, cwdHolder);

      // If it is not a special case
      if (!completed) {

        // Listing the content for item within the current directory.
        if (super.pathObject.isPathEmpty())
          this.listContentForSingleObject(fs, objectName, path);

        // Listing the content for item within a different directory.
        else {
          pathToParent = super.pathObject.toString();
          this.changeToParent(fs, objectName, pathToParent);
          fs.setCurrentDir(cwdHolder);
        }
      }
      // If an error occurred, stop
      if (output.toString().contains("!&!"))
        break;
    }
  }

  ////////////////////////////////////////// Helper Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method go through all paths given by the user, then separated them into different cases,
   * then apply different list content methods to those paths.
   * @param paths String that contains all paths provided by the user
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param currentDirHolder The original directory of the file system in the beginning
   */
  public void goThroughAllPathGiven(String paths, FileSystem fileSystem,
      Directory currentDirHolder) {
    String inputs[] = super.splitWithAndSymbol(paths);

    // If no path is provided, listing content of the current directory
    if (paths.isEmpty()) {
      if (!recursion)
        this.listContentInCurrent(fileSystem.getCurrentDir());
      else
        this.recursivelyListContentInCurrent(fileSystem, fileSystem.getCurrentDir());
    } else {
      for (int i = 0; i < inputs.length; i++) {
        if (!inputs[i].contains("/") && !inputs[i].contains(".")) {
          this.listContentForSingleObject(fileSystem, inputs[i], "");
          if (output.toString().contains("!&!")) {
            break;
          }
        }

        // If there is a path (or paths) provided
        else {
          this.listContentWithPath(fileSystem, inputs[i], currentDirHolder);
          if (output.toString().contains("!&!")) {
            break;
          }
        }
      }
    }
  }


  /////////////////////////////////////////// Main Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * {@inheritDoc} Lists all content of the paths given, with the possibility of recursion. Returns
   * the output of the command, and if an error occurs, an error message will appended to the output
   * produced before the error.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass.
   * @return The successful output of the command, appended with an error message if an error occurs
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Setting initial value
    errorMessage = "";

    // Get the arguments for the command from index 0 of arguments
    String paths = (String) arguments.get(0);
    // Check if recursion is happening
    recursion = paths.startsWith("-R");

    // Track the original current directory of the file system
    final Directory currentDirHolder = fileSystem.getCurrentDir();

    // Remove the '-R' from the givenPaths
    if (recursion) {
      paths = paths.replace("-R&", "");
      paths = paths.replace("-R", "");
    }

    this.goThroughAllPathGiven(paths, fileSystem, currentDirHolder);
    // Set the current directory again
    fileSystem.setCurrentDir(currentDirHolder);

    // Return the output
    if (output.length() == 0)
      return "";
    return output.deleteCharAt(output.length() - 1).toString();
  }
}
