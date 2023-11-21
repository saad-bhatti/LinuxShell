package commands;

import java.util.ArrayList;
import java.util.Iterator;
import fileSystem.Directory;
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
 * The Search class searches for a specific directory or file amongst the children of a single
 * directory provided by a path, or amongst multiple directories provided by multiple paths. This
 * class has three fields whose types are ChangeDirectory, PrintDirectory, and StringBuilder.
 */
public class Search extends CommandHandler {
  /**
   * Responsible for changing the current directory to the specified path
   */
  private ChangeDirectory cd;
  /**
   * Responsible for getting the full path of the current directory
   */
  private PrintWorkingDirectory pwd;
  /**
   * Responsible for containing the return value
   */
  private StringBuilder output;

  /**
   * Constructor which initializes the instance variables
   */
  public Search() {
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
    output = new StringBuilder();
  }

  /**
   * Iterates over the File System, checking if the current directory's name matches dirName. If the
   * name does match, then append the path of the current directory to the output.
   * 
   * @param fs The FileSystem to be iterated over
   * @param dirName The string containing the name of the directory we are searching for
   */
  private void searchDirectory(FileSystem fs, String dirName) {
    // Create an iterator at which starts at the current directory
    Iterator<Directory> i = fs.iterator(fs.getCurrentDir());

    // Traverse all children of current directory, and if the name of a child is equivalent, add its
    // path to output
    while (i.hasNext()) {
      fs.setCurrentDir(i.next());
      if (fs.getCurrentDir().getName().contentEquals(dirName)) {
        if (output.length() != 0)
          output.append("\n" + pwd.performOperation(fs, commandArray));
        else
          output.append(pwd.performOperation(fs, commandArray));
      }
    }
  }

  /**
   * Appends the path of the directory containing the found file and the file name to the output.
   * 
   * @param path The string containing the path to directory that contains the file
   * @param fileName The string containing the file's name
   */
  private void addFilePath(String path, String fileName) {
    // If the path is the root, do not want to append '//fileName' to the output
    if (path.contentEquals("/")) {
      if (output.length() != 0)
        output.append("\n" + path + fileName);
      else
        output.append(path + fileName);
    }
    // If the path is not the root, then append the path, a backslash, and the filename
    else {
      if (output.length() != 0)
        output.append("\n" + path + "/" + fileName);
      else
        output.append(path + "/" + fileName);
    }
  }

  /**
   * Iterates over the File System, checking if the current directory contains a file whose name
   * matches fileName. If the name does match, then append the path of the current directory and the
   * file's name to the output.
   * 
   * @param fs The FileSystem to be iterated over
   * @param fileName The string containing the name of the file we are searching for
   */
  private void searchFile(FileSystem fs, String fileName) {

    // Create an iterator at which starts at the current directory
    Iterator<Directory> i = fs.iterator(fs.getCurrentDir());

    // Traverse all children of current directory, and if the name of a child is equivalent, add its
    // path to output
    while (i.hasNext()) {
      fs.setCurrentDir(i.next());
      super.searchForFile(fs.getCurrentDir(), fileName);
      if (super.fileObject != null)
        this.addFilePath(pwd.performOperation(fs, commandArray), fileName);
    }
  }

  /**
   * Recursively searches the children of a single directory provided by a path or multiple
   * directories provided by multiple paths, for a specific directory or file specified by its name.
   * 
   * @param fileSystem The FileSystem on which the search will be conducted on
   * @param arguments An ArrayList of type object which contains the arguments required for this
   *        operation with the following elements: the path at index 0, the type of item to search
   *        for at index 1, and the itemName at index 2
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Get the arguments from the array list arguments
    String paths = (String) arguments.get(0);
    String type = (String) arguments.get(1);
    String itemName = (String) arguments.get(2);
    errorMessage = "";

    // Split the multiple paths into individual paths
    String[] seperatedPaths = paths.split("&");

    // Keep track of current directory
    super.directoryObject = fileSystem.getCurrentDir();

    // Traverse the individual paths array
    for (String path : seperatedPaths) {

      // Traverse to the path provided
      super.commandArray.clear();
      super.commandArray.add(path);
      super.errorMessage = cd.performOperation(fileSystem, commandArray);

      // If the path was invalid, append the error message to the output and return
      if (!super.errorMessage.isEmpty()) {
        if (output.length() == 0)
          return errorMessage;
        output.append("\n" + errorMessage);
        break;
      }

      // If the successfully changed to the path, call the private methods to do the searching
      if (type.contentEquals("d"))
        this.searchDirectory(fileSystem, itemName);
      else
        this.searchFile(fileSystem, itemName);

      // Set the current directory back to the original
      fileSystem.setCurrentDir(directoryObject);
    }
    // Return the output
    if (output.length() == 0)
      return "No item of name '" + itemName + "' was found";
    return output.toString();
  }
}
