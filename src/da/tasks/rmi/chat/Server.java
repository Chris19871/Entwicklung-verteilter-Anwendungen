package da.tasks.rmi.chat;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server-Klasse, die zuerst eine lokale RMI-Registry startet und dann ein
 * Objekt vom Typ {@link ChatRoomImpl} bei dieser unter dem Namen
 * {@link ChatRoom#DEFAULT_RMI_OBJECT_NAME} registriert.
 */
public class Server
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
     *             {@link ChatRoom#DEFAULT_RMI_OBJECT_NAME} bereits ein anderes
     *             RMI-Objekt bei der RMI-Registry registriert ist.
     */
    public static void main(final String[] args) throws IOException, AlreadyBoundException
    {
        final ChatRoom room = new ChatRoomImpl();
        final ChatRoom room2 = new ChatRoomImpl();
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        localReg.bind(ChatRoom.DEFAULT_RMI_OBJECT_NAME, room);
        localReg.bind(ChatRoom.DEFAULT_RMI_OBJECT_NAME_2, room2);
        System.out.println("Chat-Server bereit");
    }
}
