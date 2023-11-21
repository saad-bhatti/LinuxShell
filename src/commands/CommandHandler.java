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
 * An abstract class that is the parent of all command classes. Contains an abstract method
 * implemented by the subclasses, and protected methods utilized by the subclasses.
 */
public abstract class CommandHandler {

  // Instance Variables
  /**
   * Directory object that is mainly utilized by subclasses and when searching for a child directory
   * in the current directory.
   */
  protected Directory directoryObject;
  /**
   * Path object that is mainly utilized by subclasses and stores directory names after splitting a
   * string by a backslash.
   */
  protected Path<String> pathObject;
  /**
   * File object that is mainly utilized by subclasses and when searching for a file in the current
   * directory.
   */
  protected File fileObject;
  /**
   * A string that is utilized by methods of the subclasses. Initially this is an empty string. If
   * an error occurs in a method of a subclass, then this variable contains an error message.
   */
  protected String errorMessage;
  /**
   * An ArrayList that is utilized by a subclass when calling a method of another subclass. It will
   * contain the arguments for the method of the other subclass.
   */
  protected ArrayList<Object> commandArray;

  /**
   * Default Constructor that initializes the instance variables.
   */
  public CommandHandler() {
    directoryObject = new Directory();
    pathObject = new Path<>();
    fileObject = new File();
    commandArray = new ArrayList<>();
    errorMessage = "";
  }

  /**
   * This method is an abstract method in which all subclasses will provide the implementation
   * details.
   * 
   * @param fileSystem is the current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments is a container that contains the specific arguments required for the method of
   *        the subclass.
   * 
   * @return A string that will either be an empty string, to indicate the method completed
   *         successfully. Otherwise, it will be errorMessage which contains a specific error
   *         message.
   */
  abstract public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments);

  /**
   * This method splits the given string on the basis of a backslash, '/'. Then assigns the elements
   * of the array, produced by the splitting, to the path of pathObject. Does not throw any
   * exception.
   * 
   * @param givenString is a string with backslashes separating every object name, or may not
   *        contain a backslash at all. givenString usually represents a path as a string.
   */
  protected void splitWithBackSlash(String givenString) {

    // Clear the existing path
    this.pathObject.clearPath();

    // Split the given string with regex "/"
    String[] splitString = givenString.split("/");

    // Assign each element to the path of pathObject
    for (String individualStr : splitString) {
      if (!individualStr.isEmpty())
        this.pathObject.addToPath(individualStr);
    }

    // If the given string is full path, add backslash at index 0
    if (givenString.startsWith("/"))
      this.pathObject.addToPath(0, "/");
  }

  /**
   * This method splits the given string on the basis of a backslash, '/'. Then this method removes
   * the string at last index of the array in pathObject and returns that string. Does not throw any
   * exception.
   * 
   * @param givenString is a string with backslashes separating every object name, or may not
   *        contain a backslash at all. givenString usually represents a path as a string.
   * 
   * @return A string that was the last index of pathObject.
   */
  protected String splitAndGetLast(String givenString) {

    // Call the method which splits givenPath with a backslash
    this.splitWithBackSlash(givenString);

    // Call the method of pathObject which removes the string at the last index of pathObject and
    // return that string
    return this.pathObject.getLastAndRemoveIt();
  }

  /**
   * This method splits the given string on the basis of the AND Symbol '&' and returns the array
   * produced by the splitting of given string. Does not throw any exception.
   * 
   * @param givenString is a string with commas separating every object name, or may not contain a
   *        comma at all. givenString usually represents a arguments for a command.
   * 
   * @return An array of strings that is produced by splitting the givenString with a comma
   */
  protected String[] splitWithAndSymbol(String givenString) {

    // Split the given string with regex "&"
    String[] splitString = givenString.split("&");

    // Return the array
    return splitString;
  }

  /**
   * This method traverses the children directories of the given directory and searches for the
   * child directory by comparing key with the directory's name. If found, assign the found
   * directory to directoryObject. If not found, directoryObject is set to null. Does not throw any
   * exception.
   * 
   * @param givenDirectory is the directory in which the child directory is searched for.
   * @param key is a string that potentially matches the name of a child directory.
   */
  protected void searchForDirectory(Directory givenDirectory, String key) {

    // Set directoriesArray to the children directories of the givenDirectory
    ArrayList<Directory> directoriesArray = givenDirectory.getChildDirectory();

    // If there are no children directories
    if (directoriesArray.isEmpty()) {
      directoryObject = null;
      return;
    }

    // If children directories do exist, search for the directory
    for (Directory dir : directoriesArray) {

      // If the directory is found, assign dir to directoryObject
      if (key.contentEquals(dir.getName())) {
        directoryObject = dir;
        return;
      }
    }
    // If this line is reached, then the givenDirectory does not contain a directory with the name
    // key. Hence set directoryObject to null
    directoryObject = null;
    return;
  }

  /**
   * This method traverses the children files of the given directory and searches for the child file
   * by comparing key with it's name. If found, fileObject points to the found file. If not found,
   * fileObject is set to null. Does not throw an exception.
   * 
   * @param givenDirectory is the directory in which the file is searched for.
   * @param fileName is a string that potentially matches the name of a file belonging to
   *        givenDirectory.
   */
  protected void searchForFile(Directory givenDirectory, String fileName) {

    // Get an ArrayList of All of the Files in the Directory
    ArrayList<File> allFiles = givenDirectory.getFilesInCurrentDirectory();

    // If no files in the directory
    if (allFiles.isEmpty()) {
      fileObject = null;
      return;
    }

    // If children files do exist, search for the file
    for (File file : allFiles) {

      // If the file is found, assign file to fileObject
      if (fileName.contentEquals(file.getName())) {
        fileObject = file;
        return;
      }
    }
    // If this line is reached, then the givenDirectory does not contain a file with the name
    // key. Hence, set fileObject to null.
    fileObject = null;
    return;
  }
}

