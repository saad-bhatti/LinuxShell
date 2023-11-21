package shell;

import java.util.ArrayList;
import java.util.HashMap;
import fileSystem.*;
import fileSystem.Stack;
import java.lang.StringBuilder;
import java.lang.reflect.*;

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
 * Prepares the commands for execution
 */
public class Prepare {

  /**
   * Hash map that stores all commands
   */
  private HashMap<String, String> prepareHashMap;

  /**
   * The command entered by the user
   */
  private String command;
  /**
   * The parameters entered by the user
   */
  private ArrayList<String> parameters;
  /**
   * The user input
   */
  private String input;

  /**
   * The arguments contains all objects that a command required
   */
  private ArrayList<Object> arguments;
  /**
   * The Log object that contains all user inputs
   */
  private Log logger;
  /**
   * The Stack object that stores working directories by PushDirectory class
   */
  private Stack stack;
  /**
   * The ArrayList that determine whether the shell is running or not
   */
  private ArrayList<String> running;
  /**
   * The first argument user input
   */
  private String argument1;
  /**
   * The second argument user input
   */
  private String argument2;

  ///////////////////////////////////// Initializing ///////////////////////////////////////////////

  /**
   * Constructs a new Prepare object, initializes variables
   * 
   * @param argumentInJShell The argument variable in JShell
   * @param runningInJShell The running variable in JShell
   * @param loggerInJShell The Log object in JShell
   */
  public Prepare(ArrayList<Object> argumentInJShell, ArrayList<String> runningInJShell,
      Log loggerInJShell) {
    prepareHashMap = new HashMap<>(20);
    Prepare.initializeHashMapWithCommandName(prepareHashMap);
    arguments = argumentInJShell;
    running = runningInJShell;
    logger = loggerInJShell;
    stack = new Stack();
  }

  /**
   * Initializes the Hash Map
   * 
   * @param prepareHashMap The hash map that contains all commands prepared methods
   */
  private static void initializeHashMapWithCommandName(HashMap<String, String> prepareHashMap) {
    // Add the items into the fileHashMap
    // Keys are the command name and values are the method name.
    prepareHashMap.put("pwd", "prepareNoArguments");
    prepareHashMap.put("popd", "preparePopDirectory");
    prepareHashMap.put("exit", "prepareExit");
    prepareHashMap.put("pushd", "preparePushDirectory");
    prepareHashMap.put("cd", "prepareSingleArgument");
    prepareHashMap.put("rm", "prepareSingleArgument");
    prepareHashMap.put("curl", "prepareSingleArgument");
    prepareHashMap.put("man", "prepareSingleArgument");
    prepareHashMap.put("cp", "prepareTwoObjects");
    prepareHashMap.put("mv", "prepareTwoObjects");
    prepareHashMap.put("mkdir", "prepareMultipleObjects");
    prepareHashMap.put("cat", "prepareMultipleObjects");
    prepareHashMap.put("ls", "prepareListContent");
    prepareHashMap.put("history", "prepareHistory");
    prepareHashMap.put("echohandler", "prepareSimpleEcho");
    prepareHashMap.put("echooverwrite", "prepareComplexEcho");
    prepareHashMap.put("echoappend", "prepareComplexEcho");
    prepareHashMap.put("search", "prepareSearch");
    prepareHashMap.put("saveJShell", "prepareSaveJShell");
    prepareHashMap.put("loadJShell", "prepareSingleArgument");
    prepareHashMap.put("tree", "prepareNoArguments");
  }

  /**
   * Initializes the command, input and parameters
   * 
   * @param newCommand The command after Parse
   * @param newInput The user input
   * @param newParameters The parameters after Parse
   */
  public void setValues(String newCommand, String newInput, ArrayList<String> newParameters) {
    this.command = newCommand;
    this.input = newInput;
    this.parameters = newParameters;
  }
  
  public void setStack(Stack st) {
    this.stack = st;
  }

  //////////////////////////////////// Prepare Methods /////////////////////////////////////////////

