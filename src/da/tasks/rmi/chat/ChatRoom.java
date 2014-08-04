package da.tasks.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI-Schnittstelle für ein RMI-Objekt, welches einen Chat-Raum repräsentiert,
 * über den {@link User}-Objekte miteinaner chatten können.
 */
public interface ChatRoom extends Remote
{
    /**
     * Konstante für den Standardnamen eines RemoteAppender-Objektes in einer
     * RMI-Registry.
     */
    String DEFAULT_RMI_OBJECT_NAME = "ChatRoom";
    String DEFAULT_RMI_OBJECT_NAME_2 = "ChatRoom2";

    /**
     * Meldet den übergebenen {@link User} an, so dass ihm in Zukunft die über
     * diesen {@link ChatRoom} ausgetauschten Nachrichten über
     * {@link User#printMessage(String)} zugestellt werden.
     * 
     * @param user
     *            Anzumeldender Chatter.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     * @throws UserWithNameAlreadyAddedException
     *             Wird ausgelöst, falls in diesem {@link ChatRoom} bereits ein
     *             anderer {@link User} angemeldet ist, der den gleichen Namen
     *             verwendet.
     */
    void addUser(User user) throws RemoteException, UserWithNameAlreadyAddedException;

    /**
     * Meldet den übergebenen {@link User} ab, so dass ihm in Zukunft keine über
     * diesen {@link ChatRoom} ausgetauschten Nachrichten mehr über
     * {@link User#printMessage(String)} zugestellt werden.
     * 
     * @param user
     *            Abzumeldender Chatter.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    void removeUser(User user) throws RemoteException;

    /**
     * Sendet die übergebene Nachricht mit dem übergebenen Namen an alle in
     * diesem {@link ChatRoom} angemeldeten {@link User}.
     * 
     * @param name
     *            Name, von dem die Nachricht kommt.
     * @param message
     *            Nachricht, die an alle angemeldeten {@link User} übermittelt
     *            werden soll.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    void sendMessage(String name, String message) throws RemoteException;

    //
    //

    /**
     * Ausnahme, die von der Methode {@link ChatRoom#addUser(User)} immer dann
     * ausgelöst wird, wenn ein {@link User} mit einem Namen angemeldet werden
     * soll, der bereits von einem anderen {@link User} des {@link ChatRoom}s
     * verwendet wird.
     */
    public class UserWithNameAlreadyAddedException extends Exception
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        // Keine erweiternde Implementierung notwendig.
    }
}
