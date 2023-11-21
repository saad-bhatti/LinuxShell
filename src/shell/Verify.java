package shell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
 * Checks whether user input is valid, and returns specified error message depends on the type of
 * invalid error
 */
public class Verify {

  ///////////////////////////////////// Instance Variables /////////////////////////////////////////

  /**
   * Array containing all of the special symbols that are not allowed
   */
  final private String[] specialSymbols = {".", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
      "{", "}", "~", "|", "<", "?", "\"", ">", "\\"};

  /**
   * The command user input
   */
  private String givenCommand;
  /**
   * The parameters of the command user input
   */
  private ArrayList<String> givenParameters;
  /**
   * The user input
   */
  private String userInput;
  /**
   * The hash map for verify
   */
  private HashMap<String, String> methodHashMap;
  /**
   * The hash map which contains a boolean to see if the output of the command can be redirected
   */
  private HashMap<String, Boolean> redirectionHashMap;

  /**
   * To keep track of number of commands entered
   */
  private int disable = 0;

  /**
   * Constructs a new Verify object. Initializes all instance variables.
   */
  public Verify() {
    this.givenCommand = "";
    this.userInput = "";
    this.givenParameters = new ArrayList<>();
    methodHashMap = new HashMap<>(18);
    Verify.initializeCommandsToMethod(methodHashMap);
    redirectionHashMap = new HashMap<>(18);
    Verify.initializeCommandsToBoolean(redirectionHashMap);
  }

  /**
   * Initialize the verifyHashMap. Keys are the valid command names and values are the name of the
   * methods in this class.
   * 
   * @param verifySizeHashMap The hash map contains all verify methods
   */
  private static void initializeCommandsToMethod(HashMap<String, String> verifySizeHashMap) {

    // Keys are command names and values are method names
    verifySizeHashMap.put("pwd", "verifyNoParameters");
    verifySizeHashMap.put("popd", "verifyNoParameters");
    verifySizeHashMap.put("exit", "verifyNoParameters");
    verifySizeHashMap.put("cd", "verifyOneParameter");
    verifySizeHashMap.put("pushd", "verifyOneParameter");
    verifySizeHashMap.put("rm", "verifyOneParameter");
    verifySizeHashMap.put("man", "verifyOneParameter");
    verifySizeHashMap.put("mkdir", "verifyMakeDirectory");
    verifySizeHashMap.put("cat", "verifyParameterExist");
    verifySizeHashMap.put("search", "verifySearch");
    verifySizeHashMap.put("echo", "verifyEcho");
    verifySizeHashMap.put("history", "verifyHistory");
    verifySizeHashMap.put("curl", "verifyCurl");
    verifySizeHashMap.put("saveJShell", "verifySaveJShell");
    verifySizeHashMap.put("loadJShell", "verifyLoadJShell");
    verifySizeHashMap.put("tree", "verifyNoParameters");
    verifySizeHashMap.put("cp", "verifyTwoParameters");
    verifySizeHashMap.put("mv", "verifyTwoParameters");
  }

  /**
   * Initializes the verifyRedirectionPossible HashMap. Keys are command names and values are
   * boolean of whether its output can be redirected
   * 
   * @param redirectionPossible The hash map contains a boolean of whether the command's output can
   *        be redirected
   */
  private static void initializeCommandsToBoolean(HashMap<String, Boolean> redirectionPossible) {

    // Keys are command names and values are boolean of whether its output can be redirected
    redirectionPossible.put("ls", true);
    redirectionPossible.put("pwd", true);
    redirectionPossible.put("man", true);
    redirectionPossible.put("cat", true);
    redirectionPossible.put("tree", true);
    redirectionPossible.put("search", true);
    redirectionPossible.put("history", true);
    redirectionPossible.put("cd", false);
    redirectionPossible.put("rm", false);
    redirectionPossible.put("cp", false);
    redirectionPossible.put("mv", false);
    redirectionPossible.put("popd", false);
    redirectionPossible.put("exit", false);
    redirectionPossible.put("curl", false);
    redirectionPossible.put("pushd", false);
    redirectionPossible.put("mkdir", false);
    redirectionPossible.put("saveJShell", false);
    redirectionPossible.put("loadJShell", false);
  }

  //////////////////////////////////// Setters and Getters /////////////////////////////////////////

