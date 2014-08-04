package da.tasks.rmi.customstub;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementierung der {@link Counter}-Schnittstelle. Objekte dieser Klasse
 * können über RMI von entfernen Rechnern aus verwendet werden.
 */
public class SynchronizedCounterImpl extends UnicastRemoteObject implements Counter
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Gekapselter int-Zählwert. */
    private int counter;

    /**
     * Initialisiert eine neue CounterImpl-Instanz.
     * 
     * @throws RemoteException
     *             Wird ausgelöst, wenn es beim Exportieren dieses RMI-Objektes
     *             zu einem Fehler gekommen ist.
     */
    public SynchronizedCounterImpl() throws RemoteException
    {
        // Hier sind keine Initialisierungen notwendig. Konstruktor existiert
        // lediglich, da Konstruktor der Superklasse eine
        // RemoteException auslösen könnte und der an sonsten existierende
        // implizite Standardkonstruktor keine solche Ausnhame in
        // seiner throws-Deklaration stehen hat.
    }

    @Override
    public synchronized int reset()
    { // throws RemoteException {
      // System.out.println(Thread.currentThread().getName());

        this.counter = 0;
        return this.counter;
    }

    @Override
    public synchronized int increment()
    { // throws RemoteException {
      // System.out.println(Thread.currentThread().getName());

        this.counter++;
        return this.counter;
    }
}
