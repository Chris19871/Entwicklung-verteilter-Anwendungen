package da.tasks.rmi.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class BankImpl extends UnicastRemoteObject implements Bank
{
    private static final long serialVersionUID = 1L;
    private ArrayList<Account> accounts;

    protected BankImpl() throws RemoteException
    {
        super();
        this.accounts = new ArrayList<Account>();
    }

    public void addAccount(Account account) throws RemoteException
    {
        this.accounts.add(account);
    }

    @Override
    public int[] listAccountNumbers() throws RemoteException
    {
        int[] numbers = new int[this.accounts.size()];

        for (int i = 0; i < this.accounts.size(); ++i)
        {
            numbers[i] = this.accounts.get(i).readAccountNumber();
        }

        return numbers;
    }

    @Override
    public Account getAccountForNumber(int accountNumber) throws RemoteException
    {
        for (int i = 0; i < this.accounts.size(); ++i)
        {
            if (this.accounts.get(i).readAccountNumber() == accountNumber)
            {
                return this.accounts.get(i);
            }
        }

        return null;
    }
}