  /**
   * Setter methods for givenCommand, givenParameters, and userInput
   * 
   * @param command The user input command
   * @param parameters The parameters for a command given by the user
   * @param userInput The unmodified user input
   */
  public void setVariables(String command, ArrayList<String> parameters, String userInput) {
    this.givenCommand = command;
    this.givenParameters = parameters;
    this.userInput = userInput;
  }

  /**
   * Getter method for command variable.
   * 
   * @return The command variable stored in Verify class
   */
  public String getCommand() {
    return this.givenCommand;
  }

  /**
   * Getter method for userInput variable.
   * 
   * @return The userInput variable stored in Verify class
   */
  public String getGivenInput() {
    return this.userInput;
  }

  /**
   * Getter method for parameters variable.
   * 
   * @return The parameters variable stored in Verify class
   */
  public ArrayList<String> getParameters() {
    return this.givenParameters;
  }

  public void setDisable(int i) {
    this.disable = i;
  }


  /////////////////////////////////////// Helper Methods ///////////////////////////////////////////

  /**
   * Verifies that the directory name contain no special symbols listed. If the input contains a
   * special symbol, returns an error message. Otherwise, return ""
   * 
   * @return "" if directory name contains no special symbols, error message otherwise.
   */
  private String checkSpecialSymbolInDirectoryName() {
    // Local variables
    String directoryName, path;

    // Traverse over the paths given by user, which contain the directory names
    for (int i = 0; i < givenParameters.size(); i++) {
      path = givenParameters.get(i);
      // Get the directory name from the path
      // If a relative or absolute path is given
      if (path.contains("/")) {
        // Get last index of the backslash
        int lastIndex = path.lastIndexOf("/");
        // Directory name starts after the last backslash
        directoryName = path.substring(lastIndex + 1);
      }
      // If the directory will be added in the current directory
      else {
        // The directory name is the path
        directoryName = path;
      }
      // Traverse the special symbols array.
      for (String symbol : specialSymbols) {
        // If the file name contains a special symbol, return an error message
        if (directoryName.contains(symbol))
          givenParameters.set(i, "The name of a directory contains an invalid symbol");
      }
    }
    // If this line is reached, the directory names are valid
    return "";
  }

  /**
   * Verifies that the File name contain no special symbols listed. If the input contains a special
   * symbol, returns an error message. Otherwise, return ""
   * 
   * @return "" if File name contains no special symbols, error message otherwise.
   */
  private String checkSpecialSymbolsInFileName() {
    // Local variables
    String fileName;
    int lastIndexOfParameters = givenParameters.size() - 1;
    // Get the last parameter from givenParameters
    String lastParameter = givenParameters.get(lastIndexOfParameters);

    // Get the filename from the last Parameter
    // If a relative or absolute path is given
    if (lastParameter.contains("/")) {
      // Get last index of the backslash
      int lastIndex = lastParameter.lastIndexOf("/");
      // Filename is starts after the last backslash
      fileName = lastParameter.substring(lastIndex + 1);
    }
    // If the file will be added in the current directory
    else {
      // Split the last parameter using the regex >
      String[] splitParameter = lastParameter.split(">");
      // The file name is the last index of this array
      fileName = splitParameter[splitParameter.length - 1];
    }
    // Traverse the special symbols array.
    for (String symbol : specialSymbols) {
      // If the file name contains a special symbol, return an error message
      if (fileName.contains(symbol))
        return "!&!The name of the file contains an invalid symbol!&!";
    }
    // Check that the file name is not '/'
    if (fileName.replace("/", "").length() == 0)
      return "!&!A file with the name of '/' cannot be made!&!";
    // If the file name did not contain a special symbol, return empty string
    return "";
  }

  /**
   * Verifies only 2 quotation marks are contained in the userInput. Return error message if invalid
   * amount of quotation marks contained in userInput, "" otherwise.
   * 
   * @return error message if invalid amount of quotation marks exists, "" otherwise.
   */
  private String checkNumberOfQuotations() {
    // Holder for the userInput
    final String userInputHolder = userInput;

    // Get the number of quotations in the input
    int numOfQuotations = (userInput.length() - userInput.replace("\"", "").length());

    // If number of quotation marks is not 2, return an error message
    if (numOfQuotations != 2)
      return "!&!The number of quotation marks is invalid!&!";

    // If the number of quotation marks is valid, reset the userInput and return an empty string
    userInput = userInputHolder;
    return "";
  }


