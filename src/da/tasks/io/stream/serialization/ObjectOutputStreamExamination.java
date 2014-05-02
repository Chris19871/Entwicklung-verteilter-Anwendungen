package da.tasks.io.stream.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import da.lecture.io.stream.serialization.Point;

public class ObjectOutputStreamExamination
{

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Point point = new Point(10, 20);
        ObjectOutputStreamExamination.writePoint(point);
        ObjectOutputStreamExamination.read();

        Point restored = ObjectOutputStreamExamination.readPoint();
        System.out.println("x: " + restored.getX() + ", y: " + restored.getY());
    }

    /**
     * Hilfsmethode, die das übergebene {@link Point}-Objekt in die Datei mit
     * dem Namen {@link DataStreamSerialization#FNAME} speichert.
     * 
     * @param p
     *            Zu serialisierendes {@link Point}-Objekt.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Serialisieren zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     */
    private static void writePoint(final Point p) throws IOException
    {
        try (final ObjectOutputStream dOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("out.serial"))))
        {
            dOut.writeObject(p);
        }
    }

    private static void read() throws IOException
    {
        // Irgendwas stimmt nicht, Ausgabe bleibt leer???
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("out.serial"), Charset.forName("UTF-8")));

        String temp = null;
        StringBuffer text = new StringBuffer();
        while ((temp = reader.readLine()) != null)
        {
            text.append(temp + "\n");
        }

        System.out.println(text.toString());
    }

    /**
     * Hilfsmtehode, die ein {@link Point}-Objekt aus der Datei
     * {@link DataStreamSerialization#FNAME} deserialisiert und zurückliefert.
     * 
     * @return Deserialisiertes {@link Point}-Objekt.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Deserialisieren zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     * @throws ClassNotFoundException
     */
    private static Point readPoint() throws IOException, ClassNotFoundException
    {
        try (final ObjectInputStream dIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("out.serial"))))
        {
            return (Point) dIn.readObject();
        }
    }
}