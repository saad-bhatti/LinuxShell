package commands;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Represents the Manual command printing instruction of the other commands.
 */
public class Manual extends CommandHandler {

  /**
   * Contains the command name as key and their description as value
   */
  private HashMap<String, String> commandDescription;


  /**
   * Default Constructor initializes the hash map and call functions to create a map for allCommands
   * from command to its description
   */
  public Manual() {
    // initial creation of the hash map
    this.commandDescription = new HashMap<>(20);
    
    // initialize the Hash Map with all the commands map command to description call
    this.removeDescription(commandDescription);
    this.exitDescription(commandDescription);
    this.makeDirectoryDescription(commandDescription);
    this.changeDirectoryDescription(commandDescription);
    this.listContentDescription(commandDescription);
    this.printWorkingDirectoryDescription(commandDescription);
    this.moveDirectoryDescription(commandDescription);
    this.copyDirectoryDescription(commandDescription);
    this.catDescription(commandDescription);
    this.curlDescription(commandDescription);
    this.echoDescription(commandDescription);
    this.manualDescription(commandDescription);
    this.pushDirectoryDescription(commandDescription);
    this.popDirectoryDescription(commandDescription);
    this.historyDescription(commandDescription);
    this.saveJShellDescription(commandDescription);
    this.loadJShellDescription(commandDescription);
    this.searchDescription(commandDescription);
    this.treeDescription(commandDescription);
  }


  /**
   * {@inheritDoc} Takes in array of commands and split it with And Symbol and then prints the
   * instruction of the command if exist. Produces a specific error of the command in the array if
   * it does not exist.
   * 
   * @param unused of type FileSystem is not used inside the function its just there to fill the
   *        requirement of the abstract method
   * @param arrayOfCommand of type ArrayList Object contains an array of command separated by And
   * @return String If the command does not exist than it returns an error message that this command
   *         does not exist otherwise, if successful operation then empty string "" gets returned.
   */
  @Override
  public String performOperation(FileSystem unused, ArrayList<Object> command) {

    String cmd = (String) command.get(0);

    if (this.commandDescription.containsKey(cmd)) {
      String full = "Name:\n\t" + cmd + "\nDescription: \n\t" + this.commandDescription.get(cmd);
      return full;
    }
    // return an error message related to the command
    return "!&!Command " + cmd + " does not exist!&!";
  }
  
  /**
   * Initializes the remove command description
   * @param rm Hash map which contains remove command description
   */
  private void removeDescription(HashMap<String, String> rm) {
    rm.put("rm",
        "Removes the directory from the file system. This also removes all the\n\t"
            + "children of directories (It acts recursively). Prints an error if input is not\n\t"
            + "provided or directory does not exist.");
  }
  
  /**
   * Initializes the exit command description
   * @param exit Hash map which contains exit command description
   */
  private void exitDescription(HashMap<String, String> exit) {
    exit.put("exit", "Terminate the program. Prints an error if input is provided.");
  }
  
  /**
   * Initializes the mkdir command description
   * @param mkdir Hash map which contains mkdir command description
   */
  private void makeDirectoryDescription(HashMap<String, String> mkdir) {
    mkdir.put("mkdir", "Create directories each of which may be relative to the current\n\t"
        + "directory or may be absolute path. It accepts a list of DIR names If creating DIR1\n\t"
        + "results in any kind of error, then the process does not proceed with creating DIR2\n\t"
        + "in the list. However, if DIR1 was created successfully and creation of DIR2 results\n\t"
        + "in an error, then it produces an error specific to DIR2 and does not proceed with\n\t"
        + "the remaining list.");
  }
  
  /**
   * Initializes the cd command description
   * @param cd Hash map which contains cd command description
   */
  private void changeDirectoryDescription(HashMap<String, String> cd) {
    cd.put("cd", "Change current directory to the new directory DIR, which may be\n\t"
        + "relative to current directory or may be full path. If DIR is .. changes to parent\n\t"
        + "directory and if DIR is . then remains inside the current directory. For absolute\n\t"
        + "path the directory DIR must contain /, the forward slash. If the DIR does not exist\n\t"
        + "(relative or absolute) than it provides a suitable error related to that DIR. The\n\t"
        + "root of the file system is a single slash: /.");
  }

