package da.tasks.rmi;

import java.rmi.RemoteException;

public interface RMIProducer<T>
{
    T doRMICall() throws RemoteException;
}