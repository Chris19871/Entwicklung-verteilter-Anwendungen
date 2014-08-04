package da.tasks.rmi.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI-Schnittstelle für ein Objekt, welches ein Geldkonto repräsentiert.
 */
public interface Account extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "Konto";

    int readAccountNumber() throws RemoteException;

    /**
     * Liest den Kontostand dieses Geldkontos aus.
     * 
     * @return Kontostand.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    double readBalance() throws RemoteException;

    /**
     * Ändert den Kontostand dieses Geldkontos um den übergebenen Wert.
     * 
     * @param valueToAdd
     *            Wert, um den der Kontostand geändert werden soll. Positive
     *            Werte führen zu einer Erhöhung des Kontostandes, negative
     *            Werte zu einer Verringerung.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    void changeBalance(double valueToAdd) throws RemoteException;

    public class AccountOverrunException extends RemoteException
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
    }
}
