package fileSystem;

import java.util.*;
import java.lang.StringBuilder;

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
 * Used to save the path to a directory or file.
 */

public class Path<E> {

  /**
   * Contains an array of directories\files
   */
  private ArrayList<E> path;


  /**
   * Constructor initially creates a new array list
   */
  public Path() {
    this.path = new ArrayList<>();
  }


  /**
   * Takes in a new directory\file and adds it to path at the end
   * 
   * @param directory contains the name of the new directory\file
   */
  public void addToPath(E directory) {
    this.path.add(directory);
  }

  /**
   * Add to current path at a specific index
   * 
   * @param object which can be file or directory
   * @param index is less than the size of the array and greater than or equal to zero
   */
  public void addToPath(int index, E object) {
    if (index >= 0 || index < this.getLength()) {
      this.path.add(index, object);
    }
  }

  /**
   * Remove the last directory from the current path
   * 
   */
  public void removeFromPath() {
    this.path.remove(this.getLength() - 1);
  }

  /**
   * Remove the last directory from the current path and return the removed object. If the path is
   * empty then it return the empty string.
   * 
   * @return String If the operation is not successful then return the empty string otherwise, it
   *         return the last item from the array which was got removed
   */
  public E getLastAndRemoveIt() {
    if (this.isPathEmpty()) {
      return null;
    }
    E object = this.path.get(this.getLength() - 1);
    this.path.remove(getLength() - 1);
    return object;
  }

  /**
   * A method to check if the current path is empty or populated with items
   * 
   * @return if path is empty returns true otherwise false
   */
  public boolean isPathEmpty() {
    if (this.path.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Clears the current path i.e no previous item are present inside the current array
   */
  public void clearPath() {
    this.path.clear();
  }


  /**
   * Get the size of the current array List
   * 
   * @return int size of the array list which contains the path
   */
  public int getLength() {
    return this.path.size();
  }


  /**
   * Get the current array which contains the path
   * 
   * @return a copy of array list of type String
   */
  public ArrayList<E> getCurrentPath() {
    return new ArrayList<E>(this.path);
  }

  /**
   * Get the item at specified index from the array list if not found than return the empty string
   * 
   * @param index int Contains the index of the item which is greater than or equal to zero and is
   *        less than the size of the array.
   * @return String if the operation is successful than returns the item at specified index of the
   *         array otherwise it will return empty string.
   */
  public E getItemAtIndex(int index) {
    if (index >= 0 && index < this.getLength()) {
      return this.path.get(index);
    }
    return null;
  }

  /**
   * Set the new path
   * 
   * @param givenPath contains new path which get assigned
   */
  public void setGivenPath(ArrayList<E> givenPath) {
    this.path = givenPath;
  }

  /**
   * {@inheritDoc} This method build a path by taking each element inside the array and attach it
   * with the slash and then converts it into specified string. Which gets returned.
   * 
   * @return String Converts array list into a specified string which gets returned. If array is
   *         empty than it returns an empty string.
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    String array[] = path.toArray(new String[path.size()]);
    
    for (String nameOfDirectory : array)
      if (!nameOfDirectory.contains("/"))
        stringBuilder.append(nameOfDirectory + "/");
      else
        stringBuilder.append(nameOfDirectory);
    return stringBuilder.toString();
  }
}
