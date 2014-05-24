package da.tasks.socket.io.tcp.counter;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * TCP-Server, der einen int-Zähler verwaltet und die Befehle "increment" und
 * "reset" versteht. Auf jeden Befehl wird der Zählerstand nach dem Befehl
 * geantwortet.
 */
public class CounterServer implements AutoCloseable
{
    /** {@link ServerSocket}, auf dem Clientverbindungen angenommen werden. */
    private final ServerSocket sSocket;
    /** Aktueller Zählstand. */
    private int counter;

    /**
     * Initialisiert eine neue CounterServer-Instanz mit den übergebenen
     * Argumenten.
     * 
     * @param port
     *            Port, auf dem ein {@link ServerSocket} zur Annahme von
     *            Clientverbindungen geöffnet werden soll.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim erstellen des
     *             {@link ServerSocket}s zu einem Ein-/Ausgabefehler gekommen
     *             ist.
     */
    public CounterServer(final int port) throws IOException
    {
        this(new ServerSocket(port));
    }

    /**
     * Initialisiert eine neue CounterServer-Instanz mit den übergebenen
     * Argumenten.
     * 
     * @param sSocket
     *            {@link ServerSocket}, auf dem Clientverbindungen angenommen
     *            werden sollen.
     */
    public CounterServer(final ServerSocket sSocket)
    {
        this.sSocket = sSocket;
    }

    /**
     * Nimmt wiederholt Anfragen von Clients entgegen, verarbeitet diese und
     * antwortet dem anfragenden Client.
     * 
     * @throws IOException
     */
    public void runServer()
    {
        while (true)
        {
            try (final TCPSocket tcpSocket = new TCPSocket(this.sSocket.accept()))
            {
                String request = null;
                while ((request = tcpSocket.receiveLine()) != null)
                {
                    String[] split = request.split(" ");

                    if (split[0].equals("increment"))
                    {
                        this.counter++;
                    }

                    if (split[0].equals("decrement"))
                    {
                        this.counter--;
                    }

                    if (split[0].equals("set"))
                    {
                        if (split[1] != null)
                        {
                            try
                            {
                                this.counter = Integer.parseInt(split[1]);
                            }
                            catch (NumberFormatException e)
                            {

                            }
                        }
                    }

                    if (split[0].equals("reset"))
                    {
                        this.counter = 0;
                    }

                    tcpSocket.sendLine("" + this.counter);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode aus der Schnittstelle {@link AutoCloseable} zum automatisierten
     * Schließen aller Ressourcen dieses {@link CounterServer}-Objektes.
     * 
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Schließen der Ressourcen zu
     *             einem Ein-/Ausgabefehler gekommen ist.
     */
    @Override
    public void close() throws IOException
    {
        this.sSocket.close();
    }

    /**
     * Hauptprogramm. Als Kommandozeilenargument muss die Portnummer des Ports
     * angegeben werden, auf dem der Server sein {@link ServerSocket} öffnet.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der Erzeugung des
     *             {@link ServerSocket}s zu einem Ein-/Ausgabefehler gekommen
     *             ist.
     */
    public static void main(final String[] args) throws IOException
    {
        final int desiredServerPort = Integer.parseInt(args[0]);
        try (final CounterServer cServ = new CounterServer(desiredServerPort))
        {
            cServ.runServer();
        }
    }
}