  /**
   * Verifies whether the input contains text only within the quotation marks. Return error message
   * if any texts are outside of quotation marks, "" otherwise.
   * 
   * @return error message if any texts are outside of quotation marks, "" otherwise.
   */
  private String checkSimpleEcho() {
    // Check that userInput starts with " after 'echo'
    if (!givenParameters.get(0).startsWith("\""))
      return "!&!Text to be stored is not allowed outside of the two quotation marks!&!";

    // Check that userInput ends with a "
    int lastIndexOfParameters = givenParameters.size() - 1;
    if (!givenParameters.get(lastIndexOfParameters).endsWith("\""))
      return "!&!Text to be stored is not allowed outside of the two quotation marks!&!";

    // If the checks are successfully passed, return an empty string
    this.givenCommand = "echohandler";
    return "";
  }

  /**
   * Verifies that, for EchoOverwrite and EchoAppend, the input does not contain '>' within the text
   * to store. It also verifies that either one or two > symbols are given and if two are given,
   * they are adjacent. If the input fails any of these conditions, it returns an error message.
   * Otherwise return an empty string.
   * 
   * @return "" if passed verify successfully, specified error message otherwise.
   */
  private String checkSymbolsComplexEcho() {
    // Local variable
    String tempString;

    // Split the user input into the givenText and afterwards
    // Get the first and last occurrences of " in userInput
    int firstIndexOfQuotation = (userInput.indexOf("\"") + 1);
    int lastIndexOfQuotation = (userInput.lastIndexOf("\""));
    final String givenText = userInput.substring(firstIndexOfQuotation, lastIndexOfQuotation);

    // Check total number of '>' symbols in the user input
    int numOfSymbol = (userInput.length() - userInput.replace(">", "").length());

    // Checking if all '>' are in the text to store (actually a simple echo then)
    if (givenText.contains(">")) {
      tempString = givenText;
      if (givenText.length() - tempString.replace(">", "").length() == numOfSymbol)
        return this.checkSimpleEcho();
    }
    // If this line is reached, then user input is actually a complex echo
    final String afterText = userInput.substring(lastIndexOfQuotation + 1);

    // Find the number of '>' in the afterText
    tempString = afterText;
    int symbolInAfterText = afterText.length() - tempString.replace(">", "").length();

    // If more than two '>' are in afterText, return an error message
    if (symbolInAfterText > 2)
      return "!&!An incorrect number of '>' were given!&!";

    // If two '>' are in the afterText, check that they are both together
    if (symbolInAfterText == 2) {
      if (!afterText.contains(">>"))
        return "!&!The '>' must be adjacent to one another!&!";
    }
    // Specify the echo command name and return an empty string.
    if (symbolInAfterText == 1)
      this.givenCommand = "echooverwrite";
    else
      this.givenCommand = "echoappend";
    return "";
  }

  /**
   * This method verifies that, for EchoOverwrite and EchoAppend, the input contains text only
   * within quotation marks, and that a path is provided. It also verifies that the symbol > is not
   * within the text to store. If the input fails any of these conditions, it returns an error
   * message. Otherwise return by calling the method above.
   * 
   * @return "" if the userInput passed verify successfully, specified error message otherwise
   */
  private String checkPathAndTextComplexEcho() {
    // Get index of the last "
    int lastQuotation = userInput.lastIndexOf("\"") + 1;

    // Now checking that the text to store is given and valid
    // Check that there is no text before the first "
    if (!givenParameters.get(0).startsWith("\""))
      return "!&!Text to be stored is not allowed outside of the two quotation marks!&!";

    // Check that there is no text immediately after the last "
    String givenString = userInput.substring(lastQuotation).strip();
    if (!givenString.startsWith(">"))
      return "!&!Text to be stored is not allowed outside of the two quotation marks!&!";

    // Check that givenText contains both quotation marks
    String givenText = userInput.substring(0, lastQuotation).strip();
    int numOfQuotations = (givenText.length() - givenText.replace("\"", "").length());
    if (numOfQuotations != 2)
      return "!&!Both quotation marks should enclose only the text to be stored!&!";

    // Now checking if a path is given
    // Check if the last index is >. If it is, return an error message.
    int lastIndexOfParameters = (givenParameters.size() - 1);
    if (givenParameters.get(lastIndexOfParameters).endsWith(">"))
      return "!&!A path must be provided!&!";

    // Check that the path given does not have '//'
    if (givenParameters.get(lastIndexOfParameters).contains("//"))
      return "!&!The path cannont contain multiple backslashes together!&!";

    // If all of these checks are passed, return an empty string
    return "";
  }

