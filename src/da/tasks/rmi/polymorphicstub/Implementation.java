package da.tasks.rmi.polymorphicstub;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Implementation extends UnicastRemoteObject implements FirstInterface, SecondInterface
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected Implementation() throws RemoteException
    {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void secondMethod()
    {
        System.out.println("Second Method called!");
    }

    @Override
    public void firstMethod()
    {
        System.out.println("First Method called!");
    }

}
