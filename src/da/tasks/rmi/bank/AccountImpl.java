package da.tasks.rmi.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountImpl extends UnicastRemoteObject implements Account
// public class AccountImpl implements Account
{
    private static final long serialVersionUID = 1L;

    private int accountNumber;
    private double amount;

    protected AccountImpl(int accountNumber) throws RemoteException
    {
        super();
        this.amount = 0;
        this.accountNumber = accountNumber;
    }

    @Override
    public int readAccountNumber() throws RemoteException
    {
        return this.accountNumber;
    }

    @Override
    public double readBalance() throws RemoteException
    {
        return this.amount;
    }

    @Override
    public void changeBalance(double valueToAdd) throws RemoteException
    {
        if ((this.amount + valueToAdd) < 0)
        {
            throw new AccountOverrunException();
        }
        this.amount += valueToAdd;
    }
}