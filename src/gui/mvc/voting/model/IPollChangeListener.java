package gui.mvc.voting.model;

/**
 * Schnittstelle für Listener, die über Änderungen an einer {@link Poll}
 * implementierenden Modellkomponente benachrichtigt werden wollen.
 */
public interface IPollChangeListener
{
    /**
     * Wird aufgerufen, wenn sich die abgegebenen Stimmen geändert haben.
     * 
     * @param data
     *            Geänderte Modellinhalte in Form eines Wert-Objektes.
     */
    void voteChanged(PollData data);

    /**
     * Wird aufgerufen, wenn dem Modell eine neue Antwortmöglichkeit hinzugefügt
     * wurde.
     * 
     * @param data
     *            Geänderte Modellinhalte in Form eines Wert-Objektes.
     */
    void answerAdded(PollData data);
}