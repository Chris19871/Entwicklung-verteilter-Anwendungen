package da.tasks.socket.io.udp.counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import da.tasks.socket.io.udp.UDPSocket;

public class CounterClientAlternative
{
    public static void main(final String[] args) throws IOException
    {
        try (final UDPSocket udpSocket = new UDPSocket())
        {
            final InetAddress serverAddress = InetAddress.getByName(args[0]);
            final int desiredServerPort = Integer.parseInt(args[1]);

            udpSocket.send("reset", serverAddress, desiredServerPort);

            try
            {
                final InputStreamReader controlReader = new InputStreamReader(System.in);
                BufferedReader bufferRead = new BufferedReader(controlReader);

                String answer = null;

                while (true)
                {
                    answer = udpSocket.receive();
                    System.out.println("answer: " + answer);
                    String s = bufferRead.readLine();
                    udpSocket.send(s, serverAddress, desiredServerPort);
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