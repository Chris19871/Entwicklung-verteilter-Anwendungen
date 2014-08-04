package da.tasks.rmi.customstub;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI-Schnittstelle für einen über RMI zur Verfügung gestellten Zähler.
 */
public interface Counter extends Remote
{
    /**
     * Konstante für den Standardnamen eines Counter-Objektes in einer
     * RMI-Registry.
     */
    String DEFAULT_RMI_OBJECT_NAME = "Counter";

    /**
     * Dient dem Zurücksetzen des Zählwertes auf 0.
     * 
     * @return Neuer Zählwert.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    int reset() throws RemoteException;

    /**
     * Dient dem Inkrementieren des Zählwertes um 1.
     * 
     * @return Neuer Zählwert.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    int increment() throws RemoteException;
}