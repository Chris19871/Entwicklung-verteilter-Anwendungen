package da.tasks.rmi.registryexamination;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{
    public static void main(String[] args) throws RemoteException, NotBoundException
    {
        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        final KeinRemoteObjekt kro = (KeinRemoteObjekt) reg.lookup("kro");
        reg.lookup("kro");

        kro.equals(reg);
        // kro;
    }
}
