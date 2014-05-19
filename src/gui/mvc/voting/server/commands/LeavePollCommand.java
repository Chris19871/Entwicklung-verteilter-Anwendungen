package gui.mvc.voting.server.commands;

import gui.mvc.voting.model.IPoll;

import java.awt.Frame;
import java.awt.event.WindowEvent;

public class LeavePollCommand implements Command
{

    public LeavePollCommand()
    {
    }

    @Override
    public String execute(String[] commands)
    {
        Frame[] frames = Frame.getFrames();
        int i = 0;
        for (Frame frame : frames)
        {
            if (i == 1)
            {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
            i++;
        }

        return "Frage wieder geschlossen";
    }

    @Override
    public void setActivePoll(IPoll activePoll)
    {
        // TODO Auto-generated method stub

    }

}
