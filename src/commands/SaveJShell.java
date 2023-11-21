package commands;

import java.io.*;
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
 * Saves the current session of the JShell onto the actual fileSystem of the user
 */
public class SaveJShell extends CommandHandler {

  /**
   * Used to write object onto a file
   */
  private ObjectOutputStream outFile;

  /**
   * Initializes the instance variables
   */
  public SaveJShell() {
    outFile = null;
  }

  /**
   * {@inheritDoc} Takes in the File System and arguments of the current running JShell and creates
   * a connection to the file on the actual computer system and write necessary serialized object to
   * the file. If the file already exist then it overwrites the existing file otherwise creates a
   * new file and store the object passed in the arguments. returns "" if the operation was
   * successful returns an empty string. Otherwise returns a specified error.
   * 
   * @param fileSystem contains the current fileSystem of the JShell get stored into file
   * @param arguments Contains objects of different type which get stored into the file. Also
   *        contains the file name
   * @return "" empty string If the operation was successful. Otherwise returns a specified error
   *         message.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Contains the name of the file name Makes sure it is at the last
    String fileName = (String) arguments.get(arguments.size() - 1);
    arguments.remove(fileName);

    try {
      outFile = new ObjectOutputStream(new FileOutputStream(fileName));
    } catch (IOException e) {
      return "!&!This line will never run!&!";
    }

    // Writes object from the arguments on the file
    try {
      for (int i = 0; i < arguments.size(); i++) {
        outFile.writeObject(arguments.get(i));
      }
      outFile.writeObject(fileSystem);
    } catch (IOException e) {
      return "!&!This line will never run!&!";
    }

    try {
      outFile.close();
    } catch (IOException e) {
      return "!&!This line will never run!&!";
    }
    return "";
  }

}
