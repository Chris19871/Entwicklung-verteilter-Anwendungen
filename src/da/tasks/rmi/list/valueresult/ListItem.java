package da.tasks.rmi.list.valueresult;

import java.io.Serializable;

public class ListItem implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int value; // Gekapselter Wert
    private ListItem next; // Nächstes Kettenglied der einfach verketteten Liste

    public ListItem(final int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public ListItem getNext()
    {
        return this.next;
    }

    public void setNext(final ListItem next)
    {
        this.next = next;
    }
}