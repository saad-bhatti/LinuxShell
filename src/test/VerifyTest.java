package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import shell.Verify;
import shell.Output;
import shell.Parse;

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
 * This class is a JUnit Test which tests the public methods of the Verify class
 */
public class VerifyTest {

  /**
   * Represents the Verify object in this JUnit Test class, enables the Test class to use Verify
   * functions
   */
  private Verify verify;
  /**
   * Represents the Output object in this JUnit Test class, enables the Test class to use Output
   * functions
   */
  private Output output;
  /**
   * Represents the Parse object in this JUnit Test class, enables the Test class to use Parse
   * functions
   */
  private Parse parse;
  /**
   * The command user input
   */
  private String command;
  /**
   * The parameters of the command user input
   */
  private ArrayList<String> parameters;
  /**
   * The user input to the JShell
   */
  private String userInput;

  /**
   * This method initializes the instance variables.
   */
  @Before
  public void setUp() {
    verify = new Verify();
    output = new Output();
    parse = new Parse();
    command = new String();
    parameters = new ArrayList<String>();
    userInput = new String();
  }

  /**
   * This method tests the Verify could verify that there are parameters for commands take no
   * parameter and reports a proper error message
   */
  @Test
  public void testNoParameterVerify() {
    output.setRedirectionOccuring(false);
    command = "exit";
    parameters.add("randomParameter");
    userInput = "exit randomParameter";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The number of parameters is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify that there are multiple parameters for commands take
   * one parameter and reports a proper error message
   */
  @Test
  public void testOneParameterVerify() {
    output.setRedirectionOccuring(false);
    command = "cd";
    parameters.add("randomParameter");
    parameters.add("randomParameter2");
    userInput = "cd randomParameter randomParameter2";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The number of parameters is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify that whether the path parameter contains multiple
   * backslashes and reports a proper error message
   */
  @Test
  public void testTwoConsecutiveSlashPath() {
    output.setRedirectionOccuring(false);
    command = "cd";
    parameters.add("//a");
    userInput = "cd //a";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The path cannont contain multiple backslashes together!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when MakeDirectory class takes no input and reports a
   * proper error message
   */
  @Test
  public void testMakeDirectoryVerifyWithNoInput() {
    output.setRedirectionOccuring(false);
    command = "mkdir";
    userInput = "mkdir";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The number of parameters is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when MakeDirectory class takes root directory as
   * input and reports a proper error message
   */
  @Test
  public void testMakeDirectoryVerifySecondRoot() {
    output.setRedirectionOccuring(false);
    command = "mkdir";
    parameters.add("/");
    userInput = "mkdir /";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!Another directory with the name of '/' cannot be made!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify that there is no parameter for commands take one and
   * more than one parameters and reports a proper error message
   */
  @Test
  public void testNonEmptyParameterVerify() {
    output.setRedirectionOccuring(false);
    command = "cat";
    userInput = "cat";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The number of parameters is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when echo commands takes strings outside the
   * quotation marks as input and reports a proper error message
   */
  @Test
  public void testEchoVerifyInputOutQuotation() {
    output.setRedirectionOccuring(false);
    command = "echo";
    parameters.add("w\"ord\"");
    userInput = "echo w\"ord\"";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!Text to be stored is not allowed outside of the two quotation marks!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when echo commands takes wrong number of arrows as
   * input and reports a proper error message
   */
  @Test
  public void testEchoVerifyWrongNumArrow() {
    output.setRedirectionOccuring(false);
    command = "echo";
    parameters.add("\"word\"");
    parameters.add(">>>");
    parameters.add("file.txt");
    userInput = "echo \"word\" >>> file.txt";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!An incorrect number of '>' were given!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when echo commands takes no path after arrow as input
   * and reports a proper error message
   */
  @Test
  public void testEchoVerifyNoPathProvideAfterArrow() {
    output.setRedirectionOccuring(false);
    command = "echo";
    parameters.add("\"word\"");
    parameters.add(">>");
    userInput = "echo \"word\" >>";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!A path must be provided!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when history commands takes one negative integer as
   * input and reports a proper error message
   */
  @Test
  public void testVerifyHistoryNegativeNum() {
    output.setRedirectionOccuring(false);
    command = "history";
    parameters.add("-10");
    userInput = "history -10";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The integer cannot be negative!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when search commands missing -name in the input and
   * reports a proper error message
   */
  @Test
  public void testVerifySearchWrongNumParameters() {
    output.setRedirectionOccuring(false);
    command = "search";
    parameters.add("/dir1_1");
    parameters.add("-type");
    parameters.add("d");
    parameters.add("\"xyz\"");
    userInput = "search /dir1_1 -type d \"xyz\"";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The number of parameters is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when search commands takes empty string in the input
   * as the filename searching for and reports a proper error message
   */
  @Test
  public void testVerifySearchNoName() {
    output.setRedirectionOccuring(false);
    command = "search";
    parameters.add("/dir1_1");
    parameters.add("-type");
    parameters.add("d");
    parameters.add("-name");
    parameters.add("");
    userInput = "search /dir1_1 -type d -name \"\"";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The given name is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when curl commands takes one invalid URL as input and
   * reports a proper error message
   */
  @Test
  public void testVerifyCurlInvalidURL() {
    output.setRedirectionOccuring(false);
    command = "curl";
    parameters.add("invalidURL");
    userInput = "curl invalidURL";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The URL given is invalid!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when loadJShell commands takes invalid filename as
   * input and reports a proper error message
   */
  @Test
  public void testVerifySaveJShellInvalidFilename() {
    output.setRedirectionOccuring(false);
    command = "saveJShell";
    parameters.add("in.vali?dSymbo;l");
    userInput = "saveJShell in.vali?dSymb@o!l";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!The name of the file contains an invalid symbol!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when loadJShell commands takes filename does not
   * exist in current directory as input and reports a proper error message
   */
  @Test
  public void testVerifyLoadJShellWrongFilename() {
    output.setRedirectionOccuring(false);
    command = "loadJShell";
    parameters.add("nonExistingFilename");
    userInput = "saveJShell nonExistingFilename";
    verify.setVariables(command, parameters, userInput);
    String actual = verify.verifyInput(verify, output);
    String expected = "!&!Cannot read the file because it does not exit!&!";
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when redirection enables and invalid filename is
   * provided to the redirection, and then turns off the redirection
   */
  @Test
  public void testVerifyRedirectionInvalidFilename() {
    output.resetVariables();
    output.setRedirectionOccuring(true);
    userInput = "history > out?f:i*l\\e";
    parse.parseInput(userInput, output);
    verify.setVariables(parse.getCommand(), parse.getParameters(), userInput);
    verify.verifyInput(verify, output);
    boolean actual = output.isRedirectionOccuring();
    boolean expected = false;
    assertEquals(expected, actual);
  }

  /**
   * This method tests the Verify could verify when redirection enables and multiple filenames are
   * provided to the redirection, and then continue the redirection
   */
  @Test
  public void testVerifyRedirectionMultipleFilename() {
    output.resetVariables();
    output.setRedirectionOccuring(true);
    userInput = "history > outfile1 outfile2";
    parse.parseInput(userInput, output);
    verify.setVariables(parse.getCommand(), parse.getParameters(), userInput);
    verify.verifyInput(verify, output);
    boolean actual = output.isRedirectionOccuring();
    boolean expected = true;
    assertEquals(expected, actual);
  }
}
