package gui.mvc.voting.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class VotingTCPClient
{

    public static void main(final String[] args) throws IOException
    {
        final InetAddress server = InetAddress.getByName(args[0]); // IP-Adresse
                                                                   // od.
                                                                   // DNS-Name
        try (final Socket socket = new Socket(server, 1250))
        { // Verbindungsaufbau mit Server
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));

            final InputStreamReader controlReader = new InputStreamReader(System.in);
            BufferedReader bufferRead = new BufferedReader(controlReader);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));

            while (true)
            {
                String s = bufferRead.readLine();
                writer.write(s);

                writer.newLine(); // Warum hier extra noch Zeilenumbruch?
                writer.flush(); // Ist dieser flush()-Aufruf wirklich notwendig?

                String answer = reader.readLine(); // Blockiert bis ganze
                                                   // Zeile empfangen
                                                   // wurde
                System.out.println(answer);
            }
        } // Socket (= Verbindung) wird geschlossen, Zeichenströme nicht! Ist
          // das okay so?
    }
}