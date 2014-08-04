package da.tasks.rmi.stubexamination;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote
{
    public int coolMethod() throws RemoteException;
}
