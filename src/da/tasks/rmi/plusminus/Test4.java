package da.tasks.rmi.plusminus;

import java.awt.GridLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

public class Test4 extends JFrame
{
    private static final long serialVersionUID = 1L;

    public Test4() throws RemoteException, NotBoundException
    {
        this.setLayout(new GridLayout(3, 1));

        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        final Model model = (Model) reg.lookup(Model.DEFAULT_RMI_OBJECT_NAME);

        PlusButton plusButton = new PlusButton(model);
        MinusButton minusButton = new MinusButton(model);

        this.add(plusButton);
        this.add(minusButton);

        this.setSize(200, 300);
        this.setVisible(true);
        this.revalidate();
    }

    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        Test4 Test4 = new Test4();
        Test4.getX();
    }
}