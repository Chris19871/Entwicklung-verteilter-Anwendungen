package da.tasks.socket.io.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Klasse zum einfachen Versenden und Empfangen von Zeichenketten über ein
 * UDP-Socket ({@link DatagramSocket}). Ebenfalls kann nach dem Empfang einer
 * Zeichenkette eine Antwort an den Sender verschickt werden.
 */
public class UDPSocket implements AutoCloseable
{
    /** Puffergröße in Anzahl Bytes, die beim Empfangen verwendet wird. */
    private static final int RECEIVE_BUFFER_SIZE = 200;
    /** Zeichensatz, der für das Versenden und Empfangen verwendet werden soll. */
    private static final Charset ENC_CHARSET = Charset.forName("UTF-8");

    /** {@link DatagramSocket} zum Versenden und Empfangen von Zeichenketten. */
    protected final DatagramSocket socket;

    /** Sender-Adresse des zuletzt empfangenen Datagramms (für Antwort). */
    private InetAddress replyAddress;
    /** Sender-Port des zuletzt empfangenen Datagramms (für Antwort). */
    private int replyPort;

    /**
     * Initialisiert eine neue UDPSocket-Instanz, wobei zum Empfangen und
     * Versenden ein UDP-Socket mit einem zufälligen Port verwendet wird.
     * 
     * @throws SocketException
     *             Wird ausgelöst, wenn beim Erzeugen des UDP-Sockets ein
     *             Problem aufgetreten ist.
     */
    public UDPSocket() throws SocketException
    {
        this(new DatagramSocket());
    }

    /**
     * Initialisiert eine neue UDPSocket-Instanz, wobei zum Empfangen und
     * Versenden ein UDP-Socket mit dem übergebenen Port verwendet wird.
     * 
     * @param port
     *            Port, auf dem das UDP-Socket geöffnet werden soll.
     * @throws SocketException
     *             Wird ausgelöst, wenn beim Erzeugen des UDP-Sockets ein
     *             Problem aufgetreten ist.
     */
    public UDPSocket(final int port) throws SocketException
    {
        this(new DatagramSocket(port));
    }

    /**
     * Initialisiert eine neue UDPSocket-Instanz mit den übergebenen Argumenten.
     * 
     * @param socket
     *            UDP-Socket, das zum Empfangen und Versenden verwendet werden
     *            soll.
     */
    public UDPSocket(final DatagramSocket socket)
    {
        this.socket = socket;
    }

    //
    //

    /**
     * Sendet die übergebene Zeichenkette als UDP-Datagramm unter Verwendung des
     * in {@link UDPSocket#ENC_CHARSET} angegebenen Zeichensatzes an den durch
     * Adresse und Port spezifizierten Empfänger.
     * 
     * @param s
     *            Zu versendende Zeichenkette.
     * @param destAddress
     *            Adresse des Empfängers.
     * @param destPort
     *            Port des Empfängers.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Versenden des UDP-Datagrammes zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    public void send(final String s, final InetAddress destAddress, final int destPort) throws IOException
    {
        final byte[] outBuffer = s.getBytes(UDPSocket.ENC_CHARSET);
        final DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, destAddress, destPort);
        this.socket.send(outPacket);
    }

    /**
     * Wartet auf das Eintreffen eines Datagrammes über das UDP-Socket, wandelt
     * die Inhalte (maximal {@link UDPSocket#RECEIVE_BUFFER_SIZE} viele Bytes)
     * unter Verwendung des in {@link UDPSocket#ENC_CHARSET} angegebenen
     * Zeichensatzes in eine Zeichenkette und liefert diese zurück. Weiterhin
     * werden Adresse und Port des Senders zwischengespeichert, so dass über die
     * Methode {@link UDPSocket#reply(String)} direkt an diesen geantwortet
     * werden kann.
     * 
     * @return Zeichenkette, die aus dem UDP-Datagramm extrahiert wurde.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Empfangen des UDP-Datagrammes zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    public String receive() throws IOException
    {
        final byte[] inBuffer = new byte[UDPSocket.RECEIVE_BUFFER_SIZE];
        final DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);

        this.socket.receive(inPacket);
        this.replyAddress = inPacket.getAddress();
        this.replyPort = inPacket.getPort();
        return new String(inBuffer, 0, inPacket.getLength(), UDPSocket.ENC_CHARSET);
    }

    /**
     * Sendet die übergebene Zeichenkette als UDP-Datagramm unter Verwendung des
     * in {@link UDPSocket#ENC_CHARSET} angegebenen Zeichensatzes an die Adresse
     * und den Port des Senders, dessen UDP-Datagramm als letztes durch einen
     * Aufruf von {@link UDPSocket#receive(int)} empfangen wurde. Sollte es
     * keinen solchen Sender geben wird mit einer {@link RuntimeException}
     * reagiert.
     * 
     * @param s
     *            Zu versendende Zeichenkette.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Versenden des UDP-Datagrammes zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    public void reply(final String s) throws IOException
    {
        if (this.replyAddress == null)
        {
            throw new RuntimeException("Niemand da zum Antworten!");
        }

        this.send(s, this.replyAddress, this.replyPort);
    }

    /**
     * Setzt die Zeitspanne, die bei einem Aufruf von
     * {@link UDPSocket#receive(int)} maximal auf das Eintreffen eines
     * UDP-Datagramms gewartet wird.
     * 
     * @param timeout
     *            Zeitspanne in Millisekunden.
     * @throws SocketException
     *             Wird ausgelöst, wenn es beim Setzen der Zeitspanne zu einem
     *             Fehler gekommen ist.
     */
    public void setTimeout(final int timeout) throws SocketException
    {
        this.socket.setSoTimeout(timeout);
    }

    /**
     * Liefert den Sender, von dem als letztes eine Zeichenkette über
     * {@link UDPSocket#receive(int)} empfangen wurde in der Form "Adresse:Port"
     * zurück.
     * 
     * @return Siehe Methodenbeschreibung.
     */
    public String getSenderOfLastReceivedString()
    {
        if (this.replyAddress == null)
        {
            throw new RuntimeException("Niemand da zum Antworten!");
        }

        return this.replyAddress.toString() + ":" + this.replyPort;
    }

    public boolean isClosed()
    {
        return this.socket.isClosed();
    }

    @Override
    public void close()
    {
        this.socket.close();
    }
}