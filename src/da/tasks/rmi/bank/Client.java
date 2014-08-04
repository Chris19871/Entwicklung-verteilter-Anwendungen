package da.tasks.rmi.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import da.tasks.rmi.bank.Account.AccountOverrunException;

public class Client
{
    public static void main(String[] args) throws RemoteException, NotBoundException, AccountOverrunException
    {
        /*
         * final Registry reg =
         * LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1); final Account
         * account0 = (Account) reg.lookup(Account.DEFAULT_RMI_OBJECT_NAME +
         * "0"); final Account account11 = (Account)
         * reg.lookup(Account.DEFAULT_RMI_OBJECT_NAME + "11"); final Account
         * account27 = (Account) reg.lookup(Account.DEFAULT_RMI_OBJECT_NAME +
         * "27");
         * 
         * System.out.println("Account0: " + account0.readBalance());
         * System.out.println("Account11: " + account11.readBalance());
         * System.out.println("Account27: " + account27.readBalance());
         * account0.changeBalance(12.2); account11.changeBalance(32.30);
         * account11.changeBalance(-3332.30); System.out.println("Account0: " +
         * account0.readBalance()); System.out.println("Account11: " +
         * account11.readBalance()); System.out.println("Account27: " +
         * account27.readBalance());
         */

        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT + 1);
        final Bank bank = (Bank) reg.lookup(Bank.DEFAULT_RMI_OBJECT_NAME);

        Account account = bank.getAccountForNumber(101);
        System.out.println(account.readBalance());
        account.changeBalance(12.0);
        System.out.println(account.readBalance());

        Account account2 = bank.getAccountForNumber(107);
        System.out.println(account2.readBalance());
        account2.changeBalance(2324.0);
        System.out.println(account2.readBalance());
    }
}