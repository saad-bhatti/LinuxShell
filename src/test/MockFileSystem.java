package test;

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

//**********************************************************
//Assignment2:
//Student1:
//UTORID user_name: bhatti97
//UT Student #: 1006448248
//Author: Saad Mohy-Uddin Bhatti
//
//Student2:
//UTORID user_name: azizawai
//UT Student #: 1006103681
//Author: Awais Aziz
//
//Student3:
//UTORID user_name: changh31
//UT Student #: 1006205394
//Author: Haowen Chang
//
//Student4:
//UTORID user_name: nainzumr
//UT Student #: 1005954139
//Author: Zumran Nain
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagiarism section in the course info
//sheet of CSC B07 and understand the consequences.
//*********************************************************

/**
 * The MockFileSystem class creates a mock file system without the use of any commands. This class
 * has two methods, one that creates the mock file system, and another that sets the current
 * directory.
 */
public class MockFileSystem {

  /**
   * This method sets up the MockFileSystem. It creates three directories whose names are dirRepeat,
   * dir1_1, and dir2_2. It also creates two files whose names are fileRepeat and file2_1.
   */
  public static void setUpMockFileSystem(FileSystem fs) {

    // Create the directories to be added to the mock file system
    Directory dirRepeat1 = new Directory("dirRepeat");
    Directory dirRepeat2 = new Directory("dirRepeat");
    Directory dir1_1 = new Directory("dir1_1");
    Directory dir2_1 = new Directory("dir2_1");

    // Create the files to be added to the mock file system
    File fileRepeat1 = new File("fileRepeat", "This is the content of fileRepeat in /");
    File fileRepeat2 = new File("fileRepeat", "This is the content of fileRepeat in dir2_1");
    File file2_1 = new File("file2_1", "This is the content of file2_1");

    // Set up the file system now
    fs.getRoot().addChildDirectory(dir1_1);
    fs.getRoot().addChildDirectory(dirRepeat1);
    fs.getRoot().addFilesInCurrentDirectory(fileRepeat1);
    dirRepeat1.addFilesInCurrentDirectory(file2_1);
    dir1_1.addChildDirectory(dir2_1);
    dir2_1.addChildDirectory(dirRepeat2);
    dir2_1.addFilesInCurrentDirectory(fileRepeat2);
    dir1_1.setParentDirectory(fs.getRoot());
    dirRepeat1.setParentDirectory(fs.getRoot());
    dir2_1.setParentDirectory(dir1_1);
    dirRepeat2.setParentDirectory(dir2_1);
  }

  /**
   * Manually set the current directory of the mock file system using an absolute path.
   * 
   * @param fs FileSystem. The mock file system whose current directory will change
   * @param pathToNewDir String containing the absolute path to the new current directory
   */
  public static void setCurrentDirectory(FileSystem fs, String pathToNewDir) {

    // Change to the root
    fs.setCurrentDir(fs.getRoot());

    // If changing to the root
    if (pathToNewDir.contentEquals("/"))
      return;

    // Split the path given using a backslash
    String[] splitPath = pathToNewDir.split("/");
    boolean changed;

    // Traverse over the splitPath array
    for (String dirName : splitPath) {
      if (!dirName.contentEquals("")) {
        // Set changed to false
        changed = false;
        // Search for the directory name within the current directory's children. If a directory
        // with a name matching dirName is found, change to that directory
        for (Directory dir : fs.getCurrentDir().getChildDirectory()) {
          if (dir.getName().contentEquals(dirName)) {
            changed = true;
            fs.setCurrentDir(dir);
            break;
          }
        }
        // The current directory did not contain dirName
        if (!changed)
          return;
      }
    }
  }

  /**
   * Manually search the current directory for a directory of the mock file system.
   * 
   * @param fs FileSystem. The mock file system whose current directory we are searching
   * @param directoryName String containing the name of the directory we are searching for
   * @return Boolean true if a directory with the name of directoryName is found in the current
   *         directory. Otherwise false
   */
  public static boolean searchForDirectoryInCurrent(FileSystem fs, String directoryName) {
    // Return variable
    boolean found = false;

    // Search for the directory in the directory
    for (Directory dir : fs.getCurrentDir().getChildDirectory()) {
      // If the directory is found
      if (dir.getName().contentEquals(directoryName))
        // Set found to true
        found = true;
    }
    return found;
  }

  /**
   * Manually search the current directory for a file of the mock file system.
   * 
   * @param fs FileSystem. The mock file system whose current directory we are searching
   * @param fileName String containing the name of the file we are searching for
   * @return Boolean true if a directory with the name of directoryName is found in the current
   *         directory. Otherwise false
   */
  public static boolean searchForFileInCurrent(FileSystem fs, String fileName) {
    // Return variable
    boolean found = false;

    // Search for the file in the current directory
    for (File file : fs.getCurrentDir().getFilesInCurrentDirectory()) {
      // If the file is found
      if (file.getName().contentEquals(fileName))
        // Set found to true
        found = true;
    }
    return found;
  }

  /**
   * Manually retrieve and return the contents of a file in the current directory of the mock file
   * system. If the file does not exist, then an error message is returned.
   * 
   * @param fs FileSystem. The mock file system whose current directory we are searching
   * @param fileName String containing the name of the file whose content is being retrieved
   * @return String true The contents of the file if found. Otherwise an error message stating the
   *         file does not exist
   */
  public static String getFileContents(FileSystem fs, String fileName) {
    // Return variable
    String contents = null;

    // Search for the file in the current directory
    for (File file : fs.getCurrentDir().getFilesInCurrentDirectory()) {
      // If the file is found
      if (file.getName().contentEquals(fileName))
        // Assign the contents of the file to the variable
        contents = file.getContent();
    }
    // If the file was not found, return the error message
    if (contents == null)
      contents = "The file does not exist";
    return contents;
  }
}
