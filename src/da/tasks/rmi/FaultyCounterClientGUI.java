package da.tasks.rmi;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import da.tasks.rmi.customstub.Counter;

public class FaultyCounterClientGUI extends JFrame
{
    private static final long serialVersionUID = 1L;
    protected final Counter counter;
    protected final JLabel counterLabel;

    public FaultyCounterClientGUI(final Counter counter)
    {
        // Grafische Elemente erzeugen, ”verdrahten” und auf Oberfläche
        // platzieren
        this.counter = counter;
        counterLabel = null;
    }

    protected void onResetButtonClicked(final ActionEvent ev)
    {
        ev.getActionCommand();
        try
        {
            final int counterValueAfterReset = this.counter.reset();
            this.counterLabel.setText(String.valueOf(counterValueAfterReset));
        }
        catch (final RemoteException re)
        {
            JOptionPane.showMessageDialog(this, "Fehler: " + re.getMessage());
        }
    }

    protected void onIncrementButtonClicked(ActionEvent ev)
    {
        ev.getActionCommand();
        try
        {
            final int counterValueAfterIncrement = this.counter.increment();
            this.counterLabel.setText(String.valueOf(counterValueAfterIncrement));
        }
        catch (final RemoteException re)
        {
            JOptionPane.showMessageDialog(this, "Fehler: " + re.getMessage());
        }
    }
}