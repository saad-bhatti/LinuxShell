package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Manual;

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

public class ManualTest {

  /**
   * Represents the Manual class
   */
  private Manual man;
  /**
   * To pass in the argument
   */
  private ArrayList<Object> args;
  /**
   * To hold the string representation of command
   */
  String cmdDescription;

  @Before
  public void setUp() {
    man = new Manual();
    args = new ArrayList<>();
    cmdDescription = "";
  }

  /**
   * Check if the exit command description is correct
   */
  @Test
  public void testExitCommandDescription() {
    cmdDescription ="Name:\n\t" + "exit" + "\nDescription: \n\t"
        + "Terminate the program. Prints an error if input is provided.";
    args.add("exit");
    assertEquals(cmdDescription, man.performOperation(null, args));
  }
  
  /**
   * Check if the tree command description is correct
   */
  @Test
  public void testTreeCommandDescription() {
    cmdDescription = "Name:\n\t" + "tree" + "\nDescription: \n\t"
        + "Display a graphical representation of entire file system as a tree. Every\n\t"
        + "directory and file inside that current directory being read gets indented This ouput\n\t"
        + "can be redirected to a file. Prints an error if invalid input is provided.";
    args.add("tree");
    assertEquals(cmdDescription, man.performOperation(null, args));
  }
  
  /**
   * Check if the command does not exist and its error
   */
  @Test
  public void testErrorCommandDoesNotExist() {
    cmdDescription = "!&!Command test does not exist!&!";
    args.add("test");
    assertEquals(cmdDescription, man.performOperation(null, args));
  }
  
}

