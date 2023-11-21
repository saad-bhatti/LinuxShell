package fileSystem;

import java.io.Serializable;
import java.util.*;

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
 * Represents the Stack object which stores the directory pushed by pushd command in JShell
 */
public class Stack implements Serializable {
  /**
   * Stack serialVersionUID value for Serial purpose
   */
  private static final long serialVersionUID = 1L;
  /**
   * The Deque object that stores the directory pushed by PushDirectory class
   */
  private Deque<String> st;

  /**
   * Constructs a new Stack object, initializes the Deque object st.
   */
  public Stack() {
    this.st = new ArrayDeque<String>();
  }

  /**
   * Getter method for Deque object st
   * 
   * @return st the Deque object stored directory pushed by PushDirectory class
   */
  public Deque<String> getStack() {
    return this.st;
  }

  /**
   * Getter method for the most recent Directory's full path stored in stack.
   * 
   * @return the most recent full path stores in the stack
   */
  public String getTopString() {
    return this.st.pop();
  }

  /**
   * Setter method that pushes the Directory's full path in the stack
   * 
   * @param s The full path of a directory
   */
  public void setTopString(String s) {
    this.st.push(s);
  }

}
