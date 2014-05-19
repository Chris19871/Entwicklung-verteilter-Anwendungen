package gui.mvc.voting.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
        this.map.remove(id);

        for (int i = 0; i < this.listener.size(); ++i)
        {
            this.listener.get(i).pollRemoved(id);
        }

        return null;
    }

    @Override
    public void removePoll(String id)
    {
        this.map.remove(id);

        for (int i = 0; i < this.listener.size(); ++i)
        {
            this.listener.get(i).pollRemoved(id);
        }
    }

    @Override
    public IPoll getPoll(String id)
    {
        return map.get(id);
    }

    @Override
    public String[][] getAllIdsAndQuestions()
    {
        String[][] data = new String[this.polls.size()][2];

        int i = 0;
        for (Entry<String, IPoll> entry : this.map.entrySet())
        {
            data[i][0] = entry.getKey();
            data[i][1] = entry.getValue().getQuestion();
            i++;
        }

        return data;
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