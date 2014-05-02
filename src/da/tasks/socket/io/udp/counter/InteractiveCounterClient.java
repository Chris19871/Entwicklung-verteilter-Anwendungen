package da.tasks.socket.io.udp.counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import da.tasks.socket.io.udp.UDPSocket;

//java da.tasks.socket.io.udp.counter.CounterClient localhost 1234 1000
public class InteractiveCounterClient
{
    public static void main(final String[] args) throws IOException
    {
        try (final UDPSocket udpSocket = new UDPSocket())
        {
            // udpSocket.setTimeout(3000); // Höchstens 3 Sekunden auf
            // Server-Antwort warten

            final InetAddress serverAddress = InetAddress.getByName(args[0]);
            final int desiredServerPort = Integer.parseInt(args[1]);

            udpSocket.send("reset", serverAddress, desiredServerPort);
            // System.out.println("Antwort: " + udpSocket.receive());

            udpSocket.send("set 10", serverAddress, desiredServerPort);

            try
            {
                final InputStreamReader controlReader = new InputStreamReader(System.in);
                BufferedReader bufferRead = new BufferedReader(controlReader);

                while (true)
                {
                    String s = bufferRead.readLine();
                    udpSocket.send(s, serverAddress, desiredServerPort);
                    System.out.println("Antwort: " + udpSocket.receive());
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (final SocketTimeoutException ste)
        {
            System.out.println("ACHTUNG: Vermutlich Nachrichtenverlust aufgetreten!");
        }
    }
}