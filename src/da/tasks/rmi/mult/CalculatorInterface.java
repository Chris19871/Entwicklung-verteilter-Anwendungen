package da.tasks.rmi.mult;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorInterface extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "Calculator";

    public int calculate(int i, int j) throws RemoteException;
}
