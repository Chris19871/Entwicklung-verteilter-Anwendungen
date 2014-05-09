package gui.mvc.voting.controller;

import gui.mvc.voting.model.PollModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.JTextComponent;

/**
 * Controller für ein {@link PollModel} in Form eines {@link ActionListener}s,
 * welcher an einer {@link JTextComponent} angemeldet werden kann und bei
 * Benachrichtigung die Stimmen eines Kandidaten auf dem innerhalb der
 * {@link JTextComponent} dargestellten Wert setzt.
 * 
 * @author Patrick Fries
 */
public class VoteSetActionListener implements ActionListener
{
    /**
     * Modell, das den Kandidaten enthält, dessen Stimmen gesetzt werden sollen.
     */
    private final PollModel pollModel;
    /** Indexposition des betreffenden Kandidaten aus dem Modell. */
    private final int candidateIndex;

    /**
     * Initialisiert ein neues VoteSetActionListener-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param pollModel
     *            Modell, das den Kandidaten enthält, dessen Stimmen gesetzt
     *            werden sollen.
     * @param candidateIndex
     *            Indexposition des betreffenden Kandidaten aus dem Modell.
     */
    public VoteSetActionListener(final PollModel pollModel, final int candidateIndex)
    {
        this.pollModel = pollModel;
        this.candidateIndex = candidateIndex;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final Object source = e.getSource();
        if (source instanceof JTextComponent)
        {
            final String votesString = ((JTextComponent) source).getText();
            final int votes = Integer.valueOf(votesString);
            this.pollModel.setVotes(this.candidateIndex, votes);
        }
        else
        {
            throw new RuntimeException("Dieser ActionListener kann nur mit " + "einer JTextComponent zusammenarbeiten");
        }
    }
}
