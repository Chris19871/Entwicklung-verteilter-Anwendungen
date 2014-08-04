package da.tasks.rmi.stubexamination;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class Client
{
    public static void main(String[] args) throws IOException, NotBoundException
    {
        final String objUrl = "rmi://127.0.0.1/" + Server.NAME_1;
        final Interface impl = (Interface) Naming.lookup(objUrl);
        final Interface impl2 = (Interface) Naming.lookup(objUrl);

        final String objUrl2 = "rmi://127.0.0.1/" + Server.NAME_2;
        final Interface impl3 = (Interface) Naming.lookup(objUrl2);

        for (String string : Naming.list(Server.NAME_1))
        {
            System.out.println("Naming.list: " + string);
        }

        if (impl.equals(impl2))
        {
            System.out.println("impl is equals impl2");
        }

        if (impl.equals(impl3))
        {
            System.out.println("impl is equals impl3");
        }
    }
}
