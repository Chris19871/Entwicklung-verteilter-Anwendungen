package da.tasks.io.stream.serialization.linkedlist;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Beispielhafte Klasse zum Serialisieren und Deserialisieren einer
 * einfachverketteten Liste vom Typ {@link LinkedList}.
 */
public class LinkedListSerialization
{
    /** Name der temporären Datei, die als Zwischenspeicher dienen soll. */
    public static final File TEMPFILE = new File("oOut.tmp");
    /**
     * Anzahl an Einträgen, die der einfachverketteten Liste vor der
     * Serialisierung hinzugefügt werden sollen.
     */
    public static final int NUMBER_OF_ELEMENTS_TO_ADD = 10000;

    /**
     * Hauptprogramm.
     * 
     * @param args
     *            Kommandozeilenargumente (werden hier nicht benötigt).
     * @throws IOException
     *             Wird ausgelöst, wenn es während des Serialisierungs-
     *             prozesses zu einem Ein-/Ausgabefehler gekommen ist.
     * @throws ClassNotFoundException
     *             Wird ausgelöst, wenn bei der Deseriali- sierung eine Klasse
     *             benötigt wird, die nicht gefunden werden konnte.
     */
    public static void main(final String[] args) throws IOException, ClassNotFoundException
    {
        final LinkedList stringList = new LinkedList();
        for (int i = 0; i < NUMBER_OF_ELEMENTS_TO_ADD; i++)
        {
            stringList.add(String.valueOf(i));
        }
        System.out.println("Vor der Serialisierung:");
        System.out.println(stringList.toString());
        try (final ObjectOutputStream oOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(TEMPFILE))))
        {
            oOut.writeObject(stringList);
        }
        try (final ObjectInputStream oIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(TEMPFILE))))
        {
            final LinkedList copyList = (LinkedList) oIn.readObject();
            System.out.println("Nach der Deserialisierung:");
            System.out.println(copyList.toString());
        }
    }
}