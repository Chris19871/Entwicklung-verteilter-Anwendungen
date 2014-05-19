package gui.mvc.voting.server;

import gui.mvc.voting.SelectFrame;
import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;
import gui.mvc.voting.server.commands.AddAnswerCommand;
import gui.mvc.voting.server.commands.AddPollCommand;
import gui.mvc.voting.server.commands.Command;
import gui.mvc.voting.server.commands.DeletePollCommand;
import gui.mvc.voting.server.commands.EnterPollCommand;
import gui.mvc.voting.server.commands.IncrementVotesCommand;
import gui.mvc.voting.server.commands.LeavePollCommand;
import gui.mvc.voting.server.commands.ShowPollsCommand;
import gui.mvc.voting.server.commands.ShowVotesCommand;
import gui.mvc.voting.server.commands.VersionCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import da.tasks.socket.io.udp.UDPSocket;

public class VotingServer
{
    private Map<String, Command> map;
    private AllPolls allPolls;
    private IPoll activePoll = null;

    public VotingServer()
    {
        this.startFrame();

        this.map = new HashMap<String, Command>();
        this.map.put("-version", new VersionCommand());
        this.map.put("addPoll", new AddPollCommand(this.allPolls));
        this.map.put("deletePoll", new DeletePollCommand(this.allPolls));
        this.map.put("showPolls", new ShowPollsCommand(this.allPolls));

        this.map.put("enterPoll", new EnterPollCommand(this.allPolls));
        this.map.put("leavePoll", new LeavePollCommand());

        this.map.put("incrementVotes", new IncrementVotesCommand(this.allPolls));
        this.map.put("addAnswer", new AddAnswerCommand(this.allPolls));
        this.map.put("showVotes", new ShowVotesCommand(this.allPolls));
    }

    /**
     * Zeigt das Fenster mit der Auswahl.
     */
    private void startFrame()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        this.allPolls = new AllPolls();

        new SelectFrame(this.allPolls);
    }

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
    public void runServer(final DatagramSocket dgSocket)
    {
        try (final UDPSocket udpSocket = new UDPSocket(dgSocket); final ServerSocket serverSocket = new ServerSocket(1250))
        {
            while (true)
            {
                // TCP-Server
                try (final Socket socketToClient = serverSocket.accept())
                { // Verbindung akzeptieren
                    this.processConnection(socketToClient);
                }

                // UDP-Server
                // Blockiert bis Empfang
                final String request = udpSocket.receive();

                String answer = this.processCommand(request);
                if (answer != null)
                {
                    udpSocket.reply(answer);
                }
                else
                {
                    udpSocket.reply("Command '" + request + "' ist nicht bekannt!");
                }
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String processCommand(String request)
    {
        String[] splitResult = request.split(" ");
        Command command = map.get(splitResult[0]);

        String answer = null;
        if (command != null)
        {
            if (splitResult[0].equals("enterPoll") || splitResult[0].equals("addAnswer"))
            {
                if (splitResult.length > 1)
                {
                    System.out.println("Poll als aktiv gesetzt.");
                    this.activePoll = this.allPolls.getPoll(splitResult[1]);
                }
            }

            if (splitResult[0].equals("leavePoll"))
            {
                System.out.println("Poll als inaktiv gesetzt.");
                this.activePoll = null;
            }

            command.setActivePoll(this.activePoll);
            answer = command.execute(splitResult);
        }

        return answer;
    }

    private void processConnection(final Socket socket) throws IOException
    {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

        String request = null;
        while ((request = reader.readLine()) != null)
        { // Wann liefert readLine() den Wert null?

            writer.write("Selber " + this.processCommand(request));
            writer.newLine();
            writer.flush(); // Wäre flush() nach Schleife auch Ok?
        }
    }

    public static void main(String[] args) throws SocketException
    {
        final int desiredServerPort = Integer.parseInt(args[0]);
        System.out.println("Server startet auf Port " + desiredServerPort);

        try (final DatagramSocket sock = new DatagramSocket(desiredServerPort))
        {
            VotingServer votingServer = new VotingServer();
            votingServer.runServer(sock);
        }
    }
}
