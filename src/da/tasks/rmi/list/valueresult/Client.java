package da.tasks.rmi.list.valueresult;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{
    public static void main(final String[] args) throws RemoteException, NotBoundException
    {
        final List list = new List(0, 8, 15);
        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        final RemoteAppender appender = (RemoteAppender) reg.lookup(RemoteAppender.DEFAULT_RMI_OBJECT_NAME);

        System.out.println(list.toString()); // Ausgabe 0 8 15
        appender.test(list);
        appender.appendValue(list, 4711); // RMI-Aufruf! list und 4711 müssen
        // übertragen werden
        System.out.println(list.toString()); // Ausgabe ?
    }
}