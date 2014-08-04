package da.tasks.rmi.list.valueresult;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteAppenderImpl extends UnicastRemoteObject implements RemoteAppender
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RemoteAppenderImpl() throws RemoteException
    {
        // Muss wegen RemoteException von super() zur Verfügung gestellt werden.
    }

    @Override
    public void test(RMIList l) throws RemoteException
    {
        System.out.println("Testausgabe von test()");
    }

    @Override
    public void appendValue(RMIList listToAppendValueTo, int valueToAppend) throws RemoteException
    {
        listToAppendValueTo.append(valueToAppend);
    }
}