  /**
   * Initializes the ls command description
   * @param ls Hash map which contains ls command description
   */
  private void listContentDescription(HashMap<String, String> ls) {
    ls.put("ls", "If no input are given than it print the contents (file or directory)\n\t"
        + "of the current directory, with a new line following each of the content. If\n\t"
        + "ls -R DIR is given, recursively list all the subdirectories."
        + "(file or directory). Otherwise, if input PATH is provided than:\n\t"
        + "\t1) If PATH specifies a file FILE, then it prints the PATH.\n\t"
        + "\t2) If PATH specifies a directory DIR, than prints the PATH followed by colon and\n\t"
        + "\t   then prints the content of that directory followed by an extra blank new line\n\t"
        + "\t   to distinguish PATH from content of that directory.\n\t"
        + "\t3) If PATH does not exist than it prints a suitable error message.\n\t"
        + "\t4) If PATH is a list of inputs where the first argument (DIR\\FILE) does not exist\n\t"
        + "\t   than it prints an error message specific to it and does not proceed with\n\t"
        + "\t   displaying the content of the rest of the DIR\\FILE in the list.");
  }

  /**
   * Initializes the pwd command description
   * @param pwd Hash map which contains pwd command description
   */
  private void printWorkingDirectoryDescription(HashMap<String, String> pwd) {
    pwd.put("pwd", "Print the current working directory (including the absolute path).\n\t"
        + "Prints an error if input is provided.");
  }

  /**
   * Initializes the mv command description
   * @param mv Hash map which contains mv command description
   */
  private void moveDirectoryDescription(HashMap<String, String> mv) {
    mv.put("mv", "Takes in two arguments which can be relative or absolute from current\n\t"
        + "directory. If the newPath is directory than moves the item from oldPath to newPath.\n\t"
        + "If oldPath and NewPath are files with same name then overwrites the newPath file,\n\t"
        + "however if both are directories with same name than overwrites the newPath\n\t"
        + "directory. If oldPath is File and newPath is directory than moves the file inside\n\t"
        + "the newPath directory. Produces a suitable error if invalid arguments are passed or\n\t"
        + "directory\\file does not exist.");
  }

  /**
   * Initializes the cp command description
   * @param cp Hash map which contains cp command description
   */
  private void copyDirectoryDescription(HashMap<String, String> cp) {
    cp.put("cp", "Takes in two arguments which can be relative or absolute from current\n\t"
        + "directory. If the newPath is directory than copies the item from oldPath to newPath.\n\t"
        + "If oldPath and NewPath are files with same name then overwrites the newPath file,\n\t"
        + "however if both are directories with same name than overwrites the newPath directory\n\t"
        + "If oldPath is File and newPath is directory than copy the file inside the newPath\n\t"
        + "directory. In short, works like move (mv) command, but does not remove the oldPath.\n\t"
        + "If oldPath is a directory then recursively copy the contents. Produces a suitable\n\t"
        + "error if invalid arguments are passed or directory\\file does not exist.");
  }
  
  /**
   * Initializes the cat command description
   * @param cat Hash map which contains cat command description
   */
  private void catDescription(HashMap<String, String> cat) {
    cat.put("cat", "Concatenate FILE1 and other files (i.e list of files [FILE1 ...])\n\t"
        + "and then display their contents on the shell. This can be redirected to a file.\n\t"
        + "Three line breaks is used to distinguish the content of one file FILE1 from the\n\t"
        + "other file FILE2.If file FILE1 does not exist and there is a list of files\n\t"
        + "[FILE1 FILE2 ...] than it prints an error and does not proceed with displaying the\n\t"
        + "content of the rest of the files in the list.");
  }

  /**
   * Initializes the curl command description
   * @param curl Hash map which contains curl command description
   */
  private void curlDescription(HashMap<String, String> curl) {
    curl.put("curl", "Takes in the URL is a web address. Retrieve the file at the URL creates a\n\t"
        + "file inside the current directory and store the content of the file. Takes in Single\n\t"
        + "argument. Prints a suitable error msg if the URL is invalid or the number of\n\t"
        + "arguments are wrong");
  }

  /**
   * Initializes the echo command description
   * @param echo Hash map which contains echo command description
   */
  private void echoDescription(HashMap<String, String> echo) {
    echo.put("echo", "It prints the string STR on the shell. This can be redirected to a file\n\t"
        + "It gives a suitable error if invalid number of argument is given. Or invalid file\n\t"
        + "name is provided");
  }
  
  /**
   * Initializes the man command description
   * @param man Hash map which contains cp command description
   */
  private void manualDescription(HashMap<String, String> man) {
    man.put("man", "Print instruction of the provided command CMD. If the CMD does not exist\n"
        + "than it prints an error specific to the CMD.");
  }
  
