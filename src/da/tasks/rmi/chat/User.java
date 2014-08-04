package da.tasks.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI-Schnittstelle für ein RMI-Objekt, welches einen Chatter repräsentiert,
 * der über ein {@link ChatRoom}-Objekte mit anderen {@link User}n kommunizieren
 * will.
 */
public interface User extends Remote
{
    /**
     * Muss den Namen des Chatters zurückliefern.
     * 
     * @return Name, der innerhalb des ChatRooms verwendet werden soll.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    String getName() throws RemoteException;

    /**
     * Wird vom {@link ChatRoom} zur Zustellung von neuen Nachrichten
     * aufgerufen.
     * 
     * @param message
     *            Neue Nachricht.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    void printMessage(String message) throws RemoteException;
}
