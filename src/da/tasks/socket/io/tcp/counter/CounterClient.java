package da.tasks.socket.io.tcp.counter;

import java.io.IOException;

/**
 * TCP-Client, der verschiedene Befehle an einen {@link CounterServer} schickt.
 */
public class CounterClient implements AutoCloseable
{
    // Gegebenenfalls fehlen noch Attribute...
    private String serverAddress;
    private int serverPort;

    /**
     * Initialisiert eine neue CounterClient-Instanz mit den übergebenen
     * Argumenten.
     * 
     * @param serverAddress
     *            Adresse des Servers, auf dem die {@link CounterServer}
     *            -Anwendung läuft.
     * @param serverPort
     *            Port, auf dem die {@link CounterServer}-Anwendung auf
     *            eingehende Verbindungsanfragen lauscht.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Verbindungsaufbau mit dem
     *             {@link CounterServer} zu einem Ein-/Ausgabefehler gekommen
     *             ist.
     */
    public CounterClient(final String serverAddress, final int serverPort)
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Startet den Client. Es werden zuerst ein reset-Befehl und danach
     * <code>incrementCount</code> viele increment-Befehle gesendet. Nach jedem
     * versendeten Befehl wird die Antwort des Servers abgewartet und auf der
     * Konsole ausgegeben.
     * 
     * @param incrementCount
     *            Anzahl an zu versendenden increment-Befehlen.
     */
    public void runClient(final int incrementCount)
    {
        // Und hier muss auch noch Leben rein...
        try (final TCPSocket tcpSocket = new TCPSocket(this.serverAddress, this.serverPort))
        {
            tcpSocket.sendLine("reset");
            System.out.println(tcpSocket.receiveLine());

            for (int i = 0; i < incrementCount; i++)
            {
                tcpSocket.sendLine("increment");
                System.out.println(tcpSocket.receiveLine());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Methode aus der Schnittstelle {@link AutoCloseable} zum automatisierten
     * Schließen aller Ressourcen dieses {@link CounterClient}-Objektes.
     * 
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Schließen der Ressourcen zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    @Override
    public void close() throws IOException
    {
        // Je nach Attributen muss hier bestimmt auch noch was gemacht werden...
    }

    /**
     * Hauptprogramm. Als Kommandozeilenargumente müssen Adresse und Port des
     * {@link CounterServer}s übergeben werden sowie die Anzahl an zu
     * versendenden "increment"-Befehlen.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Verbindungsaufbau mit dem
     *             {@link CounterServer} zu einem Ein-/Ausgabefehler gekommen
     *             ist.
     */
    public static void main(final String[] args) throws IOException
    {
        final String svrAdr = args[0];
        final int svrPort = Integer.parseInt(args[1]);
        final int incrementCount = Integer.parseInt(args[2]);
        try (final CounterClient cClient = new CounterClient(svrAdr, svrPort))
        {
            cClient.runClient(incrementCount);
        }
    }
}