package da.tasks.rmi.plusminus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import da.tasks.rmi.plusminus.Model.OverrunException;

public class PlusButton extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Model myModel;

    public PlusButton(Model model)
    {
        super("+");

        this.myModel = model;

        this.setVisible(true);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            /*
             * Nicht gut, da "actionPerformed" im dispatch-Thread der Oberfläche
             * läuft. "myModel.increment" könnte sau lange dauern und damit die
             * Oberfläche einfrieren.
             */
            this.myModel.increment();

            // Besser mit Lambda-Ausdruck!!
            // new Thread(() -> this.myModel.increment()).start();

            // Mit try-catch
            // new Thread(() -> { try { this.myModel.increment(); } catch(..)
            // {} }).start();
        }
        catch (RemoteException | OverrunException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
