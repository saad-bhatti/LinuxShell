package commands;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
 * Represents the pushd command in JShell. Pushed the current directory on the stack and make change
 * current working directory to given directory.
 */
public class PushDirectory extends CommandHandler {

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
   * The directory keeps in track of the current working directory
   */
  Directory cwd;
  /**
   * The path of Directory separated by path
   */
  Deque<String> arrayOfDirectoryNames;
  /**
   * The full path of the directory
   */
  StringBuilder fullpath;

  /**
   * Constructs a new PushDirectory object, initializes cd, arrayOfDirectoryNames and fullpath.
   */
  public PushDirectory() {
    cd = new ChangeDirectory();
    arrayOfDirectoryNames = new ArrayDeque<String>();
    fullpath = new StringBuilder();
  }

  /**
   * {@inheritDoc} Pushes the full path of the current working directory into the stack, and then
   * changes to the specified directory entered by the user.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass
   * @return "" if no error occurred, specified error message if error occurred.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Get the stack at index 0 of arguments
    stack = (Stack) arguments.get(0);

    // Get the given path from user at index 1 of arguments
    String givenPath = (String) arguments.get(1);

    // Get the current directory of the fileSystem
    cwd = fileSystem.getCurrentDir();

    // Now change the current directory to directory given
    super.commandArray.clear();
    super.commandArray.add(givenPath);
    errorMessage = cd.performOperation(fileSystem, super.commandArray);

    // Return errorMessage if changing the directory fails
    if (!errorMessage.isEmpty())
      return errorMessage;

    // Get the full path of the current directory by traversing up the fileSystem
    for (Directory traversalDir = cwd; traversalDir != null; traversalDir =
        traversalDir.getParentDirectory())
      arrayOfDirectoryNames.push(traversalDir.getName());

    if (!arrayOfDirectoryNames.isEmpty()) {
      fullpath.append(arrayOfDirectoryNames.pop());
    }

    while (!arrayOfDirectoryNames.isEmpty()) {
      if (arrayOfDirectoryNames.size() == 1) {
        fullpath.append(arrayOfDirectoryNames.pop());
      } else {
        fullpath.append(arrayOfDirectoryNames.pop() + "/");
      }
    }

    // Now that we have the full path of the current directory, push the full path into the stack
    if (!fullpath.toString().isEmpty()) {
      stack.setTopString(fullpath.toString());
    }

    // Return an empty string to indicate success
    return "";
  }
}
