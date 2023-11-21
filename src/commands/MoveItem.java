package commands;

import java.util.ArrayList;
import fileSystem.Directory;
import fileSystem.File;
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
 * The MoveItem class moves a specific file or a directory and its children provided by a path to
 * another file or directory, with the exception of moving a directory to a file. This class has
 * five fields whose types are ChangeDirectory, PrintWorkingDirectory, Directory, and File.
 */
public class MoveItem extends CommandHandler {

  /**
   * Responsible for pointing to the directory that is being moved in the file system
   */
  private Directory dirToMove;
  /**
   * Responsible for pointing to the file that is being moved in the file system
   */
  private File fileToMove;
  /**
   * Responsible for pointing to the parent directory of the item to be moved
   */
  private Directory itemToMoveParent;
  /**
   * Responsible for changing the current directory to the specified path
   */
  private ChangeDirectory cd;
  /**
   * Responsible for getting the full path of a directory
   */
  private PrintWorkingDirectory pwd;

  /**
   * Constructor which initializes the instance variables
   */
  public MoveItem() {
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
    dirToMove = null;
    fileToMove = null;
    itemToMoveParent = null;
  }

  /**
   * Checks whether the item being moved is a directory or a file. If the given path is invalid or
   * the item was not found, then return a message describing the error. If the item was found, a
   * variable points to that item in the file system.
   * 
   * @param fs The FileSystem in which the item is being search for
   * @param pathToMove The String containing the path to the item that is being moved
   * @return A String that is an empty string if no error occurs or an error message
   */
  private String checkItemToMove(FileSystem fs, String pathToMove) {
    // Set the itemName
    String itemName = pathToMove;

    // Moving the root (will return error later)
    if (itemName.contentEquals("/"))
      return "!&!The root cannot be moved!&!";

    // Item to be move is given by a path
    else if (pathToMove.contains("/")) {
      // Get the name of the item to be moved
      itemName = super.splitAndGetLast(pathToMove);
      // Change the directory to the parent of the item to move
      super.commandArray.add(super.pathObject.toString());
      errorMessage = cd.performOperation(fs, commandArray);
      commandArray.clear();
      // If the given path was invalid, return
      if (!errorMessage.isEmpty())
        return errorMessage;
    }

    // Search if itemName is a directory
    for (Directory dir : fs.getCurrentDir().getChildDirectory()) {
      // If itemName is a directory, point a variable to it and to the directory that contains it
      if (dir.getName().contentEquals(itemName)) {
        dirToMove = dir;
        itemToMoveParent = fs.getCurrentDir();
        return "";
      }
    }

    // Search if itemName is a file
    for (File file : fs.getCurrentDir().getFilesInCurrentDirectory()) {
      // If itemName is a file, point a variable to that file and to the directory that contains it
      if (file.getName().contentEquals(itemName)) {
        fileToMove = file;
        itemToMoveParent = fs.getCurrentDir();
        return "";
      }
    }

    // If itemName does not exist, set error message to describe this error
    return "!&!The item '" + itemName + "' does not exist!&!";
  }

  /**
   * Changes the current directory of the file system to the directory containing the destination
   * item. If the given path is invalid, then returns a message describing the error. If the item
   * was found, the name of the item is returned.
   * 
   * @param fs The FileSystem whose current directory will be changed
   * @param pathToDestination The String containing the path to the destination item
   * @return A String that contains either the name of the item or an error message
   */
  private String changeDirToDestinationAndReturnItemName(FileSystem fs, String pathToDestination) {

    // The destination is within the current directory
    if (!pathToDestination.contains("/") && !pathToDestination.contains("."))
      return pathToDestination;

    // If the destination is root, change to root and return special symbol
    else if (pathToDestination.contentEquals("/")) {
      super.commandArray.add("/");
      cd.performOperation(fs, commandArray);
      return "&";
    }

    // The destination is given by a path and is not the root
    else {
      // Get the name of the destination
      String itemName = super.splitAndGetLast(pathToDestination);

      // Change the directory to the parent of the destination
      super.commandArray.add(super.pathObject.toString());
      errorMessage = cd.performOperation(fs, commandArray);

      // If the given path was invalid, return errorMessage
      if (!errorMessage.isEmpty())
        return errorMessage;

      // Otherwise, return the name of the item
      return itemName;
    }
  }

  ///////////////////////////////////////// Moving File \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Searches for a file with the name of the item within the current directory. If the file is
   * found, the contents of the existing file are overwritten and empty string is returned. If the
   * file is not found, a status of incomplete is returned.
   * 
   * @param fs The FileSystem in which the file is being search for
   * @param itemName The String which contains the name of item that is being searched for
   * @return A String that is either empty or contains an the incomplete status
   */
  private String moveFileToExistingFile(FileSystem fs, String itemName) {

    errorMessage = "";

    // Search if itemName is a file
    super.searchForFile(fs.getCurrentDir(), itemName);

    // If a file with the name of itemName exists, then itemName is a file
    if (super.fileObject != null) {
      // Overwrite the contents of the existing file with the contents of the file to be moved
      super.fileObject.setContent(new String(fileToMove.getContent()));

      // Return an empty string to indicate successful operation
      return "";
    }

    // If itemName is not an existing file, return an empty string
    return "incomplete";
  }

