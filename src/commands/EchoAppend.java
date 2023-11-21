package commands;

import java.lang.StringBuilder;
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
 * Represents the command echo in JShell that append input text to files
 *
 */
public class EchoAppend extends CommandHandler {

  /**
   * The instance variable of ChangeDirectory class, enables class to use methods from
   * ChangeDirectory class
   */
  private ChangeDirectory cd;

  /**
   * Constructor initializes the instance variable by creating the cd class object
   */
  public EchoAppend() {
    cd = new ChangeDirectory();
  }

  /**
   * This method appends user input text String givenText to an existing file.
   * 
   * @param givenText The text appends to the file inputs by the user
   */
  private void appendContent(String givenText) {

    // Variable that allow the two strings to be appended
    StringBuilder finalContent = new StringBuilder();

    // Add the existing content to finalContent
    finalContent.append(super.fileObject.getContent() + "\n");

    // Append the givenText to finalContent
    finalContent.append(givenText);

    // Store finalContent in the file
    super.fileObject.setContent(finalContent.toString());
  }

  /**
   * This method creates a new file with user desired filename to store the givenText if the
   * filename file does not exist in the current directory. If the file with same name already
   * exists under the current directory, then appends the givenText to the filename file
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param fileName The user desired file name
   * @param givenText The text appends to the file or stores in the new file inputs by the user
   */
  private void storeTextInCurrent(FileSystem fileSystem, String fileName, String givenText) {

    // Search if the directory has an existing file with name of fileName
    super.searchForFile(fileSystem.getCurrentDir(), fileName);

    // Search if a directory with the same name already exist
    super.searchForDirectory(fileSystem.getCurrentDir(), fileName);

    // If a directory with the same name does exist and a new file has to be created, return an
    // error message
    if (super.directoryObject != null && super.fileObject == null) {
      errorMessage = "!&!A directory with the name " + fileName + " already exists!&!";
      return;
    }

    // File with fileName does not exist and a directory with the same name does not exist
    if (super.fileObject == null) {
      // Create the file
      File newFile = new File(fileName, givenText);

      // Store the file in the current directory
      fileSystem.getCurrentDir().addFilesInCurrentDirectory(newFile);
    }

    // File with fileName does exist
    else {

      // Append givenText to the existing file's content
      this.appendContent(givenText);
    }
  }

  /**
   * This method creates a new file givenName under the path and stores the givenText into that file
   * under the path given by the user if the path exists and givenName file doesn't exist under that
   * path. If the path does not exist, do nothing. If the file givenName exists under that path,
   * then appends the givenText to the givenName file.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param givenName The user desired file name
   * @param path The path specified by the user.
   * @param givenText The text appends to the file or stores in the new file inputs by the user
   */
  private void storeTextWithPath(FileSystem fileSystem, String givenName, String path,
      String givenText) {

    // Attempt to change the current directory to the parent directory of the file.
    super.commandArray.clear();
    super.commandArray.add((Object) path);
    errorMessage = cd.performOperation(fileSystem, this.commandArray);

    // If the parent directory could not be found, stop the method
    if (!errorMessage.isEmpty())
      return;

    // If this line reached, successfully changed current directory to the parent directory.
    // Now storing the new directory in the current directory.
    this.storeTextInCurrent(fileSystem, givenName, givenText);
  }


  /**
   * {@inheritDoc} This method provides implementation of performOperation. This method stores given
   * text in a file under current directory or specified by a path. If the file exists, it appends
   * the content. Otherwise, it stores the content in a new file. If successful, it will return ""
   * and return an error otherwise.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass.
   * @return "" if the method execute without any error, specified errorMessage if error occurs.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Set error message to empty string
    errorMessage = "";
    // The path given by the user is at index 0 of arguments
    String givenPath = (String) arguments.get(0);
    // The text given by the user is at index 1 of arguments
    String givenText = (String) arguments.get(1);
    // Get name of the file that text will be stored in and remove it from the path
    String name = super.splitAndGetLast(givenPath);

    // Storing givenText in the current directory
    if (super.pathObject.isPathEmpty()) {
      // Call the private method.
      this.storeTextInCurrent(fileSystem, name, givenText);
      // Return empty string, which is either an empty string or contains an error message
      return errorMessage;
    }

    // Not storing givenText in the current directory
    else {
      // Get the path to parent as a string
      String pathToParent = super.pathObject.toString();
      // Track the original current directory of the file system
      final Directory currentDirHolder = fileSystem.getCurrentDir();
      // Call the private method
      this.storeTextWithPath(fileSystem, name, pathToParent, givenText);
      // Set the current directory to the initial directory
      fileSystem.setCurrentDir(currentDirHolder);
      // Return empty string, which is either an empty string or contains an error message
      return errorMessage;
    }
  }
}
