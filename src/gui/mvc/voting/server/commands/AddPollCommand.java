package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;

public class AddPollCommand implements Command
{

    private AllPolls allPolls;

    public AddPollCommand(AllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        String result;

        if (this.allPolls.getPoll(commands[1]) != null)
        {
            result = "Poll konnte nicht angelegt werden!";
        }
        else
        {
            this.allPolls.addPoll(commands[1], commands[2]);
            result = "Poll wurde erfolgreich angelegt.";
        }

        return result;
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }
}
