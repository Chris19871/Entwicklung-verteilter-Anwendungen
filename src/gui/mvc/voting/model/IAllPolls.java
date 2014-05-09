package gui.mvc.voting.model;

/**
 * Schnittstelle zur Repräsentation der Funktionalitäten einer Umfragesammlung
 * für {@link Poll}-Modelle.
 */
public interface IAllPolls
{
    /**
     * Fügt eine neue Umfrage zur Sammlung hinzu, wobei anfänglich noch keine
     * Antworten enthalten sind.
     * 
     * @param id
     *            Kennung der Umfrage.
     * @param question
     *            Frage, um die es in der Umfrage geht.
     * @return {@link Poll}-Modell für die neue Umfrage.
     */
    IPoll addPoll(String id, String question);

    /**
     * Fügt eine neue Umfrage zur Sammlung hinzu, die bereits die übergebenen
     * Antworten enthält.
     * 
     * @param id
     *            Kennung der Umfrage.
     * @param question
     *            Frage, um die es in der Umfrage geht.
     * @param answers
     *            Initial verfügbare Antworten.
     * @return {@link Poll}-Modell für die neue Umfrage.
     */
    IPoll addPoll(String id, String question, String[] answers);

    /**
     * Entfernt die Umfrage mit der übergebenen Kennung.
     * 
     * @param id
     *            Kennund der zu entfernen Umfrage.
     */
    void removePoll(String id);

    /**
     * Liefert die Umfrage mit der übergebenen Kennung zurück.
     * 
     * @param id
     *            Kennung der gewünschten Umfrage.
     * @return Siehe Methodenbeschreibung.
     */
    IPoll getPoll(String id);

    /**
     * Liefert einer Auflistung aller Umfragenkennungen und zugehöriger Fragen
     * zurück. Die zweite Dimension des zurückzuliefernden Arrays soll dazu zwei
     * Positionen aufweisen, eine für die Kennung und eine für die Frage einer
     * Umfrage.
     * 
     * @return Siehe Methodenbeschreibung.
     */
    String[][] getAllIdsAndQuestions();

    /**
     * Meldet den übergebenen {@link IAllPollsChangeListener} beim Modell an, so
     * dass er bei Modelländerungen benachrichtigt wird.
     * 
     * @param l
     *            Anzumeldender {@link AllPollsChangeListener}.
     */
    void addAllPollsChangeListener(IAllPollsChangeListener l);

    /**
     * Meldet den übergebenen {@link IAllPollsChangeListener} beim Modell ab, so
     * dass er bei Modelländerungen nicht mehr benachrichtigt wird.
     * 
     * @param l
     *            Abzumeldender {@link IAllPollsChangeListener}.
     */
    void removeAllPollsChangeListener(IAllPollsChangeListener l);
}