package da.tasks.rmi.customstub;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

/**
 * Server-Klasse, die ein Objekt vom Typ {@link SynchronizedCounterImpl} bei
 * einer lokal laufenden RMI-Registry unter dem Namen
 * {@link Counter#DEFAULT_RMI_OBJECT_NAME} registriert.
 */
public class CounterServer
{
    /**
     * Hauptprogramm.
     * 
     * @param args
     *            Kommandozeilenargumente (werden hier nicht benötigt).
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der Kommunikation mit der
     *             RMI-Registry zu einem Ein-/Ausgabefehler gekommen ist.
     * @throws AlreadyBoundException
     *             Wird ausgelöst, wenn unter dem Namen
     *             {@link Counter#DEFAULT_RMI_OBJECT_NAME} bereits ein anderes
     *             RMI-Objekt bei der RMI-Registry registriert ist.
     */
    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final SynchronizedCounterImpl conuter = new SynchronizedCounterImpl();
        Naming.bind(Counter.DEFAULT_RMI_OBJECT_NAME, conuter);
        System.out.println("Counter-Server bereit");
    }
}