package da.tasks.socket.io.udp.counter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import da.tasks.socket.io.udp.UDPSocket;

public class RepeatCounterClient
{
    private UDPSocket udpSocket1;

    public RepeatCounterClient(InetAddress serverAddress, int desiredServerPort, int messageCount) throws IOException
    {
        try (UDPSocket udpSocket = new UDPSocket())
        {
            udpSocket1 = udpSocket;

            udpSocket1.setTimeout(1000); // Höchstens 3 Sekunden auf
            // Server-Antwort warten

            if (send("reset", serverAddress, desiredServerPort))
            {
                udpSocket.close();
                udpSocket1.close();
            }
            else
            {
                for (int i = 0; i < messageCount; i++)
                {
                    if (send("increment", serverAddress, desiredServerPort))
                    {
                        udpSocket.close();
                        udpSocket1.close();
                        break;
                    }
                }
            }
        }
        catch (final SocketTimeoutException ste)
        {
            System.out.println("ACHTUNG: Vermutlich Nachrichtenverlust aufgetreten!");
        }
    }

    public static void main(final String[] args) throws IOException
    {
        final InetAddress serverAddress = InetAddress.getByName(args[0]);
        final int desiredServerPort = Integer.parseInt(args[1]);
        final int messageCount = Integer.parseInt(args[2]);

        new RepeatCounterClient(serverAddress, desiredServerPort, messageCount);
    }

    public boolean send(String message, InetAddress serverAddress, int port) throws IOException
    {
        boolean continueSending = true;
        int counter = 0;

        udpSocket1.send(message, serverAddress, port);

        while (continueSending && counter <= 2)
        {
            // send to server omitted
            counter++;
            try
            {
                System.out.println("Antwort: " + udpSocket1.receive());
                continueSending = false; // a packet has been received : stop
                                         // sending
            }
            catch (SocketTimeoutException e)
            {
                if (counter == 3)
                {
                    return true;
                }

                // no response received after 1 second. continue sending
                udpSocket1.send(message, serverAddress, port);
            }
        }

        return false;
    }
}