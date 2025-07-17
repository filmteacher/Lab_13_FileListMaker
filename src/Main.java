//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    }
}


//For this lab, you will extend the list Maker program you created in Lab 11 to allow the user to load and save list files from disk.  This will give you practice with the Java file operations and creating a central system in main that handles file exceptions thrown from static support methods.  And, you will explore program “state” which we use here to ensure that no data is lost by closing a file. etc.
//Requirements:
//You will refactor our Lab 11 Listmaker lab. Instead of a single list held in memory, the new refactored program will be able to save and load lists from disk.
//•	In addition to these existing options:
//A – Add an item to the list
//D – Delete an item from the list
//I – Insert an item into the list
//P - Print the list to the screen Change this to V for view
//Q – Quit the program
//•	You will add:
//M – Move an item (THIS IS A NEW EDITING OPTION)
//O – Open a list file from disk
//S – Save the current list file to disk
//C – Clear removes all the elements from the current list
//V - CHANGE THE P Print OPTION to V for View
//
//For the M Move option: the user indicates by index which item they want to move and then indicates which location they want to move it to. (Just remove the element from the list and then re-insert it at the new location.)
//
//The big challenge here is to keep track of the program state:
//•	Use a Boolean variable like needsToBeSaved to keep track of list edits. (Traditionally this has been called a 'dirty' flag. The file becomes 'dirty' when it needs to be saved.)
//•	Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
//•	If the user loads a list, it does not need to be saved until it is changed by adding or deleting items. (The user could load a list only to view it and then would want to load another in its place without saving…)
//•	If the user begins to build a new list by adding items and does not load an existing list, the list is dirty. Prompt the user on exit to save the list or abandon it.
//•	Similarly, prompt the user to save an unsaved list before loading a new list from disk.. etc.
//•	Loaded lists are always saved with the same filename.
//•	All list files have the .txt extension
//
//Suggested Process
//1.	Create an IntelliJ Project: Lab_13_FileListMaker and add it to Gitub source control.
//2.	Implement and thoroughly test your refactored code. Just like we did for the original listMaker, you can develop each menu function and modify the existing ones testing as you go.
//3.	Static support methods that use the Java NIO methods must throw the usual File exceptions. (IOException, FileNotFoundException) You must implement the usual try catch block in main and call these static methods from within the try block there.
//Some analysis will show that you have some common static functions for handling the file operations. For instance, don’t write the same code in several menu cases to save the file. Instead, create the static method saveFile(…) and use it in the menu methods that need it.
//
//4.	Provide a series of screenshots that document and establish that your program will 'do' the following:
//
//a.	Will load a list from disk
//Paste your screenshots here:
//
//b.	 Will save a list loaded from disk that has been modified by deleting or adding items using the same filename, so it overwrites the file. Reload the changed file to clearly show that you were able to save the changes
//Paste your screenshots here:
//
//c.	Will allow the user to create a new list and save it to disk with a provided base name. List files should have the .txt extension.
//Paste your screenshot here:
//
//d.	Bullet proof the program so that a user can't lose data. If they ask to load a file but have an unsaved list in memory, prompt them to first save the list.
//Paste your screenshot here:
//
//e.	Bullet proof the program so that a user can't lose data. If they go to quit the program and have an unsaved list prompt them to save it.
//Paste your screenshot here:
//
//Criteria:
//Try Catch Block and Methods throw Exceptions: Code uses a central Try Catch Block and Methods throw Exceptions
//Code Handles the Dirty File correctly: The Dirty status is used so data is never lost inadvertently.
//Overall: Code is cleanly structured with an outer do while repeat loop and a CMD switch case structure. Menu options are implemented as specific methods.
//Example Runs are Complete: The submitted screen shots clearly show the creation of a list and saving it to disk and then reopening it.