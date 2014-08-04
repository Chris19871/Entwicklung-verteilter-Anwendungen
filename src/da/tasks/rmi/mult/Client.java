package da.tasks.rmi.mult;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client
{
    public static void main(String[] args) throws IOException, NotBoundException
    {
        final String objUrl = "rmi://127.0.0.1/" + CalculatorInterface.DEFAULT_RMI_OBJECT_NAME;
        final CalculatorInterface calcInt = (CalculatorInterface) Naming.lookup(objUrl);
        Client.calc(calcInt, 9, 9);
    }

    public static void calc(final CalculatorInterface calculator, int i, int j) throws RemoteException
    {
        System.out.println(calculator.calculate(i, j));
    }
}
