/**
* The Math class contains additional methods not found in java.lang.Math class, but it uses methods from it.
*  
* Method getRandom will generate a pseudorandom Integer which minimum value is the first parameter of method,
* and the maximum value is equal to the second parameter of the method. It will accept integers as parameters.
*
* Method weeksToYears will simply divide integer given as parameter by 52, and return it, rounded down.
*
*@author Lauri Paajarvi
*/

package fi.tuni.tamk.tiko.paajarvilauri.util;

public class Math {
    public static int getRandom(int min, int max) {
        return min + (int) (java.lang.Math.random() * ((max - min) + 1));
    }
    public static int weeksToYears(int weeks) {
        return weeks/52;
    }
}
