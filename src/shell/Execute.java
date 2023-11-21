package shell;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import commands.*;
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
 * It execute the command specified by the user
 */
public class Execute {

  // Instance Variables
  /**
   * A hash map Stores commands name to the function class names
   */
  private HashMap<String, String> commands;
  /**
   * CommandHandler reference used to call the subclass methods
   */
  private CommandHandler cmdHandler;

  /**
   * Constructor initializes the hash map which contains command name as key and class name as
   * values
   */
  public Execute() {
    this.commands = new HashMap<String, String>(21);
    this.mappingCommandsToClass(commands);
  }

  /**
   * Takes in the user input and fileSystem and performs the specified command mentioned by the
   * user. This class instantiates other classes specified by the commandName at run time and
   * perform the specified operation.
   * 
   * @param fileSystem the FilySystem which contains the current working directory
   * @param commandName the String which contains name of the command specified by the user
   * @param arguments the array list which contains the specified arguments which helps perform the
   *        operation
   * @return If the operation is successful then returns an empty String. Otherwise, returns a
   *         specified error message
   */

  public String executeCommand(FileSystem fileSystem, String commandName,
      ArrayList<Object> arguments) {

    String error = "";

    try {

      // Get the name of the class (value) from the HashMap with key commandName
      String className = this.commands.get(commandName);

      // Downcast cmdHandler and perform the operation of the specified class from className
      try {

        this.cmdHandler =
            (CommandHandler) Class.forName(className).getDeclaredConstructor().newInstance();
        error = cmdHandler.performOperation(fileSystem, arguments);
        return error;

      } catch (InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SecurityException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return error;
  }

  /**
   * Initializes the map from command name to its related class
   * 
   * @param commandsToClass Contains the hash map commandsToClass
   */
  private void mappingCommandsToClass(HashMap<String, String> commandsToClass) {

    commandsToClass.put("man", "commands.Manual");
    commandsToClass.put("pwd", "commands.PrintWorkingDirectory");
    commandsToClass.put("mkdir", "commands.MakeDirectory");
    commandsToClass.put("echohandler", "commands.EchoHandler");
    commandsToClass.put("echooverwrite", "commands.EchoOverwrite");
    commandsToClass.put("echoappend", "commands.EchoAppend");
    commandsToClass.put("cd", "commands.ChangeDirectory");
    commandsToClass.put("ls", "commands.ListContent");
    commandsToClass.put("cat", "commands.Concatenate");
    commandsToClass.put("popd", "commands.PopDirectory");
    commandsToClass.put("exit", "commands.Exit");
    commandsToClass.put("pushd", "commands.PushDirectory");
    commandsToClass.put("history", "commands.History");
    commandsToClass.put("rm", "commands.RemoveDirectory");
    commandsToClass.put("curl", "commands.Curl");
    commandsToClass.put("saveJShell", "commands.SaveJShell");
    commandsToClass.put("search", "commands.Search");
    commandsToClass.put("tree", "commands.Tree");
    commandsToClass.put("mv", "commands.MoveItem");
    commandsToClass.put("cp", "commands.CopyItem");
  }
}
