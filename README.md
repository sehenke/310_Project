# Assignment_02

For our project, which has been done in Java, the user takes on the role of an interviewer and the agent takes on the role of an interviewee for a software engineering job. The user prompts the agent by asking questions related to their history, experience, etc. and the agent responds with a relevant answer. The software we developed utilizes Named Entity Recognition, Coreference Resolution, SpellCheck and POS Tagging to identify various structures within user input phrases to respond appropriately. The software utilizes a minimalistic User Interface which allows users to easily enter interview-based phrases and receive responses that are cohesive to an interviewee.

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

The next step is to properly reference the Stanford CoreNLP jars:
* Download the CoreNLP zip file from: https://stanfordnlp.github.io/CoreNLP/ and extract
* Reference all of the jars to the class path of the Java project