  /**
   * Adds the file to be moved to the root. If a file with the same name already exists, its
   * contents are overwritten. If such file does not exist, then the file to be moved is added here.
   * If an error occurs, then an error message is returned. If the file is not added to the root,
   * then return a status of incomplete. Otherwise, an empty string is returned.
   * 
   * @param fs The FileSystem whose root the file is being added to
   * @param itemName The String which contains the name of item that is being searched for
   * @return A String that is either empty, an error message, or contains an the incomplete status
   */
  private String moveFileToRoot(FileSystem fs, String itemName) {

    errorMessage = "";

    // Check if the current directory is the root
    if (fs.getCurrentDir().getName().contentEquals("/") && itemName.contentEquals("&")) {

      // Search if another directory already exists in root with the name of fileToMove
      super.searchForDirectory(fs.getRoot(), fileToMove.getName());

      // If another directory does exist with the same name, return an error message
      if (directoryObject != null)
        return "!&!A directory already exists with name of '" + fileToMove.getName() + "'!&!";

      // If no directory with the same name exists, search if a file with the same name exists
      super.searchForFile(fs.getRoot(), fileToMove.getName());

      // If a file does already exist, overwrite its contents with contents of file to be moved
      if (super.fileObject != null)
        super.fileObject.setContent(new String(fileToMove.getContent()));

      // If a file with the same name does not already exist, move the file into the directory
      else
        fs.getRoot().addFilesInCurrentDirectory(fileToMove);

      // Return an empty string to indicate successful operation
      return "";
    }

    // If the current directory is not root, return incomplete status
    return "incomplete";
  }

  /**
   * Adds the file to be moved to the destination. If a file with the same name already exists, its
   * contents are overwritten. If such file does not exist, then the file to be moved is added here.
   * If an error occurs, then an error message is returned. If the file is not added , then return a
   * status of incomplete. Otherwise, an empty string is returned.
   * 
   * @param fs The FileSystem whose destination the file is being added to
   * @param itemName The String which contains the name of item that is being searched for
   * @return A String that is either empty, an error message, or contains an the incomplete status
   */
  private String moveFileToDirectory(FileSystem fs, String itemName) {

    // See if we are adding the file to the root
    errorMessage = this.moveFileToRoot(fs, itemName);
    if (!errorMessage.contentEquals("incomplete"))
      return errorMessage;

    // Search if itemName is a directory
    errorMessage = "";
    super.searchForDirectory(fs.getCurrentDir(), itemName);

    // If a file with the name of itemName exists, then itemName is a directory
    if (super.directoryObject != null) {

      // Search if another directory already exists with the name of fileToMove
      Directory dirDestination = directoryObject;
      super.searchForDirectory(dirDestination, fileToMove.getName());

      // If another directory does exist with the same name, return an error message
      if (directoryObject != null)
        return "!&!A directory already exists with name of '" + fileToMove.getName() + "'!&!";

      // If no directory with the same name exists, search if a file with the same name exists
      super.searchForFile(dirDestination, fileToMove.getName());

      // If a file does already exist, overwrite its contents with contents of file to be moved
      if (super.fileObject != null)
        super.fileObject.setContent(new String(fileToMove.getContent()));

      // If a file with the same name does not already exist, move the file into the directory
      else
        dirDestination.addFilesInCurrentDirectory(fileToMove);

      // Return an empty string to indicate successful operation
      return "";
    }

    // If itemName was not a directory, return an message that it was incomplete
    return "incomplete";
  }

  /**
   * Adds the file to be moved. Moves the file to an existing directory or overwrites an existing
   * file. If itemName is not a existing file or directory, then it is assumed that the destination
   * is a new file. The file to be moved will be renamed and added to its destination. After adding,
   * the file will be removed from its original location. If an error occurs, then an error message
   * is returned. Otherwise, an empty string is returned.
   * 
   * @param fs The FileSystem whose file is being moved
   * @param itemName The String which contains the name of the destination item
   * @return A String that is either empty or an error message
   */
  private String moveFile(FileSystem fs, String itemName) {

    // See if moving file to an existing file
    errorMessage = this.moveFileToExistingFile(fs, itemName);

    // See if moving file to an existing directory
    if (errorMessage.contentEquals("incomplete"))
      errorMessage = this.moveFileToDirectory(fs, itemName);

    // If an occur has not occurred and the file has not been moved, then moving file to new file
    if (errorMessage.contentEquals("incomplete")) {
      // Rename the file to be moved to itemName
      fileToMove.setName(itemName);

      // Add the fileToMove to the current directory
      fs.getCurrentDir().addFilesInCurrentDirectory(fileToMove);

      // Set error message to empty string
      errorMessage = "";
    }

    // Delete the fileToMove from its original location if no error occurred
    if (errorMessage.isEmpty())
      itemToMoveParent.getFilesInCurrentDirectory().remove(fileToMove);

    // Return errorMessage
    return errorMessage;
  }

