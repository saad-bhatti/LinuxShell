package driver;

import java.util.ArrayList;
import java.util.Scanner;
import commands.LoadJShell;
import fileSystem.FileSystem;
import fileSystem.Log;
import fileSystem.Stack;
import shell.Execute;
import shell.Output;
import shell.Parse;
import shell.Prepare;
import shell.Verify;

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
 * This class continuously asks the user for input until the command exit is given.
 */
public class JShell {

  ////////////////////////////////////////// Main Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Continuously asks the user for input. If the input is valid, then a command will execute, and
   * the output produced, if any, can be either printed or redirected to a file in the file system.
   * If the input given by the user contains an error or the input was invalid, then an error
   * message is printed for the user to see.
   * 
   * @param arg Required for the main method
   */
  public static void main(String[] arg) {
    // Local variables
    String userInput;
    int count = 0;
    JShell jshell = new JShell();
    Scanner scan = new Scanner(System.in);

    // First Time input to check loadJshell
    System.out.print("/#: ");

    // Get user input
    userInput = scan.nextLine();

    // If the command is possibly loadJShell
    if (userInput.contains("loadJShell")) {
      jshell.parseUserInput(userInput);
      jshell.verifyUserInput(userInput);
      jshell.setPreviousJShell(userInput);
    } else {
      jshell.executeShellMethod(userInput);
    }
    count++;
    jshell.verifier.setDisable(count);

    // Continuous prompting of user done here
    while (jshell.running.isEmpty()) {

      // Prompt user for input
      System.out.print("/#: ");

      // Get user input
      userInput = scan.nextLine();
      jshell.executeShellMethod(userInput);
    }
    // Close the scanner
    scan.close();
  }

  /////////////////////////////////////// Instance Variables \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Responsible for ensuring the user is continuously prompted for input
   */
  private ArrayList<String> running;
  /**
   * The file system the user will build and use in the session
   */
  private FileSystem fileSystem;
  /**
   * Responsible for parsing the user input
   */
  private Parse parser;
  /**
   * Responsible for verifying the user input
   */
  private Verify verifier;
  /**
   * Responsible for preparing the arguments required for the execution of the command
   */
  private Prepare preparer;
  /**
   * Responsible for executing the valid command specified by the user
   */
  private Execute executer;
  /**
   * Responsible for handling the output of the input given by the user
   */
  private Output output;
  /**
   * Responsible for loading a previously saved JShell session
   */
  private LoadJShell load;
  /**
   * Responsible for storing all the inputs given by the user
   */
  private Log logger;
  /**
   * Responsible for containing the arguments given by the user for the command
   */
  private ArrayList<Object> arguments;
  /**
   * Responsible for containing the output of a command or an error message
   */
  private String errorMessage;

  ////////////////////////////////////////// Constructor \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Constructor of the JShell Object which initializes all of the fields.
   */
  private JShell() {
    fileSystem = FileSystem.createFileSystemInstance();
    parser = new Parse();
    verifier = new Verify();
    executer = new Execute();
    output = new Output();
    logger = new Log();
    arguments = new ArrayList<>();
    load = new LoadJShell();
    running = new ArrayList<>(1);
    errorMessage = "";
    preparer = new Prepare(arguments, running, logger);
  }

  /////////////////////////////////// Central Method of JShell \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Takes in user input, and makes method calls to do the following: store the user input to the
   * log, parse the input, verify the input, prepare the arguments for the the command, and execute
   * the command. If an error occurs along any stage of this, it stops and calls the output class to
   * print the error message.
   * 
   * @param userInput String The input given by the user
   */
  private void executeShellMethod(String userInput) {
    // Store input into logger and parse it
    addInputToLog(userInput);

    // Parse the user input
    parseUserInput(userInput);

    // If this line reached, verifying input was successful, now prepare the parameters
    if (isEmptyError())
      verifyUserInput(userInput);

    // Now prepare the arguments and execute the command
    if (isEmptyError())
      prepareAndExecute(userInput);

    // Print or redirect the output of the command
    this.output.printOutput(fileSystem, errorMessage);

    // Reset the error message
    setErrorMessage();
  }

  /////////////////////////////////////// Log Method Call \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Stores the user input given the user in the log
   * 
   * @param userInput String the user input given by the user to be stored inside the log
   */
  private void addInputToLog(String userInput) {
    this.logger.addToRecords(userInput);
  }

  /////////////////////////////////////// Parse Method Call \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Parses the user input by calling the method of the Parse object
   * 
   * @param userInput String the input given by the user
   */
  private void parseUserInput(String userInput) {
    this.errorMessage = parser.parseInput(userInput, output);
    return;
  }

  ////////////////////////////////////// Verify Method Call \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Verifies the user input for the depending on the given command.
   * 
   * @param userInput String the input given by the user
   */
  private void verifyUserInput(String userInput) {
    this.verifier.setVariables(parser.getCommand(), parser.getParameters(), userInput);
    this.errorMessage = verifier.verifyInput(verifier, output);
    return;
  }

  /////////////////////////////////////// Prepare Method Call \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Prepares the arguments of the specific to the command for execution.
   * 
   * @param userInput String the input given by the user
   */
  private void prepareInput(String userInput) {
    this.preparer.setValues(verifier.getCommand(), userInput, verifier.getParameters());
    preparer.prepareArguments(preparer);
    return;
  }

  ///////////////////////////////// Prepare and Execute Method Call \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Prepares the arguments for execution via a method call and executes the command.
   * 
   * @param userInput String the input given by the user
   */
  private void prepareAndExecute(String userInput) {
    this.prepareInput(userInput);
    // Executing the class
    this.errorMessage = executer.executeCommand(fileSystem, verifier.getCommand(), arguments);
    return;
  }

  ////////////////////////////////// Error Message Related Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Resets the error message
   */
  private void setErrorMessage() {
    this.errorMessage = "";
  }

  /**
   * Checks if the error message is empty
   * 
   * @return true if error message is empty and false if the message is not empty
   */
  private boolean isEmptyError() {
    return this.errorMessage.isEmpty();
  }

  /////////////////////////////////////// LoadJShell Related \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Sets up the previous JShell commands after receiving input from the command LoadJShell
   * 
   * @param userInput Takes in the raw user Input of type String
   */
  private void setPreviousJShell(String userInput) {

    if (this.isEmptyError())
      this.prepareInput(userInput);

    if (this.isEmptyError()) {
      this.arguments = load.performOperation(arguments);
      if (this.arguments.size() == 1)
        errorMessage = (String) arguments.get(0);
    }

    // If no error is passed from the prepare into execution than load the previous JShell
    if (this.isEmptyError()) {
      this.logger = (Log) arguments.get(0);
      this.preparer = new Prepare(arguments, running, logger);
      this.preparer.setStack((Stack) arguments.get(1));
      this.fileSystem = (FileSystem) arguments.get(2);
      this.addInputToLog(userInput);
    }

    if (!this.isEmptyError()) {
      this.addInputToLog(userInput);
      this.output.printOutput(fileSystem, errorMessage);
    }
  }
}
