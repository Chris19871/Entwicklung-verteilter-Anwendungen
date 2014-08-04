package da.tasks.rmi.mult;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

public class Server
{
    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final CalculatorImpl counter = new CalculatorImpl();
        Naming.bind(CalculatorInterface.DEFAULT_RMI_OBJECT_NAME, counter);
        System.out.println("Server bereit");
    }
}