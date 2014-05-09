package gui.mvc.voting.model;

import java.util.ArrayList;
import java.util.HashMap;

public class AllPolls implements IAllPolls
{
    private ArrayList<IPoll> polls;

    private HashMap<String, IPoll> map;

    private ArrayList<IAllPollsChangeListener> listener;

    public AllPolls()
    {
        this.polls = new ArrayList<IPoll>();
        this.map = new HashMap<String, IPoll>();
        this.listener = new ArrayList<IAllPollsChangeListener>();
    }

    @Override
    public IPoll addPoll(String id, String question)
    {
        IPoll model = new PollModel(question);
        this.polls.add(model);

        this.map.put(id, model);

        for (int i = 0; i < this.listener.size(); ++i)
        {
            this.listener.get(i).pollAdded(id, question);
        }

        return model;
    }

    @Override
    public IPoll addPoll(String id, String question, String[] answers)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removePoll(String id)
    {
    }

    @Override
    public IPoll getPoll(String id)
    {
        return map.get(id);
    }

    @Override
    public String[][] getAllIdsAndQuestions()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addAllPollsChangeListener(IAllPollsChangeListener l)
    {
        this.listener.add(l);
    }

    @Override
    public void removeAllPollsChangeListener(IAllPollsChangeListener l)
    {
        this.listener.remove(l);
    }
}