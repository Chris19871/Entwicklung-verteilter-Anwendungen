package da.tasks.io.stream.serialization.linkedlist;

import java.io.Serializable;

/**
 * Klasse zur Repräsentation eines Listenelementes einer einfachverketteten
 * Liste.
 */
public class ListEntry implements Serializable
{
    /** Referenz auf das nächste Listenelement der einfachverketteten Liste. */
    /*
     * Ohne transient "Exception in thread "main" java.lang.StackOverflowError"
     * Mit transient gekennzeichnete Variablen werden bei Serialisierungen zur
     * persistenten Abspeicherung eines Objekts nicht mit gespeichert.
     */
    private transient ListEntry next = null;
    /** Wert dieses Listenelementes. */
    private String value;

    /**
     * Initialisiert eine neue ListEntry-Instanz mit den übergebenen Argumenten.
     * 
     * @param value
     *            Wert, den dieses Listenelement aufweisen soll.
     */
    public ListEntry(final String value)
    {
        this.value = value;
    }

    /**
     * Liefert den Wert des Listenelementes zurück.
     * 
     * @return Wert des Listenelementes.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * Liefert das nächste Listenelement der einfachverketteten Liste.
     * 
     * @return Nächstes Listenelement der einfachverketteten Liste.
     */
    public ListEntry getNext()
    {
        return this.next;
    }

    /**
     * Setzt das nächste Listenelement der einfachverketteten Liste.
     * 
     * @param next
     *            Listenelement, das als nächstes Listenelement der
     *            einfachverketteten Liste gesetzt werden soll.
     */
    public void setNext(final ListEntry next)
    {
        this.next = next;
    }
}