package fileSystem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

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

public class FileSystem implements Iterable<Directory>, Serializable {

  /**
   * FileSystem serialVersionUID value for Serial purpose
   */
  private static final long serialVersionUID = 1L;
  /**
   * Static Variable of FileSystem since there should only be 1 file system in the Jshell
   */
  private static FileSystem createdFileSystem = null;
  /**
   * Instance constant directory that stores the root of the directory
   */
  private Directory root;
  /**
   * Instance variable that stores the current Directory
   */
  private Directory currentDir;

  /**
   * The method constructs and initializes a new FileSystem type object
   */
  private FileSystem() {
    root = new Directory("/");
    currentDir = root;
  }

  /**
   * The factory method that constructs the FileSystem if there is no FileSystem created. Do nothing
   * if there is already a FileSystem existed.
   * 
   * @return FileSystem created if there is none, FileSystem existed if the static variable is
   *         assigned before
   */
  public static FileSystem createFileSystemInstance() {

    // If a new file system has not been created yet, create one
    if (createdFileSystem == null)
      createdFileSystem = new FileSystem();

    // Return the new or already created file system
    return createdFileSystem;
  }

  /**
   * Getter method for instance constant variable root stored in FileSystem.
   * 
   * @return root stored in the fileSystem
   */
  public Directory getRoot() {
    return this.root;
  }

  /**
   * Getter method for instance variable currentDir stored in FileSystem.
   * 
   * @return currentDir stored in FileSystem
   */
  public Directory getCurrentDir() {
    return this.currentDir;
  }

  /**
   * Setter method for instance variable currentDir stored in FileSystem
   * 
   * @param newCurrentDir the new current directory after changing
   */
  public void setCurrentDir(Directory newCurrentDir) {
    this.currentDir = newCurrentDir;
  }

  /**
   * Overrides the method iterator() from the Iterable Interface
   * 
   * @return Iterator<Directory>. An iterator object of type directory.
   */
  @Override
  public Iterator<Directory> iterator() {
    return new FileSystem.PreOrderIterator<>(root);
  }

  /**
   * This method creates and returns the iterator for the given directory
   * 
   * @param dir The directory of the file system on which the iterator begins
   * @return The Iterator of type directory which traverses the directories of the file system
   */
  public Iterator<Directory> iterator(Directory dir) {
    return new FileSystem.PreOrderIterator<>(dir);
  }

  /**
   * This class creates an iterator that traverses the file system in a pre-order traversal.
   * 
   * @param <T> The type of object the iterator will be traversing
   */
  private static class PreOrderIterator<T> implements Iterator<Directory> {
    // Instance variable: A stack of type directory
    Stack<Directory> stack;

    /**
     * Constructor for the PreOrderIterator Class. Initializes the stack and adds the directory
     * given to the stack.
     * 
     * @param dir The directory of the file system on which the iterator begins
     */
    public PreOrderIterator(Directory dir) {
      // Initialize the stack
      stack = new Stack<>();
      // Add the root to the stack
      if (dir != null)
        stack.push(dir);
    }

    /**
     * Override of the method hasNext(), from the Iterator interface
     * 
     * @return boolean. Returns true if the another directory exists in the stack. Returns false if
     *         the stack is empty
     */
    @Override
    public boolean hasNext() {
      // Return the boolean of whether the stack is empty or not
      return !(stack.isEmpty());
    }

    /**
     * Override of the method next(), from the Iterator interface
     * 
     * @return Directory. Returns the directory at the top of the stack. Otherwise, return null.
     */
    @Override
    public Directory next() {
      /*
       * For PreOrder Traversal, the order of printing is: Root, Left, Right. With a stack, we first
       * pop to get the current directory, and after we pop, we want to push all the children of the
       * current directory from the right to left, so that when we pop, we get the first child
       * directory first. After we add the children of the current directory, we return the current
       * directory. If the stack was empty before we popped (hasNext == false), then we have
       * traversed the whole file system and so we return null.
       */
      if (hasNext()) {
        Directory current = stack.pop();
        if (!current.getChildDirectory().isEmpty()) {
          for (int i = current.getChildDirectory().size() - 1; i >= 0; i--)
            stack.push(current.getChildDirectory().get(i));
        }
        return current;
      }
      return null;
    }
  }
}
