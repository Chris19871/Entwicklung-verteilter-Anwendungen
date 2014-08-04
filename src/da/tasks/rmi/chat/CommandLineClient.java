package da.tasks.rmi.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import da.tasks.rmi.chat.ChatRoom.UserWithNameAlreadyAddedException;

/**
 * Implementierung der Schnittestelle {@link User} zum Austauschen von Meldungen
 * mit anderen {@link User}n in einem {@link ChatRoom}.
 */
public class CommandLineClient extends UnicastRemoteObject implements User
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Name des Chatters. */
    private final String name;
    /** {@link ChatRoom}, in dem der Chatter angemeldet ist. */
    private final ChatRoom chatRoom;

    /**
     * Initialisiert eine neue CommandLineClient-Instanz mit den übergebenen
     * Argumenten.
     * 
     * @param name
     *            Gewünschter Name.
     * @param chatRoom
     *            {@link ChatRoom}, über den kommuniziert werden soll.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es beim Exportieren des RMI-Objektes,
     *             also beim Erzeugen des Skeletons, zu einem Fehler gekommen
     *             ist oder das Anmelden beim übergebenen {@link ChatRoom}
     *             fehlgeschlagen ist.
     * @throws UserWithNameAlreadyAddedException
     *             Wird ausgelöst, wenn ein bereits beim übergebenen
     *             {@link ChatRoom} angemeldeter {@link User} den gleichen Namen
     *             verwendet.
     */
    public CommandLineClient(final String name, final ChatRoom chatRoom) throws RemoteException, UserWithNameAlreadyAddedException
    {
        this.name = name;
        this.chatRoom = chatRoom;
        this.chatRoom.addUser(this);
    }

    @Override
    public String getName() throws RemoteException
    {
        return this.name;
    }

    @Override
    public void printMessage(final String message) throws RemoteException
    {
        System.out.println(message);
    }

    /**
     * Hilfsmethode, die Zeilenweise von der Konsole liest und diese Zeilen an
     * den {@link ChatRoom} im Attribut {@link #chatRoom} weitergibt.
     */
    public void sendConsoleMessagesToChatRoom()
    {
        try (final BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in)))
        {
            String readLine = null;
            while ((readLine = consoleIn.readLine()) != null)
            {
                if (readLine.equals("exit"))
                {
                    break;
                }

                this.chatRoom.sendMessage(this.name, readLine);
            }
        }
        catch (final RemoteException re)
        {
            System.err.println("Kommunikationsfehler mit Chat-Raum: " + re.getMessage());
        }
        catch (final IOException ioe)
        {
            System.err.println("Ein-/Ausgabefehler auf Kommandozeile: " + ioe.getMessage());
        }
    }

    //
    //

    /**
     * Hauptprogramm. Als Kommandozeilenargumente müssen die Adresse des
     * Rechners angegeben werden, auf dem die RMI-Registry läuft, sowie der
     * Name, der für den Chat verwendet werden soll.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der Kommunikation mit der
     *             RMI-Registry zu einem Ein-/Ausgabefehler gekommen ist.
     * @throws NotBoundException
     *             Wird ausgelöst, wenn bei der RMI-Registry auf dem Rechner mit
     *             der übergebenen Adresse kein Objekt mit dem Namen
     *             {@link ChatRoom#DEFAULT_RMI_OBJECT_NAME} registriert ist.
     */
    public static void main(final String[] args) throws RemoteException, NotBoundException
    {
        final Registry localReg = LocateRegistry.getRegistry(args[0]);
        final ChatRoom room = (ChatRoom) localReg.lookup(ChatRoom.DEFAULT_RMI_OBJECT_NAME);
        final String name = args[1];

        try
        {
            final CommandLineClient cmdLineClient = new CommandLineClient(name, room);
            cmdLineClient.sendConsoleMessagesToChatRoom();
            room.removeUser(cmdLineClient);

        }
        catch (final UserWithNameAlreadyAddedException e)
        {
            System.err.println("Ein Benutzer mit diesem Namen ist bereits anwesend!");
        } finally
        {
            // Warum ist hier noch System.exit(0) notwendig? Die main()-Metode
            // wird doch sowieso
            // gleich verlassen und damit das
            // Programm beendet.
            System.exit(0);
        }
    }
}
