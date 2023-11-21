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
 * Reads an existing file from the user computer and resets the JShell back to its previous status
 */

public class LoadJShell {

  /**
   * Instance variable to build a connection with the file
   */
  private ObjectInputStream inFile;
  /**
   * Store the content from the file being read to bring back the status of JShell
   */
  private ArrayList<Object> preShell;

  public LoadJShell() {
    inFile = null;
    preShell = new ArrayList<>();
  }

  /**
   * The parameter contains the file name which already exist on the user computer. It reads the
   * content of the file and store it into an array and returns it.
   * 
   * @exception ClassCastException returns back an exception if an improper format file is being
   *            read or any other file in general that was not saved using saveJShell class
   * @param fileName Contains the name of the file in string format
   * @return An array of heterogeneous object which were read from the file
   */
  public ArrayList<Object> performOperation(ArrayList<Object> fileName) {
    // Contains the file name
    String file = (String) fileName.get(0);

    try {
      inFile = new ObjectInputStream(new FileInputStream(file));
    } catch (IOException e) {
    }

    // Read object from the file and returns an error is improper format of file is being read
    // Throws back an exception if wrong implemented file is being read
    try {
      preShell.add((Log) inFile.readObject());
      preShell.add((Stack) inFile.readObject());
      preShell.add((FileSystem) inFile.readObject());
    } catch (ClassCastException e) {
      preShell.clear();
      preShell
          .add("!&!File with improper format is being read. Cannot load the previous JShell!&!");
      return preShell;
    } catch (IOException e) {
      preShell.clear();
      preShell
          .add("!&!Cannot read the specified file!&!");
      return preShell;
    } catch (ClassNotFoundException e) {
    }

    // close the inFile object
    try {
      inFile.close();
    } catch (IOException e) {
    }

    return preShell;
  }
}
