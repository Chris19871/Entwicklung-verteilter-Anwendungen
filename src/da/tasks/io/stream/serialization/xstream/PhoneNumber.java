package da.tasks.io.stream.serialization.xstream;

public class PhoneNumber
{
    private int code;
    private String number;
    private SomeData someData;

    public PhoneNumber(int code, String number, SomeData data)
    {
        this.code = code;
        this.number = number;
        this.someData = data;
    }

    public String toString()
    {
        return code + ", " + number + " - " + someData.toString();
    }
}