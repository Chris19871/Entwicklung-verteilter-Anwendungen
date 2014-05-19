package gui.mvc.voting.model;

import java.util.ArrayList;

/**
 * Wert-Objekt für die umfragerelevanten Inhalte einer {@link Poll}
 * implementierenden Modellkomponente.
 */
public class PollData
{
    /** Frage, die Thema der Umfrage ist. */
    private final String question;
    /** Antwortmöglichkeiten der Umfrage. */
    // private final String[] answers;
    private final ArrayList<String> answers;
    /** Stimmen, die auf die Antwortmöglichkeiten verteilt wurden. */
    private final int[] votes;

    /** Gesamtanzahl der bereits verteilten Stimmen. */
    // private final int totalVotes;

    /**
     * Initialisiert ein neues {@link PollData}-Objekt mit den übergebenen
     * Argumenten.
     * 
     * @param question
     *            Frage, die Thema der Umfrage ist.
     * @param answers
     *            Antwortmöglichkeiten der Umfrage.
     * @param votes
     *            Stimmen, die auf die Antwortmöglichkeiten verteilt wurden.
     * @param totalVotes
     *            Gesamtanzahl der bereits verteilten Stimmen.
     */
    // public PollData(final String question, final String[] answers, final
    // int[] votes, final int totalVotes)
    public PollData(final String question, final String[] answers, final int[] votes)
    {
        this.question = question;
        // this.answers = answers;

        this.answers = new ArrayList<String>();

        for (String s : answers)
        {
            this.answers.add(s);
        }

        this.votes = votes;
        // this.totalVotes = totalVotes;
    }

    // Getter für den lesenden Zugriff auf alle Attribute und eventuell
    // weitere lesende Methoden.

    public String getQuestion()
    {
        return this.question;
    }

    public String[] getAnswers()
    {
        return (String[]) this.answers.toArray();
    }

    public String getAnswer(int index)
    {
        return this.answers.get(index);
    }

    public int getVoteCountToQuestion(int index)
    {
        return this.votes[index];
    }

    public int getTotalVotes()
    {
        int count = 0;

        for (int i = 0; i < this.votes.length; ++i)
        {
            count += this.votes[i];
        }

        return count;
    }

    public int getNumberOfCandidates()
    {
        return this.answers.size();
    }

    public int getAnswersCount()
    {
        return this.answers.size();
    }

    public int getAnswerPercentage(int index)
    {
        final double candidateVotes = this.getVoteCountToQuestion(index);
        final double percentage = candidateVotes / this.getTotalVotes() * 100;
        return (int) Math.round(percentage);
    }

    public void addAnswerInPollData(String answer)
    {
        this.answers.add(answer);
    }

    public void setVotes(int answerIndex, int votesCount)
    {
        this.votes[answerIndex] = votesCount;
    }

    public void incrementVotes(int answerIndex)
    {
        this.votes[answerIndex]++;
    }
}