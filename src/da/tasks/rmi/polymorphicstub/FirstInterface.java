package da.tasks.rmi.polymorphicstub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FirstInterface extends Remote
{
    public void firstMethod() throws RemoteException;
}
