package da.lecture.io.stream.serialization;

import java.io.Serializable;

/**
 * Simple Klasse zur Repräsentation eines Punktes.
 */
@SuppressWarnings("serial")
public class Point implements Serializable
{
    /** X-Koordinate. */
    private int x;
    /** Y-Koordinate. */
    private int y;

    private Point ref;
    private static long serialVersionUID;

    /**
     * Initialisiert eine neue {@link Point}-Instanz mit den ��bergebenen
     * Argumenten.
     * 
     * @param x
     *            X-Koordinate
     * @param y
     *            Y-Koordinate
     */
    public Point(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setRef(Point point)
    {
        this.ref = point;
    }

    public Point getRef()
    {
        return this.ref;
    }

    /**
     * Liefert die X-Koordinate des Punktes zur��ck.
     * 
     * @return X-Koordinate.
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Setzt die X-Koordindate des Punktes.
     * 
     * @param x
     *            Neue X-Koordinate
     */
    public void setX(final int x)
    {
        this.x = x;
    }

    /**
     * Liefert die Y-Koordinate des Punktes zur��ck.
     * 
     * @return Y-Koordinate.
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * Setzt die Y-Koordindate des Punktes.
     * 
     * @param y
     *            Neue Y-Koordinate
     */
    public void setY(final int y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return this.x + ", " + this.y;
    }
}
