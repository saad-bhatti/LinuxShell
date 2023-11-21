package shell;

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
 * This class parses the user input entered into command and parameters, and if given, the
 * redirection parameters
 */
public class Parse {

  /**
   * Responsible for storing the command name given by the user
   */
  private String command;
  /**
   * The array list which will store the parameters of the command given by the user
   */
  private ArrayList<String> parameters;

  /**
   * Constructs a new Parse object, initialize command to "" and parameter to a new ArrayList
   */
  public Parse() {
    this.command = "";
    this.parameters = new ArrayList<>();
  }

  ///////////////////////////////////// Parse Command & Parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Parses the input with the regex ' '. Stores the command given by the user and the parameters
   * provided for the command.
   * 
   * @param input String containing the command name and parameters provided for the command
   */
  private void parseCommandParameters(String input) {
    // Split input on basis of " "
    String param[] = input.split(" ");

    // Integer to keep track if the command has been assigned
    int commandAssigned = 0;

    // Add all parameters to parameters
    for (String string : param) {
      if (!string.contentEquals("")) {
        if (commandAssigned != 0)
          parameters.add(string);
        else {
          this.command = string;
          commandAssigned++;
        }
      }
    }
  }

  /////////////////////////////////////// Parse Redirection Parameters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Parses the input with the regex ' '. Stores the parameters into the array list within the
   * output object.
   * 
   * @param input String containing the parameters for redirection
   */
  private void parseRedirectionParameters(String input, Output output) {
    // Split input on basis of " "
    String param[] = input.split(" ");

    // Set Redirection to true
    output.setRedirectionOccuring(true);

    // Add all parameters to array list within output
    for (String string : param) {
      if (!string.contentEquals(""))
        output.getRedirectionParameters().add(string);
    }
  }


  /////////////////////////////////////// Check Redirection \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method splits the user input into two strings on the basis of an angled bracket. The
   * portion of the input that comes before the bracket is assigned to the command parameters while
   * the rest of the string is assigned to the redirection parameters
   * 
   * @param userInput The non-empty String given by the user
   * @param output The output object which contains the redirection parameters array list
   */
  private void splitInputForRedirection(String userInput, Output output) {
    // If the command is echo, then return by calling private method
    if (userInput.startsWith("echo")) {
      this.parseCommandParameters(userInput);
      return;
    }

    // Local variables
    String commandSubString, redirectionSubString;
    int firstIndex;

    // Get first occurrence of the '>'
    firstIndex = userInput.indexOf(">");
    
    // If the '>' symbol is the last character, set the whole input as the command portion
    if (userInput.stripTrailing().length() - 1 == firstIndex) {
      this.parseCommandParameters(userInput);
      return;
    }
      
    // Assign the command related and redirection related portions
    commandSubString = userInput.substring(0, firstIndex);
    redirectionSubString = userInput.substring(firstIndex);

    // Appending to the file content
    if (redirectionSubString.startsWith(">>")) {
      output.setTypeOfRedirect(2);
      redirectionSubString = redirectionSubString.replaceFirst(">>", "");
    }
    // Overwriting the file content
    else {
      output.setTypeOfRedirect(1);
      redirectionSubString = redirectionSubString.replaceFirst(">", "");
    }

    // Call the private methods
    this.parseCommandParameters(commandSubString);
    this.parseRedirectionParameters(redirectionSubString, output);
  }

  /**
   * Parses userInput and separates it into the command and its parameters, and if given, the file
   * that the output will be redirected to. Returns "" if parse the userInput successfully, error
   * message otherwise.
   * 
   * @param userInput The user input in the JShell
   * @param output The output object which contains the redirection parameters array list
   * @return "" if the parse successfully, error message if userInput is not parsed successfully
   */
  public String parseInput(String userInput, Output output) {
    // Clear the existing parameters
    this.parameters.clear();
    output.resetVariables();

    // If the user input was empty
    if (userInput.isEmpty())
      return "!&!The user input was empty!&!";

    // If the user input contains a '>' (possible redirection)
    if (userInput.contains(">"))
      this.splitInputForRedirection(userInput, output);

    // No redirection is occurring
    else
      this.parseCommandParameters(userInput);

    // Return empty string to signify no error occurred
    return "";
  }

  /**
   * Getter method for variable command.
   * 
   * @return command variable in Parse class
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Getter method for ArrayList parameters
   * 
   * @return parameters variable in Parse class
   */
  public ArrayList<String> getParameters() {
    return new ArrayList<String>(this.parameters);
  }
}
