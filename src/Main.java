import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author matt.bennett@uc.edu
 */

//For this lab, you will extend the list Maker program you created in Lab 11 to allow the user to load and save list files from disk.
// This will give you practice with the Java file operations and creating a central system in main that handles file exceptions thrown from static support methods.
// And, you will explore program “state” which we use here to ensure that no data is lost by closing a file. etc.


class Reference {
    //The big challenge here is to keep track of the program state:
    //Use a Boolean variable like needsToBeSaved to keep track of list edits.
    //I had to look up the reference class and how to use it online to make this global.
    public static boolean needsToBeSaved = false;
}


public class Main {
    public static void main(String[] args) {

        //Initialize variables
        boolean quit = false;
        ArrayList<String> yourList = new ArrayList();

        //Menu-driven loop until user inputs Quit command
        do {

            //Display menu and get input
            //Convert to uppercase for easy check
            String input = menu();
            input = input.toUpperCase();

            //Initially stub out the functions so you have a program that you can run almost immediately as you develop it as per the Agile Software Dev approach.
            //You will develop each of the menu options as a separate java method.
            switch (input.toUpperCase()) {
                case "A":
                    addItem(yourList);
                    break;
                case "D":
                    deleteItem(yourList);
                    break;
                case "I":
                    insertItem(yourList);
                    break;
                case "V":
                    viewList(yourList);
                    break;
                //M – Move an item (THIS IS A NEW EDITING OPTION)
                case "M":
                    moveItem(yourList);
                    break;
                //O – Open a list file from disk
                case "O":
                    //openList(yourList);
                    break;
                //S – Save the current list file to disk
                case "S":
                    //saveList(yourList);
                    break;
                //C – Clear removes all the elements from the current list
                case "C":
                    clearList(yourList);
                    break;
                case "Q":
                    quit = quitList(yourList);
            }
        } while(!quit);
    }

    //private static String menu()
    //You will want to display the current list along with the menu of options so the user can see what they are doing.
    //gets input from the user
    private static String menu() {
        Scanner in = new Scanner(System.in);
        String input = "";
        System.out.println("\nA – Add an item to the list");
        System.out.println("D – Delete an item from the list");
        System.out.println("I – Insert an item into the list");
        System.out.println("V – View the list");
        System.out.println("M – Move an item");
        System.out.println("O – Open a list file from disk");
        System.out.println("S – Save the current list file to disk");
        System.out.println("C – Clear all items from the current list");
        System.out.println("Q – Quit the program");
        input = SafeInput.getRegExString(in, "What would you like to do [AaDdIiVvMmOoSsCcQq]? ", "[AaDdIiVvMmOoSsCcQq]");
        return input;
    }

    //You need to display a numbered version of the list to allow users to pick list elements for deletion.
    // Here the user looks at the display and then indicates the item to delete by the number.
    private static void numList(ArrayList<String> theList) {
        System.out.println("");
        for(int x = 0; x < theList.size(); ++x) {
            System.out.println(x + 1 + ": " + (String)theList.get(x));
        }
    }

    //private static boolean quitlist()
    //confirm that they want to quit
    //print the final list
    //return true if they want to quit to set the quit variable
    private static boolean quitList(ArrayList<String> theList) {
        boolean confirm = SafeInput.getYNConfirm(new Scanner(System.in), "Are you sure you want to quit? ");
        if (!confirm) {
            return false;
        } else {
            System.out.println("\nThank you. This is your final list:");
            System.out.println(theList);
            return true;
        }
    }

    //private static void viewlist()
    //view the list
    private static void viewList(ArrayList<String> theList) {
        System.out.println("\nThis is your list so far:");
        System.out.println(theList);
    }

    //private static void addItem()
    //user inputs the item
    //add to the list
    //print the list
    private static void addItem(ArrayList<String> theList) {
        Scanner in = new Scanner(System.in);
        String item = "";
        item = SafeInput.getNonZeroLenString(in, "Type in the item you want to add");
        theList.add(item);
        System.out.println("\nThis is your list so far:");
        System.out.println(theList);
        //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
        Reference.needsToBeSaved = true;
    }

    //private static void deleteItem()
    //print the list
    //if the list is empty there is nothing to delete
    //Use your getRangedInt method to get the item number to delete, etc.
    //confirm they want to delete the item
    //delete item from the list
    //print the list
    private static void deleteItem(ArrayList<String> theList) {
        Scanner in = new Scanner(System.in);
        int item = 0;
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThere is nothing to delete!");
        } else {
            item = SafeInput.getRangedInt(in, "Enter the number of the item you want to delete", 1, theList.size());
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to delete item " + item + ": " + (String)theList.get(item - 1) + "? ");
            if (confirm) {
                theList.remove(item - 1);
                System.out.println("\nThis is your list after deleting the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                Reference.needsToBeSaved = true;
            } else {
                System.out.println("\nItem was not deleted!");
            }
        }
    }

