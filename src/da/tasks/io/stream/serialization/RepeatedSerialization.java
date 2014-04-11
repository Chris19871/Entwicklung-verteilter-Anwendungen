package da.tasks.io.stream.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import da.lecture.io.stream.serialization.Point;

public class RepeatedSerialization
{
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
    {

        Point a = new Point(10, 15);
        Point b = new Point(20, 30);
        a.setRef(b);

        try (final ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("out.serial"))))
        {
            out.writeObject(a);
            out.writeObject(b);
        }

        try (final ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("out.serial"))))
        {
            Point aStrich = (Point) in.readObject();
            Point bStrich = (Point) in.readObject();

            if (aStrich.getRef().equals(bStrich))
            {
                System.out.println("aStrich.ref  -equals-  bStrch");
            }

            if (aStrich.getRef().equals(b))
            {
                System.out.println("aStrich.ref  -equals-  b");
            }
        }
    }
}