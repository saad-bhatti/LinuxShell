package commands;

import fileSystem.*;
import java.util.*;

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
 * This Tree class display entire file system from the root directory "\" in a specified format
 */
public class Tree extends CommandHandler {

  /**
   * This instance StringBuilder represent the output of the tree
   */
  private StringBuilder output;

  /**
   * Constructor for the Tree class, initialize the output variable
   */
  public Tree() {
    output = new StringBuilder();
  }

  /**
   * This class generates the entire system and stores it in the StringBuilder output in the correct
   * tree format with tab symbols in front
   * 
   * @param root The current root we are in of the file system
   * @param space The indentation space for formatting
   */
  private void generateOutput(Directory root, StringBuilder space) {

    if (root == null) {
      return;
    }
    output.append(space + root.getName() + "\n");
    for (int i = 0; i < root.getFilesInCurrentDirectory().size(); i++) {
      output.append(space.append("\t") + root.getFilesInCurrentDirectory().get(i).getName() + "\n");
      space.delete(space.length() - 1, space.length());
    }
    for (int i = 0; i < root.getChildDirectory().size(); i++) {
      generateOutput(root.getChildDirectory().get(i), space.append("\t"));
      space.delete(space.length() - 1, space.length());
    }
  }

  /**
   * {@inheritDoc} This method initializes the curDir and space to call the generateOutput class.
   * Then after the StringBuilder output is completed, delete the last empty line, then return the
   * output as the result in String.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass
   * @return output in String, the entire file system in tree format.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    Directory curDir = fileSystem.getRoot();
    StringBuilder space = new StringBuilder();
    space.append("");
    generateOutput(curDir, space);
    output.delete(output.length() - 1, output.length());
    return output.toString();
  }

}