  /**
   * Traverses the parameters ArrayList and combines to a new String str contains all elements in
   * parameters. Returns the new String str.
   * 
   * @return str that contains all elements of parameters
   */
  private String combineParameters(int start, int end) {

    // Create the string builder
    StringBuilder str = new StringBuilder();

    // Traverse the ArrayList parameters (till second to last index) and append the elements to str
    for (int i = start; i < end; i++) {
      str.append(this.parameters.get(i) + "&");
    }

    // Add the last index of parameters
    str.append(this.parameters.get(end));

    // Return str
    return str.toString();
  }

  /**
   * Return a String subString that contains the given text that is contained within userInput
   * 
   * @return a String subString that contains the given text that is contained within userInput
   */
  private String isolateGivenText() {

    // Get the first and last occurrences of " in userInput
    int firstIndexOfSubString = (this.input.indexOf("\"") + 1);
    int lastIndexOfSubString = (this.input.lastIndexOf("\""));

    // Get the substring of userInput that will contain the given text
    String subString = this.input.substring(firstIndexOfSubString, lastIndexOfSubString);

    return subString;
  }

  // Return a string that contains the given path that is contained within userInput
  private String isolateGivenPath() {

    // Get the last parameter from givenParameters
    int lastIndexOfParameters = parameters.size() - 1;
    String lastParameter = parameters.get(lastIndexOfParameters);

    // Split the last parameter with regex '>'
    String[] splitParameter = lastParameter.split(">");
    // The file name is the last index of this array
    String path = splitParameter[splitParameter.length - 1];

    return path;
  }

  /////////////////////////////////// Command Specific Methods /////////////////////////////////////


  // Suppress warning because this method would be called at runtime.
  /**
   * Prepares for PrintWorkingDirectory. This method is here so that if the command is "pwd", the
   * HashMap has a method (value) for it.
   */
  @SuppressWarnings("unused")
  private void prepareNoArguments() {
    return;
  }


  // Suppress warning because this method would be called at runtime.
  /**
   * Prepares for PopDirectory class. Adds stack to the arguments.
   */
  @SuppressWarnings("unused")
  private void preparePopDirectory() {
    // Add stack to the ArrayList arguments
    arguments.add(stack);
  }


  // Suppress warning because this method would be called at runtime.
  /**
   * Prepares for PushDirectory class. Adds stack and a directory path to the arguments
   */
  @SuppressWarnings("unused")
  private void preparePushDirectory() {

    // Add stack to the ArrayList arguments
    arguments.add(stack);

    // Add the path given by the user to arguments
    this.prepareSingleArgument();
  }

  // Suppress warning because this method would be called at runtime.
  /**
   * Prepares for Exit, adds running to the arguments.
   */
  @SuppressWarnings("unused")
  private void prepareExit() {
    // Add the ArrayList running to the ArrayList arguments
    arguments.add(running);
  }

  @SuppressWarnings("unused")
  private void prepareSaveJShell() {
    arguments.add(logger);
    arguments.add(stack);
    this.prepareSingleArgument();
  }


  /**
   * Prepares for ChangeDirectory, PushDirectory, Manual, RemoveDirectory. Adds one directory to
   * arguments.
   */
  private void prepareSingleArgument() {

    // Assign the path to argument1
    argument1 = parameters.get(0);

    // Add argument1 to the ArrayList arguments
    arguments.add(argument1);
  }

  // Suppress warning because this method would be called at runtime
  /**
   * Prepares for CopyItem and MoveItem. Adds two objects to arguments.
   */
  @SuppressWarnings("unused")
  private void prepareTwoObjects() {

    // Add the first object to the ArrayList arguments
    arguments.add(parameters.get(0));

    // Add the second object to the ArrayList arguments
    arguments.add(parameters.get(1));
  }

  /**
   * Prepares for Concatenate and MakeDirectory. Adds multiple objects to arguments.
   */
  private void prepareMultipleObjects() {

    // Assign the multiple objects combined to argument1
    argument1 = this.combineParameters(0, parameters.size() - 1);

    // Add argument1 to the ArrayList arguments
    arguments.add(argument1);
  }


