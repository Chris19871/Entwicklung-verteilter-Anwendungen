package da.tasks.io.stream.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import da.lecture.io.stream.serialization.Point;

public class ObjectCopy
{

    public static void main(String[] args) throws Exception
    {
        Point point = new Point(10, 20);

        Point pointCopy = (Point) ObjectCopy.deepCopy(point);
        System.out.println(pointCopy.getX());
    }

    /**
     * DeepCopy kopiert auch referenzierte Objekte komplett und verweist nicht
     * einfach nur auf die alten Objekte, wie es z.B. bei clone() der Fall wäre.
     * 
     * @param o
     * @return
     * @throws Exception
     */
    public static Object deepCopy(Object o) throws Exception
    {
        // Statt den beiden Klassen FileInputStream und FileOutputStream
        // werden ByteArrayInputStream und ByteArrayOutputStream benutzt.
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(o);

        ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectInput = new ObjectInputStream(byteInput);

        return objectInput.readObject();
    }
}
