package gui.mvc.voting.model;

/**
 * Schnittstelle, die von einer Modellkomponente zur Repräsentation der
 * Umfrageergebnisse zu einer einzelnen Umfrage implementiert werden muss.
 */
public interface IPoll
{
    /**
     * Liefert die Frage zurück, die Thema der Umfrage ist.
     * 
     * @return Thema der Umfrage.
     */
    String getQuestion();

    /**
     * Liefert den aktuellen Stand der Umfrage in Form eines Wert-Objektes vom
     * Typ {@link PollData} zurück.
     * 
     * @return Stand der Umfrage als Wert-Objekt.
     */
    PollData getPollData();

    /**
     * Fügt der Umfrage eine neue Antwortmöglichkeit hinzu.
     * 
     * @param answer
     *            Neue Antwortmöglichkeit.
     */
    void addAnswer(String answer);

    /**
     * Setzt die Stimmen für eine bestimmte Antwortmöglichkeit auf den
     * übergebenen Wert.
     * 
     * @param answerIndex
     *            Index der Antwortmöglichkeit.
     * @param votes
     *            Neue Simmenanzahl für die Antwortmöglichkeit.
     */
    void setVotes(int answerIndex, int votes);

    /**
     * Inkrementiert die Stimmen für eine bestimmte Antwortmöglichkeit um Eins.
     * 
     * @param answerIndex
     *            Index der Antwortmöglichkeit.
     */
    void incrementVotes(int answerIndex);

    /**
     * Meldet den übergebenen {@link IPollChangeListener} beim Modell an, so dass
     * er bei Modelländerungen benachrichtigt wird.
     * 
     * @param pcl
     *            Anzumeldender {@link IPollChangeListener}.
     */
    void addPollChangeListener(IPollChangeListener pcl);

    /**
     * Meldet den übergebenen {@link IPollChangeListener} beim Modell ab, so dass
     * er bei Modelländerungen nicht mehr benachrichtigt wird.
     * 
     * @param pcl
     *            Abzumeldender {@link IPollChangeListener}.
     */
    void removePollChangeListener(IPollChangeListener pcl);
}