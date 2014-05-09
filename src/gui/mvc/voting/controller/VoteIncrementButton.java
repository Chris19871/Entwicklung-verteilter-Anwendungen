package gui.mvc.voting.controller;

import gui.mvc.voting.model.PollModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Controller für ein {@link IVotingModel} in Form eines {@link JButton}s,
 * welcher die Stimmen eines Kandidaten bei einem Klick um eins erhöht.
 * 
 * @author Patrick Fries
 */
public class VoteIncrementButton extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;
    /**
     * Modell, das den Kandidaten enthält, dessen Stimmen inkrementiert werden
     * sollen.
     */
    private final PollModel pollModel;
    /** Indexposition des betreffenden Kandidaten aus dem Modell. */
    private final int candidateIndex;

    /**
     * Initialisiert ein neues VoteIncrementButton-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param votingModel
     *            Modell, das den Kandidaten enthält, dessen Stimmen
     *            inkrementiert werden sollen.
     * @param candidateIndex
     *            Indexposition des betreffenden Kandidaten aus dem Modell.
     */
    public VoteIncrementButton(final PollModel pollModel, final int candidateIndex)
    {
        super("Erhöhen");

        this.pollModel = pollModel;
        this.candidateIndex = candidateIndex;

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        this.pollModel.incrementVotes(this.candidateIndex);
        // this.votingModel.incrementCandidateVotes(this.candidateIndex);
    }
}
