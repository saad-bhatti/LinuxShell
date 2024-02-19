# LinuxShell

## Team

- [Saad Mohy-Uddin Bhatti](https://www.linkedin.com/in/saad-bhatti/)
- [Haowen Chang](https://www.linkedin.com/in/haowen-chang-7a8b13250/)
- [Zumran Nain](https://www.linkedin.com/in/zumrannain/)
- [Awais Aziz](https://www.linkedin.com/in/awais-aziz-4bb7a318a/)

## About LinuxShell

LinuxShell is a project that aims to replicate the functionality of a Linux shell. The project is divided into two parts. The first part is a simple shell that can execute basic commands. The second part is an extension of the first part, adding more complex commands and features.

## Technical Features

The project is developed using Java and follows the Agile methodology. The project is divided into sprints, with each sprint having its own backlog. The project is developed using the Test-Driven Development (TDD) approach, with JUnit tests being written before the actual code. The project also follows the SOLID principles.

Software design patterns are used throughout the project:
- Polymorphism
- Encapsulation
- Inheritance
- Interfaces
- Singleton pattern
- Composite pattern

## Implemented Commands

Conventions:
_[input] - implies that the input is optional._
_input... - implies a possibility of a list of inputs._

- `cat FILE...`: Display the contents of each file concatenated in the shell.
- `cd DIR`: Change directory to DIR, which may be relative to the current directory or may be a full path.
- `cp OLDPATH NEWPATH`:
  - Copies the item OLDPATH to NEWPATH, either of which may be relative to the current directory or may be a full path.
  - If OLDPATH is a directory, recursively copy its contents.
- `curl URL`: Retrieve the content of the URL and display it in the shell.
- `echo STRING [> OUTFILE]`: If OUTFILE is not provided, print STRING on the shell. Otherwise, put STRING into the file OUTFILE. This creates a new file if OUTFILE does not exist and erases the old contents if OUTFILE already exists.
- `echo STRING >> OUTFILE`: Append STRING to OUTFILE.
- `exit`: Quit the program.
- `history [number]`: Print out recent commands, with the most recent command having the highest number. If a number is specified, the output is truncated to that number of recent commands.
- `loadJShell FILENAME`: Load a previously saved state of the shell from a file named FILENAME.
- `ls [-R] [PATH…]`:
  - If no paths are given, print the contents of the current directory
  - Otherwise, print the contents of each directory given as a path.
  - If the -R option is given, recursively list the contents of all subdirectories.
- `man CMD...`: Print documentation for each command to the shell.
- `mkdir DIR...`: Creates directories, each of which may be relative to the current directory or may be a full path.
- `mv OLDPATH NEWPATH`:
  - Moves the item OLDPATH to NEWPATH, either of which may be relative to the current directory or may be a full path.
  - If NEWPATH is a directory, move the item into the directory.
- `popd`: Remove the top entry from the directory stack and change the directory to it.
- `pushd DIR`: Saves the current working directory by pushing onto directory stack and then changes the new current working directory to DIR.
- `pwd`: Print the current working directory (including the whole path).
- `rm DIR`: Removes the DIR from the file system and recursively removes all its children.
- `saveJShell FILENAME`: Save the current state of the shell to a file named FILENAME.
- `search PATH... -type [f|d] -name EXPRESSION`: Search for files or directories with the name EXPRESSION found in any of the paths in PATH.
- `tree`: Print the entire file system as a tree.

## File structure

**`crcCards/`** - Contains the CRC cards for the project.
**`dailyScrumMeetings/`** - Contains the daily scrum meeting reports.
**`instructions/`** - Contains the instructions for the project.
**`productBacklog/`** - Contains the product backlogs for both parts of the project.
**`sprints/`** - Contains the backlogs for each sprint of the development process.
**`src/`** - Contains the source code for the project.

- **`commands/`** - Contains the classes representing unix shell commands.
- **`driver/`** - Contains the driver (shell) for the project.
- **`fileSystem/`** - Contains the classes related a file system within a linux shell.
- **`shell/`** - Contains the classes that handle the shell's input and output.
- **`test/`** - Contains the JUnit tests for the project.
