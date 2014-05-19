package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;

public class IncrementVotesCommand implements Command
{

    private AllPolls allPolls;
    private IPoll activePoll;

    public IncrementVotesCommand(AllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        if (this.activePoll != null && this.allPolls.getPoll(commands[1]) != null)
        {
            this.activePoll.incrementVotes(Integer.parseInt(commands[1]));
        }

        return "Wert wurde inkrementiert";
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        this.activePoll = activePoll;
    }
}