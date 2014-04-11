package da.tasks.io.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataStreamExamination
{

    private DataOutputStream out;
    private DataInputStream in;
    private String fileName;

    public DataStreamExamination() throws IOException
    {
        this.fileName = "DataStreamExam.txt";
        this.out = new DataOutputStream(new FileOutputStream(this.fileName));
        this.in = new DataInputStream(new FileInputStream(this.fileName));

        this.out.writeBoolean(false);
        this.out.writeShort(10000001);
        this.out.writeInt(66);
        this.out.writeLong(1234);
        this.out.writeBytes("sdfsd");
        this.out.writeChars("chars");
        this.out.writeUTF("writeUTF");

        this.in.readBoolean();
        this.in.readShort();
        this.in.readInt();
        this.in.readLong();
        this.in.readUTF();

        this.out.flush();
    }

    public void test()
    {

    }

    public static void main(String[] args) throws IOException
    {
        DataStreamExamination ds = new DataStreamExamination();
        ds.test();
    }
}
