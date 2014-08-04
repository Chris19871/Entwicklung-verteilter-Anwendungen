package da.tasks.rmi.compressexamination;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import da.tasks.rmi.mult.CalculatorInterface;

public class Client
{

    public static void main(final String[] args) throws RemoteException, NotBoundException
    {
        final Registry localReg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        final CalculatorInterface someObj = (CalculatorInterface) localReg.lookup("measuring");
        // final CalculatorImpl someOtherObj = new CalculatorImpl();

        System.out.println(someObj.calculate(10, 2));
        // someObj.someMethod(someOtherObj); // RMI-Kommunikation für Aufruf
        // jetzt
        // komprimiert
    }
}

// ohne Komprimierung:
// Output Stream: 24576 Bytes
// Input Stream: 61585 Bytes

// mit Komprimierung:
// Output Stream: 8192 Bytes
