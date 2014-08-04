package da.tasks.rmi.stubexamination;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

public class Server
{
    static final String NAME_1 = "name11";
    static final String NAME_2 = "name21";

    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final Implementation impl = new Implementation();
        Naming.bind(Server.NAME_1, impl);
        Naming.bind(Server.NAME_2, impl);
        System.out.println("Server bereit");
    }
}
