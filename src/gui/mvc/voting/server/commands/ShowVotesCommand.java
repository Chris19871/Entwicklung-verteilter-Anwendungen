package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.IAllPolls;
import gui.mvc.voting.model.IPoll;

public class ShowVotesCommand implements Command
{

    private IAllPolls allPolls;

    public ShowVotesCommand(IAllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        String data = "";

        for (int i = 0; i < this.allPolls.getPoll(commands[1]).getPollData().getAnswersCount(); i++)
        {
            data += this.allPolls.getPoll(commands[1]).getPollData().getAnswer(i) + "(" + this.allPolls.getPoll(commands[1]).getPollData().getAnswerPercentage(i) + ")";
        }
        return data;
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }

}
