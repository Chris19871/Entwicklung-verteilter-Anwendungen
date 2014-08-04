package da.tasks.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementierung von {@link ChatRoom}, die einen über RMI operierenden
 * Chat-Raum nach dem Entwurfsmuster Oberserver zur Verfügung stellt.
 */
public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Liste aller Benutzer dieses Chat-Raumes. */
    private final List<User> users;
    private ArrayList<String> latestMessages;

    /**
     * Initialisiert eine neue ChatRoomImp-Instanz.
     * 
     * @throws RemoteException
     *             Wird ausgelöst, wenn es beim Exportieren des RMI-Objektes,
     *             also beim Erzeugen des Skeletons, zu einem Fehler gekommen
     *             ist.
     */
    public ChatRoomImpl() throws RemoteException
    {
        this.users = new ArrayList<>();
        this.latestMessages = new ArrayList<String>();
    }

    @Override
    public synchronized void addUser(final User newUser) throws RemoteException, UserWithNameAlreadyAddedException
    {
        final String newUserName = newUser.getName();
        final Iterator<User> userIter = this.users.iterator();

        User currentUser = null;
        while (userIter.hasNext())
        {
            currentUser = userIter.next();

            try
            {
                if (currentUser.getName().equals(newUserName))
                {
                    throw new UserWithNameAlreadyAddedException();
                }
            }
            catch (final RemoteException re)
            {
                // Es scheint ein Kommunikationsproblem mit dem gerade
                // betrachteten Chatter zu geben. Es kann davon ausgegangen
                // werden, dass die Verbindung zu diesem Chatter gestört ist.
                // Daher wird er aus der Liste der Benutzer dieses
                // Chat-Raumes entfernt.
                userIter.remove();
            }
        }

        this.users.add(newUser);
        this.send(newUser.getName(), " ... hat sich angemeldet");

        // Die letzten Nachrichten an den neuen User schicken.
        for (int i = 0; i < this.latestMessages.size(); ++i)
        {
            this.sendToUser(newUser, this.latestMessages.get(i));
        }
    }

    @Override
    public synchronized void removeUser(final User user) throws RemoteException
    {
        this.users.remove(user);
        this.send(user.getName(), " ... hat sich abgemeldet");
    }

    @Override
    public synchronized void sendMessage(final String name, final String message) throws RemoteException
    {
        // final String messageToPrint = name + ": " + message;
        // final Iterator<User> userIter = this.users.iterator();
        //
        // User currentUser = null;
        // while (userIter.hasNext())
        // {
        // currentUser = userIter.next();
        //
        // try
        // {
        // currentUser.printMessage(messageToPrint);
        // }
        // catch (final RemoteException re)
        // {
        // // Es scheint ein Kommunikationsproblem mit dem gerade
        // // betrachteten Chatter zu geben. Es kann davon ausgegangen
        // // werden, dass die Verbindung zu diesem Chatter gestört ist.
        // // Daher wird er aus der Liste der Benutzer dieses
        // // Chat-Raumes entfernt.
        // userIter.remove();
        // }
        // }
        this.send(name, message);
        this.latestMessages.add(name + ": " + message);
    }

    private void send(final String name, final String message)
    {
        final String messageToPrint = name + ": " + message;
        final Iterator<User> userIter = this.users.iterator();

        User currentUser = null;
        while (userIter.hasNext())
        {
            currentUser = userIter.next();

            try
            {
                this.sendToUser(currentUser, messageToPrint);
            }
            catch (final RemoteException re)
            {
                // Es scheint ein Kommunikationsproblem mit dem gerade
                // betrachteten Chatter zu geben. Es kann davon ausgegangen
                // werden, dass die Verbindung zu diesem Chatter gestört ist.
                // Daher wird er aus der Liste der Benutzer dieses
                // Chat-Raumes entfernt.
                userIter.remove();
            }
        }
    }

    private void sendToUser(User user, String msg) throws RemoteException
    {
        user.printMessage(msg);
    }
}