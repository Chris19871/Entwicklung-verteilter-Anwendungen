package da.tasks.rmi.list.valueresult;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIList extends Remote
{
    void append(int valueToAppend) throws RemoteException;
}