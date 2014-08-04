package da.tasks.rmi.list.valueresult;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class List extends UnicastRemoteObject implements RMIList
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ListItem first; // Erstes Kettenglied
    private ListItem last; // Letztes Kettenglied

    public List(final int... intialValues) throws RemoteException
    {
        for (final int currentValue : intialValues)
        {
            this.append(currentValue);
        }
    }

    public void append(final int valueToAppend)
    {
        final ListItem itemToAppend = new ListItem(valueToAppend);

        if (this.first == null)
        { // Ist Liste aktuell noch leer?
            this.first = itemToAppend; // Dann wird neues Kettenglied Kopf der
                                       // Liste
        }
        else
        { // Sonst
            this.last.setNext(itemToAppend); // Wird neues Kettenglied hinten
                                             // angehängt
        }
        this.last = itemToAppend;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        ListItem currentItem = this.first;
        while (currentItem != null)
        {
            builder.append(currentItem.getValue()).append(" ");
            currentItem = currentItem.getNext();
        }
        return builder.toString();
    }
}