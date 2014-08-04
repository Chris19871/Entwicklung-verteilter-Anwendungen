package da.tasks.io.stream;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileGenerator
{

    public static void main(final String[] args) throws IOException
    {
        try (final FileOutputStream os = new FileOutputStream(args[0]))
        {
            String s = "";

            for (int i = 0; i < Integer.parseInt(args[1]); i++)
            {
                s += "a";
            }

            os.write(s.getBytes());
        }
    }
}