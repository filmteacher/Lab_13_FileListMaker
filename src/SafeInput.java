import java.util.Scanner;

/**
 * SafeInput methods for Comp Progrm I
 * @author Matt Bennett matt.bennett@uc.edu
 */

public class SafeInput
{
    /**
    * From lab assignment file - by Tom Wulf
    * @param pipe a Scanner opened to read from System.in
    * @param prompt prompt for the user
    * @return a String response that is not zero length
    */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String response = ""; // Set this to zero length. Loop runs until it isn't

        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            response = pipe.nextLine();
        }while(response.length() == 0);

        return response;
    }

     /**
     * getInt
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return an int response
     */
    public static int getInt(Scanner pipe, String prompt)
    {
        int value = 0;
        String trash = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextInt())
            {
                value = pipe.nextInt();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an integer: " + trash);
            }
        }while(!done);

        return value;
    }

     /**
     * getDouble
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a double response
     */
    public static double getDouble(Scanner pipe, String prompt)
    {
        double value = 0;
        String trash = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextDouble())
            {
                value = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        }while(!done);

        return value;
    }

     /**
     * Get an integer within a range
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low low end of range
     * @param high high end of range
     * @return an int response within the range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int value = 0;
        String trash = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + " in the range [" + low + "-" + high + "]: ");
            if(pipe.hasNextInt())
            {
                value = pipe.nextInt();
                pipe.nextLine();
                if(value >= low && value <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("\nNumber is out of the range [" + low + "-" + high + "]: " + value);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter an integer: " + trash);
            }
        }while(!done);

        return value;
    }

     /**
     * Get a double within a range
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low low end of range
     * @param high high end of range
     * @return a double response within the range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double value = 0;
        String trash = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + " in the range [" + low + "-" + high + "]: ");
            if(pipe.hasNextDouble())
            {
                value = pipe.nextDouble();
                pipe.nextLine();
                if(value >= low && value <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("\nNumber is out of the range [" + low + "-" + high + "]: " + value);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a double: " + trash);
            }
        }while(!done);

        return value;
    }

     /**
     * Get a Y or N confirmation from the user
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return - true for yes or false for no
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean value = true;
        String response = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + " [Y/N] ");
            response = pipe.nextLine();
            if(response.equalsIgnoreCase("Y"))
            {
                done = true;
                value = true;
            }
            else if(response.equalsIgnoreCase("N"))
            {
                done = true;
                value = false;
            }
            else
            {
                System.out.println("You must answer [Y/N]! " + response);
            }
        }while(!done);

        return value;
    }

    /**
     * Get a string and compare to RegEx pattern
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param regEx RegEx pattern to constrain the input
     * @return a String found in the RegEx pattern
     */
    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String response = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt);
            response = pipe.nextLine();
            if(response.matches(regEx))
            {
                done = true;
            }
            else
            {
                System.out.println("\n" + response + " must be in this format: " + regEx);
            }

        }while(!done);

        return response;
    }

}
