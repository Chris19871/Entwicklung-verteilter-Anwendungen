package da.tasks.rmi.plusminus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ModelImpl extends UnicastRemoteObject implements Model
{
    private static final long serialVersionUID = 1L;
    private int min;
    private int max;
    private int step;
    private int count = 0;
    private ArrayList<ListenerInterface> listener;

    /**
     * @param min
     *            Untere Schranke, darf vom Zählwert nicht unterschritten werden
     * @param max
     *            Obere Schranke, darf vom Zählwert nicht überschritten werden
     * @param step
     *            Schrittweite, die Zählwert inkrementiert bzw. dekrementiert
     *            wird
     */
    public ModelImpl(final int min, final int max, final int step) throws RemoteException
    {
        this.min = min;
        this.max = max;
        this.step = step;
        this.listener = new ArrayList<ListenerInterface>();
    }

    public void increment() throws OverrunException, RemoteException
    {
        if (this.count + step > max)
        {
            throw new OverrunException();
        }
        this.count += step;

        this.sendToListener(this.count);
    }

    public void decrement() throws OverrunException, RemoteException
    {
        if (this.count - step < min)
        {
            throw new OverrunException();
        }

        this.count -= step;

        this.sendToListener(this.count);
    }

    @Override
    public void addListener(ListenerInterface myListener)
    {
        this.listener.add(myListener);

        System.out.println("Listener Count: " + this.listener.size());
    }

    private void sendToListener(int myCount)
    {
        for (ListenerInterface label : this.listener)
        {
            label.change("" + myCount);
        }
    }
}