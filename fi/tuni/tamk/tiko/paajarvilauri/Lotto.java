package fi.tuni.tamk.tiko.paajarvilauri;

import fi.tuni.tamk.tiko.paajarvilauri.util.Arrays;
import fi.tuni.tamk.tiko.paajarvilauri.util.Console;
import fi.tuni.tamk.tiko.paajarvilauri.util.Math;


public class Lotto {
    static final int MIN = 1;
    static final int MAX = 40;
    static final int NUMBERS = 7;
    
    static int yearsToHitJackpot=-1;
    static String instruction="Please give a unique number between ["+MIN+", "+MAX+"]";
    static int[] lottoSheet = new int[NUMBERS];
    static int[] roundsItTakesToWin = new int[NUMBERS+1];
    static int[] yearsItTakesToWin = new int[NUMBERS+1];
    static int[][] winningRounds = new int[NUMBERS+1][]; 
    
    static int timeline=50000;
    static boolean roundsVisible= false;
    static boolean gameContinues=true;
    public static void main(String [] args) {

        lottoSheet=askNumbers(lottoSheet);
        Arrays.sort(lottoSheet);
        roundsVisible = askVisibility();
        timeline=askTimeline();

        while(gameContinues) {

            if (yearsToHitJackpot > timeline || yearsToHitJackpot == -1) {
                //yearsToHitJackpot being -1 means it's the first round, so there is no need to ask if the player wants to continue for another round
                if (yearsToHitJackpot!=-1) {
                    gameContinues=askToStart();
                }
            }

            if(gameContinues) {

                // roundsItTakesToWin array will hold the information on how many lotto calculation rounds it will take to reach each landmark on winnings
                // For example: if it takes 4 lotto rounds to have 1 number rights, it will have a value of 4 in its index 1. Index of 0
                // will aways be 0, because I thought it would be easier to follow and possibly reuse this way.

                roundsItTakesToWin = calculateRoundsItTakesToWin();
                yearsItTakesToWin=roundsToYears(roundsItTakesToWin);
                yearsToHitJackpot=yearsItTakesToWin[NUMBERS];
                printResultsByYears();
            }
        }
            
    }
        
    private static int[] askNumbers(int[] userNumbers) {
        int[] toBeReturned = new int[userNumbers.length];
        for (int i=0; i<toBeReturned.length; i++) {
            System.out.println(instruction+" ("+(i+1)+" of "+NUMBERS+")");
            
            int userAnswer;
            boolean continues = false;
            while (!continues) {
                userAnswer = Console.readInt(MIN,MAX,"Please give a number.",instruction);
                if (Arrays.contains(userAnswer, toBeReturned)) {
                    System.out.println("The number "+userAnswer+" is not a unique number.");
                } else {
                    toBeReturned[i]=userAnswer;
                    continues=true;
                }
            }
        }
        return toBeReturned;
    }

    private static int askTimeline() {
        int timeline;
        System.out.println("In how many years do you hope to win the jackpot?\n(The game will ask you if you want to try again if you fail to get "+NUMBERS+" right in this time)");
        timeline = Console.readInt(0,10000000,"Please give a number.","That number was quite silly. Please be reasonable.");
        return timeline;
    }
    private static int[] doTheLottery(int amount, int min, int max) {
        int[] availableNumbers = new int[MAX];
        int[] generatedNumbers = new int[amount];
        for (int i=0; i<MAX; i++) {
            availableNumbers[i]=i+1;
        }

        for (int i=0; i<amount; i++) {
            boolean continues=false;
            while (!continues) {
                int lottoIndex = Math.getRandom(0,availableNumbers.length-1);
                if (!Arrays.contains(availableNumbers[lottoIndex], generatedNumbers)) {
                    generatedNumbers[i]=availableNumbers[lottoIndex];
                    Arrays.removeIndex(generatedNumbers, lottoIndex);    
                    continues=true;
                }
            }
        }
        return generatedNumbers;
    }
    private static int[] calculateRoundsItTakesToWin() {
        System.out.println("Calculating lottery... Please be patient");
        int[] time = new int[NUMBERS+1];
        int howManyRight=0;
        for (int i = 1; howManyRight < NUMBERS; i++) {
            int[] currentRound = new int[1];
            currentRound=doTheLottery(NUMBERS, MIN, MAX);
            if(Arrays.containsSameValues(currentRound, lottoSheet)>howManyRight) {
                
                int howManyMore = Arrays.containsSameValues(currentRound, lottoSheet)-howManyRight;
                if(howManyMore==1) {
                          
                    howManyRight++;
                    time[howManyRight]=i;
                    winningRounds[howManyRight]=currentRound;
                }
                else {
                    for (int k=howManyMore; k>0; k--) {
                    time[k+howManyRight]=i;
                    winningRounds[k+howManyRight]=currentRound;
                    }
                howManyRight += howManyMore;
                }
            }
        }
    return time;
    }
    private static int[] roundsToYears(int[] rounds) {
        int years[] = new int[NUMBERS+1];
        for (int i=0; i<years.length; i++) {
            years[i]=Math.weeksToYears(rounds[i]);
        }
    return years;
    }
    private static void printResultsByYears() {
        for (int i = 1; i<NUMBERS+1; i++) {
            if ((i < (NUMBERS) && (Arrays.containsSameValues(winningRounds[i], winningRounds[i+1])<NUMBERS))
                || (i==NUMBERS)) {
                
                if(roundsVisible) {
                    Arrays.sort(winningRounds[i]);
                    System.out.println("Your numbers:  "+Arrays.addPrefix(lottoSheet, 2));
                    System.out.println("Lotto numbers: "+Arrays.addPrefix(winningRounds[i], 2));
                }
                
                System.out.println("Getting "+i+" right took "+yearsItTakesToWin[i]+" years");
                System.out.println();
            }
        }
        if (yearsToHitJackpot > timeline) {
            System.out.println("But unfortunately getting "+NUMBERS+" right took more than "+timeline+" years.");
        } else {
            System.out.println("Wow! Well done!");
            gameContinues=false;
        }
    }
    private static boolean askVisibility() {
        return Console.readBooleanYorN("Do you want to see results after each new record in correct numbers in your lotto sheet?");
    }
    private static boolean askToStart() {
        return Console.readBooleanYorN("Do you want to try again?");
    }

}
