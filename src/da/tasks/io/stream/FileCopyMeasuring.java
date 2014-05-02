package da.tasks.io.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyMeasuring
{
    private static final String COPIED_FILE_NAME = "copiedFile";

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        long startTime = System.nanoTime();
        copyFileInputStream("testfile");
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("copyFile: " + estimatedTime);

        startTime = System.nanoTime();
        copyFileBufferedInput("testfile");
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("copyFileBuff: " + estimatedTime);

        startTime = System.nanoTime();
        copyFileInputStreamModified("testfile");
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("copyFileBuffMod: " + estimatedTime);

        startTime = System.nanoTime();
        copyFileFileChannel("testfile");
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("copyFileFileChannel: " + estimatedTime);
    }

    public static void copyFileInputStream(String file) throws FileNotFoundException, IOException
    {
        try (final FileInputStream is = new FileInputStream(file); final FileOutputStream os = new FileOutputStream(COPIED_FILE_NAME))
        {
            int data = 0;
            while ((data = is.read()) != -1)
            {
                os.write(data);
            }
        }
    }

    public static void copyFileBufferedInput(String file) throws FileNotFoundException, IOException
    {
        try (final BufferedInputStream is = new BufferedInputStream(new FileInputStream(file)); final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(COPIED_FILE_NAME)))
        {
            int readData = -1;
            while ((readData = is.read()) != -1)
            {
                out.write(readData);
            }
        }
    }

    public static void copyFileInputStreamModified(String file) throws FileNotFoundException, IOException
    {
        try (final FileInputStream is = new FileInputStream(file); final FileOutputStream os = new FileOutputStream(COPIED_FILE_NAME))
        {
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, len);
            }
        }
    }

    public static void copyFileFileChannel(String file) throws IOException
    {
        try (final FileInputStream is = new FileInputStream(file); final FileOutputStream os = new FileOutputStream(COPIED_FILE_NAME))
        {
            FileChannel sourceFileChannel = is.getChannel();
            FileChannel destinationFileChannel = os.getChannel();
            long size = sourceFileChannel.size();
            sourceFileChannel.transferTo(0, size, destinationFileChannel);
        }
    }
}