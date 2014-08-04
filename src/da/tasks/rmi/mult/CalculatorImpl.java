package da.tasks.rmi.mult;

public class CalculatorImpl implements CalculatorInterface
{
    // private static final long serialVersionUID = 1L;

    public CalculatorImpl()
    {

        // System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int calculate(int i, int j)
    {
        // TODO Auto-generated method stub
        return i * j;
    }
}