import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YahtzeeFrame extends JFrame implements ActionListener
{
    private JLabel messageLabel;
    private JButton rollButton;
    private YahtzeeGame referee;

    public YahtzeeFrame(YahtzeePanel yzPanel)
    {
        super("Yahtzee");
        setSize(350,500);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(yzPanel, BorderLayout.CENTER);
        messageLabel = new JLabel("");
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(messageLabel);
        getContentPane().add(topPanel,BorderLayout.NORTH);
        rollButton = new JButton("Roll");
        rollButton.addActionListener(this);
        getContentPane().add(rollButton,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        referee = null;
    }

    public void setReferee(YahtzeeGame ref)
    {
        referee = ref;
    }

    /**
     * changes the message displayed at the top of the window.
     * @param message
     */
    public void setMessage(String message)
    {
        messageLabel.setText(message);
        repaint();
    }

    // These methods are about whether the Roll button at the bottom of the screen is active or greyed out and
    //    unresponsive.
    public void enableButton() {rollButton.setEnabled(true);}
    public void disableButton() {rollButton.setEnabled(false);}
    public boolean isButtonEnabled() {return rollButton.isEnabled();}

    /**
     * A mandatory method if this class is to be considered an ActionListener, this method is called whenever a linked
     * button is pressed.
     * @param e the event to be processed, which includes information about which button was pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == rollButton) // at the moment, this is the only option, but it doesn't hurt to check, in
                                         //    case we add more.
        {
            referee.handleRollDiceButton();
        }
    }
}
