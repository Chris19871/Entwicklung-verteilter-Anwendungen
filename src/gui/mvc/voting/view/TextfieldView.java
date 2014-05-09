package gui.mvc.voting.view;

import gui.mvc.voting.model.IPollChangeListener;
import gui.mvc.voting.model.PollData;
import gui.mvc.voting.model.PollModel;

import javax.swing.JTextField;

/**
 * View für ein {@link PollModel} in Form eines {@link JTextField}s, welches die
 * Stimmen eines durch das Modell repräsentierten Kandidaten anzeigt.
 * 
 * @author Patrick Fries
 */
public class TextfieldView extends JTextField implements IPollChangeListener
{
    /** Modell, aus dem die Stimmen eines Kandidaten angezeigt werden. */
    private final PollModel pollModel;
    /** Indexposition des betreffenden Kandidaten aus dem Modell. */
    private final int candidateIndex;

    /**
     * Initialisiert ein neues TextfieldView-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param pollModel
     *            Modell, aus dem die Stimmen eines Kandidaten angezeigt werden.
     * @param candidateIndex
     *            Indexposition des betreffenden Kandidaten aus dem Modell.
     */
    public TextfieldView(final PollModel pollModel, final int candidateIndex)
    {
        this.pollModel = pollModel;
        this.candidateIndex = candidateIndex;

        this.pollModel.addPollChangeListener(this);
        this.voteChanged(this.pollModel.getPollData());
    }

    @Override
    public void voteChanged(PollData data)
    {
        this.setText(String.valueOf(data.getVoteCountToQuestion(this.candidateIndex)));
    }

    @Override
    public void answerAdded(PollData data)
    {
        // TODO Auto-generated method stub
    }
}