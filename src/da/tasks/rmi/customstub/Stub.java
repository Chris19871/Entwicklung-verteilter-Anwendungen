package da.tasks.rmi.customstub;

import java.rmi.RemoteException;

public class Stub implements Counter
{

    private String server;
    private int port;

    public Stub(String server, int port)
    {
        // TODO Auto-generated constructor stub
        this.setServer(server);
        this.setPort(port);

    }

    @Override
    public int reset() throws RemoteException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int increment() throws RemoteException
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }
}
