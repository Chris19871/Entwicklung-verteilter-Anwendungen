package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.AllPolls;
import gui.mvc.voting.model.IPoll;

public class ShowPollsCommand implements Command
{
    private AllPolls allPolls;

    public ShowPollsCommand(AllPolls allPolls)
    {
        this.allPolls = allPolls;
    }

    @Override
    public String execute(String[] commands)
    {
        String polls = "";

        String[][] data = this.allPolls.getAllIdsAndQuestions();

        for (int i = 0; i < data.length; i++)
        {
            polls += "(" + data[i][0] + ") " + data[i][1] + "\n";
        }

        return polls;
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }
}