  /////////////////////////////////////// Moving Directory \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method checks if a parent directory is being moved to one of its children. If this is the
   * case, then error message is assigned a message describing the error.
   * 
   * @param fs The FileSystem which contains both directories.
   * @param moveDir The directory that is being moved
   * @param destination The directory that is the destination
   * @return A String that is either an empty string or contains an error message
   */
  private String checkMoveParentToChild(FileSystem fs, Directory moveDir, Directory destination) {

    // Check if the directory to be copied is a parent of the directory to copied to
    if (dirToMove != null) {

      // Get the full path of the directory to be moved
      fs.setCurrentDir(dirToMove);
      String fullPathofMove = pwd.performOperation(fs, commandArray);

      // Get the full path of the directory to be copied to (current directory)
      fs.setCurrentDir(destination);
      String fullPathOfDestination = pwd.performOperation(fs, commandArray);

      // If a parent is being moved to a child, return an error message
      if (fullPathOfDestination.startsWith(fullPathofMove))
        return "!&!Cannot move a parent directory to a child directory!&!";
    }
    // If no error, set the current directory back to its parent
    fs.setCurrentDir(destination.getParentDirectory());

    // Return an empty string otherwise
    return "";
  }

  /**
   * Checks whether a directory is being moved to a file. If the directory is being moved to the
   * file, then return an error message. Otherwise, return a status of incomplete.
   * 
   * @param fs The FileSystem whose directory is being moved
   * @param itemName The String containing the name of the destination item
   * @return A String that is either an error message or incomplete status
   */
  private String moveDirectoryToFile(FileSystem fs, String itemName) {

    errorMessage = "";

    // Search if itemName is a file [directory -> file]
    super.searchForFile(fs.getCurrentDir(), itemName);

    // If a file with the name of itemName exists, then itemName is a file
    if (super.fileObject != null)
      // Return an error message because cannot move a directory to a file
      return "!&!Cannot move a directory to a file!&!";

    // If itemName is not a file, return incomplete status
    return "incomplete";
  }

  /**
   * Adds the directory to be moved to the root. If a directory with the same name already exists,
   * it is overwritten. If such directory does not exist, then the file to be moved is added here.
   * If an error occurs, then an error message is returned. If the directory is not added to the
   * root, then return a status of incomplete. Otherwise, an empty string is returned.
   * 
   * @param fs The FileSystem whose root the directory is being added to
   * @param itemName The String which contains the name of item that is being searched for
   * @return A String that is either empty, an error message, or contains an the incomplete status
   */
  private String moveDirectoryToRoot(FileSystem fs, String itemName) {

    errorMessage = "";

    // Check if the current directory is the root
    if (fs.getCurrentDir().getName().contentEquals("/") & itemName.contentEquals("&")) {

      // Search if another file already exists in root with the name of directoryToMove
      super.searchForFile(fs.getRoot(), dirToMove.getName());

      // If another file does exist with the same name, return an error message
      if (fileObject != null)
        return "!&!A file already exists with name of '" + dirToMove.getName() + "'!&!";

      // If no file with the same name exists, search if a directory with the same name exists
      super.searchForDirectory(fs.getRoot(), dirToMove.getName());

      // If a directory does already exist, delete it and add the directory to be moved
      if (super.directoryObject != null) {
        fs.getRoot().getChildDirectory().remove(directoryObject);
        fs.getRoot().addChildDirectory(dirToMove);
      }

      // If a directory with same name does not already exist, move the directory into the directory
      else
        fs.getCurrentDir().addChildDirectory(dirToMove);

      // Return an empty string to indicate successful operation
      return "";
    }
    // If the current directory is not root, return incomplete status
    return "incomplete";
  }

