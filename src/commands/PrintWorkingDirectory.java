package commands;

import java.util.ArrayList;
import java.util.Stack;
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
 * Prints the path from root directory to the current working directory.
 */
public class PrintWorkingDirectory extends CommandHandler {

  /**
   * Takes in no arguments and call the super constructor which initializes the fields of the super
   * class constructor.
   */
  public PrintWorkingDirectory() {}

  /**
   * {@inheritDoc} Traverse and builds the path from current working directory to the root
   * directory. And then returns the absolute path from root to current directory.
   * 
   * @param fileSystem of type FileSystem contains current working directory object
   * @param unused of type ArrayList of Object is not used inside the function its just there to
   *        fill the requirement of the abstract method
   * @return String After successful operation absolute path's string representation gets returned
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> unused) {

    // Used to traverse the directory array list
    Stack<String> buildPath = new Stack<String>();
    // Used to store path from root to current directory
    StringBuilder path = new StringBuilder();

    // Push elements inside the stack
    for (super.directoryObject =
        fileSystem.getCurrentDir(); super.directoryObject != null; super.directoryObject =
            super.directoryObject.getParentDirectory()) {
      buildPath.push(super.directoryObject.getName());
    }

    // append root to the buildPath and pop the element from the stack
    if (!buildPath.empty()) {
      path.append(buildPath.pop());
    }


    // Pop elements from the stack and append it to the buildPath
    while (!buildPath.empty()) {
      if (buildPath.size() == 1) {
        path.append(buildPath.pop());
      } else {
        path.append(buildPath.pop() + "/");
      }

    }
    return path.toString();
  }

}
