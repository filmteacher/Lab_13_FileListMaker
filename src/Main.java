import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

//For this lab, you will extend the list Maker program you created in Lab 11 to allow the user to load and save list files from disk.
// This will give you practice with the Java file operations and creating a central system in main that handles file exceptions thrown from static support methods.
// And, you will explore program “state” which we use here to ensure that no data is lost by closing a file. etc.

//The big challenge here is to keep track of the program state:
//•	Use a Boolean variable like needsToBeSaved to keep track of list edits. (Traditionally this has been called a 'dirty' flag. The file becomes 'dirty' when it needs to be saved.)
//•	Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
//•	If the user loads a list, it does not need to be saved until it is changed by adding or deleting items. (The user could load a list only to view it and then would want to load another in its place without saving…)
//•	If the user begins to build a new list by adding items and does not load an existing list, the list is dirty. Prompt the user on exit to save the list or abandon it.
//•	Similarly, prompt the user to save an unsaved list before loading a new list from disk.. etc.
//•	Loaded lists are always saved with the same filename.
//•	All list files have the .txt extension


//Tried putting the try and catch blocks in the main method,
//but resulted in error that the exceptions are never thrown.
//I suspect this is because the file operations are being called in the static methods,
//and don't appear in the main loop of the program, in the main method.

public class Main {
    static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        //Initialize variables
        boolean quit = false;
        ArrayList<String> yourList = new ArrayList<>();

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
                    openList(yourList);
                    break;
                //S – Save the current list file to disk
                case "S":
                    saveList(yourList);
                    break;
                //C – Clear removes all the elements from the current list
                case "C":
                    clearList(yourList);
                    break;
                case "Q":
                    quit=quitList(yourList);
            }
        } while (!quit);
    }

    //private static String menu()
    //You will want to display the current list along with the menu of options so the user can see what they are doing.
    //gets input from the user
    private static String menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nA – Add an item to the list");
        System.out.println("D – Delete an item from the list");
        System.out.println("I – Insert an item into the list");
        System.out.println("V – View the list");
        System.out.println("M – Move an item");
        System.out.println("O – Open a list file from disk");
        System.out.println("S – Save the current list file to disk");
        System.out.println("C – Clear all items from the current list");
        System.out.println("Q – Quit the program");
        String input = SafeInput.getRegExString(in, "What would you like to do [AaDdIiVvMmOoSsCcQq]? ", "[AaDdIiVvMmOoSsCcQq]");
        return input;
    }

    //You need to display a numbered version of the list to allow users to pick list elements for deletion.
    // Here the user looks at the display and then indicates the item to delete by the number.
    private static void numList(ArrayList<String> theList) {
        System.out.println();
        for (int x = 0; x < theList.size(); ++x) {
            System.out.println(x + 1 + ": " + theList.get(x));
        }
    }

    //private static boolean quitlist()
    //confirm that they want to quit
    //print the final list
    //return true if they want to quit to set the quit variable
    private static boolean quitList(ArrayList<String> theList) {
        boolean confirm = SafeInput.getYNConfirm(new Scanner(System.in),"Are you sure you want to quit? ");
        if (!confirm) {
            return false;
        } else {
            // Prompt the user on exit to save the list or abandon it.
            if (needsToBeSaved) {
                confirm = SafeInput.getYNConfirm(new Scanner(System.in),"Do you want to save the current list before quitting?");
                if (confirm) {
                    saveList(theList);
                } else {
                    System.out.println("\nYour list was not saved.");
                }
            }
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
        String item = SafeInput.getNonZeroLenString(in, "Type in the item you want to add");
        theList.add(item);
        System.out.println("\nThis is your list so far:");
        System.out.println(theList);
        //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
        needsToBeSaved = true;
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
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThere is nothing to delete!");
        } else {
            int item = SafeInput.getRangedInt(in, "Enter the number of the item you want to delete", 1, theList.size());
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to delete item " + item + ": " + theList.get(item - 1) + "? ");
            if (confirm) {
                theList.remove(item - 1);
                System.out.println("\nThis is your list after deleting the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                needsToBeSaved = true;
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
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThe list is empty. You need to add items before you can insert them!");
        } else {
            int location = SafeInput.getRangedInt(in, "Enter the number of the list item you want to insert a new item before", 1, theList.size());
            String item = SafeInput.getNonZeroLenString(in, "Type in the item you want to insert");
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to insert " + item + " before " + location + ":" + theList.get(location - 1) + "? ");
            if (confirm) {
                theList.add(location - 1, item);
                System.out.println("\nThis is your list after inserting the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                needsToBeSaved = true;
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
        if (theList.isEmpty()) {
            System.out.println("\nThere is nothing in the list to clear!");
            return;
        }

        if (needsToBeSaved) {
            boolean saveFirst = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save the list first?");
            if (saveFirst) {
                saveList(theList);
            }
        }

        boolean confirm = SafeInput.getYNConfirm(in, "Are you sure you want to clear all items from the list?");
        if (confirm) {
            theList.clear();
            System.out.println("\nYour list is now empty.");
            needsToBeSaved = false;  // List is empty, so no need to save
        } else {
            System.out.println("\nThe list was not cleared!");
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
        boolean confirm = false;
        numList(theList);
        if (theList.isEmpty()) {
            System.out.println("\nThe list is empty. You need to add items before you can move them!");
        } else {
            int startLocation = SafeInput.getRangedInt(in, "Enter the number of the list item you want to move", 1, theList.size());
            String item = theList.get(startLocation - 1);
            theList.remove(startLocation - 1);
            numList(theList);
            int endLocation = SafeInput.getRangedInt(in, "Enter the number of the list item you want to move " + item + " before", 1, theList.size());
            confirm = SafeInput.getYNConfirm(in, "Are you sure you want to move " + item + " before " + endLocation + ":" + theList.get(endLocation - 1) + "? ");
            if (confirm) {
                theList.add(endLocation - 1, item);
                System.out.println("\nThis is your list after moving the item:");
                System.out.println(theList);
                //Any operations that change the list, add, insert, delete, and move all need to set the dirty flag because the changed file must be saved or data will be lost.
                needsToBeSaved = true;
            } else {
                theList.add(startLocation - 1, item);
                System.out.println("\nItem was not moved!");
            }
        }
    }

    //private static void openList()
    //ask if they want to save first
    //user navigates to file
    //get the filename
    //set the target to the file
    //set the chooser to the project src directory
    //open the file
    //read the file line by line
    //add each line to the list
    //print the list
    //If the user loads a list, it does not need to be saved until it is changed by adding or deleting items.
    // (The user could load a list only to view it and then would want to load another in its place without saving…)
    private static void openList(ArrayList<String> theList) {
        try  // Code that might trigger the exception goes here
        {
            //Prompt the user to save an unsaved list before loading a new list from disk.. etc.
            Scanner in = new Scanner(System.in);
            boolean empty = theList.isEmpty();
            boolean confirm = false;
            if (!empty && needsToBeSaved) {
                confirm = SafeInput.getYNConfirm(in, "Do you want to save the current list before opening a new one?");
                if (confirm) {
                    saveList(theList);
                } else {
                    System.out.println("\nYour list was not saved.");
                }
            }

            JFileChooser chooser = new JFileChooser();
            Scanner inFile;
            String line;

            // set the chooser to the project src directory
            Path target = new File(System.getProperty("user.dir")).toPath();
            target = target.resolve("src");
            chooser.setCurrentDirectory(target.toFile());

            // Create a File object with the desired default filename
            File defaultFile = new File("default_list_file.txt");

            // Set the default selected file in the JFileChooser
            chooser.setSelectedFile(defaultFile);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                target = chooser.getSelectedFile().toPath();  // this is a File object not a String filename
                inFile = new Scanner(target);

                // Clear the current list before loading a new oneJFileChooser chooser = new JFileChooser();
                theList.clear();

                while (inFile.hasNextLine()) {
                    line = inFile.nextLine();
                    String[] items = line.split(",");
                    for (String item : items) {
                        theList.add(item.trim());
                    }
                }

                inFile.close();
                viewList(theList);
                needsToBeSaved = false;
            } else   // User did not pick a file, closed the chooser
            {
                System.out.println("Sorry, you must select a file! Terminating!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found Error");
            ex.printStackTrace();
        } catch (IOException ex) // code to handle this exception
        {
            System.out.println("IOException Error");
            ex.printStackTrace();
        }
    }

    //private static void saveList()
    //get the filename
    //set the target to the file
    //set the chooser to the project src directory
    //create the file
    //write each item in the list to the file, separated by commas
    //If the user loads a list, it does not need to be saved until it is changed by adding or deleting items.
    // (The user could load a list only to view it and then would want to load another in its place without saving…)
    private static void saveList(ArrayList<String> theList) {
        //prompt for the file name (add the .csv extension)
        //(Code the JFileChooser to open in the src directory of the IntelliJ project.)
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        Path fileLocation = getOutputPath(target.toString());

        if (fileLocation == null) {
            System.out.println("Save cancelled.");
            return;
        }

        //Write the data into the csv file which should be in the src directory of the intelliJ project.
        //This was the syntax in the cookbook, couldn't get the CREATE syntax from the lecture to work
        try (BufferedWriter writer =
                     Files.newBufferedWriter(fileLocation, Charset.forName("UTF-8"))) {
            for (int i = 0; i < theList.size(); i++) {
                writer.write(theList.get(i));
                if (i < theList.size() - 1) {
                    writer.write(",");
                }
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
            needsToBeSaved = false;
        }
        // This was also a little different in the cookbook - ex instead of e
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Path getOutputPath(String s) {
        JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);
        jd.setDialogTitle("Filename to save to:");

        // Create a File object with the desired default filename
        File defaultFile = new File("default_list_file.txt");

        // Set the default selected file in the JFileChooser
        jd.setSelectedFile(defaultFile);

        int returnVal = jd.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        } else {
            return jd.getSelectedFile().toPath();
        }

    }
}