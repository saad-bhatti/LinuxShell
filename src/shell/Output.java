package shell;

import java.util.ArrayList;
import commands.EchoAppend;
import commands.EchoOverwrite;
import fileSystem.FileSystem;

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
 * The Output class prints the output of the user input, which can either be an error message or the
 * output of a specific command. This class contains multiple fields of types boolean, ArrayList,
 * StringBuilder, EchoOverwrite, and EchoAppend.
 */
public class Output {

  /////////////////////////////////////// Instance Variables \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Responsible for keeping the status of whether redirection is happening on the user input
   */
  private boolean redirectionOccuring;
  /**
   * Responsible for storing the parameters provided for redirection
   */
  private ArrayList<String> redirectionParameters;
  /**
   * Responsible for containing any error descriptions that occur for redirecting the output
   */
  private StringBuilder redirectError;
  /**
   * Responsible for keeping the status of whether redirection is overwriting or appending. The
   * value of 1 means overwriting while the value of 2 means appending
   */
  private int typeOfRedirect;
  /**
   * Responsible for redirecting the output by overwriting the file's contents with the output
   */
  private EchoOverwrite echoOver;
  /**
   * Responsible for redirecting the output by appending the output to the file's content
   */
  private EchoAppend echoAppend;

  ////////////////////////////////////////// Constructor \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Constructor that takes in no arguments and initializes fields
   */
  public Output() {
    redirectionOccuring = false;
    redirectionParameters = new ArrayList<>();
    redirectError = new StringBuilder();
    typeOfRedirect = -1;
    echoOver = new EchoOverwrite();
    echoAppend = new EchoAppend();
  }

  /////////////////////////////////////// Main Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method prints the output onto the screen if no redirection is happening, or can redirect
   * the valid input into a file if redirection is happening. Error messages will always be printed
   * onto the screen and never redirected into a file.
   * 
   * @param fs FileSystem that contains the file to which the output is redirected to
   * @param output A string that contains the output of a command or an error message
   */
  public void printOutput(FileSystem fs, String output) {

    // If the output is not being redirected
    if (!redirectionOccuring)
      this.printUndirectedOutput(output);

    // If the output is being redirected
    else
      this.redirectOutput(fs, output);
  }

  //////////////////////////////////// No Redirection \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Prints the output when no redirection is occurring or the output starts with an error statement
   * 
   * @param output String the output of the command or an error message
   */
  private void printUndirectedOutput(String output) {

    // If the output contains an error message
    if (output.contains("!&!"))
      System.out.println(output.replace("!&!", ""));

    // If the output does not contain an error message but is not empty
    else if (!output.isEmpty()) {
      // Print any redirection related errors
      this.printRedirectionError();
      System.out.println(output);
    }

  }

  /////////////////////////////////////// Redirection \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Redirects the successful output into the valid file name. If any errors occurred, prints the
   * errors on the screen for the user to see.
   * 
   * @param fs FileSystem that contains the file to which the output is redirected to
   * @param output String the output of the command or an error message
   */
  private void redirectOutput(FileSystem fs, String output) {

    // If the output starts with an error message, call the method
    if (output.startsWith("!&!")) {
      this.printUndirectedOutput(output);
      return;
    }

    // If the output does not contain an error redirect it by calling a method
    else if (!output.contains("!&!"))
      this.saveToFile(fs, output);

    // The output contains an successful output with an error message
    else if (output.contains("!&!")) {
      String[] splitOutput = output.split("!&!");

      // Get the successful output and redirect it
      String successfulOutput = splitOutput[0];
      this.saveToFile(fs, successfulOutput);

      // Print the error messages
      for (int i = 1; i < splitOutput.length; i++)
        if (!splitOutput[i].contentEquals(""))
          System.out.println(splitOutput[i]);
    }
  }

