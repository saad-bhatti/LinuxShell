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
 * The CopyItem class copies a specific file or a directory and its children provided by a path to
 * another file or directory, with the exception of copying a directory to a file. This class has
 * six fields whose types are ChangeDirectory, PrintWorkingDirectory, Directory, and File.
 */
public class CopyItem extends CommandHandler {

  /**
   * Responsible for pointing to the directory that is being copied or copied to, in the file system
   */
  private Directory dirToBeCopied, dirToCopyTo;
  /**
   * Responsible for pointing to the file that is being copied or copied to, in the file system
   */
  private File fileToBeCopied, fileToCopyTo;
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
  public CopyItem() {
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
    dirToBeCopied = null;
    dirToCopyTo = null;
    fileToBeCopied = null;
    fileToCopyTo = null;
  }

  ////////////////////////////////// Checking Item Types \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Checks whether the item being copied is a directory or a file. If the given path is invalid or
   * the item was not found, then assign to the error message a message describing the error. If the
   * item was found, point a variable to that item in the file system.
   * 
   * @param fs The FileSystem in which the item is being search for
   * @param pathToBeCopied The String containing the path to the item that is being copied
   */
  private void checkItemToBeCopied(FileSystem fs, String pathToBeCopied) {
    // Set the itemName
    String itemName = pathToBeCopied;

    // Copying the root (this is an error)
    if (itemName.contentEquals("/")) {
      errorMessage = "!&!Cannot copy the root directory!&!";
      return;
    }

    // Item to be copied is given by a path
    else if (pathToBeCopied.contains("/")) {
      // Get the name of the item to be copied
      itemName = super.splitAndGetLast(pathToBeCopied);
      // Change the directory to the parent of the item to be copied
      super.commandArray.add(super.pathObject.toString());
      errorMessage = cd.performOperation(fs, commandArray);
      commandArray.clear();
      // If the given path was invalid, return
      if (!errorMessage.isEmpty())
        return;
    }

    // Search if itemName is a directory
    super.searchForDirectory(fs.getCurrentDir(), itemName);

    // If itemName is a directory, point a variable to it
    if (directoryObject != null) {
      dirToBeCopied = directoryObject;
      return;
    }

    // Search if itemName is a file
    super.searchForFile(fs.getCurrentDir(), itemName);

    // If itemName is a file, point a variable to it
    if (fileObject != null)
      fileToBeCopied = fileObject;

    // If itemName does not exist, set error message to describe this error
    if (dirToBeCopied == null && fileToBeCopied == null)
      errorMessage = "!&!The item '" + itemName + "' does not exist!&!";
  }

  /**
   * Checks whether the item that is being copied to is a directory or a file. If the given path is
   * invalid or if an another error occurs, then assign to the error message a message describing
   * the error. If the item was found, point a variable to that item in the file system.
   * 
   * @param fs The FileSystem in which the item is being search for
   * @param pathToCopyTo The String containing the path to the item that is being copied to
   */
  private void checkItemToCopyTo(FileSystem fs, String pathToCopyTo) {
    // Set the itemName
    String itemName = pathToCopyTo;

    // Copying to the root
    if (itemName.contentEquals("/")) {
      dirToCopyTo = fs.getRoot();
      return;
    }

    // Item to be copied to is given by a path
    else if (pathToCopyTo.contains("/")) {
      // Get the name of the item to be copied
      itemName = super.splitAndGetLast(pathToCopyTo);
      // Change the directory to the parent of the item to be copied
      super.commandArray.add(super.pathObject.toString());
      errorMessage = cd.performOperation(fs, commandArray);
      // If the given path was invalid, return
      if (!errorMessage.isEmpty())
        return;
    }

    // Search if itemName is a directory
    super.searchForDirectory(fs.getCurrentDir(), itemName);
    // If itemName is a directory, point a variable to it
    if (directoryObject != null) {
      dirToCopyTo = directoryObject;
      // If the item to copy is also a directory, check if copying parent to child
      if (dirToBeCopied != null)
        this.checkCopyParentToChild(fs);
      return;
    }

    // Search if itemName is a file
    super.searchForFile(fs.getCurrentDir(), itemName);
    // If itemName is a file, point a variable to it
    if (fileObject != null) {
      fileToCopyTo = fileObject;
      // If the item to copy is a directory, set error message as an error
      if (dirToBeCopied != null)
        errorMessage = "!&!Cannot copy a directory to a file!&!";
      return;
    }

    // If item copied is a file and the file to copy to does not exist, create the file
    if (fileToBeCopied != null && fileToCopyTo == null) {
      this.createFileToBeCopiedTo(fs, itemName);
      return;
    }
    // If itemName does not exist, set error message to describe this error
    errorMessage = "!&!The item '" + itemName + "' does not exist!&!";
  }

