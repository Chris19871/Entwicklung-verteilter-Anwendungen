package da.tasks.rmi.plusminus;

import java.awt.BorderLayout;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

public class Test1 extends JFrame
{
    private static final long serialVersionUID = 1L;

    public Test1() throws RemoteException, AlreadyBoundException
    {
        this.setLayout(new BorderLayout());
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        final Model model = new ModelImpl(0, 100, 1);
        localReg.bind(Model.DEFAULT_RMI_OBJECT_NAME, model);

        final Label label = new LabelImpl(model);
        localReg.bind(Label.DEFAULT_RMI_OBJECT_NAME, label);
        // model.addListener(label);

        this.add((LabelImpl) label);

        this.setSize(200, 300);
        this.setVisible(true);
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        Test1 test1 = new Test1();
        test1.getX();
    }
}
