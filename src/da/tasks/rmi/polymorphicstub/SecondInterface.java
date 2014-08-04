package da.tasks.rmi.polymorphicstub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SecondInterface extends Remote
{
    public void secondMethod() throws RemoteException;
}
