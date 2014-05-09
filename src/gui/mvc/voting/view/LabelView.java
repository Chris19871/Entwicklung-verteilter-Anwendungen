package gui.mvc.voting.view;

import gui.mvc.voting.model.IPollChangeListener;
import gui.mvc.voting.model.PollData;
import gui.mvc.voting.model.PollModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * View für ein {@link IVotingModel} in Form eines {@link JLabel}s, welches die
 * Daten eines durch das Modell repräsentierten Kandidaten in der Form
 * "[Name]: [Stimmen] von [Gesamtstimmen] ([Prozentuale Stimmen]%)" anzeigt
 * (Siehe {@link LabelView#FORMAT_STRING}).
 * 
 * @author Patrick Fries
 */
public class LabelView extends JLabel implements IPollChangeListener
{
    /** Formatvorlage für die angezeigte Zeichenkette. */
    public static final String FORMAT_STRING = "%s: %d von %d (%d%s)";

    /** Modell, aus dem die Daten eines Kandidaten angezeigt werden. */
    private final PollModel pollModel;
    /** Indexposition des betreffenden Kandidaten aus dem Modell. */
    private final int candidateIndex;

    /**
     * Initialisiert ein neues LabelView-Objekt mit den übergebenen Argumenten.
     * 
     * @param votingModel
     *            Modell, aus dem die Daten eines Kandidaten angezeigt werden.
     * @param candidateIndex
     *            Indexposition des betreffenden Kandidaten aus dem Modell.
     */
    public LabelView(final PollModel pollModel, final int candidateIndex)
    {
        this.pollModel = pollModel;
        this.candidateIndex = candidateIndex;

        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.pollModel.addPollChangeListener(this);
        // this.votingModel.addVotingModelListener(this);
        this.voteChanged(this.pollModel.getPollData());
    }

    @Override
    public void voteChanged(PollData data)
    {
        final String newText = String.format(LabelView.FORMAT_STRING, this.pollModel.getPollData().getAnswer(this.candidateIndex),
                this.pollModel.getPollData().getVoteCountToQuestion(this.candidateIndex), this.pollModel.getPollData().getTotalVotes(),
                this.pollModel.getPollData().getAnswerPercentage(this.candidateIndex), '%');

        // TODO this.votingModel.getCandidatePercentage(this.candidateIndex)
        // anstatt 19
        this.setText(newText);
    }

    @Override
    public void answerAdded(PollData data)
    {
        // TODO Auto-generated method stub

    }
}
