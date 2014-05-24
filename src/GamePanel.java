import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import da.tasks.socket.io.udp.UDPSocket;

/**
 * This is the main class. GamePanel derives from JPanel, and all the drawing is
 * performed in the paintComponent() method. It implements ActionListener to
 * handle Timer events, and implements KeyListener to handle keyboard events.
 * 
 * @author Andrew Lim
 * @version 0.1
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    // Game Constants
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 240;
    public static final int STATE_MENU = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_RIGHTWINS = 2;
    public static final int STATE_LEFTWINS = 3;
    public static final int STATE_PAUSED = 4;
    public static final Color GAME_BACKGROUND_COLOR = Color.BLACK;

    // Ball Constants
    public static int BALL_WIDTH = 4;
    public static int BALL_HEIGHT = 4;
    public static final Color BALL_COLOUR = Color.WHITE;

    // Paddle Constants
    public static final int PADDLE_WIDTH = 6;
    public static final int PADDLE_HEIGHT = 64;
    public static final int PADDLE_ARC = PADDLE_WIDTH / 2;
    public static final int PADDLE_START_Y = (GAME_HEIGHT - PADDLE_HEIGHT) / 2;
    public static final int LEFT_PADDLE_START_X = PADDLE_WIDTH;
    public static final int RIGHT_PADDLE_START_X = GAME_WIDTH - PADDLE_WIDTH * 2;
    public static final Color LEFT_PADDLE_COLOUR = Color.WHITE;
    public static final Color RIGHT_PADDLE_COLOUR = Color.WHITE;
    public static final int PADDLE_SPEED = 4;

    // Game Timer
    Timer timer = new Timer(20, this);

    // Game state - initial state is menu
    int gameState = STATE_MENU;

    // number of players
    int playerCount = 1;

    // Ball data
    public static final int BALL_SPEED = 5;
    int ballXi = BALL_SPEED, ballYi = BALL_SPEED;
    Rectangle ball = new Rectangle((GAME_WIDTH - BALL_WIDTH) / 2, (GAME_HEIGHT - BALL_HEIGHT) / 2, BALL_WIDTH, BALL_HEIGHT);

    // Left Paddle
    Rectangle leftPaddle = new Rectangle(LEFT_PADDLE_START_X, PADDLE_START_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

    // Right Paddle
    Rectangle rightPaddle = new Rectangle(RIGHT_PADDLE_START_X, PADDLE_START_Y, PADDLE_WIDTH, PADDLE_HEIGHT);

    // Game rectangle
    Rectangle gameRectangle = new Rectangle(0, 0, GAME_WIDTH, GAME_HEIGHT);

    // This array of boolean values saves the keyboard state.
    // If K is a key constant, keys[K] is true if K is being
    // pressed and false if K is not being pressed.
    boolean[] keys = new boolean[256];

    /**
     * Creates a GamePanel.
     */
    public GamePanel()
    {
        setBackground(GAME_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(GAME_WIDTH + 1, GAME_HEIGHT + 1));
    }

    /**
     * Resets the position of the ball.
     */
    void resetBall()
    {
        ball.x = (GAME_WIDTH - BALL_WIDTH) / 2;
        ball.y = (GAME_HEIGHT - BALL_HEIGHT) / 2;
        // ballXi = BALL_SPEED ;
        // ballYi = BALL_SPEED ;
    }

    /**
     * Resets the positions of the paddles.
     */
    void resetPaddles()
    {
        leftPaddle.x = LEFT_PADDLE_START_X;
        rightPaddle.x = RIGHT_PADDLE_START_X;
        leftPaddle.y = rightPaddle.y = PADDLE_START_Y;
    }

    public static void main(String[] args) throws SocketException
    {
        final int desiredServerPort = 5005;
        System.out.println("Server startet auf Port " + desiredServerPort);

        GamePanel gamePanel = new GamePanel();

        JFrame frame = new JFrame("p0ng v0.1 � Andrew Lim 2005");
        frame.setResizable(false);
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(gamePanel);
        gamePanel.timer.start();

        try (final DatagramSocket sock = new DatagramSocket(desiredServerPort))
        {
            gamePanel.runServer(sock);
        }
    }

    /**
     * Called each game cycle, this lets the computer move the left paddle. The
     * AI used by the computer is very simple. It just tries to align the left
     * paddle's vertical center with the ball's center.
     */
    void moveLeftPaddle()
    {
        int paddleCenterY = leftPaddle.y + PADDLE_HEIGHT / 2;
        int ballCenterY = ball.y + BALL_HEIGHT / 2;

        if (paddleCenterY > ballCenterY)
        {
            if (leftPaddle.y - PADDLE_SPEED >= 0)
            {
                leftPaddle.y -= PADDLE_SPEED;
            }
        }

        else if (paddleCenterY < ballCenterY)
        {
            if (leftPaddle.y + PADDLE_HEIGHT + PADDLE_SPEED <= GAME_HEIGHT)
            {
                leftPaddle.y += PADDLE_SPEED;
            }
        }
    }

    /**
     * Called every game cycle.
     */
    private void update()
    {
        if (gameState != STATE_PLAYING)
            return;

        // right paddle player's movement
        if (keys[KeyEvent.VK_UP])
            this.rightPaddle(true);
        if (keys[KeyEvent.VK_DOWN])
            this.rightPaddle(false);

        if (playerCount == 2)
        {
            // left paddle player's movement

            if (keys[KeyEvent.VK_A])
                if (leftPaddle.y - PADDLE_SPEED >= 0)
                    leftPaddle.y -= PADDLE_SPEED;
            if (keys[KeyEvent.VK_Z])
                if (leftPaddle.y + PADDLE_HEIGHT + PADDLE_SPEED <= GAME_HEIGHT)
                    leftPaddle.y += PADDLE_SPEED;
        }
        else
        {
            moveLeftPaddle();
        }

        // ball.x += ballXi;
        // ball.y += ballYi;

        if (ball.intersects(leftPaddle))
        {
            ball.x = leftPaddle.x + PADDLE_WIDTH;
            ballXi = -ballXi;
        }
        else if (ball.intersects(rightPaddle))
        {
            ball.x = rightPaddle.x - BALL_WIDTH;
            ballXi = -ballXi;
        }
        else if (ball.x < 0)
        {
            ball.x = 0;
            gameState = STATE_RIGHTWINS;
        }
        else if (ball.x > GAME_WIDTH - BALL_WIDTH)
        {
            ball.x = GAME_WIDTH - BALL_WIDTH;
            gameState = STATE_LEFTWINS;
        }
        else if (ball.y < 0)
        {
            ball.y = 0;
            ballYi = -ballYi;
        }
        else if (ball.y > GAME_HEIGHT - BALL_HEIGHT)
        {
            ball.y = GAME_HEIGHT - BALL_HEIGHT;
            ballYi = -ballYi;
        }
    }

    private void rightPaddle(boolean up)
    {
        if (up)
        {
            if (rightPaddle.y - PADDLE_SPEED >= 0)
                rightPaddle.y -= PADDLE_SPEED;
        }
        else
        {
            if (rightPaddle.y + PADDLE_HEIGHT + PADDLE_SPEED <= GAME_HEIGHT)
                rightPaddle.y += PADDLE_SPEED;
        }

    }

    /**
     * Paints the GamePanel. This method draws the game.
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g);

        // Ball
        g2D.setColor(BALL_COLOUR);
        g2D.fill(ball);

        // Left Paddle
        g2D.setColor(LEFT_PADDLE_COLOUR);
        g2D.fillRoundRect(leftPaddle.x, leftPaddle.y, leftPaddle.width, leftPaddle.height, PADDLE_ARC, PADDLE_ARC);

        // Right Paddle
        g2D.setColor(RIGHT_PADDLE_COLOUR);
        g2D.fillRoundRect(rightPaddle.x, rightPaddle.y, rightPaddle.width, rightPaddle.height, PADDLE_ARC, PADDLE_ARC);

        if (gameState != STATE_PLAYING)
        {
            String msg;

            switch (gameState) {
            case STATE_LEFTWINS:
                msg = "Left player wins! Press SPACE to Continue";
                break;

            case STATE_RIGHTWINS:
                msg = "Right player Wins! Press SPACE to Continue";
                break;

            case STATE_PAUSED:
                msg = "Game Paused. Press SPACE to Continue";
                break;

            default:
                msg = "New Game: How many players? (1/2)";
                break;
            }

            FontMetrics fm = g2D.getFontMetrics();

            int x = (GAME_WIDTH - fm.stringWidth(msg)) / 2;
            int y = (GAME_HEIGHT + fm.getAscent()) / 2 - fm.getDescent();

            g2D.setColor(Color.WHITE);
            g2D.drawString(msg, x, y);
        }
    }

    /**
     * Handles action events.
     */
    public void actionPerformed(ActionEvent e)
    {
        // Timer
        if (e.getSource() == timer)
        {
            update();
            repaint();
        }
    }

    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;

        if (gameState == STATE_MENU)
        {
            if (keys[KeyEvent.VK_1])
            {
                this.vk1Pressed();
            }
            else if (keys[KeyEvent.VK_2])
            {
                playerCount = 2;
                gameState = STATE_PLAYING;
                resetBall();
                resetPaddles();
            }
        }
        else if (gameState == STATE_RIGHTWINS || gameState == STATE_LEFTWINS)
        {
            if (keys[KeyEvent.VK_SPACE])
            {
                gameState = STATE_MENU;
            }
        }
        else if (gameState == STATE_PLAYING)
        {
            if (keys[KeyEvent.VK_P])
            {
                gameState = STATE_PAUSED;
            }
        }
        else if (gameState == STATE_PAUSED)
        {
            if (keys[KeyEvent.VK_SPACE])
            {
                gameState = STATE_PLAYING;
            }
        }
    }

    private void vk1Pressed()
    {
        playerCount = 1;
        gameState = STATE_PLAYING;
        resetBall();
        resetPaddles();
    }

    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e)
    {

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
        try (final UDPSocket udpSocket = new UDPSocket(dgSocket);)
        {
            while (true)
            {
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

        System.out.println(splitResult[0]);

        switch (splitResult[0]) {
        case "start":
            this.vk1Pressed();
            break;
        case "up":
            this.rightPaddle(true);
            break;
        case "down":
            this.rightPaddle(false);
            break;
        default:
            break;
        }

        String answer = null;

        return answer;
    }
}