  /**
   * This method checks if a parent directory is being copied to one of its children. If this is the
   * case, then error message is assigned a message describing the error.
   * 
   * @param fs The FileSystem which contains both directories.
   */
  private void checkCopyParentToChild(FileSystem fs) {

    // Check if the directory to be copied is a parent of the directory to copied to
    if (dirToBeCopied != null) {

      // Get the full path of the directory to be copied
      fs.setCurrentDir(dirToBeCopied);
      String fullPathToBeCopied = pwd.performOperation(fs, commandArray);

      // Get the full path of the directory to be copied to
      fs.setCurrentDir(dirToCopyTo);
      String fullPathToCopyTo = pwd.performOperation(fs, commandArray);

      // If a parent is being copied to a child, return an error message
      if (fullPathToCopyTo.startsWith(fullPathToBeCopied))
        errorMessage = "!&!Cannot copy a parent directory to a child directory!&!";
    }
  }

  ////////////////////////////////// Copying File to File \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method creates a file with the name itemName in the file system fs, when copying a file to
   * another file that does not exist yet.
   * 
   * @param fs The FileSystem in which the file will be created.
   * @param itemName The String that contains the name of the file to be created
   */
  private void createFileToBeCopiedTo(FileSystem fs, String itemName) {

    // If the item to be copied is a file and we did not find the item to copy to
    if (fileToBeCopied != null) {

      // Create the new file with the new name
      fileToCopyTo = new File(itemName);

      // Add the new file to the current directory's children
      fs.getCurrentDir().addFilesInCurrentDirectory(fileToCopyTo);
    }
  }

  ///////////////////////////////// Copying File To Directory \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method copies file to the the directory destination. If a file of the same name as 'file'
   * already exists in destination, then the contents of that file are overwritten. If a file of the
   * same name does not exist, then a file of the same name is created and the contents of 'file'
   * are copied to it.
   * 
   * @param file The File that is being copied
   * @param destination The directory in which the file is being copied to
   */
  private void copyFileToDirectory(File file, Directory destination) {

    // Search if a directory exists within destination with the same name as the copied file
    super.searchForDirectory(destination, file.getName());

    // If a directory does exist with the same name, set error message and return
    if (directoryObject != null) {
      errorMessage =
          "!&!A directory already exists with the name '" + file.getName() + "'!&!";
      return;
    }

    // Search if the file does not exist in the destination
    super.searchForFile(destination, file.getName());

    // If the file does not exist within the destination
    if (fileObject == null) {

      // Create a new file with the name and content of the file to be copied
      fileObject = new File(new String(file.getName()), new String(file.getContent()));

      // Add the new file to the destination
      destination.addFilesInCurrentDirectory(fileObject);
    }

    // If the file already exists within the destination
    else
      // Set the content of the existing file to the content of the copied file
      fileObject.setContent(new String(file.getContent()));
  }

