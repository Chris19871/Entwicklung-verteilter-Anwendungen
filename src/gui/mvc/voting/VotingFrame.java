package gui.mvc.voting;

import gui.mvc.voting.controller.AddCandidateTextField;
import gui.mvc.voting.controller.VoteIncrementButton;
import gui.mvc.voting.controller.VoteSetActionListener;
import gui.mvc.voting.model.IPoll;
import gui.mvc.voting.model.IPollChangeListener;
import gui.mvc.voting.model.PollData;
import gui.mvc.voting.model.PollModel;
import gui.mvc.voting.view.BarView;
import gui.mvc.voting.view.CakeView;
import gui.mvc.voting.view.LabelView;
import gui.mvc.voting.view.TextfieldView;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Hauptklasse zur Aufgabe "pollModel".
 * 
 * @author Patrick Fries
 */
public class VotingFrame extends JFrame
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
    public VotingFrame(IPoll poll)
    {
        super("Meinungsumfrage");

        final String[] candidates = new String[0];
        // final PollModel pollModel = new pollModel(candidates);
        final PollModel pollModel = new PollModel(poll.getPollData().getQuestion(), candidates);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        final JPanel questionAndAddCandidatePanel = new JPanel(new GridLayout(1, 0, 10, 10));
        questionAndAddCandidatePanel.add(new QuestionPanel(poll.getQuestion()));
        questionAndAddCandidatePanel.add(new AddCandidatePanel(pollModel));

        final JPanel incrementAndSetPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        incrementAndSetPanel.add(new IncrementPanel(pollModel));
        incrementAndSetPanel.add(new SetPanel(pollModel));

        final JPanel cakeAndBarPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        cakeAndBarPanel.add(new CakePanel(pollModel));
        cakeAndBarPanel.add(new BarPanel(pollModel));

        this.setLayout(new BorderLayout());
        this.add(questionAndAddCandidatePanel, BorderLayout.NORTH);

        final JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(incrementAndSetPanel, BorderLayout.NORTH);
        centerPanel.add(cakeAndBarPanel, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        this.setSize(900, 600);
        this.setVisible(true);
    }

    //
    //

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

    /**
     * Panel, auf dem eine Frage angezeigt wird.
     * 
     * @author Patrick Fries
     */
    private static class QuestionPanel extends TitledPanel
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
        public QuestionPanel(final String question)
        {
            super("Frage");
            this.setLayout(new BorderLayout());
            this.add(new JLabel(question, SwingConstants.CENTER));
        }
    }

    /**
     * Panel, auf dem ein {@link AddCandidateTextField}-Controller nebst einem
     * Beschreibungstext angezeigt wrid.
     * 
     * @author Patrick Fries
     */
    private static class AddCandidatePanel extends TitledPanel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Initialisiert ein neues NewCandidatePanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param pollModel
         *            Modell, zu dem sich über den angezeigten
         *            {@link AddCandidateTextField} -Controller Kandidaten
         *            hinzufügen lassen sollen.
         */
        public AddCandidatePanel(final PollModel pollModel)
        {
            super("Neue Antwortmöglichkeit hinzufügen");

            this.setLayout(new GridLayout(1, 0, 10, 10));
            this.add(new JLabel("Zusätzliche Antwortmöglichkeit:", SwingConstants.RIGHT));
            this.add(new AddCandidateTextField(pollModel));
        }
    }

    /**
     * Panel, welches zu jedem in einem {@link PollModel} enthaltenen Kandidaten
     * eine Zeile bestehend aus {@link LabelView} und
     * {@link VoteIncrementButton} anzeigt.
     * 
     * @author Patrick Fries
     */
    private static class IncrementPanel extends TitledPanel implements IPollChangeListener
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * Modell, auf das die {@link LabelView}s und
         * {@link VoteIncrementButton}s zugreifen.
         */
        private final PollModel pollModel;

        /**
         * Initialisiert ein neues IncrementPanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param pollModel
         *            Modell, auf das die {@link LabelView}s und
         *            {@link VoteIncrementButton}s zugreifen sollen.
         */
        public IncrementPanel(final PollModel pollModel)
        {
            super("Erhöhen");

            this.pollModel = pollModel;
            this.pollModel.addPollChangeListener(this);
            // this.pollModel.addpollModelListener(this);

            this.setLayout(new GridLayout(0, 2, 10, 6));

            for (int i = 0; i < this.pollModel.getPollData().getAnswersCount(); i++)
            {
                this.addComponentsForCandidateWithIndex(i);
            }
        }

        /**
         * Hilfsmethode, welche die grafischen Elemente für den Kandidaten mit
         * dem übergebenen Index zum Panel hinzufügen.
         * 
         * @param index
         *            Siehe Methodenbeschreibung.
         */
        private void addComponentsForCandidateWithIndex(final int index)
        {
            this.add(new LabelView(this.pollModel, index));
            this.add(new VoteIncrementButton(this.pollModel, index));
        }

        @Override
        public void voteChanged(PollData data)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void answerAdded(PollData data)
        {
            this.addComponentsForCandidateWithIndex(this.pollModel.getPollData().getAnswersCount() - 1);

            // Neue Komponenten wurden zum Layout hinzu gefügt während das
            // Layout bereits angezeigt
            // wurde. Da Swing ein einem solchen Fall nicht selbst das Layout
            // gerade biegt, muss man
            // das hier explizit anstoßen.
            this.revalidate();
        }
    }

    /**
     * Panel, welches zu jedem in einem {@link PollModel} enthaltenen Kandidaten
     * eine Zeile bestehend aus einem {@link JLabel} mit dem Namen des
     * Kandidaten sowie einer {@link TextfieldView} anzeigt.
     * 
     * @author Patrick Fries
     */
    private static class SetPanel extends TitledPanel implements IPollChangeListener
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * Modell, auf das die {@link LabelView}s und
         * {@link VoteIncrementButton}s zugreifen.
         */
        private final PollModel pollModel;

        /**
         * Initialisiert ein neues IncrementPanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param pollModel
         *            Modell, auf das die {@link LabelView}s und
         *            {@link VoteIncrementButton}s zugreifen sollen.
         */
        public SetPanel(final PollModel pollModel)
        {
            super("Setzen");

            this.pollModel = pollModel;
            this.pollModel.addPollChangeListener(this);

            this.setLayout(new GridLayout(0, 2, 10, 6));
            for (int i = 0; i < this.pollModel.getPollData().getNumberOfCandidates(); i++)
            {
                this.addComponentsForCandidateWithIndex(i);
            }
        }

        /**
         * Hilfsmethode, welche die grafischen Elemente für den Kandidaten mit
         * dem übergebenen Index zum Panel hinzufügen.
         * 
         * @param index
         *            Siehe Methodenbeschreibung.
         */
        private void addComponentsForCandidateWithIndex(final int index)
        {
            this.add(new JLabel(this.pollModel.getPollData().getAnswer(index), SwingConstants.RIGHT));

            final TextfieldView view = new TextfieldView(this.pollModel, index);
            view.addActionListener(new VoteSetActionListener(this.pollModel, index));
            this.add(view);
        }

        @Override
        public void voteChanged(PollData data)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void answerAdded(PollData data)
        {
            this.addComponentsForCandidateWithIndex(this.pollModel.getPollData().getNumberOfCandidates() - 1);

            // Neue Komponenten wurden zum Layout hinzu gefügt während das
            // Layout bereits angezeigt
            // wurde. Da Swing ein einem solchen Fall nicht selbst das Layout
            // gerade biegt, muss man
            // das hier explizit anstoßen.
            this.revalidate();
        }
    }

    /**
     * Panel, welches die Stimmenverteilung eines {@link PollModel} mit einer
     * {@link CakeView} darstellt.
     * 
     * @author Patrick Fries
     */
    private static class CakePanel extends TitledPanel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Initialisiert ein neues CakePanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param pollModel
         *            Modell, dessen Inhalte dargestellt werden sollen.
         */
        public CakePanel(final PollModel pollModel)
        {
            super("Tortendiagramm");
            this.setLayout(new BorderLayout());
            this.add(new CakeView(pollModel));
        }
    }

    /**
     * Panel, welches zu jedem in einem {@link PollModel} enthaltenen Kandidaten
     * eine {@link BarView} anzeigt.
     * 
     * @author Patrick Fries
     */
    private static class BarPanel extends TitledPanel implements IPollChangeListener
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        /**
         * Modell, auf das die {@link LabelView}s und
         * {@link VoteIncrementButton}s zugreifen.
         */
        private final PollModel pollModel;

        /**
         * Initialisiert ein neues IncrementPanel-Objekt mit den übergebenen
         * Argumenten.
         * 
         * @param pollModel
         *            Modell, auf das die {@link LabelView}s und
         *            {@link VoteIncrementButton}s zugreifen sollen.
         */
        public BarPanel(final PollModel pollModel)
        {
            super("Balkendiagramm");

            this.pollModel = pollModel;
            this.pollModel.addPollChangeListener(this);

            this.setLayout(new GridLayout(0, 1));
            for (int i = 0; i < this.pollModel.getPollData().getAnswersCount(); i++)
            {
                this.addComponentsForCandidateWithIndex(i);
            }
        }

        /**
         * Hilfsmethode, welche die grafischen Elemente für den Kandidaten mit
         * dem übergebenen Index zum Panel hinzufügen.
         * 
         * @param index
         *            Siehe Methodenbeschreibung.
         */
        private void addComponentsForCandidateWithIndex(final int index)
        {
            this.add(new BarView(this.pollModel, index));
        }

        @Override
        public void voteChanged(PollData data)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void answerAdded(PollData data)
        {
            this.addComponentsForCandidateWithIndex(data.getNumberOfCandidates() - 1);

            // Neue Komponenten wurden zum Layout hinzu gefügt während das
            // Layout bereits angezeigt
            // wurde. Da Swing ein einem solchen Fall nicht selbst das Layout
            // gerade biegt, muss man
            // das hier explizit anstoßen.
            this.revalidate();
        }
    }
}
