package gui.mvc.voting.view;

import gui.mvc.voting.VotingFrame;
import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IAllPollsChangeListener;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class AllPollsView extends JPanel implements IAllPollsChangeListener, ActionListener
{
    private static final long serialVersionUID = 1L;
    private final AllPolls allPolls;
    private JPanel panel;

    public AllPollsView(final AllPolls allPolls)
    {
        super();
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 22, 358, 266);
        this.add(scrollPane);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        panel.setLayout(new GridLayout(8, 1, 0, 2));

        this.allPolls = allPolls;
        this.allPolls.addAllPollsChangeListener(this);
    }

    @Override
    public void pollAdded(String id, String question)
    {
        JPanel rowPanel = new JPanel();
        rowPanel.setName(id);
        rowPanel.setLayout(new GridLayout(1, 3));

        rowPanel.add(new JLabel(question));

        JButton display = new JButton("Anzeigen");
        display.setName(id);
        display.addActionListener(this);
        rowPanel.add(display);

        JButton delete = new JButton("Löschen");
        delete.setName(id);
        delete.setActionCommand(id);
        delete.addActionListener(this);
        rowPanel.add(delete);

        this.panel.add(rowPanel);
        this.panel.updateUI();
        System.out.println("Poll added");
    }

    @Override
    public void pollRemoved(String id)
    {
        this.removeRow(id);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Anzeigen"))
        {
            JButton o = (JButton) e.getSource();
            String name = o.getName();

            VotingFrame frame = new VotingFrame(this.allPolls.getPoll(name));
            frame.setTitle("hey");
            frame.setSize(400, 400);
            frame.setVisible(true);
        }
        else
        {
            this.removeRow(e.getActionCommand());
        }
    }

    private void removeRow(String id)
    {
        for (int i = 0; i < this.panel.getComponents().length; ++i)
        {
            if (this.panel.getComponent(i).getName() == id)
            {
                this.panel.remove(this.panel.getComponent(i));
            }
        }

        this.panel.updateUI();
    }
}