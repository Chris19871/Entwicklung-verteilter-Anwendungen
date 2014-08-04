package da.tasks.rmi.plusminus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Model extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "IncrementTool";

    void increment() throws RemoteException, OverrunException;

    void decrement() throws RemoteException, OverrunException;

    void addListener(ListenerInterface listener) throws RemoteException;

    public class OverrunException extends Exception
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        // Keine erweiternde Implementierung notwendig.
    }
}
