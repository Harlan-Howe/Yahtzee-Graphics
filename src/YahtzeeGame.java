public class YahtzeeGame
{
    private Die[] dice;
    /**
     * These scores are as follows:
     * 0  unused
     * 1  ones
     * 2  twos
     * 3  threes
     * 4  fours
     * 5  fives
     * 6  sixes
     * 7  subtotal for rows 1-6
     * 8  top bonus ... +35 if the sum of rows 1-6 is 63 or more.
     * 9  3 of a kind
     * 10 4 of a kind
     * 11 Full House
     * 12 Small Straight
     * 13 Large Straight
     * 14 Chance
     * 15 Yahtzee
     * 16 subtotal for rows 9-15
     * 17 total = both subtotals + bonus.
     *
     * Note: these ALL start off as -1, which means (unused/do not display). Don't count the -1 in your calculations.
     */
    private int[] player1Scores;
    private int[] player2Scores;

    //Recommended variables:
    // int whoseTurnIsIt; (1 or 2)
    // int whichRollIsIt; (1, 2, 3 or 4)  // 1 means about to roll first time.... 4 means all 3 rolls completed.


    private final YahtzeePanel GUI;
    private final YahtzeeFrame window;

    public YahtzeeGame(Die[] dieList, int[] p1Scores, int[] p2Scores, YahtzeePanel guiPanel, YahtzeeFrame frame)
    {
        dice = dieList;
        player1Scores = p1Scores;
        player2Scores = p2Scores;
        GUI = guiPanel;
        window = frame;
        window.setMessage("Welcome to Yahtzee!");
        runTestDebuggingCode();  // JUST FOR DEBUGGING. Comment this out once game is rolling.
    }

    /**
     * runs some testing code on the recommended to-do methods. Feel free to modify them. Once you are working on the
     * main program, you should deactivate this code.
     */
    public void runTestDebuggingCode()
    {
        System.out.println("This is a temporary method to check whether your recommended methods are working.");
        System.out.println("Testing getFrequencyArray(). --------------------------------------------");
        // For debugging, feel free to modify these to be whatever is most helpful.
        dice[0] = new Die(3);
        dice[1] = new Die(3);
        dice[2] = new Die(4);
        dice[3] = new Die(1);
        dice[4] = new Die(6);

        System.out.print("Dice:");
        for (int i=0; i<5; i++)
        {
            System.out.print(dice[i]+" ");
        }
        System.out.println("\nFrequency Array:");
        int[] freqArray = getFrequencyArray();
        for (int i=1; i<6; i++)
            System.out.println(i+"\t"+freqArray[i]);
        System.out.println(" testing getScoreInCategoryForCounts  ----------------------------------");
        for (int row=1; row<16; row++)
        {
            if (row != 7 && row != 8)
                System.out.println(row+"\t"+getScoreInCategoryForCounts(row,freqArray));
        }
        System.out.println("testing calculateTotalsForScoreSet()  -----------------------------------");
        for (int i=1; i<16; i++)
            player1Scores[i] = (int)(Math.random()*60);
        calculateTotalsForScoreSet(player1Scores);
    }


    /**
     * the user has just clicked somewhere on the scorecard. This is your opportunity to react to that, perhaps by
     * calculating the score for this set of dice on this row and entering it; perhaps by doing nothing, if the field
     * is already filled, isn't a clickable field, or if the dice haven't been rolled yet.
     * @param whichRow - the number of the row (0 - 16) the user clicked in.
     */
    public void handleUserClickedRow(int whichRow)
    {
        System.out.println("I was just told that the user clicked in row "+whichRow+"!");
        // TODO - Required: write this method, which will likely call other methods.
    }

    /**
     * the user has just moved into this row from some other row in the scorecard. This is your opportunity to update
     * the message with information about what would happen if the user clicked in this row.
     * @param whichRow
     */
    public void handleUserMovedIntoRow(int whichRow)
    {
        //TODO - Optional: write something that might give the user a preview of what would happen if the user clicked
        //    in this row.
        return;
    }

    /**
     * The user has clicked the Roll button. This might mean that you should tell some or all of the dice to roll(), but
     * it might also mean that you do nothing, if this player has already rolled three times.
     */
    public void handleRollDiceButton()
    {
        System.out.println("The user just pressed the Roll button. I should do something about that, maybe.");
        // Note: you may wish to tell some or all of the dice to roll(). After you do, be sure to tell
        // GUI.repaint() so that it knows to redraw the dice, or they won't show that they have changed!
        // TODO - Required: write this method.



        GUI.repaint(); // make sure to do this so that the GUI updates the appearance of the dice on screen to match the
                       //   ones in memory.
    }


    /**
     * returns an array counting how many of each die there are. Note the #0 slot in the array is ignored.
     * So if you had dice: { 1, 2, 4, 2, 5}  the result would be {0, 1, 2, 0, 1, 1, 0}
     * If you had dice: {3, 6, 6, 3, 6} the result would be {0, 0, 0, 2, 0, 0, 3}
     * If you had dice: {4, 4, 4, 4, 4} the result would be {0, 0, 0, 0, 5, 0 ,0}
     * @return - a 7-element array of the count of the dice's values.
     */
    public int[] getFrequencyArray()
    {
        int[] counts = {0,0,0,0,0,0,0}; // note the #0 slot will not be used.
        //TODO - Recommended: I think you will find this handy.

        return counts;
    }

    /**
     * Given a desired category and the frequency count of the dice currently showing, finds the score that would be
     * put into this field if the user were to select this category.
     * @param category - the number of one of the rows in the score card.
     * @param frequencyArray - the count of ones, twos, threes, etc. on the five dice. Note: Position #0 is ignored.
     * @return - the score to put into this field.
     */
    public int getScoreInCategoryForCounts(int category, int[] frequencyArray)
    {
        // TODO - Recommended: I think you will find this handy, although I would advise that you break it into smaller
        //    methods for the various types of categories.


        return 0;
    }

    /**
     * calculates the subtotal for the top of the scorecard for the given scorecard array; calculates whether there is a
     * bonus; calculates the subtotal of the bottom of the scorecard for the given scorecard array; calculates the total
     * of the subtotals and bonus. Each of these values is updated in the player scorecard array.
     * @param playerNScores - the column of scores for one of the players, which will be updated.
     */
    public void calculateTotalsForScoreSet(int[] playerNScores)
    {
        // TODO - Recommended: I think you will find this handy to have.

        GUI.repaint(); // -- end with this so that if you have changed the values, the screen will update to reflect the
                       // changes.
    }
}
