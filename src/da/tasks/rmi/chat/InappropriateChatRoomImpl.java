package da.tasks.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung von {@link ChatRoom}, die einen über RMI operierenden
 * Chat-Raum nach dem Entwurfsmuster Oberserver zur Verfügung stellt.
 */
public class InappropriateChatRoomImpl extends UnicastRemoteObject implements ChatRoom
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Liste aller Benutzer dieses Chat-Raumes. */
    private final List<User> users;

    /**
     * Initialisiert eine neue ChatRoomImp-Instanz.
     * 
     * @throws RemoteException
     *             Wird ausgelöst, wenn es beim Exportieren des RMI-Objektes,
     *             also beim Erzeugen des Skeletons, zu einem Fehler gekommen
     *             ist.
     */
    public InappropriateChatRoomImpl() throws RemoteException
    {
        this.users = new ArrayList<>();
    }

    @Override
    public synchronized void addUser(final User newUser) throws RemoteException, UserWithNameAlreadyAddedException
    {
        final String newUserName = newUser.getName();
        for (final User currentUser : this.users)
        {
            if (currentUser.getName().equals(newUserName))
            {
                throw new UserWithNameAlreadyAddedException();
            }
        }

        this.users.add(newUser);
    }

    @Override
    public synchronized void removeUser(final User user)
    {
        this.users.remove(user);
    }

    @Override
    public synchronized void sendMessage(final String name, final String message) throws RemoteException
    {
        final String messageToPrint = name + ": " + message;
        for (final User currentUser : this.users)
        {
            currentUser.printMessage(messageToPrint);
        }
    }
}
