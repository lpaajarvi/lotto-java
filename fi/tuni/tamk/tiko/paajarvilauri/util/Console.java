
/**
 * 
 * readIntPrintErrorIfNot int(string) will read an integer from the end-user using java.util.Scanner, and print out an
 * error message (given as parameter) if the user tries to insert a non-integer value.
 * 
 * readInt int(int min, int max, String errorMessageNonNumeric, String errorMessageNonMinAndMax) will use the readIntPrintErrorIfNot -method, but in addition it will test if the given value is
 * equal or between minimum and maximum integers, which are set by parameters. If the given value is not equal or
 * between these numbers, it will print out an error message given as parameter.
 * 
 * readBooleanYorN (String question) will print out the String given as parameter, while adding " (y/n)" at the end of it, and will continue to do so until the end user
 * manages to provide either y, Y, n or N as first letter in their answer. Answering Y or y will return a boolean value of True, while answering N or n will return
 * a boolean value of False. Excepetion will be printed out and the question will be prompted again, in case something unexpected happens. If user hits just enter without
 * providing any information, or tries to write something other than a String containin Y, y, N or n as the first letter, there will be an additional information,
 * "Please type Y, y, N or n and press enter to continue." printed out, and the original question is repeated. 
 * 
 *@author Lauri Paajarvi
 */
package fi.tuni.tamk.tiko.paajarvilauri.util;

import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class Console {

    public static int readIntPrintErrorIfNot(String errorMessage) {

        Scanner s = new Scanner(System.in);
        
        boolean success=false;
        int input = 0;

        while (!success) {
            try {
                input = Integer.parseInt(s.nextLine());
                success=true;

            } catch(NumberFormatException e) {
                System.out.println(errorMessage);
                success = false;
            }
        }
        return input;
    }

    public static int readInt(int min, int max, String errorMessageNonNumeric, String errorMessageNonMinAndMax) {
        boolean validValue=false;
        int input = 0;
        while (!validValue) {
            input=readIntPrintErrorIfNot(errorMessageNonNumeric);
            if (input >= min && input <=max) {
                validValue=true;
            } else {
                System.out.println(errorMessageNonMinAndMax);
            }
        }
        return input;
    }

    public static boolean readBooleanYorN(String question) { //TÄÄTÄYTYYKORJATA INDEXOUTOFBOUND
        boolean answer=false;
        boolean success=false;
        String temp = "";
        String information="Please type Y, y, N or n and press enter to continue."; 
        Scanner s = new Scanner(System.in);
        while (!success) {
            try {
                System.out.println(question+" (y/n)");
                temp=s.nextLine();
                //we don't want to let user see String
                if (temp.length()==0) {
                    System.out.println(information);
                }
                else if (temp.charAt(0)=='n' || temp.charAt(0)=='N') {
                    return false;
                } else if (temp.charAt(0)=='y' || temp.charAt(0)=='Y') {
                    return true;
                } else {
                    System.out.println(information);
                }
            } catch (Exception e){
                System.out.println(e);
                }
        }
        return answer;
    }
}