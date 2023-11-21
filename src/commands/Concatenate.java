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
 * Represents the Concatenate command.
 */
public class Concatenate extends CommandHandler {

  /**
   * The instance variable of ChangeDirectory class, enables class to use methods from
   * ChangeDirectory class
   */
  private ChangeDirectory cd;
  /**
   * Responsible for containing the output for the concatenate command
   */
  private StringBuilder output;

  /**
   * Constructor initializes the instance variable
   */
  public Concatenate() {
    cd = new ChangeDirectory();
    output = new StringBuilder();
  }

  /**
   * This method prints the contents of a file in the current directory. If no error occurs, return
   * an empty string. If a file with the name nameOfFile does not exist, return an errorMessage.
   */
  private String printFileInCurrent(FileSystem fileSystem, String nameOfFile) {
    // Search for the file with the name nameOfFile in the current directory using super method
    super.searchForFile(fileSystem.getCurrentDir(), nameOfFile);

    // If the file with the specified name does not exist, return an error message
    if (super.fileObject == null)
      return "!&!The file " + nameOfFile + " does not exist!&!";

    // If the file does exist, then add the contents of the file to the output
    output.append(fileObject.getContent() + "\n\n\n");

    // Return an empty string since the file's content was printed successfully
    return "";
  }


  /**
   * This method prints the content of a file that belongs to a directory specified by a path. If an
   * error occurs, return an error message. If no error occurs, return by attempting to print the
   * file's content in the new current directory.
   * 
   */
  private String printFileWithPath(FileSystem fileSystem, String nameOfFile, String path) {
    // Attempt to change the current directory to the parent directory of the directory to which the
    // file with name nameOfFile belongs to.
    super.commandArray.clear();
    super.commandArray.add((Object) path);
    errorMessage = cd.performOperation(fileSystem, super.commandArray);

    // If the parent directory could not be found, return errorMessage
    if (!errorMessage.isEmpty())
      return errorMessage;

    // If this line reached, successfully changed current directory to the parent directory.
    // Now attempting to print the file's content that is located in the new current directory.
    return this.printFileInCurrent(fileSystem, nameOfFile);
  }

  /**
   * {@inheritDoc} The perform Operation method returns the content of file(s) specified by relative
   * or absolute path(s)
   * 
   * @param fileSystem of type FileSystem contains the current working directory
   * @param arguments of type object array list contains the specific arguments required for the
   *        method to search the file and print it.
   * @return String The output of the command. If an error occurs, the error message will be
   *         appended to the output
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Local variables and setting initial value
    errorMessage = "";
    String nameOfFile;
    String pathToParent;
    // The path given by the user is stored in index 0 of the ArrayList
    String givenPaths = (String) arguments.get(0);
    // Track the original current directory of the file system
    final Directory currentDirHolder = fileSystem.getCurrentDir();
    // Call to a super method to split the given path with regex &
    String[] pathsToFiles = super.splitWithAndSymbol(givenPaths);

    // Attempt to create each directory individually
    for (String pathToFile : pathsToFiles) {

      // Get name of the file to be printed and remove it from the path
      nameOfFile = super.splitAndGetLast(pathToFile);
      // Printing the content of a file that is in the current directory.
      if (super.pathObject.isPathEmpty()) {
        // Call the private method.
        errorMessage = this.printFileInCurrent(fileSystem, nameOfFile);
        // If error occurs while trying to print the contents of the file, return errorMessage
        if (!errorMessage.isEmpty()) {
          output.append(errorMessage + "\n\n\n");
          break;
        }
      }

      // Printing a file's content that belongs to a different directory.
      else {
        // Get the path to parent as a string
        pathToParent = super.pathObject.toString();
        // Call the private method.
        errorMessage = this.printFileWithPath(fileSystem, nameOfFile, pathToParent);
        // Set the current directory back to the initial directory
        fileSystem.setCurrentDir(currentDirHolder);
        // If an error occurred while attempting to print the file's content, return errorMessage
        if (!errorMessage.isEmpty()) {
          output.append(errorMessage + "\n\n\n");
          break;
        }
      }
    }
    // Return the output
    output.delete(output.length()-3, output.length());
    return output.toString();
  }
}
