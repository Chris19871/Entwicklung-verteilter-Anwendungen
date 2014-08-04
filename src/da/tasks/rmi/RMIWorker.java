package da.tasks.rmi;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import javax.swing.JOptionPane;

public class RMIWorker
{
    private static final ExecutorService EXECSERVICE = Executors.newCachedThreadPool();

    public static <S, P> void doAsync(final RMIProducer<S> rmiProd, final Function<S, P> castFunc, final GUIConsumer<P> guiCons)
    {
        new Thread(() -> RMIWorker.doDirect(rmiProd, castFunc, guiCons)).start();
        RMIWorker.EXECSERVICE.execute(() -> RMIWorker.doDirect(rmiProd, castFunc, guiCons));
    }

    public static <S, P> void doDirect(final RMIProducer<S> rmiProd, final Function<S, P> castFunc, final GUIConsumer<P> guiCons)
    {
        try
        {
            final S rmiReturnValue = rmiProd.doRMICall();
            EventQueue.invokeLater(() -> guiCons.doGUIManipulation(castFunc.apply(rmiReturnValue)));
        }
        catch (final RemoteException re)
        {
            EventQueue.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error: " + re.getMessage()));
        }
    }

    public static <T> void doAsync(final RMIProducer<T> rmiProd, final GUIConsumer<T> guiCons)
    {
        RMIWorker.EXECSERVICE.execute(() -> RMIWorker.doDirect(rmiProd, guiCons));
    }

    public static <T> void doDirect(final RMIProducer<T> rmiProd, final GUIConsumer<T> guiCons)
    {
        try
        {
            final T rmiReturnValue = rmiProd.doRMICall();
            EventQueue.invokeLater(() -> guiCons.doGUIManipulation(rmiReturnValue));

        }
        catch (final RemoteException re)
        {
            EventQueue.invokeLater(() -> JOptionPane.showMessageDialog(null, re.getMessage()));
        }
    }
}