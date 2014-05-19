package gui.mvc.voting.controller;

import gui.mvc.voting.model.PollModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * Controller für ein {@link IVotingModel} in Form eines {@link JTextField}s,
 * welcher neue Kandidaten in das Modell einfügen kann.
 * 
 * @author Patrick Fries
 */
public class AddCandidateTextField extends JTextField implements ActionListener
{
    private static final long serialVersionUID = 1L;
    /** Modell, in das neue Kandidat eingefügt werden sollen. */
    private final PollModel pollModel;

    /**
     * Initialisiert ein neues NewCandidateTextField-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param votingModel
     *            Modell, in das neue Kandidat eingefügt werden sollen.
     */
    public AddCandidateTextField(final PollModel pollModel)
    {
        this.pollModel = pollModel;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        // this.pollModel.getPollData().addAnswerInPollData(this.getText());
        this.pollModel.addAnswer(this.getText());
        // this.votingModel.addCandidate(this.getText());
        this.setText("");
    }
}