  // Suppress warning because this method would be called at runtime
  /**
   * Prepares for History. Adds Log and user input to arguments.
   */
  @SuppressWarnings("unused")
  private void prepareHistory() {

    // Add logger into the ArrayList arguments
    arguments.add(logger);

    // Prepare the integer, if it is given.
    if (!parameters.isEmpty())
      this.prepareSingleArgument();

    // If no integer is given, then do nothing
  }


  // Suppress warning because this method would be called at runtime.
  /**
   * Prepares for ListCContent. Add nothing to arguments if no path is given, one path if one path
   * is given, all paths if multiple paths are given.
   */
  @SuppressWarnings("unused")
  private void prepareListContent() {

    // If no path is given, then add an empty string
    if (parameters.isEmpty())
      arguments.add("");

    // If a single path is given, then prepare a single path, if -R is given, then prepare -R
    else if (parameters.size() == 1)
      this.prepareSingleArgument();

    // If -R and multiple paths are given, then prepare -R and combine the multiple paths
    else if ((parameters.get(0).equals("-R")) && (parameters.size() > 1)) {
      this.prepareMultipleObjects();
    }
    
    else if ((!parameters.get(0).equals("-R")) && (parameters.size() > 1)) {
      this.prepareMultipleObjects();
    }
      
  }


  // Suppress warning because this method would be called at runtime
  /**
   * Prepares for EchoHandler. Add user input text to arguments.
   */
  @SuppressWarnings("unused")
  private void prepareSimpleEcho() {

    // Assign the given text to argument1
    this.argument1 = this.isolateGivenText();

    // Add the argument to the ArrayList arguments
    this.arguments.add(argument1);
  }

  // Suppress warning because this method would be called at runtime
  /**
   * Prepares for EchoOverwrite and EchoAppend. Add the filename and contents to arguments.
   */
  @SuppressWarnings("unused")
  private void prepareComplexEcho() {

    // Assign the single path to argument1
    this.argument1 = this.isolateGivenPath();

    // Add the argument to the ArrayList arguments
    this.arguments.add(argument1);

    // Assign the given text to argument2
    this.argument2 = this.isolateGivenText();

    // Add the argument to the ArrayList arguments
    this.arguments.add(argument2);
  }

  // Suppress warning because this method would be called at runtime
  /**
   * Prepares for Search Command. Add the path(s), the type to search for, and the name of what to
   * search for.
   */
  @SuppressWarnings("unused")
  private void prepareSearch() {
    // Get the path(s) and combine them if necessary
    int lastPathIndex = parameters.indexOf("-type") - 1;
    argument1 = this.combineParameters(0, lastPathIndex);
    arguments.add(argument1);

    // Add the type to the arguments
    argument1 = parameters.get(lastPathIndex + 2);
    arguments.add(argument1);

    // Add the name of the object to searched for
    argument1 = parameters.get(lastPathIndex + 4).replace("\"", "");
    arguments.add(argument1);
  }

  ///////////////////////////////////////// Main Method ////////////////////////////////////////////

  // Main method in charge of preparing the arguments for commands
  /**
   * Takes the command input by the user, and prepares for that command by calling its prepare
   * method.
   * 
   * @param object The Prepare object initialized before.
   */
  public void prepareArguments(Prepare object) {
    // Clear the existing arguments
    arguments.clear();

    // Get the string containing the method name from prepareHashMap for the corresponding command.
    String methodName = prepareHashMap.get(this.command);

    // Create a method object
    java.lang.reflect.Method method;

    // Get the method whose name is the same as methodName and assign it to the variable method.
    // Catch will never occur as the command has been verified to exist and the command is a key
    // to the method name in the HashMap.
    try {
      Class<?> c = object.getClass();
      method = c.getDeclaredMethod(methodName);
    } catch (SecurityException e) {
      return;
    } catch (NoSuchMethodException e) {
      return;
    }
    // Invoke the Method, giving the Prepare object as a parameter
    try {
      method.invoke(object);
    } catch (IllegalArgumentException e) {
      return;
    } catch (IllegalAccessException e) {
      return;
    } catch (InvocationTargetException e) {
      return;
    }
  }
}
