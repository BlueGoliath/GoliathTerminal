# Goliath-Terminal
Convenience Java class for executing CLI commands
---
## Features
* Easily read from command line standard and error outputs via Scanner objects
* Easily write to the command line via the PrintWriter object
* Set current working directory of the Terminal object instance
---
## Basic Usage breakdown

```
Terminal shell = new Terminal(); // create the terminal instance
shell.setCommand("cd"); // set the command to execute
shell.startCommand(); // start the command

while(shell.getCommandReader().hasNextLine()) // line print loop
	System.out.println(shell.getCommandReader().nextLine()); // print next line of output

shell.terminate(); // safely close and terminate the shell
```
---
## Compatibility
Excecuting programs not in Windows's `path` must have `cmd /c` in order to execute. 
