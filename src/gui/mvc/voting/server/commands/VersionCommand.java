package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.IPoll;

public class VersionCommand implements Command
{
    @Override
    public String execute(String[] commands)
    {
        return "Version 1.0";
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }
}