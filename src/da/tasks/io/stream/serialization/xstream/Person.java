package da.tasks.io.stream.serialization.xstream;

public class Person
{
    private String firstname;
    private String lastname;
    private PhoneNumber phone;
    private PhoneNumber fax;

    public Person(String first, String last)
    {
        this.firstname = first;
        this.lastname = last;
    }

    public void setPhone(PhoneNumber phone)
    {
        this.phone = phone;
    }

    public void setFax(PhoneNumber fax)
    {
        this.fax = fax;
    }

    public String toString()
    {
        return lastname + ", " + firstname + "(Fax: " + this.fax.toString() + ", " + this.phone.toString() + ")";
    }
}
