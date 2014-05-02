package da.tasks.io.stream.serialization.xstream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamTest
{
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException
    {
        XStream xstream = new XStream(new DomDriver());

        xstream.alias("personTEst", Person.class);
        xstream.alias("phonenumberTEST", PhoneNumber.class);

        Person joe = new Person("Joe", "Walnes");
        joe.setPhone(new PhoneNumber(123, "1234-456", new SomeData(66)));
        joe.setFax(new PhoneNumber(123, "9999-999", new SomeData(77)));

        String xml = xstream.toXML(joe);

        new DataOutputStream(new FileOutputStream("xmlTest.xml")).writeUTF(xml);

        DataInputStream in = new DataInputStream(new FileInputStream("xmlTest.xml"));

        Person newJoe = (Person) xstream.fromXML(in.readUTF());
        System.out.println(newJoe.toString());
    }
}