  /**
   * Initializes the pushd command description
   * @param pushd Hash map which contains pushd command description
   */
  private void pushDirectoryDescription(HashMap<String, String> pushd) {
    pushd.put("pushd", "Saves the current working directory by pushing DIR on top of the\n\t"
        + "directory stack and then changes the new current working directory to DIR. It saves\n\t"
        + "the old current working directory in the directory stack so that it can be returned\n\t"
        + "to at any time (via popd command). The size of the directory is dynamic and depends\n\t"
        + "on the pushd and popd commands. The push of DIR is consistent as per last in first\n\t"
        + "out (LIFO) behaviour of the stack. If input is not given or DIR does not exist than\n\t"
        + "it gives an appropriate error message.");
  }
  
  /**
   * Initializes the popd command description
   * @param popd Hash map which contains popd command description
   */
  private void popDirectoryDescription(HashMap<String, String> popd) {
    popd.put("popd", "Remove the top directory from the stack, and changes the current\n\t"
        + "directory to the new top directory. If there is no directory onto the stack, then\n\t"
        + "it produces an appropriate error message. The removal is consistent as per last in\n\t"
        + "first out (LIFO) behaviour of the stack. The size of the stack is dynamic and\n\t"
        + "depends on the pushd and popd commands. If input is given or the stack is empty\n\t"
        + "than it prints an appropriate error message.");
  }
  
  /**
   * Initializes the history command description
   * @param history Hash map which contains history command description
   */
  private void historyDescription(HashMap<String, String> history) {
    history.put("history", "If history is called without input then it will print out the\n\t"
        + "recent commands, one command per line and the format will contain two columns. The\n\t"
        + "first column is numbered such that the line with the highest number is the most\n\t"
        + "recent command, where the most recent command is history itself. The second column\n\t"
        + "contains the actual command. Actual command means it can contain any syntactical\n\t"
        + "errors typed by the user. However, if a number N is specified and N >= 0 then\n\t"
        + "history print the last N entries typed by the user. It gives a suitable error if a\n\t"
        + "negative number or inavlid number of arguments are given by the user.");
  }
  
  /**
   * Initializes the saveJShell command description
   * @param saveJShell Hash map which contains saveJShell command description
   */
  private void saveJShellDescription(HashMap<String, String> saveJShell) {
    saveJShell.put("saveJShell", "This command interact with the real file system of the user\n\t"
        + "computer. This command accepts file name from the user. If it exist then it\n\t"
        + "overwrites that file. If the file does not exist then it creates a new file and\n\t"
        + "write current JShell status to it. It prints a suitable error if invalid file name\n\t"
        + "or invalid number of arguments are passed.");
  }
  
  /**
   * Initializes the loadJShell command description
   * @param loadJShell Hash map which contains loadJShell command description
   */
  private void loadJShellDescription(HashMap<String, String> loadJShell) {
    loadJShell.put("loadJShell", "This command interact with the real file system of the user\n\t"
        + "computer. This command accepts file name from the user. If it exist then it reads\n\t"
        + "that file and sets the current JShell to its previous JShell that was saved using\n\t"
        + "saveJShell command. This command can be called once only and it will be the first\n\t"
        + "command user will ever call. After the execution of first command (any command)\n\t"
        + "this command is disabled. And afterward if the user tries to call this command then\n\t"
        + "it will print an error message. If the file does not exist then it prints a suitable\n\t"
        + "error message. If the user tries to read a file that was not saved by saveJShell\n\t"
        + "command then it will print a suitable error message.");
  }
  
  /**
   * Initializes the search command description
   * @param search Hash map which contains search command description
   */
  private void searchDescription(HashMap<String, String> search) {
    search.put("search", "It takes parameters: path(s) -type f|d -name \"itemName\"\n\t"
        + "It recursively searches the directory or directories provided in the parameters for\n\t"
        + "itemName, a file or directory. It prints the absolute paths of all items found of\n\t"
        + "the specified type and matching name. The successful output of this command can be\n\t"
        + "redirected to a file. If the number of parameters are wrong or no item was found\n\t"
        + "during the search, then a message describing the error is printed.");
  }
  
  /**
   * Initializes the tree command description
   * @param tree Hash map which contains tree command description
   */
  private void treeDescription(HashMap<String, String> tree) {
    tree.put("tree", "Display a graphical representation of entire file system as a tree. Every\n\t"
        + "directory and file inside that current directory being read gets indented This ouput\n\t"
        + "can be redirected to a file. Prints an error if invalid input is provided.");
  }


}
