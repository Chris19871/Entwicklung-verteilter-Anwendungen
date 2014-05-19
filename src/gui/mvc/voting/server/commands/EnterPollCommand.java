package gui.mvc.voting.server.commands;

import gui.mvc.voting.VotingFrame;
import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;

public class EnterPollCommand implements Command
{
    private AllPolls allPolls;

    public EnterPollCommand(AllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        IPoll poll = this.allPolls.getPoll(commands[1]);

        if (poll == null)
        {
            return "Poll nicht gefunden!";
        }

        VotingFrame frame = new VotingFrame(poll);
        frame.setTitle(poll.getQuestion());
        frame.setSize(400, 400);
        frame.setVisible(true);
        return null;
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }

}
