package da.tasks.io.stream.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import da.lecture.io.stream.serialization.Point;

public class ObjectOutputStreamExamination
{

    public static void main(String[] args) throws IOException
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
        try (final DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("out.serial"))))
        {
            dOut.writeInt(p.getX());
            dOut.writeInt(p.getY());
        }
    }

    private static void read() throws IOException
    {
        // Irgendwas stimmt nicht, Ausgabe bleibt leer???
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("out.serial"), Charset.forName("UTF-8")));
        final String answer = reader.readLine();
        System.out.println(answer);
    }

    /**
     * Hilfsmtehode, die ein {@link Point}-Objekt aus der Datei
     * {@link DataStreamSerialization#FNAME} deserialisiert und zurückliefert.
     * 
     * @return Deserialisiertes {@link Point}-Objekt.
     * @throws IOException
     *             Wird ausgelöst, wenn es beim Deserialisieren zu einem
     *             Ein-/Ausgabefehler gekommen ist.
     */
    private static Point readPoint() throws IOException
    {
        try (final DataInputStream dIn = new DataInputStream(new BufferedInputStream(new FileInputStream("out.serial"))))
        {
            final int x = dIn.readInt();
            final int y = dIn.readInt();

            return new Point(x, y);
        }
    }
}