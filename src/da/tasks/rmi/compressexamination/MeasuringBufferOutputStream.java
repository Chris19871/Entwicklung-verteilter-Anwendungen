package da.tasks.rmi.compressexamination;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class MeasuringBufferOutputStream extends GZIPOutputStream
{
    private int bytes;

    public MeasuringBufferOutputStream(OutputStream out) throws IOException
    {
        super(out);
        this.bytes = 0;
    }

    @Override
    public void write(byte[] b) throws IOException
    {
        // TODO Auto-generated method stub
        super.write(b);

        this.bytes += b.length;
        System.out.println("Output Stream: " + this.bytes + " Bytes");
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException
    {
        // TODO Auto-generated method stub
        super.write(b, off, len);

        this.bytes += b.length;
        System.out.println("Output Stream: " + this.bytes + " Bytes");
    }
}
