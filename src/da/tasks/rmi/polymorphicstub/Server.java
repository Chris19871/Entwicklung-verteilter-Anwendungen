package da.tasks.rmi.polymorphicstub;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

public class Server
{
    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final Implementation impl = new Implementation();
        Naming.bind("Aufgabe21", impl);
        System.out.println("Server bereit");
    }
}