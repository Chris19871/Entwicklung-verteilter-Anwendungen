package gui.mvc.voting.controller;

import gui.mvc.voting.model.AllPolls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class AddQuestionTextField extends JTextField implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private AllPolls allPolls;
    private JTextField identifier, question;

    public AddQuestionTextField(AllPolls allPolls, JTextField identifier, JTextField question)
    {
        this.allPolls = allPolls;
        this.identifier = identifier;
        this.question = question;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.allPolls.addPoll(this.identifier.getText(), this.question.getText());
        this.setText("");
    }
}