  /**
   * Redirects the successful output into a valid file name, by either appending or overwriting the
   * output to the file's content. Prints any errors related to redirection onto the screen for the
   * user to see.
   * 
   * @param fs FileSystem that contains the file to which the output is redirected to
   * @param givenText String the successful output of the command to be stored in the file
   */
  private void saveToFile(FileSystem fs, String givenText) {

    // Create an arraylist to contain the arguments for the echo call
    ArrayList<Object> parameters = new ArrayList<>();
    
    // If the givenText contains a newline character at the end, remove it
    if (givenText.endsWith("\n"))
      givenText = givenText.stripTrailing();
    
    // Add the given text and file name to the parameters
    parameters.add(this.redirectionParameters.get(0));
    parameters.add(givenText);

    // Overwriting the files content
    if (typeOfRedirect == 1)
      redirectError.append(echoOver.performOperation(fs, parameters));
    // Appending to the files content
    else if (typeOfRedirect == 2)
      redirectError.append(echoAppend.performOperation(fs, parameters));

    // Print any redirection related errors
    this.printRedirectionError();
  }

  /**
   * Prints the redirection related errors to the screen for the user to see
   */
  private void printRedirectionError() {

    // Print any errors related to redirection
    if (redirectError.length() != 0) {

      // Get index at last index
      int lastIndex = redirectError.length() - 1;

      // Remove the new line character at the end
      if (redirectError.charAt(lastIndex) == '\n')
        redirectError.deleteCharAt(redirectError.length() - 1);

      // Get the error message
      String error = redirectError.toString();

      // Replace the symbols and print the message
      System.out.println(error.replace("!&!", ""));
    }
  }

  /////////////////////////////////////// Getters and Setters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Getter for the redirectionOccurring variable
   * 
   * @return boolean The value of the boolean
   */
  public boolean isRedirectionOccuring() {
    return redirectionOccuring;
  }

  /**
   * Setter for the redirectionOccurring variable
   * 
   * @param redirectionOccuring Boolean value which set the value of the variable
   */
  public void setRedirectionOccuring(boolean redirectionOccuring) {
    this.redirectionOccuring = redirectionOccuring;
  }

  /**
   * Getter for the redirectionParameters variable
   * 
   * @return ArrayList<String> The redirectionParameters variable
   */
  public ArrayList<String> getRedirectionParameters() {
    return redirectionParameters;
  }

  /**
   * Setter for the redirectionOccurring variable
   * 
   * @param redirectionParameters ArrayList<String> The variable will be assigned to this array list
   */
  public void setRedirectionParameters(ArrayList<String> redirectionParameters) {
    this.redirectionParameters = redirectionParameters;
  }

  /**
   * Getter for the String type redirectionError variable
   * 
   * @return String The redirectionError variable
   */
  public String getRedirectionError() {
    return redirectError.toString();
  }

  /**
   * Setter for the redirectionError variable
   * 
   * @param redirectionError redirectionError will be assigned to this input
   */
  public void clearRedirectionError() {
    this.redirectError = new StringBuilder();
  }

  /**
   * Append a string to the redirectionError variable
   * 
   * @param redirectionError String this input will be appended to the variable
   */
  public void appendRedirectionError(String redirectionError) {
    this.redirectError.append(redirectionError);
  }

  /**
   * Getter for the type of redirect that is occurring
   * 
   * @return int the type of redirect that is occurring
   */
  public int getTypeOfRedirect() {
    return typeOfRedirect;
  }

  /**
   * Setter for the type of redirect that is occurring
   * 
   * @param int the type of redirect that is occurring
   */
  public void setTypeOfRedirect(int typeOfRedirect) {
    this.typeOfRedirect = typeOfRedirect;
  }

  /////////////////////////////////////////// Reset Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Resets all the instance variables of the Output class back to their default value.
   */
  public void resetVariables() {
    getRedirectionParameters().clear();
    setRedirectionOccuring(false);
    clearRedirectionError();
    typeOfRedirect = -1;
  }
}
