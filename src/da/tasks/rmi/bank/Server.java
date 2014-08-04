package da.tasks.rmi.bank;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server
{

    public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException
    {
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        final Bank bank = new BankImpl();

        // 100 Konten anlegen.
        for (int i = 0; i < 100; ++i)
        {
            System.out.println("Konto " + i + " anlegen ...");
            final Account account = new AccountImpl(i + 100);
            bank.addAccount(account);
        }

        localReg.bind(Bank.DEFAULT_RMI_OBJECT_NAME, bank);
    }
}
