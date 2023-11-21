package commands;

import java.util.ArrayList;
import fileSystem.FileSystem;
import java.net.*;
import java.io.*;

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
 * Retrieves the content from the URL provided by the user, and then write the content in a new file
 * with the same name from the URL.
 */
public class Curl extends CommandHandler {

  /**
   * The instance variable of EchoOverwrite class, enables the Curl class to access functions in the
   * EchoOverwrite class
   */
  EchoOverwrite write;
  /**
   * The instance variable of URL. Represents the URL given by the user
   */
  URL fileURL;
  /**
   * The instance variable for the full contents retrieved from URL
   */
  StringBuilder fulltext;

  /**
   * Constructs a new Curl object, initialize write and append.
   */
  public Curl() {
    write = new EchoOverwrite();
    fulltext = new StringBuilder();
  }


  /**
   * {@inheritDoc} Retrieves the content written in the URL provided, create a new file with the
   * same name by the given URL. If the file with same name already exist, overwrite the content
   * with the content retrieved. Else, create the file then write the content into the file.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass
   * @return "" if no error occurred, specified error message if error occurred.
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    String errorMsg;
    String url = (String) arguments.get(0);
    String content = null;
    String fileName = null;
    fileURL = null;

    try {
      fileURL = new URL(url);
      fileName = super.splitAndGetLast(url).replace(".", "");
      super.searchForFile(fileSystem.getCurrentDir(), fileName);
      if(super.fileObject != null) {
        return "!&!The file " + fileName + " already exists!&!";
      }
      BufferedReader br = new BufferedReader(new InputStreamReader(fileURL.openStream()));
      content = br.readLine();
      while (content != null) {
        fulltext.append(content + "\n");
        content = br.readLine();
      }
      super.commandArray = new ArrayList<Object>();
      super.commandArray.add(fileName);
      super.commandArray.add(fulltext.toString());
      errorMsg = write.performOperation(fileSystem, super.commandArray);
      if (!errorMsg.isEmpty()) {
        return errorMsg;
      }
      br.close();
    } catch (Exception e) {
    }
    return "";
  }
}
