package gui.mvc.voting;

import gui.mvc.voting.controller.AddQuestionTextField;
import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.view.AllPollsView;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class SelectFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initialisiert ein neues VotingFrame-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param question
     *            Frage, über die abgestimmt werden soll.
     * @param pollModel
     *            Modell, welches die zur Verfügung stehenden Kandidaten nebst
     *            ihren Stimmen verwaltet.
     */
    public SelectFrame(final AllPolls allPolls)
    {
        super("Auswahl");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        final JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(new AllPollsView(allPolls));
        centerPanel.add(new CreateQuestionPanel(allPolls), BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);

        this.setSize(900, 600);
        this.setVisible(true);
    }

    /**
     * Hauptprogramm.
     * 
     * @param args
     *            Kommandozeilenargumente.
     */
    public static void main(final String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        final AllPolls allPolls = new AllPolls();

        new SelectFrame(allPolls);
    }

    /**
     * Panel, mit dem eine neue Umfrage angelegt werden kann.
     * 
     * @author Stefan Probst
     */
    private static class CreateQuestionPanel extends TitledPanel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Initialisiert ein neues QuestionPanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param question
         *            Anzuzeigende Frage.
         */
        public CreateQuestionPanel(AllPolls allPolls)
        {
            super("Neue Umfrage anlegen");
            this.setLayout(new GridLayout(1, 3));

            JTextField identifier = new JTextField();
            JTextField question = new JTextField();
            JButton button = new JButton("Anlegen");

            button.addActionListener(new AddQuestionTextField(allPolls, identifier, question));

            this.add(identifier);
            this.add(question);
            this.add(button);
        }
    }

    /**
     * Basisklasse für alle Panels, die auf dem {@link VotingFrame} angezeigt
     * werden.
     * 
     * @author Patrick Fries
     */
    private static class TitledPanel extends JPanel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Initialisiert ein neues TitledPanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param title
         *            Titel, der in einem Rahmen um das Panel angezeigt werden
         *            soll.
         */
        public TitledPanel(final String title)
        {
            this.setBorder(BorderFactory.createTitledBorder(title));
        }
    }
}
