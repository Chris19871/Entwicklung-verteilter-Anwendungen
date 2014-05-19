package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.IPoll;

public interface Command
{
    String execute(String[] commands);

    public void setActivePoll(IPoll activePoll);
}
