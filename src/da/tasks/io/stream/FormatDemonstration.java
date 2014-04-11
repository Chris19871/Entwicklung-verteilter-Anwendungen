package da.tasks.io.stream;

import java.io.PrintStream;

public class FormatDemonstration
{
    public static void main(String[] args)
    {
        // Bekommt als Argument den Output-Stream.
        PrintStream ps = new PrintStream(System.out);
        String s = "super";

        // Kann beliebig viele Parameter bekommen, wie in C++.
        ps.format("Das ist ein %s Test %d", s, 1);
    }
}