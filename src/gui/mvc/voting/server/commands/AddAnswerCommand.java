package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.IAllPolls;
import gui.mvc.voting.model.IPoll;

public class AddAnswerCommand implements Command
{

    private IAllPolls allPolls;
    private IPoll activePoll;

    public AddAnswerCommand(IAllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        if (this.allPolls.getPoll(commands[1]) != null)
        {
            this.activePoll.addAnswer("test");
        }

        return "Antwort hinzugefuegt";
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        this.activePoll = activePoll;
    }
}