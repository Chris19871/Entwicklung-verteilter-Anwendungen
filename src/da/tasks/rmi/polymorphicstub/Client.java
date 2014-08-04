package da.tasks.rmi.polymorphicstub;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class Client
{
    public static void main(String[] args) throws IOException, NotBoundException
    {
        final String objUrl = "rmi://127.0.0.1/" + "Aufgabe21";
        Object obj = Naming.lookup(objUrl);

        FirstInterface first = (FirstInterface) obj;
        first.firstMethod();

        SecondInterface second = (SecondInterface) obj;
        second.secondMethod();
    }
}
