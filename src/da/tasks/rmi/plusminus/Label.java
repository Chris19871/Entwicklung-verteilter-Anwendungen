package da.tasks.rmi.plusminus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Label extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "LabelRemote";

    void add() throws RemoteException;
}
