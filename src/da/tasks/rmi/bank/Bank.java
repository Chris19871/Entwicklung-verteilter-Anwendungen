package da.tasks.rmi.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI-Schnittstelle für eine Bank, die aus einer Ansammlung von
 * Account-Objekten besteht.
 */
public interface Bank extends Remote
{
    String DEFAULT_RMI_OBJECT_NAME = "Bank";

    /**
     * Liefert alle Kontonummern zurück, für die bei dieser Bank Account-Objekte
     * exisitieren.
     * 
     * @return Kontonummern aller Account-Objekte.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    int[] listAccountNumbers() throws RemoteException;

    /**
     * Liefert das Account-Objekt zu dem Geldkonto mit der übergebenen
     * Kontonummer zurück.
     * 
     * @param accountNumber
     *            Kontonummer.
     * @return Siehe Methodenbeschreibung.
     * @throws RemoteException
     *             Wird ausgelöst, wenn es bei der RMI-Kommunikation zu einem
     *             Fehler gekommen ist.
     */
    Account getAccountForNumber(int accountNumber) throws RemoteException;

    void addAccount(Account account) throws RemoteException;
}
