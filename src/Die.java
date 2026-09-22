import java.awt.*;

public class Die
{
    private int value;
    private boolean isSelected;

    // CONSTANTS - colors for the dice
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color PIP_COLOR = Color.BLACK;
    private static final Color OUTLINE_COLOR = Color.BLACK;
    private static final Color SELECTION_COLOR = Color.YELLOW;

    /**
     * standard constructor - starts with a random die that isn't selected.
     */
    public Die()
    {
        roll();
        deselect();
    }

    /**
     * Debugging constructor - not to be used for final game... in case you need to set up a specific combination of
     * dice to test your scoring.
     * @param v - which value (1-6) this die should have.
     */
    public Die(int v)
    {
        value = v;
        deselect();
    }


    // Several self-explanatory accessor/modifier methods for the selection status of this die.
    public void setSelected(boolean b) {isSelected = b;}
    public void toggleSelected() {isSelected = !isSelected;}
    public void select() {setSelected(true);}
    public void deselect() {setSelected(false);}
    public boolean isSelected() {return isSelected;}


    /**
     * randomizes this die in a range (1-6) inclusive.
     */
    public void roll()
    {
        value = (int)(6*Math.random()+1);
    }

    public int getValue()
    {
        return value;
    }

    /**
     * draws this die at (x,y) with overall width & height of "size" (including optional highlighted area) by means of
     * the graphics context g.
     * @param g - the graphics context in which/by which to draw
     * @param x - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param y - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param size - the size of the overall die (including outline)
     */    public void drawSelfAt(Graphics g, int x, int y, int size)
    {
        // draw highlighted outline, if this is selected.
        if (isSelected)
        {
            drawHighlight(g, x, y, size);
        }
        // draw the die shape and outline
        drawShapeAndOutline(g, x, y, size);

        // draw pips
        drawPips(g, x, y, size);

    }

    /**
     * draws a colored outline around this die.
     * @param g - the graphics context in which/by which to draw
     * @param x - the upper left corner of this outline
     * @param y - the upper left corner of this outline
     * @param size - how large the die (including outline) should be.
     */
    private static void drawHighlight(Graphics g, int x, int y, int size)
    {
        g.setColor(SELECTION_COLOR);
        g.fillRoundRect(x, y, size, size, size /8, size /8);
    }

    /**
     * draws the background color and shape of the die itself, slightly smaller than "size"
     * @param g - the graphics context in which/by which to draw
     * @param x - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param y - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param size - the size of the overall die (including outline)
     */
    private static void drawShapeAndOutline(Graphics g, int x, int y, int size)
    {
        g.setColor(BACKGROUND_COLOR);
        g.fillRoundRect(x + size /10, y + size /10,8* size /10,8* size /10, size /8, size /8);
        g.setColor(OUTLINE_COLOR);
        g.drawRoundRect(x + size /10, y + size /10,8* size /10,8* size /10, size /8, size /8);
    }

    /**
     * based on the value of this die, draws the correct number of pips on this die.
     * @param g - the graphics context in which/by which to draw
     * @param x - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param y - the upper left corner of the overall die picture, including highlight. This die will appear inset
     * @param size - the size of the overall die (including outline)
     */
    private void drawPips(Graphics g, int x, int y, int size)
    {
        int pipSize = Math.max(2, size /8);
        g.setColor(PIP_COLOR);
        switch(value)
        {
            case 1:
                g.fillOval(x + size / 2 - pipSize / 2, y + size / 2 - pipSize / 2, pipSize, pipSize);
                break; // stop here for value = 1

            case 3:  // this overlaps with case 2, so at first, we'll just draw the center pip.
                g.fillOval(x + size / 2 - pipSize / 2, y + size / 2 - pipSize / 2, pipSize, pipSize);

            case 2: // and case 3 continues... now we'll draw the NW & SE pips.
                g.fillOval(x + size / 3 - pipSize / 2, y + size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + 2 * size / 3 - pipSize / 2, pipSize, pipSize);
                break; // cases 2 & 3 stop here

            case 5:  // this overlaps with case 4, so we'll start by drawing the center pip.
                g.fillOval(x + size / 2 - pipSize / 2, y + size / 2 - pipSize / 2, pipSize, pipSize);

            case 4:  // and case 5 continues... now we'll draw the pips at the corners.
                g.fillOval(x + size / 3 - pipSize / 2, y + size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + 2 * size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + size / 3 - pipSize / 2, y + 2 * size / 3 - pipSize / 2, pipSize, pipSize);
                break; // cases 4 & 5 stop here.

            case 6:
                g.fillOval(x + size / 3 - pipSize / 2, y + size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + 2 * size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + size / 3 - pipSize / 2, y + 2 * size / 3 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + 2 * size / 3 - pipSize / 2, y + size / 2 - pipSize / 2, pipSize, pipSize);
                g.fillOval(x + size / 3 - pipSize / 2, y + size / 2 - pipSize / 2, pipSize, pipSize);
                break; // case 6 stops here.
            default:
                throw new RuntimeException("Tried to draw a die with a value outside 1-6: " + value);
        }
    }

    public String toString()
    {
        if (isSelected)
            return " "+value+" ";
        else
            return "["+value+"]";
    }
}
