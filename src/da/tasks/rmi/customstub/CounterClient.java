package da.tasks.rmi.customstub;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client-Klasse, die ein {@link Counter}-Objekt von einer RMI-Registry bezieht
 * und darauf verschiedene Methodenaufrufe ausführt.
 */
public class CounterClient
{
    /**
     * Hauptprogramm. Als Kommandozeilenargumente müssen die Adresse des
     * Rechners angegeben werden, auf dem die RMI-Registry läuft, sowie die
     * Anzahl an zu versendenden "increment"-Befehlen.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der Kommunikation mit der
     *             RMI-Registry zu einem Ein-/Ausgabefehler gekommen ist.
     * @throws NotBoundException
     *             Wird ausgelöst, wenn bei der RMI-Registry auf dem Rechner mit
     *             der übergebenen Adresse kein Objekt mit dem Namen
     *             {@link Counter#DEFAULT_RMI_OBJECT_NAME} registriert ist.
     */
    public static void main(final String[] args) throws IOException, NotBoundException
    {
        final String objUrl = String.format("rmi://%s/%s", args[0], Counter.DEFAULT_RMI_OBJECT_NAME);
        final Counter counter = (Counter) Naming.lookup(objUrl);

        final int incrementCount = Integer.parseInt(args[1]);

        /*
         * Soll im Stub der Lookup gemacht werden oder ein Server erstellt
         * werden? Stub stub = new Stub(server, port) Wird dann das Skeleton
         * auch so erstellt und was hat das dann noch mit RMI zu tun?
         */

        CounterClient.playWithCounter(counter, incrementCount);
    }

    /**
     * Eigentlich unnötige Hilfsmethode, die ein {@link Counter}-Objekt entgegen
     * nimmt und auf diesem Aufrufe durchführt. Dadurch, dass als Parameter die
     * Schnittstelle {@link Counter} verwendet wird, kann man der Methode sowohl
     * einen passenden Stub als auch die Referenz auf ein im gleichen Adressraum
     * existierenden {@link SynchronizedCounterImpl}-Objekt übergeben. Für den
     * Quelltext in der Methode ist dieser Unterschied nicht sichbar!
     * 
     * @param ctr
     *            {@link Counter}-Objekt, auf dem Methoden aufgerufen werden
     *            sollen.
     * @param incrCnt
     *            Anzahl an zu tätigenden {@link Counter#increment()}-Aufrufen.
     * @throws RemoteException
     *             Wird ausgelöst, wenn als {@link Counter}-Objekt ein Stub
     *             übergeben wurde und es bei einem Methodenaufruf auf diesem
     *             Stub zu einem Kommunikationsproblem mit dem betreffenden
     *             Skeleton gekommen ist.
     */
    private static void playWithCounter(final Counter ctr, final int incrCnt) throws RemoteException
    {
        final int counterValueAfterReset = ctr.reset();
        System.out.println("Antwort: " + counterValueAfterReset);

        for (int i = 0; i < incrCnt; i++)
        {
            final int counterValueAfterIncrement = ctr.increment();
            System.out.println("Antwort: " + counterValueAfterIncrement);
        }
    }
}
