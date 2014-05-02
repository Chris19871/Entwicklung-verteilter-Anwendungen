package da.tasks.socket.io.udp.relay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.DatagramSocket;
import java.net.InetAddress;

import da.tasks.socket.io.udp.UDPSocket;

/** UDP-Relay zur bedingten Weiterleitung von UPD-Datagrammen. */
public class UDPRelay
{
    /**
     * Methode zum Starten des UDP-Relays.
     * 
     * @param inSocket
     *            {@link DatagramSocket}, über das Datagramme zum Weiterleiten
     *            empfangen werden sollen.
     * @param addressToRelayTo
     *            Adresse des Rechners, an den die über inSocket empfagenen
     *            Datagramme weitergeleitet werden sollen.
     * @param portToRelayTo
     *            Portnummer, an die die über inSocket empfagenen Datagramme
     *            weitergeleitet werden sollen.
     * @param controlReader
     *            {@link Reader} von dem gelesen werden soll, ob ein über
     *            inSocket empfangenes Datagramm weitergeleitet oder verworfen
     *            werden soll.
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der UDP-Kommunikation zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     */
    public static void startUDPRelay(final DatagramSocket inSocket, final InetAddress addressToRelayTo, final int portToRelayTo, final Reader controlReader) throws IOException
    {
        BufferedReader bufferRead = new BufferedReader(controlReader);
        UDPSocket udpSocketOutServer = new UDPSocket();

        try (final UDPSocket udpSocket = new UDPSocket(inSocket))
        {
            while (true)
            {
                // Blockiert bis Empfang
                final String request = udpSocket.receive();

                System.out.println("Soll die Nachricht \"" + request + "\" weitergeleitet werden? (j/n)");
                String s = bufferRead.readLine();

                if (s.equals("j"))
                {
                    udpSocketOutServer.send(request, addressToRelayTo, portToRelayTo);

                    System.out.println("Soll die Antwort \"" + request + "\" weitergeleitet werden? (j/n)");
                    s = bufferRead.readLine();
                    if (s.equals("j"))
                    {
                        String result = udpSocketOutServer.receive();
                        udpSocket.reply(result);
                    }
                }
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Hauptprogramm. Als Kommandozeilenargumente sollen die Portnummer für den
     * Empfang von Datagrammen sowie Adresse und Port für die Weiterleitung der
     * empfangenen Datagramme angegeben werden.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der UDP-Kommunikation zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     */
    // UDPRealy 2345 localhost 1234
    public static void main(final String[] args) throws IOException
    {
        final int inPort = Integer.parseInt(args[0]);
        final InetAddress addressToRelayTo = InetAddress.getByName(args[1]);
        final int portToRelayTo = Integer.parseInt(args[2]);
        final InputStreamReader controlReader = new InputStreamReader(System.in);
        try (final DatagramSocket dgSocket = new DatagramSocket(inPort))
        {
            UDPRelay.startUDPRelay(dgSocket, addressToRelayTo, portToRelayTo, controlReader);
        }
    }
}