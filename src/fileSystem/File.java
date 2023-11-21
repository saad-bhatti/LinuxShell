package fileSystem;

import java.io.Serializable;

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
 * Represents the File object in JShell
 */
public class File implements Serializable {

  /**
   * File serialVersionUID value for Serial purpose
   */
  private static final long serialVersionUID = 1L;
  /**
   * The name of the file
   */
  private String name;
  /**
   * The content of the file
   */
  private String content;

  /**
   * Constructs a new File object, initialize the name and content to "".
   */
  public File() {
    this.name = "";
    this.content = "";
  }

  /**
   * Constructs a new File object when the file name if offered. Initialize the name to the name
   * offered, and content to "".
   * 
   * @param name The String that contains the specified file name.
   */
  public File(String name) {
    this.name = name;
    content = "";
  }

  /**
   * Constructs a new File object when the file name and givenContent are specified. Initialize the
   * name to the name specified, and content to givenContent.
   * 
   * @param name The name of the file specified.
   * @param givenContent The content of the file given by the user.
   */
  public File(String name, String givenContent) {
    this.name = name;
    content = givenContent;
  }

  /**
   * Getter method for variable name
   * 
   * @return String which contains the name of the File
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter method for variable content
   * 
   * @return String which contains the content of the File
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Setter method for variable name
   * 
   * @param newName The String that contains the new name of the File
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Setter method for variable content
   * 
   * @param newContent The String that contains the new content of the File
   */
  public void setContent(String newContent) {
    this.content = newContent;
  }
}
