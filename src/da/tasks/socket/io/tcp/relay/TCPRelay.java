package da.tasks.socket.io.tcp.relay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/** TCP-Relay zur Weiterleitung der Daten einer TCP-Verbindung. */
public class TCPRelay
{
    /** {@link ServerSocket} zur Annahme von Clientverbindungen. */
    private final ServerSocket sSocket;
    /** Adresse und Port, an die weitergeleitet wird. */
    private final InetSocketAddress addressToRelayTo;

    public static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * Initialisiert eine neue TCPRelay-Instanz mit den übergebenen Argumenten.
     * 
     * @param sSocket
     *            {@link ServerSocket}, das zur Annahme von Clientverbindungen
     *            genutzt werden soll.
     * @param addressToRelayTo
     *            Adresse und Port, an die weitergeleitet werden soll.
     */
    public TCPRelay(final ServerSocket sSocket, final InetSocketAddress addressToRelayTo)
    {
        this.sSocket = sSocket;
        this.addressToRelayTo = addressToRelayTo;
    }

    /**
     * Startet das TCP-Relay.
     * 
     * @throws IOException
     */
    public void startRelaying() throws IOException
    {
        // Kobra, übernehmen Sie...
        while (true)
        {
            final Socket socket = this.sSocket.accept();
            final Socket serverSocket = new Socket(this.addressToRelayTo.getHostName(), this.addressToRelayTo.getPort());

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            InputStream serverInputStream = serverSocket.getInputStream();
            OutputStream outputServerStream = serverSocket.getOutputStream();

            byte[] buf = null;
            buf = new byte[serverSocket.getReceiveBufferSize()];
            int r = 1;

            // TODO Irgendwie in Threads auslagern.
            // Client -> Server
            buf = new byte[serverSocket.getReceiveBufferSize()];
            do
            {
                try
                {
                    r = inputStream.read(buf);
                    if (r > 0)
                    {
                        outputServerStream.write(buf, 0, r);
                    }
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                    r = -1;
                }
            } while (r > 0);

            // Server -> Client
            buf = new byte[socket.getReceiveBufferSize()];

            while (r > 0)
            {
                r = serverInputStream.read(buf);
                if (r > 0)
                {
                    outputStream.write(buf, 0, r);
                }
            }
        }
    }

    /**
     * Hauptprogramm. Als Kommandozeilenargumente sollen die Portnummer für die
     * Annahme von Verbindungen sowie Adresse und Port für die Weiterleitung
     * angegeben werden.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Öffnen des {@link ServerSocket}s
     *             zur Annahme von Verbindungen zu einem Ein-/Ausgabefehler
     *             gekommen ist.
     */
    public static void main(final String[] args) throws IOException
    {
        final int inPort = Integer.parseInt(args[0]);
        final InetAddress addressToRelayTo = InetAddress.getByName(args[1]);
        final int portToRelayTo = Integer.parseInt(args[2]);
        try (final ServerSocket sSock = new ServerSocket(inPort))
        {
            final TCPRelay relay = new TCPRelay(sSock, new InetSocketAddress(addressToRelayTo, portToRelayTo));
            relay.startRelaying();
        }
    }
}