package da.tasks.socket.io.udp.counter;

import java.io.IOException;
import java.net.DatagramSocket;

import da.tasks.socket.io.udp.UDPSocket;

/**
 * UDP-Server, der einen int-Zähler verwaltet und die Befehle "increment" und
 * "reset" versteht. Auf jeden Befehl wird der Zählerstand nach dem Befehl
 * geantwortet.
 */

public class CounterServer
{
    /** Verwalteter int-Zähler. */
    private static int counter;

    /**
     * Nimmt Anfragen vom übergebenen {@link DatagramSocket} entgegen,
     * verarbeitet diese und antwortet dem anfragenden Client. Gegebenenfalls
     * auftretende Ausnahmen sollen gefangen und so behandelt werden, dass sie
     * nicht zu einem Absturz des Servers führen.
     * 
     * @param dgSocket
     *            {@link DatagramSocket}, über das die Kommunikation stattfinden
     *            soll.
     */
    public static void runServer(final DatagramSocket dgSocket)
    {
        try (final UDPSocket udpSocket = new UDPSocket(dgSocket))
        {
            while (true)
            {
                // Blockiert bis Empfang
                final String request = udpSocket.receive();
                final String answer = CounterServer.processRequestAndReturnAnswer(request);
                udpSocket.reply(answer);
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static String processRequestAndReturnAnswer(final String request)
    {
        if (request.equals("increment"))
        {
            CounterServer.counter++;
        }
        else if (request.equals("decrement"))
        {
            CounterServer.counter--;
        }
        else if (request.startsWith("set "))
        {
            String tmp = cutFront(request, " ", 1);

            try
            {
                int count = Integer.parseInt(tmp);
                CounterServer.counter = count;
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        else if (request.equals("reset"))
        {
            CounterServer.counter = 0;
        }

        return String.valueOf(CounterServer.counter);
    }

    private static String cutFront(String txt, String teil, int number)
    {
        for (int i = 0; i < number; i++)
        {
            txt = txt.substring(txt.indexOf(teil) + 1, txt.length());
        }
        return txt;
    }

    public static void main(String[] args) throws IOException
    {
        final int desiredServerPort = Integer.parseInt(args[0]);
        try (final DatagramSocket sock = new DatagramSocket(desiredServerPort))
        {
            CounterServer.runServer(sock);
        }
    }
}