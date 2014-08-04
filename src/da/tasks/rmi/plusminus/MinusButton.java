package da.tasks.rmi.plusminus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import da.tasks.rmi.plusminus.Model.OverrunException;

public class MinusButton extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Model myModel;

    public MinusButton(Model model)
    {
        super("-");

        this.myModel = model;

        this.setVisible(true);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            this.myModel.decrement();
        }
        catch (RemoteException | OverrunException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}