  /**
   * Adds the directory to be moved to the destination. If a directory with the same name already
   * exists, it is overwritten. If such directory does not exist, then the directory to be moved is
   * added here. If an error occurs, then an error message is returned. If the directory is not
   * added, then return a status of incomplete. Otherwise, an empty string is returned.
   * 
   * @param fs The FileSystem whose destination the directory is being added to
   * @param itemName The String which contains the name of item that is being searched for
   * @return A String that is either empty, an error message, or contains an the incomplete status
   */
  private String moveDirectoryToDirectory(FileSystem fs, String itemName) {

    // See if we are adding the directory to the root
    errorMessage = this.moveDirectoryToRoot(fs, itemName);
    if (!errorMessage.contentEquals("incomplete"))
      return errorMessage;

    // Search of itemName is a directory [directory -> directory]
    errorMessage = "";
    super.searchForDirectory(fs.getCurrentDir(), itemName);

    // If a directory with the name of itemName exists, then itemName is a directory
    if (super.directoryObject != null) {

      // Search if another file already exists with the name of dirToMove
      final Directory dirDestination = directoryObject;
      super.searchForFile(dirDestination, dirToMove.getName());

      // If another file does exist with the same name, return an error message
      if (fileObject != null)
        return "!&!A file already exists with name of '" + dirToMove.getName() + "'!&!";

      // Now check that a parent directory is not being copied to its child
      errorMessage = this.checkMoveParentToChild(fs, dirToMove, dirDestination);
      if (!errorMessage.isEmpty())
        return errorMessage;
      
      // Search if a directory with same name as dirToMove already exists
      super.searchForDirectory(dirDestination, dirToMove.getName());

      // If a directory does already exist, delete it and add the directory to be moved
      if (super.directoryObject != null) {
        dirDestination.getChildDirectory().remove(directoryObject);
        dirDestination.addChildDirectory(dirToMove);
      }

      // If a directory with same name does not already exist, move the directory into the directory
      else
        dirDestination.addChildDirectory(dirToMove);

      // Return an empty string to indicate successful operation
      return "";
    }
    // If itemName was not an existing directory, return status of incomplete
    return "incomplete";
  }

  /**
   * Adds the directory to be moved. Moves the directory to an existing directory. If itemName is a
   * file, an error message is returned. If itemName is not an existing file or directory, then it
   * is assumed that the destination is a new directory. The directory to be moved will be renamed
   * and added to its destination. After adding, the directory will be removed from its original
   * location. If an error occurs, then an error message is returned. Otherwise, an empty string is
   * returned.
   * 
   * @param fs The FileSystem whose directory is being moved
   * @param itemName The String which contains the name of the destination item
   * @return A String that is either empty or an error message
   */
  private String moveDirectory(FileSystem fs, String itemName) {

    // See if moving the directory to a file
    errorMessage = this.moveDirectoryToFile(fs, itemName);

    // See if moving the directory to another directory
    if (errorMessage.contentEquals("incomplete"))
      errorMessage = this.moveDirectoryToDirectory(fs, itemName);

    // If an occur has not occurred and the directory has not been moved, then moving directory to
    // a new directory
    if (errorMessage.contentEquals("incomplete")) {
      // Rename the directory to be moved to itemName
      dirToMove.setName(itemName);

      // Add the fileToMove to the current directory
      fs.getCurrentDir().addChildDirectory(dirToMove);

      // Set error message to empty string
      errorMessage = "";
    }

    // If no error occurred, remove the directory to be moved from its original location
    if (errorMessage.isEmpty())
      itemToMoveParent.getChildDirectory().remove(dirToMove);

    // Return errorMessage
    return errorMessage;
  }

  ///////////////////////////////////////// Main Method \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * * {@inheritDoc} This method moves a specific file or a directory and its children provided by a
   * path to another file or directory that is provided by a path, with the exception of moving a
   * directory to a file. If an error occurs, then an error message describing the error is
   * returned. Otherwise, an empty string is returned.
   * 
   * @param fileSystem The FileSystem in which the move operation occurs
   * @param arguments The container that contains the specific arguments required. The path of the
   *        item being moved is at index 0 and the path of the destination item is at index 1
   * @return A String, that is either an empty string if no error occurred or an error message
   *         describing the error that occurred
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Get the arguments from the ArrayList and set errorMessage
    errorMessage = "";
    String pathToMove = (String) arguments.get(0);
    String pathDestination = (String) arguments.get(1);

    // Save the current working directory
    Directory cwdHolder = fileSystem.getCurrentDir();

    // Check the type of item being moved and then set the previous current directory
    errorMessage = this.checkItemToMove(fileSystem, pathToMove);
    fileSystem.setCurrentDir(cwdHolder);
    if (!errorMessage.isEmpty())
      return errorMessage;

    // Change the current directory to the parent of the destination item
    String itemName = this.changeDirToDestinationAndReturnItemName(fileSystem, pathDestination);
    if (!errorMessage.isEmpty())
      return errorMessage;

    // Moving a file
    if (fileToMove != null)
      errorMessage = this.moveFile(fileSystem, itemName);

    // Moving a directory
    else
      errorMessage = this.moveDirectory(fileSystem, itemName);

    // Set the previous working directory
    fileSystem.setCurrentDir(cwdHolder);

    // Return the output of these two methods
    return errorMessage;
  }
}
