package gui.mvc.voting.model;

/**
 * Schnittstelle für Listener, die über Änderungen an einer {@link AllPolls}
 * implementierenden Modellkomponente benachrichtigt werden wollen.
 */
public interface IAllPollsChangeListener
{
    /**
     * Wird aufgerufen, wenn dem Modell eine neue Umfrage hinzugefügt wurde.
     * 
     * @param id
     *            Kennung der neuen Umfrage.
     * @param question
     *            Frage, um die es in der neuen Umfrage geht.
     */
    void pollAdded(String id, String question);

    /**
     * Wird aufgerufen, wenn eine Umfrage aus dem Modell gelöscht wurde.
     * 
     * @param id
     *            Kennung der gelöschten Umfrage.
     */
    void pollRemoved(String id);
}