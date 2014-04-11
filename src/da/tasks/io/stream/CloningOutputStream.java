package da.tasks.io.stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class CloningOutputStream extends OutputStream
{

    private ArrayList<OutputStream> outputStreams;

    public CloningOutputStream(ArrayList<OutputStream> outputStreams)
    {
        this.outputStreams = outputStreams;
    }

    @Override
    public void write(int b) throws IOException
    {
        for (OutputStream outputStream : this.outputStreams)
        {
            outputStream.write(b);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException
    {

        ArrayList<OutputStream> outputStreams = new ArrayList<OutputStream>();

        PrintStream ps = new PrintStream(System.out);
        PrintStream ps2 = new PrintStream(System.out);

        outputStreams.add(ps);
        outputStreams.add(ps2);

        try (final FileInputStream is = new FileInputStream("testfile"); final CloningOutputStream cl = new CloningOutputStream(outputStreams))
        {
            int data = 0;
            while ((data = is.read()) != -1)
            {
                cl.write(data);
            }
        }
    }
}