  //////////////////////////// Copying Directory To Directory \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * This method recursively copies the children of actualDir to copyParent.
   * 
   * @param actualDir The Directory whose contents are being copied
   * @param copyParent The Directory in which the copied content is being added
   */
  private void copyChildrenDirectories(Directory actualDir, Directory copyParent) {

    // Copy the files of the actual directory to the copyParent
    for (File file : actualDir.getFilesInCurrentDirectory())
      this.copyFileToDirectory(file, copyParent);

    // Copy the children directories of the actual directory to the copyParent
    for (Directory dir : actualDir.getChildDirectory()) {
      Directory dirCopy = new Directory(new String(dir.getName()));
      // Add it to the destination directory
      copyParent.addChildDirectory(dirCopy);
    }

    // Now recursively call the the children
    for (int i = 0; i < actualDir.getChildDirectory().size(); i++)
      this.copyChildrenDirectories(actualDir.getChildDirectory().get(i),
          copyParent.getChildDirectory().get(i));
  }

  /**
   * This method recursively copies the content of one directory to another directory. If a
   * directory of the same name already exists within the destination, then the contents of that
   * directory are overwritten. If a directory with the same name does not exist in the destination,
   * then a new directory is made and the copied contents are added in that directory.
   */
  private void copyDirectoryToDirectory() {
    // Search if a file with the same name as the directory to be copied exists
    super.searchForFile(dirToCopyTo, dirToBeCopied.getName());

    // If such file already exists, set error message and return
    if (fileObject != null) {
      errorMessage = "!&!A file already exists with the name '" + dirToBeCopied.getName() + "'!&!";
      return;
    }

    // Search if directory with the same name exists as copied directory within the destination
    Directory copyParent = null;
    for (Directory dir : dirToCopyTo.getChildDirectory()) {

      // If there does exist such directory, point a variable to it and clear its existing children
      if (dir.getName().contentEquals(dirToBeCopied.getName())) {
        copyParent = dir;
        copyParent.getChildDirectory().clear();
        copyParent.getFilesInCurrentDirectory().clear();
        break;
      }
    }

    // If the destination does not contain a directory with same name, create the copy directory
    if (copyParent == null) {
      copyParent = new Directory(new String(dirToBeCopied.getName()));
      // Add it to the destination directory
      dirToCopyTo.addChildDirectory(copyParent);
    }

    // Call helper function which recursively adds the children of the directory being copied
    this.copyChildrenDirectories(dirToBeCopied, copyParent);
    return;
  }

  /**
   * {@inheritDoc} This method copies a specific file or a directory and its children provided by a
   * path to another file or directory that is provided by a path, with the exception of copying a
   * directory to a file. If an error occurs, then an error message describing the error is
   * returned. Otherwise, an empty string is returned.
   * 
   * @param fileSystem The FileSystem in which the copy operation occurs
   * @param arguments The container that contains the specific arguments required. The path of the
   *        item being copied is at index 0 and the path of the item being copied to is at index 1
   * @return A String, that is either an empty string if no error occurred or an error message
   *         describing the error that occurred
   */
  @Override
  public String performOperation(FileSystem fileSystem, ArrayList<Object> arguments) {
    // Get the arguments from the arraylist and set errorMessage
    errorMessage = "";
    String pathToBeCopied = (String) arguments.get(0);
    String pathToCopyTo = (String) arguments.get(1);

    // Save the current working directory
    Directory cwdHolder = fileSystem.getCurrentDir();

    // Check the type of item being copied and the type of item being copied
    this.checkItemToBeCopied(fileSystem, pathToBeCopied);
    fileSystem.setCurrentDir(cwdHolder);
    if (!errorMessage.isEmpty())
      return errorMessage;
    this.checkItemToCopyTo(fileSystem, pathToCopyTo);
    fileSystem.setCurrentDir(cwdHolder);
    if (!errorMessage.isEmpty())
      return errorMessage;

    // If copying file to file, overwrite contents of fileToCopyTo with contents of fileToBeCopied
    if (fileToBeCopied != null && fileToCopyTo != null)
      fileToCopyTo.setContent(fileToBeCopied.getContent());

    // If copying a file to a directory
    else if (fileToBeCopied != null && dirToCopyTo != null)
      this.copyFileToDirectory(fileToBeCopied, dirToCopyTo);

    // Copying a directory to a directory
    else
      this.copyDirectoryToDirectory();

    // Return errorMessage as method is complete
    return errorMessage;
  }
}
