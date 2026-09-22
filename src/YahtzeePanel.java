import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class YahtzeePanel extends JPanel implements MouseListener, MouseMotionListener
{
    private final int ROW_HEIGHT = 20;
    private final int DIE_SIZE = 60;
    private final int DIE_SPACING = 64;

    private final int PLAYER_1 = 0;
    private final int PLAYER_2 = 1;

    private final Font cardTextFont;
    private final String[] ROW_NAMES = {"","Ones","Twos","Threes","Fours","Fives","Sixes","Top Subtotal", "Bonus", "3 of a Kind",
            "4 of a Kind", "Full House", "Small Straight", "Large Straight", "Chance", "Yahtzee", "Bottom Subtotal","Total"};
    private YahtzeeGame referee;
    private Die[] diceToDisplay;
    private int[] p1Scores, p2Scores;
    private int whichPlayersTurn;
    private boolean allowTogglingDice;
    private int lastMouseOveredRow;

    public YahtzeePanel()
    {
        super();
        cardTextFont = new Font("Times",Font.PLAIN, ROW_HEIGHT - 4);
        diceToDisplay = null;
        p1Scores = null;
        p2Scores = null;
        whichPlayersTurn = PLAYER_1;
        addMouseListener(this);
        addMouseMotionListener(this);
        referee = null;
        allowTogglingDice = false;
        lastMouseOveredRow = -1;
    }

    public void setReferee(YahtzeeGame ref)
    {
        referee = ref;
    }

    public void setDiceToDisplay(Die[] dieList)
    {
        diceToDisplay = dieList;
    }

    public void setAllowTogglingDice(boolean allowToggle)
    {
        allowTogglingDice = allowToggle;
    }
    /**
     * @param player - 0 -> no player; 1 -> player 1; 2 -> player 2
     */
    public void setWhichPlayersTurn(int player)
    {
        whichPlayersTurn = player;
        repaint();
    }

    public void setPlayerScoreLists(int[] p1, int[] p2)
    {
        p1Scores = p1;
        p2Scores = p2;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawEmptyCard(g);
        drawScores(g);
        drawDice(g);
    }

    /**
     * draws the category names, grid and highlighted column by means of graphics context g.
     * @param g - the graphics context in which/by which to draw.
     */
    public void drawEmptyCard(Graphics g)
    {
        g.setColor(new Color(196,255,196));
        g.fillRect(150+50*whichPlayersTurn, 0, 50, ROW_HEIGHT*ROW_NAMES.length);

        g.setColor(Color.lightGray);
        g.fillRect(0,ROW_HEIGHT,250,6*ROW_HEIGHT);
        g.fillRect(0,9*ROW_HEIGHT,250,7*ROW_HEIGHT);

        g.setColor(Color.BLACK);
        g.drawLine(0 ,0, 250, 0);
        g.setFont(cardTextFont);
        drawCenteredStringAt(g,"P1",175,ROW_HEIGHT-2);
        drawCenteredStringAt(g,"P2",225,ROW_HEIGHT-2);
        for (int i=0; i<ROW_NAMES.length; i++)
        {
            int base = ROW_HEIGHT * (i + 1);
            g.drawString(ROW_NAMES[i], 5, base - 2);
            g.drawLine(0, base, 250, base);
        }
        g.drawLine(150,0,150,ROW_HEIGHT*ROW_NAMES.length);
        g.drawLine(200,0,200,ROW_HEIGHT*ROW_NAMES.length);
        g.drawLine(250,0,250,ROW_HEIGHT*ROW_NAMES.length);
    }

    /**
     * prints the numbers from both players' scorecards into the grid.
     * @param g -  - the graphics context in which/by which to draw.
     */
    public void drawScores(Graphics g)
    {
        if (p1Scores == null)
            return;
        for (int row = 1; row < p1Scores.length; row ++)
        {
            if (p1Scores[row] != -1)
                drawCenteredStringAt(g,""+p1Scores[row], 175, ROW_HEIGHT*(row+1)-2);
            if (p2Scores[row] != -1)
                drawCenteredStringAt(g,""+p2Scores[row], 225, ROW_HEIGHT*(row+1)-2);


        }
    }

    /**
     * a handy utility function that will draw the given string with its baseline at y, centered horizontally at x.
     * @param g - the graphics utility that contains where you will draw and the tools to do so.
     * @param s - the string to draw
     * @param x - the desired x-location of the center of the string
     * @param y - the desired y-location of the baseline of the string
     */
    public void drawCenteredStringAt(Graphics g, String s, int x, int y)
    {
        int width = g.getFontMetrics(cardTextFont).stringWidth(s);
        g.drawString(s,x-width/2,y);
    }

    /**
     * draws all the dice in diceToDisplay on the right side of the board.
     * @param g - the graphics context in which/by which to draw.
     */
    public void drawDice(Graphics g)
    {
        if (diceToDisplay != null)
        {
            for (int i = 0; i<diceToDisplay.length; i++)
            {
                diceToDisplay[i].drawSelfAt(g, 260, 10+DIE_SPACING*i, DIE_SIZE);
            }
        }
    }

    //   The rest of this class is mandatory methods to enable this class to react to mouse clicks and mouse movement.
    //   You have to have them, but you don't have to make them do anything. Only a few do, in this case.
    @Override
    public void mouseClicked(MouseEvent e)
    {
        // (mouse was just pressed and released in the same spot)
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // user has just pushed down on the mouse or trackpad button but has not let up on it yet.
    }

    @Override
    /**
     * The user has just released the mouse button. The given MouseEvent contains information about the state of the
     * mouse when the user did so, including where it was at that time.
     */
    public void mouseReleased(MouseEvent e)
    {
        // Check whether the click was in the scorecard.
        if (e.getY()< ROW_NAMES.length*ROW_HEIGHT && e.getX()<250)
        {
            if (referee != null)
                referee.handleUserClickedRow(e.getY()/ROW_HEIGHT);
        }
        // Check whether the click was on a die.
        else if (allowTogglingDice && e.getX()>=260 && e.getX() <= 260+DIE_SIZE)
        {
            int yBin = (e.getY() - 10) / DIE_SPACING;
            int yFrac = (e.getY() - 10) % DIE_SPACING;
            if (yFrac<=DIE_SIZE && yBin >= 0 && yBin <= 5)
            {
                diceToDisplay[yBin].toggleSelected();
                repaint();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // the mouse just entered this panel from outside
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // the mouse just exited this panel to another graphics area outside of this panel.
        lastMouseOveredRow = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // the mouse just changed position while the user held the button down.
    }

    /**
     * the user has just moved the mouse within this panel with the mouse button unpressed. If this has moved it into
     * a new row of the sheet, let the referee know.
     * @param e the event to be processed, which includes the (x,y) pixel coordinates within this panel.
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (e.getY()< ROW_NAMES.length*ROW_HEIGHT && e.getX()<250)
        {
            if (referee != null)
            {
                int row = e.getY() / ROW_HEIGHT;
                if (row != lastMouseOveredRow)
                {
                    referee.handleUserMovedIntoRow(row);
                    lastMouseOveredRow = row;
                }
            }
        }
        else  // if we have exited the scorecard
            lastMouseOveredRow = -1;
    }
}
