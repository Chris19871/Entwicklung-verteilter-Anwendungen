package da.tasks.rmi.restrictstub;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import da.tasks.rmi.compressexamination.MeasuringBufferInputStream;
import da.tasks.rmi.compressexamination.MeasuringBufferOutputStream;

public class ControlServerSocketFactory implements RMIClientSocketFactory, RMIServerSocketFactory, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public Socket createSocket(final String host, final int port) throws IOException
    {
        return new CompressingSocket(host, port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException
    {
        return new RestrictedServerSocket(port);
    }

    private static class CompressingSocket extends Socket
    {
        public CompressingSocket(final String host, final int port) throws IOException
        {
            super(host, port);
        }

        public CompressingSocket(final SocketImpl impl) throws IOException
        {
            super(impl);
        }

        @Override
        public OutputStream getOutputStream() throws IOException
        {
            System.out.println("getOutputStream");

            return new MeasuringBufferOutputStream(new GZIPOutputStream(super.getOutputStream(), true));
        }

        @Override
        public InputStream getInputStream() throws IOException
        {
            System.out.println("getInputStream");
            return new MeasuringBufferInputStream(new GZIPInputStream(super.getInputStream()));
        }
    }

    private static class RestrictedServerSocket extends ServerSocket
    {
        public RestrictedServerSocket(final int port) throws IOException
        {
            super(port);
        }

        @Override
        public Socket accept() throws IOException
        {
            if (this.isClosed() || !this.isBound())
            {
                throw new SocketException("Socket is closed or not bound yet");
            }

            final CompressingSocket s = new CompressingSocket((SocketImpl) null);
            this.implAccept(s);

            // Kontrollieren ob Stub auch auf localhost läuft.
            if (!s.getInetAddress().equals(InetAddress.getLocalHost()))
            {
                s.close();
            }

            return s;
        }
    }
}