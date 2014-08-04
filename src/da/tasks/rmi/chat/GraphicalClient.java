package da.tasks.rmi.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import da.tasks.rmi.RMIWorker;
import da.tasks.rmi.chat.ChatRoom.UserWithNameAlreadyAddedException;

/**
 * Implementierung der Schnittestelle {@link User} zum grafischen Austauschen
 * von Meldungen mit anderen {@link User}n in einem {@link ChatRoom}.
 */
public class GraphicalClient extends JFrame implements User
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Name des Chatters. */
    private final String name;
    /** {@link ChatRoom}, in dem der Chatter angemeldet ist. */
    private final ChatRoom chatRoom;
    /** Textfeld zum Verfassen und Versenden von eigenen Meldungen. */
    private final JTextField messageField;
    /** Textbereich zur Anzeige der ausgetauschten Meldungen. */
    private final JTextArea chatArea;

    /**
     * Initialisiert eine neue GraphicalClient-Instanz mit den übergebenen
     * Argumenten.
     * 
     * @param name
     *            Gewünschter Name.
     * @param chatRoom
     *            {@link ChatRoom}, über den kommuniziert werden soll.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es beim Exportieren des RMI-Objektes,
     *             also beim Erzeugen des Skeletons, zu einem Fehler gekommen
     *             ist.
     */
    public GraphicalClient(final String name, final ChatRoom chatRoom) throws RemoteException
    {
        UnicastRemoteObject.exportObject(this, 0); // Erzeugung eines Skeletons
                                                   // für dieses Objekt

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(final WindowEvent e)
            {
                GraphicalClient.this.leaveChatRoomAndCloseUi();
            }
        });

        this.name = name;
        this.chatRoom = chatRoom;
        this.messageField = new JTextField();
        this.chatArea = new JTextArea();
        final JButton exitButton = new JButton("Chat beenden");

        this.chatArea.setWrapStyleWord(true);
        this.chatArea.setEditable(false);

        exitButton.addActionListener(e -> this.leaveChatRoomAndCloseUi());
        this.messageField.addActionListener(e -> this.sendMessage());

        this.setLayout(new BorderLayout(4, 4));
        this.add(this.messageField, BorderLayout.NORTH);
        this.add(new JScrollPane(this.chatArea), BorderLayout.CENTER);
        this.add(exitButton, BorderLayout.SOUTH);
        this.setSize(200, 450);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void printMessage(final String message)
    {
        EventQueue.invokeLater(() -> this.chatArea.append(message + "\n"));
    }

    /**
     * Wird aufgerufen, sobald der Benutzer im {@link JTextField} zur Eingabe
     * einer neuen Nachricht die Enter-Taste betätigt. Als konsequenz wird die
     * neue Nachricht an den Server geschickt (
     * {@link ChatRoom#sendMessage(String, String)}). Während der Zustellung der
     * neuen Nachricht ist es dabei nicht möglich, eine weitere Nachricht
     * abzuschicken.
     * 
     * @param ev
     *            Ereignisobjekt, welches den Druck der Enter-Taste
     *            repräsentiert.
     */
    private void sendMessage()
    {
        this.messageField.setEnabled(false);

        // Hier wird zuerst der Text aus dem TextField ausgelesen, in einer
        // String-Variable gespeichert und dann wird im
        // Lambda-Ausruck direkt danach auf die String-Variable zugegriffen.
        // Muss man das so machen, oder könnte man im
        // nachfolgenden Lambda-Ausruck nicht einfach
        // this.messageField.getText() verwenden?
        final String messageToSend = this.messageField.getText();

        RMIWorker.doAsync(() -> {
            this.chatRoom.sendMessage(this.name, messageToSend);
            return null;

        }, dummy -> {
            this.messageField.setText("");
            this.messageField.setEnabled(true);
            this.messageField.requestFocus();
        });
    }

    /**
     * Methode zum Betreten des {@link ChatRoom}s in {@link #chatRoom} und
     * anschließendem Einblenden der grafischen Benutzeroberfläche.
     */
    public void enterChatRoomAndShowUi()
    {
        RMIWorker.doAsync(() -> {
            try
            {
                this.chatRoom.addUser(this);
            }
            catch (final UserWithNameAlreadyAddedException e)
            {
                return false;
            }
            return true;

        }, successfullyEntered -> {
            if (successfullyEntered)
            {
                this.setVisible(true);

            }
            else
            {
                JOptionPane.showMessageDialog(null, "Ein Benutzer mit diesem Namen ist bereits anwesend!");
                System.exit(0);
            }
        });
    }

    /**
     * Methoden zum Verlassen des {@link ChatRoom}s in {@link #chatRoom},
     * anschließendem Ausblenden der grafischen Benutzeroberfläche und Verlassen
     * des gesamten Programmes.
     */
    public void leaveChatRoomAndCloseUi()
    {
        RMIWorker.doAsync(() -> {
            this.chatRoom.removeUser(this);
            return null;

        }, dummy -> {
            this.setVisible(false);
            System.exit(0);
        });
    }

    //
    //

    /**
     * Hauptprogramm. Als Kommandozeilenargumente müssen die Adresse des
     * Rechners angegeben werden, auf dem die RMI-Registry läuft, sowie der
     * Name, der für den Chat verwendet werden soll.
     * 
     * @param args
     *            Kommandozeilenargumente.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der Kommunikation mit der
     *             RMI-Registry zu einem Ein-/Ausgabefehler gekommen ist.
     * @throws NotBoundException
     *             Wird ausgelöst, wenn bei der RMI-Registry auf dem Rechner mit
     *             der übergebenen Adresse kein Objekt mit dem Namen
     *             {@link ChatRoom#DEFAULT_RMI_OBJECT_NAME} registriert ist.
     */
    public static void main(final String[] args) throws RemoteException, NotBoundException
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        final Registry localReg = LocateRegistry.getRegistry(args[0]);
        final ChatRoom room = (ChatRoom) localReg.lookup(ChatRoom.DEFAULT_RMI_OBJECT_NAME);
        final String name = args[0];

        final GraphicalClient graphClient = new GraphicalClient(name, room);
        // final GraphicalClient graphClient = new GraphicalClient(name, room2);
        graphClient.enterChatRoomAndShowUi();
    }
}
