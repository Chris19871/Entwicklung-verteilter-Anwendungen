package da.tasks.io.stream.serialization.linkedlist;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** Einfachverkettete Liste bestehend aus {@link ListEntry}-Objekten. */
public class LinkedList implements Serializable
{
    /** Erstes Listenelement. */
    private ListEntry head;
    /** Letztes Listenelement. */
    private ListEntry tail;

    private void writeObject(ObjectOutputStream s) throws Exception
    {
        s.defaultWriteObject();

        ListEntry currentEntry = this.head;
        while (currentEntry != null)
        {
            s.writeUTF(currentEntry.getValue());
            currentEntry = currentEntry.getNext();
        }
    }

    private void readObject(ObjectInputStream s) throws Exception
    {
        s.defaultReadObject();

        ListEntry entry = new ListEntry(s.readUTF());
        this.head = entry;

        while (s.available() > 0)
        {
            ListEntry newEntry = new ListEntry(s.readUTF());
            entry.setNext(newEntry);
            entry = newEntry;
        }
    }

    /**
     * Fügt den übergebenen Wert an das Ende der einfachverketten Liste an.
     * 
     * @param value
     *            Anzufügender Wert.
     */
    public void add(final String value)
    {
        final ListEntry newEntry = new ListEntry(value);
        if ((this.head == null) && (this.tail == null))
        {
            this.head = newEntry;
        }
        else
        {
            this.tail.setNext(newEntry);
        }
        this.tail = newEntry;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder("Listeninhalt: \n");
        ListEntry currentEntry = this.head;
        while (currentEntry != null)
        {
            builder.append(currentEntry.getValue()).append("\n");
            currentEntry = currentEntry.getNext();
        }
        return builder.toString();
    }
}