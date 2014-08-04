package da.tasks.rmi.registryexamination;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server
{

    public static void main(String[] args) throws RemoteException, AlreadyBoundException
    {
        final Registry localReg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT + 1);
        //
        // final ChatRoom room = new ChatRoomImpl();
        //
        // localReg.bind("ChatRoom", room);
        // ChatRoom x = (ChatRoom) localReg.lookup("ChatRoom");
        //
        // System.out.println(x.getClass().getName());

        // Server beendet sich sofort ohne Exception?!?
        final KeinRemoteObjekt kro = new KeinRemoteObjektImpl();
        localReg.bind("kro", kro);
    }
}