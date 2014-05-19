package gui.mvc.voting.model;

import java.util.ArrayList;

public class PollModel implements IPoll
{
    private PollData pollData;
    private ArrayList<IPollChangeListener> listener;

    public PollModel(String question, String[] candidates)
    {
        int[] votes = new int[10];

        this.pollData = new PollData(question, candidates, votes);

        this.listener = new ArrayList<IPollChangeListener>();
    }

    public PollModel(String question)
    {
        this(question, new String[1]);
    }

    @Override
    public String getQuestion()
    {
        return this.pollData.getQuestion();
    }

    @Override
    public PollData getPollData()
    {
        return this.pollData;
    }

    @Override
    public void addAnswer(String answer)
    {
        this.pollData.addAnswerInPollData(answer);

        for (int i = 0; i < listener.size(); ++i)
        {
            this.listener.get(i).answerAdded(this.pollData);
        }
    }

    @Override
    public void setVotes(int answerIndex, int votes)
    {
        this.pollData.setVotes(answerIndex, votes);
        this.fireVoteChange();
    }

    @Override
    public void incrementVotes(int answerIndex)
    {
        this.pollData.incrementVotes(answerIndex);
        this.fireVoteChange();
    }

    public void fireVoteChange()
    {
        for (int i = 0; i < listener.size(); ++i)
        {
            this.listener.get(i).voteChanged(this.pollData);
        }
    }

    @Override
    public void addPollChangeListener(IPollChangeListener pcl)
    {
        this.listener.add(pcl);
    }

    @Override
    public void removePollChangeListener(IPollChangeListener pcl)
    {
        this.listener.remove(pcl);
    }
}