package da.tasks.socket.io.tcp.counter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Klasse zum einfachen Versenden und Empfangen von Zeichenketten über ein
 * TCP-Socket ({@link Socket}). Als Trennsymbol zwischen den Zeichenketten wird
 * dabei ein Zeilenumbruch verwendet.
 */
public class TCPSocket implements AutoCloseable
{
    /** Zeichensatz, der für das Versenden und Empfangen verwendet werden soll. */
    private static final Charset ENC_CHARSET = Charset.forName("UTF-8");

    /** {@link Socket} zum Versenden und Empfangen von Zeichenketten. */
    private final Socket socket;
    /** Zeichenstrom zum Empfangen von Zeichenketten über das {@link Socket}. */
    private final BufferedReader inStream;
    /** Zeichenstrom zum Senden von Zeichenketten über das {@link Socket}. */
    private final BufferedWriter outStream;

    /**
     * Initialisiert eine neue TCPSocket-Instanz mit den übergebenen Argumenten.
     * 
     * @param serverAddress
     *            IP-Adresse oder Hostname des Kommunikationspartners.
     * @param serverPort
     *            Portnummer des Kommunikationspartners.
     * @throws IOException
     *             Wird ausgelöst, wenn beim Verbindungsaufbau mit dem
     *             Kommunikationspartner ein Ein-/Ausgabefehler augetreten ist.
     */
    public TCPSocket(final String serverAddress, final int serverPort) throws IOException
    {
        this(new Socket(serverAddress, serverPort));
    }

    /**
     * Initialisiert eine neue TCPSocket-Instanz mit den übergebenen Argumenten.
     * 
     * @param socket
     *            {@link Socket}-Objekt, welches die Verbindung zum
     *            Kommunikationspartner repräsentiert.
     * @throws IOException
     *             Wird ausgelöst, wenn beim Verbindungsaufbau mit dem
     *             Kommunikationspartner ein Ein-/Ausgabefehler augetreten ist.
     */
    public TCPSocket(final Socket socket) throws IOException
    {
        this.socket = socket;

        this.inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), TCPSocket.ENC_CHARSET));
        this.outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), TCPSocket.ENC_CHARSET));
    }

    /**
     * Sendet die übergebene Zeichenkette an den Kommunikationspartner.
     * 
     * @param line
     *            Zu sendende Zeichenkette.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Senden der Zeichenkette zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     */
    public void sendLine(final String line) throws IOException
    {
        this.outStream.write(line);
        this.outStream.newLine();
        this.outStream.flush();
    }

    /**
     * Empfängt eine Zeichenkette vom Kommunikationspartner.
     * 
     * @return Empfangene Zeichenkette.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Empfangen der Zeichenkette zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    public String receiveLine() throws IOException
    {
        return this.inStream.readLine();
    }

    public int receiveLine(CharBuffer charBuffer) throws IOException
    {
        return this.inStream.read(charBuffer);
    }

    @Override
    public void close() throws IOException
    {
        this.socket.close();
    }
}
