package da.tasks.rmi.compressexamination;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class MeasuringBufferInputStream extends GZIPInputStream
{
    private int bytes;

    public MeasuringBufferInputStream(InputStream in) throws IOException
    {
        super(in);
        this.bytes = 0;
    }

    @Override
    public int read(byte[] b) throws IOException
    {
        this.bytes += b.length;
        System.out.println("Input Stream: " + this.bytes + " Bytes");

        return super.read(b);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len1) throws IOException
    {
        this.bytes += b.length;
        System.out.println("Input Stream: " + this.bytes + " Bytes");

        return super.read(b, off, len1);
    }

    @Override
    public int read() throws IOException
    {
        System.out.println("read ohne Parameter");
        return super.read();
    }
}