    //private static void insertItem()
    //print the list
    //if the list is empty, let them know they need to add items first
    //Use your getRangedInt method to get the item number to delete, etc.
    //confirm they want to insert the item at the location
    //insert item into the list at the location
    //print the list
    private static void insertItem(ArrayList<String> theList) {
        Scanner in = new Scanner(System.in);
        int location = 0;
        String item = "";
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThe list is empty. You need to add items before you can insert them!");
        } else {
            location = SafeInput.getRangedInt(in, "Enter the number of the list item you want to insert a new item before", 1, theList.size());
            item = SafeInput.getNonZeroLenString(in, "Type in the item you want to insert");
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to insert " + item + " before " + location + ":" + (String)theList.get(location - 1) + "? ");
            if (confirm) {
                theList.add(location - 1, item);
                System.out.println("\nThis is your list after inserting the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                Reference.needsToBeSaved = true;
            } else {
                System.out.println("\nItem was not inserted!");
            }
        }
    }

    //private static void clearList()
    //remove all items from the list
    //if the list is empty, let them know they need to add items first
    //confirm they want to clear the list
    //notify them that the list is now empty
    private static void clearList(ArrayList<String> theList) {
        Scanner in = new Scanner(System.in);
        int item = 0;
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThere is nothing in the list to clear!");
        } else {
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to clear all items from the list? ");
            if (confirm) {
                theList.clear();
                System.out.println("\nYour list is now empty.");
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                Reference.needsToBeSaved = true;
            } else {
                System.out.println("\nThe list was not cleared!");
            }
        }
    }

    //private static void moveItem()
    //print the list
    //if the list is empty, let them know they need to add items first
    //Use your getRangedInt method to get the item number to move
    //save the item in a String variable
    //remove the item from the list
    //print the list
    //Use your getRangedInt method to get the item number to move to
    //confirm they want to move the item to the location
    //insert item into the list at the location
    //print the list
    private static void moveItem(ArrayList<String> theList) {
        Scanner in = new Scanner(System.in);
        int startLocation = 0;
        int endLocation = 0;
        String item = "";
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThe list is empty. You need to add items before you can move them!");
        } else {
            startLocation = SafeInput.getRangedInt(in, "Enter the number of the list item you want to move", 1, theList.size());
            item = theList.get(startLocation - 1);
            theList.remove(startLocation - 1);
            numList(theList);
            endLocation = SafeInput.getRangedInt(in, "Enter the number of the list item you want to move " + item + " before", 1, theList.size());
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to move " + item + " before " + endLocation + ":" + (String)theList.get(endLocation - 1) + "? ");
            if (confirm) {
                theList.add(endLocation - 1, item);
                System.out.println("\nThis is your list after moving the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                Reference.needsToBeSaved = true;
            } else {
                theList.add(startLocation - 1, item);
                System.out.println("\nItem was not moved!");
            }
        }
    }

    private static void openList(ArrayList<String> theList) {
        JFileChooser chooser = new JFileChooser();
        Scanner inFile;
        String line;
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        // set the chooser to the project src directory
        chooser.setCurrentDirectory(target.toFile());

        try  // Code that might trigger the exception goes here
        {

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                target = chooser.getSelectedFile().toPath();  // this is a File object not a String filename
                theList.clear();
                inFile = new Scanner(target);

                while(inFile.hasNextLine())
                {
                    line = inFile.nextLine();
                    System.out.println(line);
                }

                inFile.close();
            }
            else   // User did not pick a file, closed the chooser
            {
                System.out.println("Sorry, you must select a file! Termininating!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found Error");
            e.printStackTrace();
        }
        catch (IOException e) // code to handle this exception
        {
            System.out.println("IOException Error");
            e.printStackTrace();
        }
    }

}



//•	If the user loads a list, it does not need to be saved until it is changed by adding or deleting items.
// (The user could load a list only to view it and then would want to load another in its place without saving…)
//•	If the user begins to build a new list by adding items and does not load an existing list, the list is dirty.
// Prompt the user on exit to save the list or abandon it.
//•	Similarly, prompt the user to save an unsaved list before loading a new list from disk.. etc.
//•	Loaded lists are always saved with the same filename.
//•	All list files have the .txt extension
//
//Static support methods that use the Java NIO methods must throw the usual File exceptions. (IOException, FileNotFoundException)
//You must implement the usual try catch block in main and call these static methods from within the try block there.
//Some analysis will show that you have some common static functions for handling the file operations.
//For instance, don’t write the same code in several menu cases to save the file.
//Instead, create the static method saveFile(…) and use it in the menu methods that need it.
//
//4.	Provide a series of screenshots that document and establish that your program will 'do' the following:
//
//a.	Will load a list from disk
//Paste your screenshots here:
//
//b.	 Will save a list loaded from disk that has been modified by deleting or adding items using the same filename, so it overwrites the file.
// Reload the changed file to clearly show that you were able to save the changes
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