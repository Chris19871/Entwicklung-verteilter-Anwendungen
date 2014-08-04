package da.tasks.rmi.plusminus;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JLabel;

public class LabelImpl extends JLabel implements Label, ListenerInterface
{
    private static final long serialVersionUID = 1L;
    private Model model;

    public LabelImpl(Model model) throws RemoteException
    {
        this.model = model;
        this.setText("hallo");
        this.model.addListener(this);
        System.out.println("added");
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    @Override
    public void change(String text)
    {
        this.setText(text);
    }

    @Override
    public void add() throws RemoteException
    {
        this.model.addListener(this);
    }
}
