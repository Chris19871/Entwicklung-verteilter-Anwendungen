package da.tasks.rmi.stubexamination;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Implementation extends UnicastRemoteObject implements Interface
{

    private static final long serialVersionUID = 1L;

    public Implementation() throws RemoteException
    {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int coolMethod() throws RemoteException
    {
        System.out.println("coolMethod() called!");
        return 0;
    }

}
