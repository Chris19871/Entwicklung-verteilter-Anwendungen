package da.tasks.rmi.plusminus;

import java.awt.GridLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

public class Test3 extends JFrame
{
    private static final long serialVersionUID = 1L;

    public Test3() throws RemoteException, NotBoundException
    {
        this.setLayout(new GridLayout(3, 1));

        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        // final Model model = (Model)
        // reg.lookup(Model.DEFAULT_RMI_OBJECT_NAME);

        final Label label = (Label) reg.lookup(Label.DEFAULT_RMI_OBJECT_NAME);

        this.add((LabelImpl) label);

        this.setSize(200, 300);
        this.setVisible(true);
        this.revalidate();
    }

    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        Test3 Test3 = new Test3();
        Test3.getX();
    }
}