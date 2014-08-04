package da.tasks.rmi.list.valueresult;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server
{
    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final RemoteAppender appender = new RemoteAppenderImpl();
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        localReg.bind(RemoteAppender.DEFAULT_RMI_OBJECT_NAME, appender);
    }
}