  //////////////////////////////////// Command Specific Verify /////////////////////////////////////

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for commands take no parameter. PrintWorkingDirectory, PopDirectory or Exit. It
   * returns "" if no parameter is given, returns an error message otherwise
   * 
   * @return "" if no parameter is given, returns an error message otherwise
   */
  @SuppressWarnings("unused")
  private String verifyNoParameters() {

    // Verifying the number of parameters given is valid
    int numbOfParameters = this.givenParameters.size();

    // The number of parameters allowed is 0. If not zero, then return an error message.
    if (numbOfParameters != 0)
      return "!&!The number of parameters is invalid!&!";

    // We do not need to check if given parameters are correct.
    return "";
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for commands take 1 parameter as input. ChangeDirectory, PushDiretory,
   * RemoveDirectory, and Manual. If the number of parameter is valid, returns "". Otherwise, it
   * returns specified error message if wrong number of parameters is given or if an invalid path is
   * given.
   * 
   * @return "" if correct number of parameter given. Specified error message if wrong number of
   *         parameters is given or if an invalid path is given.
   */
  @SuppressWarnings("unused")
  private String verifyOneParameter() {

    // Verifying the number of parameters given is valid
    int numbOfParameters = this.givenParameters.size();

    // The number of parameters allowed is 1. If not one, then return an error message.
    if (numbOfParameters != 1)
      return "!&!The number of parameters is invalid!&!";

    // Check that the path given does not have '//'
    if (givenParameters.get(0).contains("//"))
      return "!&!The path cannont contain multiple backslashes together!&!";

    return "";
  }

  /**
   * Verifies for the SaveJShell. It returns "" if the number of parameter is valid and the fileName
   * is valid. Otherwise, it returns a specified error message if wrong number of parameters is
   * given or invalid fileName is given.
   * 
   * @return "" if correct number of parameters are passed, specified error message is given
   * 
   */
  private String verifySaveJShell() {

    // Verifying the number of parameters given is valid
    int numbOfParameters = this.givenParameters.size();

    // The number of parameters allowed is 1. If not one, then return an error message.
    if (numbOfParameters != 1)
      return "!&!The number of parameters is invalid!&!";

    // Check that its a file and not path '/'
    if (givenParameters.get(0).contains("/"))
      return "!&!Cannot accept path to store file!&!";

    // Checks the file name does not contain any of the special symbol
    this.checkSpecialSymbolInDirectoryName();
    if (this.givenParameters.get(0)
        .contains("The name of a directory contains an invalid symbol"))
      return "!&!The name of the file contains an invalid symbol!&!";

    return "";
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for the LoadJShell. It returns "" if the number of parameter is valid and the fileName
   * is valid. Otherwise, it returns a specified error message if wrong number of parameters is
   * given or invalid fileName is given. If the file does not exit than it provides an error
   * 
   * @return "" if correct number of parameters are passed, specified error message is given
   * 
   */
  @SuppressWarnings("unused")
  private String verifyLoadJShell() {
    String error = this.verifySaveJShell();

    if (error.length() != 0)
      return error;

    // Check whether the file exit or not on the user file System
    java.nio.file.Path fileName;
    fileName = Paths.get(this.getParameters().get(0));

    if (!Files.exists(fileName))
      return "!&!Cannot read the file because it does not exit!&!";

    return "";
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for the MakeDirectory. It returns "" if number of parameters is valid. Otherwise, it
   * returns a specified error message if wrong number of parameters is given or if an invalid path
   * is given.
   * 
   * @return "" if correct number of parameters is valid, specified error message otherwise if wrong
   *         number of parameters is given or invalid path is given.
   */
  @SuppressWarnings("unused")
  private String verifyMakeDirectory() {

    // Verifying the number of parameters given is valid
    int numbOfParameters = this.givenParameters.size();

    // The number of parameters allowed is greater than zero. If zero parameters given, then return
    // an error message.
    if (numbOfParameters == 0)
      return "!&!The number of parameters is invalid!&!";

    // Verify that you cannot make a root
    String tempString;
    for (String parameter : givenParameters) {
      tempString = parameter;
      if (tempString.replace("/", "").length() == 0)
        return "!&!Another directory with the name of '/' cannot be made!&!";
    }

    // Check that the path given does not have '//'
    for (String parameter : givenParameters) {
      if (parameter.contains("//"))
        return "!&!The path cannot contain multiple backslashes together!&!";
    }

    // Verify the directory names do not contain any special symbols
    return this.checkSpecialSymbolInDirectoryName();
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for commands take at least one parameter as input. It returns "" if the parameters are
   * valid. It returns an error message if no parameter is given or invalid parameter is given.
   * 
   * @return "" if the parameter are all valid. Specified error message if no parameter is given or
   *         an invalid parameter is given.
   */
  @SuppressWarnings("unused")
  private String verifyParameterExist() {

    // Verifying the number of parameters given are valid
    int numbOfParameters = this.givenParameters.size();

    // The minimum number of parameters allowed is 1. If not, then return an error message.
    if (numbOfParameters == 0)
      return "!&!The number of parameters is invalid!&!";

    // Check that the path given does not have '//'
    if (givenParameters.get(0).contains("//"))
      return "!&!The path cannont contain multiple backslashes together!&!";

    return "";
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for Echo commands that take at least one parameter as input. It returns "" if all
   * parameters are valid. Otherwise, it returns an error message if no parameter is given or an
   * invalid parameter is given.
   * 
   * @return "" if all parameters are valid, specified error message if no parameter is given or an
   *         invalid parameter is given.
   */
  @SuppressWarnings("unused")
  private String verifyEcho() {
    // Verifying the number of parameters given are valid
    // The minimum number of parameters allowed is 1. If not, then return an error message.
    if (this.givenParameters.size() == 0)
      return "!&!The number of parameters is invalid!&!";

    // Verifying if the parameters are valid.
    String errorMessage;

    // Checking the number of quotation marks
    errorMessage = this.checkNumberOfQuotations();
    if (!errorMessage.isEmpty())
      return errorMessage;

    // Checking simple echo. Call helper method to verify the input.
    if (!userInput.contains(">"))
      errorMessage = this.checkSimpleEcho();

    // Checking complex echo or a special case of simple echo
    else {
      // Call helper method to verify the '>' given in the input is valid
      errorMessage = this.checkSymbolsComplexEcho();

      // If the input is not a simple echo, perform these checks
      if (!givenCommand.contentEquals("echohandler")) {
        // Call helper method to verify that text and a path are given
        if (errorMessage.isEmpty())
          errorMessage = this.checkPathAndTextComplexEcho();

        // Call the helper method to verify that a valid file name was given
        if (errorMessage.isEmpty())
          errorMessage = this.checkSpecialSymbolsInFileName();
      }
    }
    // Return the error message, or empty string
    return errorMessage;
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies that one or no parameter is given by History. It returns "" if all parameters are
   * valid. Otherwise, it returns a specified error message if wrong number of parameters is given
   * or invalid parameter is given.
   * 
   * @return "" if all parameters are valid. Specified error message if wrong number of parameters
   *         is given or invalid parameter is given.
   */
  @SuppressWarnings("unused")
  private String verifyHistory() {
    // Verifying the number of parameters given are valid
    int numbOfParameters = this.givenParameters.size();

    // The number of parameters allowed is 0 or 1. If not, return an error message.
    if (numbOfParameters >= 2)
      return "!&!The number of parameters is invalid!&!";

    // Verifying if a correct parameter is given. Checking if the parameter is an integer.
    if (!this.givenParameters.isEmpty()) {
      try {
        // Try to convert the string to an integer
        int value = Integer.parseInt(givenParameters.get(0));

        // If the integer provided is less than 0
        if (value < 0)
          return "!&!The integer cannot be negative!&!";
      } catch (NumberFormatException e) {
        // If unable to convert to an integer
        return "!&!The parameter provided must be a positive integer!&!";
      }
    }
    // If no parameter provided or the parameter is positive integer, return an empty string
    return "";
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Verifies for the Search command that a valid number of parameters are given and valid
   * parameters are given.
   * 
   * @return String - Empty string if all valid quantity and type of parameters are valid. Otherwise
   *         an error message
   */
  @SuppressWarnings("unused")
  private String verifySearch() {
    // Verifying the number of parameters given are valid
    int numbOfParameters = this.givenParameters.size();
    // The number of parameters allowed is greater than six. If not, return an error message.
    if (numbOfParameters < 5)
      return "!&!The number of parameters is invalid!&!";

    // Check that the path given does not have '//'
    if (userInput.contains("//"))
      return "!&!The path cannont contain multiple backslashes together!&!";

    // Check that last parameter has two double quotes, a name is provided, and no text comes before
    int lastIndexOfParameters = numbOfParameters - 1;
    String parameter = givenParameters.get(lastIndexOfParameters);
    if (parameter.length() - parameter.replace("\"", "").length() != 2)
      return "!&!The given name is invalid!&!";
    if (!parameter.startsWith("\""))
      return "!&!No text can come before the double quote!&!";
    if (parameter.contains("\"\""))
      return "!&!A name must be provided!&!";

    // Check the parameter '-name' is given
    parameter = givenParameters.get(lastIndexOfParameters - 1);
    if (!parameter.contentEquals("-name"))
      return "!&!The argument '-name' must be provided!&!";

    // Check that search type is given
    parameter = givenParameters.get(lastIndexOfParameters - 2);
    if (!parameter.contentEquals("f") && !parameter.contentEquals("d"))
      return "!&!The type specification is incorrect!&!";

    // Check that parameter '-type' is given
    parameter = givenParameters.get(lastIndexOfParameters - 3);
    if (!parameter.contentEquals("-type"))
      return "!&!The argument '-type' must be provided!&!";

    // Check that at least one path is given
    if (givenParameters.get(0).contentEquals(parameter))
      return "!&!At least one path must be provided!&!";
    // All checks have been passed
    return "";
  }

  /**
   * Verifies that only one URL is provided to Curl. It returns "" if the URL provided is valid.
   * Otherwise, it returns a specified error message if wrong number of parameters is given or
   * invalid URL is given.
   * 
   * @return "" if the URL is valid. Specified error message if wong number of parameters is given
   *         or the URL given is invalid
   */
  @SuppressWarnings("unused")
  private String verifyCurl() {
    int numOfParameters = this.givenParameters.size();

    if (numOfParameters != 1) {
      return "!&!The number of parameters is invalid!&!";
    }
    try {
      URL url = new URL(givenParameters.get(0));
      url.toURI();
      return "";
    } catch (Exception e) {
      return "!&!The URL given is invalid!&!";
    }
  }

  /**
   * Verifies that only two parameters are given and that valid directory or file names are given.
   * It returns "" if the input is valid. Otherwise, it returns a specified error message if wrong
   * number of parameters is given or invalid names are given.
   * 
   * @return An empty string if valid input is given. Otherwise, a specified error message if wrong
   *         number of parameters is given or invalid input given
   */
  @SuppressWarnings("unused")
  private String verifyTwoParameters() {
    int numOfParameters = this.givenParameters.size();

    // Check that valid number of parameters is given (two)
    if (numOfParameters != 2) {
      return "!&!The number of parameters is invalid!&!";
    }

    // Now check the name of the directories or the files given are valid
    this.checkSpecialSymbolInDirectoryName();

    // If a directory contained an invalid name, return the error
    for (String argument : this.givenParameters) {
      if (argument.contentEquals("!&!The name of a directory contains an invalid symbol!&!"))
        return argument;
    }
    // If the directory names were valid, return an empty string
    return "";
  }

  ///////////////////////////////////// Verify Redirection /////////////////////////////////////////

  /**
   * Verifies that the file name to be created by redirection contains no special symbols listed. If
   * the input contains a special symbol, set redirection to false and append the error.
   * 
   * @param output The output object which contains the parameters for redirection
   */
  private void checkRedirectionFileName(Output output) {
    String fileName, filePath = output.getRedirectionParameters().get(0);

    // If a relative or absolute path is given
    if (filePath.contains("/")) {

      // Get last index of the backslash
      int lastIndex = filePath.lastIndexOf("/");
      // Filename is starts after the last backslash
      fileName = filePath.substring(lastIndex + 1);
    }

    // If the file will be added in the current directory
    else
      fileName = filePath;

    // Traverse the special symbols array.
    for (String symbol : specialSymbols) {
      // If the file name contains a special symbol, set redirection to false and append message
      if (fileName.contains(symbol)) {
        output.setRedirectionOccuring(false);
        output.appendRedirectionError(
            "Name of the file contains an invalid symbol, so the output will not be redirected\n");
        break;
      }
    }

    // If the file name is '/', set redirection to false and append the message
    if (fileName.replace("/", "").length() == 0) {
      output.setRedirectionOccuring(false);
      output.appendRedirectionError("A file with the name of '/' cannot be made\n");
    }
  }

  /**
   * Verifies that the output given by the specific command can be redirected. If the output cannot
   * be redirected or the file name provided is invalid, then the redirection variable within the
   * output object is set to false and an error message is appended if necessary.
   * 
   * @param output The output object which contains the parameters for redirection
   */
  private void verifyRedirection(Output output) {

    // If redirection is not occurring, return
    if (!output.isRedirectionOccuring())
      return;

    // Check if redirection is possible with this command
    boolean redirectionPossible = redirectionHashMap.get(givenCommand);

    // If redirection is not possible for the given command, set the variable in output to false
    if (!redirectionPossible)
      output.setRedirectionOccuring(false);

    // Redirection is possible, so check if the
    else {
      // If no file name is given, set redirection to false
      if (output.getRedirectionParameters().size() == 0)
        output.setRedirectionOccuring(false);

      // Check if the filename at index 0 is valid
      this.checkRedirectionFileName(output);

      // If parameters are given after the file name, append to error message
      if (output.getRedirectionParameters().size() >= 2
          && output.getRedirectionError().length() == 0)
        output.appendRedirectionError(
            "The parameters entered after the file name were not considered\n");
    }
  }

  ///////////////////////////////////////// Main Method ////////////////////////////////////////////

  /**
   * Invokes a method belonging to the Verify class, which checks the parameters given for the
   * specific command.
   * 
   * @param object The Verify object initialized in JShell
   * @param methodName The name of the method belonging to the Verify Class
   * @return A string that is returned by the method
   */
  private String invokeMethod(Verify object, String methodName) {
    // Create an object that will contain the return value and object that contains the method
    java.lang.reflect.Method method;
    Object returnMessage;

    // Get the method whose name is the same as methodName and assign it to the variable method.
    try {
      Class<?> c = object.getClass();
      method = c.getDeclaredMethod(methodName);
    } catch (SecurityException e) {
      return "This line will never run";
    } catch (NoSuchMethodException e) {
      return "This line will never run";
    }
    // Invoke the Method, giving the Verify object as a parameter
    try {
      returnMessage = method.invoke(object);
    } catch (IllegalArgumentException e) {
      return "This line will never run";
    } catch (IllegalAccessException e) {
      return "This line will never run";
    } catch (InvocationTargetException e) {
      return "This line will never run";
    }
    // Return the message
    return (String) returnMessage;
  }

  /**
   * Takes the user input command, and calls that command's verify method by the hash map. Returns
   * returnMessage from the verify method of current command. Specified error message if invalid
   * command is given.
   * 
   * @param object The Verify object initialized in JShell
   * @return returnMessage from the verify method of user desired command. Specified error message
   *         if invalid command is given.
   */
  public String verifyInput(Verify object, Output output) {
    // If the command given was loadJShell
    if ((disable != 0) && this.givenCommand.contentEquals("loadJShell")) {
      return "!&!loadJShell command is disabled because new file System has been created!&!";
    }

    // If the command is ls, only check the redirection parameters
    if (givenCommand.contentEquals("ls")) {
      this.verifyRedirection(output);
      return "";
    }

    // Try to get the string containing the method name from verifyHashMap for the given command.
    String methodName = methodHashMap.get(this.givenCommand);

    // If the given command does not exist, then methodName will be null.
    if (methodName == null)
      return "!&!An invalid command is given!&!";

    // Now to verify if the parameters given by the user are valid.
    this.verifyRedirection(output);

    // Return by invoking the method
    return this.invokeMethod(object, methodName);
  }
}
