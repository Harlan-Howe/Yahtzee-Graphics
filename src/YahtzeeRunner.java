public class YahtzeeRunner
{
    static void main()
    {
        // Build the dice
        Die[] diceList = new Die[5];
        for (int i=0; i<5; i++)
        {
            diceList[i] = new Die();
            if (Math.random() > 0.5)
                diceList[i].select();
        }

        // build the players' scorecard columns
        int[] p1ScoreList = new int[17];
        int[] p2ScoreList = new int[17];
        for (int i=0; i<17; i++)
        {
            p1ScoreList[i] = -1;
            p2ScoreList[i] = -1;
        }

        // build the GUI for the center of the window and tell it about the dice and scorecard lists
        YahtzeePanel contentGUI = new YahtzeePanel();
        contentGUI.setDiceToDisplay(diceList);
        contentGUI.setPlayerScoreLists(p1ScoreList, p2ScoreList);

        // build the window and set it's center to the panel we just created.
        YahtzeeFrame window = new YahtzeeFrame(contentGUI);

        // create the YahtzeeGame (a.k.a. referee, and let it know about all the things we just made.
        YahtzeeGame referee = new YahtzeeGame(diceList, p1ScoreList, p2ScoreList, contentGUI, window);
        contentGUI.setReferee(referee);
        window.setReferee(referee);

        window.setVisible(true);
    }
}
