package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;

public class DeletePollCommand implements Command
{
    private AllPolls allPolls;

    public DeletePollCommand(AllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        this.allPolls.removePoll(commands[1]);

        return "Poll wurde entfernt";
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }

}
