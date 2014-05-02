package da.tasks.socket.io.udp.counter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import da.tasks.socket.io.udp.UDPSocket;

// java da.tasks.socket.io.udp.counter.CounterClient localhost 1234 1000
public class CounterClient
{
    public static void main(final String[] args) throws IOException
    {
        try (final UDPSocket udpSocket = new UDPSocket())
        {
            udpSocket.setTimeout(3000); // Höchstens 3 Sekunden auf
                                        // Server-Antwort warten

            final InetAddress serverAddress = InetAddress.getByName(args[0]);
            final int desiredServerPort = Integer.parseInt(args[1]);
            final int messageCount = Integer.parseInt(args[2]);

            udpSocket.send("reset", serverAddress, desiredServerPort);
            System.out.println("Antwort: " + udpSocket.receive());

            for (int i = 0; i < messageCount; i++)
            {
                udpSocket.send("increment", serverAddress, desiredServerPort);
                System.out.println("Antwort: " + udpSocket.receive());
            }
        }
        catch (final SocketTimeoutException ste)
        {
            System.out.println("ACHTUNG: Vermutlich Nachrichtenverlust aufgetreten!");
        }
    }
}