# Assignment_02

For our project, which will be done in Java, the user will take on the role of an interviewer and the agent will take on the role of an interviewee for a software engineering job. The user will prompt the agent by asking questions related to their history, experience, etc. and the agent will respond with a relevant answer.

## Compiling and Running

After getting all of the files from the GitHub repository the CSVs location must be changed. This can either be absolute or relative and is found within ChatBot.java in the search function. After this JavaFX must be installed onto the machine. 

VSCode:
* Update VSCode to 1.49.3 or above.
* Install JavaFX support extension in VSCode.
* Download and install open JDK and configure Java runtime in VSCode (may need to add to system variables).
* Download JavaFX SDK, extract, and copy path of lib folder.
* Reference all jar files within JavaFX SDK to the Java project.
* Edit run configuration within launch.json by adding:
`"vmArgs": "--module-path {Your path}/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml", ` 
