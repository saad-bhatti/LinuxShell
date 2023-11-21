package fileSystem;

import java.io.Serializable;
import java.util.ArrayList;

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
 * Layout the structure of the directory inside the file system
 */
public class Directory implements Serializable {

  /**
   * Directory serialVersionUID value for Serial purpose
   */
  private static final long serialVersionUID = 1L;
  /**
   * Contains the name of the directory
   */
  private String name;
  /**
   * Points towards the parent directory from current directory
   */
  private Directory parentDirectory;
  /**
   * Contains an array of children directories inside the current directory
   */
  private ArrayList<Directory> childrenDirectory;
  // Contains an array of file object inside the current directory
  private ArrayList<File> filesInCurrentDirectory;

  /**
   * Default Constructor initializes the instance variables
   * 
   */
  public Directory() {
    this.name = "";
    this.parentDirectory = null;
    this.childrenDirectory = new ArrayList<Directory>();
    this.filesInCurrentDirectory = new ArrayList<File>();
  }

  /**
   * Overloaded Constructor initializes the directory instance variables and assigned the
   * directoryName variable to the name of the current directory
   * 
   * @param directoryName contains the name of the current directory
   */
  public Directory(String directoryName) {
    this.name = directoryName;
    this.parentDirectory = null;
    this.childrenDirectory = new ArrayList<Directory>();
    this.filesInCurrentDirectory = new ArrayList<File>();

  }



  /**
   * Takes in new file object and adds it to the end of the current directory file array
   * 
   * @param fileName Contains the name of the file object
   */
  public void addFilesInCurrentDirectory(File fileName) {
    this.filesInCurrentDirectory.add(fileName);
  }

  /**
   * Takes in Sub-directory gets added to the end of the current directory array
   * 
   * @param childDirectory Contains the sub-directory of the current directory
   */
  public void addChildDirectory(Directory childDirectory) {
    this.childrenDirectory.add(childDirectory);
  }

  /**
   * Getter for instance variable name
   * 
   * @return String the name of the current directory
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the current directory
   * 
   * @param nameOfDirectory contains the name of current directory
   */
  public void setName(String nameOfDirectory) {
    this.name = nameOfDirectory;
  }

  /**
   * Getter for parent directory of the current directory
   * 
   * @return Directory reference to parent directory object of the current directory
   */
  public Directory getParentDirectory() {
    return this.parentDirectory;
  }

  /**
   * Sets the parent directory object of the current directory
   * 
   * @param parentDirectory contains a reference to parent directory
   */
  public void setParentDirectory(Directory parentDirectory) {
    this.parentDirectory = parentDirectory;
  }

  /**
   * Getter for files in current directory
   * 
   * @return array list of type File the names of all the files in the current directory
   */
  public ArrayList<File> getFilesInCurrentDirectory() {
    return this.filesInCurrentDirectory;
  }

  /**
   * Getter for directories inside the current directory
   * 
   * @return array list of type Directory the name of all the directories inside the current
   *         directory
   */
  public ArrayList<Directory> getChildDirectory() {
    return this.childrenDirectory;
  }
}
