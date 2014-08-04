package da.tasks.rmi.list.valueresult;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAppender extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "RemoteAppender";

    void appendValue(RMIList listToAppendValueTo, int valueToAppend) throws RemoteException;

    void test(RMIList l) throws RemoteException;
}