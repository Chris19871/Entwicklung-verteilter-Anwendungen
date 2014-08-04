package da.tasks.rmi.compressexamination;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import da.tasks.rmi.mult.CalculatorImpl;

public class Server
{
    public static void main(final String[] args) throws RemoteException, AlreadyBoundException
    {
        final CalculatorImpl someObj = new CalculatorImpl();
        final MeasuringSocketFactory custFact = new MeasuringSocketFactory();
        UnicastRemoteObject.exportObject(someObj, 0, custFact, custFact);
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        localReg.bind("measuring", someObj);
    }
}
