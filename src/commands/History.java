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
 * Represents the History command in JShell
 */
public class History extends CommandHandler {

  /**
   * The number of lines user want to read
   */
  private int numberOfLines;
  /**
   * The log that stores all history input from the user
   */
  private Log logger;
  /**
   * Responsible for containing the output of the command
   */
  private StringBuilder output;

  /**
   * Initializes the output variable
   */
  public History() {
    output = new StringBuilder();
  }


  /**
   * {@inheritDoc} This method returns all user inputs stored in the log if the numberOfLines is not
   * specified. If the numberOfLines is provided in the arguments, then returns the most recent
   * numberOfLines lines user input. If the numberOfLines is larger than the size of the log, then
   * returns all input stored in log.
   * 
   * @param fileSystem The current file system in the JShell and contains a reference to the root
   *        and current directory.
   * @param arguments The container that contains the specific arguments required for the method of
   *        the subclass.
   * @return The output of the command
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {

    // Get the log object from arguments at index 0
    logger = (Log) arguments.get(0);

    // If the number of lines that need to be printed was given by user, then they at index 1 of
    // arguments. If the number of lines is not specified, then there is no index 1 of arguments.
    if (arguments.size() == 2)
      numberOfLines = Integer.parseInt((String) arguments.get(1));
    else
      numberOfLines = -1;

    // Assign the records of the log to a local variable
    ArrayList<String> records = logger.getRecords();

    int counter = 1;
    if (numberOfLines < 0) {
      for (String s : records) {
        output.append(counter + ". " + s + "\n");
        counter++;
      }
    } else if (numberOfLines == 0)
      ;

    else if (numberOfLines >= records.size()) {
      for (String s : records) {
        output.append(counter + ". " + s + "\n");
        counter++;
      }
    } else {
      for (int i = numberOfLines; i >= 1; i--) {
        output.append((records.size() - i + 1) + ". " + records.get(records.size() - i) + "\n");
      }
    }
    
    // Return the output
    return output.deleteCharAt(output.length() - 1).toString();
  }
}
