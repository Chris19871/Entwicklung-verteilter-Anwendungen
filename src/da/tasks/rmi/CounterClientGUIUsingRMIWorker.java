package da.tasks.rmi;

import java.awt.event.ActionEvent;

import da.tasks.rmi.customstub.Counter;

public class CounterClientGUIUsingRMIWorker extends FaultyCounterClientGUI
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CounterClientGUIUsingRMIWorker(final Counter counter)
    {
        super(counter);
    }

    @Override
    protected void onIncrementButtonClicked(final ActionEvent ev)
    {
        RMIWorker.doAsync(() -> this.counter.increment(), newValue -> this.counterLabel.setText(String.valueOf(newValue)));
    }

    @Override
    protected void onResetButtonClicked(final ActionEvent ev)
    {
        RMIWorker.doAsync(this.counter::reset, this::updateLabel);

        RMIWorker.doAsync(this.counter::reset, String::valueOf, this.counterLabel::setText);
    }

    private void updateLabel(final int newCounterValue)
    {
        this.counterLabel.setText(String.valueOf(newCounterValue));
    }
}