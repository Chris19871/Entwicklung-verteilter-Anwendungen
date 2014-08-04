package da.tasks.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Predicate;

public class Main
{
    public static void main(final String[] args)
    {
        final String arni = "Arnold Schwarzenegger";
        final String sly = "Sylvester Stallone";

        final Movie predator = new Movie("Predator", 1987, 107, arni, "Carl Weathers", "Bill Duke");
        final Movie commando = new Movie("Commando", 1985, 90, arni, "Rae Dawn Chong", "Dan Hedaya");
        final Movie cobra = new Movie("Cobra", 1986, 87, sly, "Brigitte Nielsen");
        final Movie demolitionMan = new Movie("Demolition Man", 1993, 115, sly, "Wesley Snipes", "Sandra Bullock");
        final Movie judgeDredd = new Movie("Judge Dredd", 1995, 96, sly, "Rob Schneider", "Jürgen Prochnow");
        final Movie snatch = new Movie("Snatch", 2000, 104, "Brad Pitt", "Benicio Del Toro", "Vinnie Jones", "Jason Statham");
        final Movie fightClub = new Movie("Fight Club", 1999, 139, "Brad Pitt", "Edward Norton", "Helena Bonham Carter");
        final Movie expendables = new Movie("The Expendables", 2010, 103, sly, "Jason Statham", "Dolph Lundgren", arni);

        final Collection<Movie> movies = Arrays.asList(predator, commando, cobra, demolitionMan, judgeDredd, snatch, fightClub, expendables);
        final Collection<String> stars = new TreeSet<>();
        for (final Movie curMovie : movies)
        {
            stars.addAll(curMovie.stars);
        }

        Main.printMoviesToSysout(movies);
        Main.printStarsToSysout(stars);
        Main.printToSysout(movies);
        Main.printToSysout(stars);

        // Anonyme innere Klasse
        Main.printMovies(movies, new MoviePrinter()
        {
            @Override
            public void printMovie(Movie movie)
            {
                System.out.println("Anonym: " + movie);
            }
        });

        MoviePrinter moviePrinter = new MoviePrinter()
        {
            @Override
            public void printMovie(Movie movie)
            {
                System.out.println(movie);
            }
        };

        // Lambda
        System.out.println("+++++++ Lambda +++++++ ");
        movies.stream().forEach(element -> moviePrinter.printMovie(element));

        // Methoden-Referenz
        System.out.println("+++++++ Mehtodenreferenz +++++++ ");
        movies.forEach(moviePrinter::printMovie);

        // Teil d
        Printer printer = new Printer()
        {
            @Override
            public <T> void print(T type)
            {
                System.out.println(type);
            }
        };

        System.out.println("++++++++++ Aufgabe Teil d +++++++++++++");
        movies.forEach(element -> printer.print(element));
        movies.forEach(printer::print);
        stars.forEach(element -> printer.print(element));
        stars.forEach(printer::print);

        // Teil e
        // filter(movies, (movie) -> movie.releaseYear < 2000);
        System.out.println("++++++++++ Filme mit Arni ++++++++++++");
        movies.stream().filter(p -> p.stars.contains("Arnold Schwarzenegger"));
        movies.stream().filter(p -> p.releaseYear < 2000);

        // filter(movies, (movie) -> movie.releaseYear < 2000);
    }

    private static void printMoviesToSysout(final Collection<Movie> movies)
    {
        for (final Movie curMovie : movies)
        {
            System.out.println(curMovie);
        }
    }

    private static void printStarsToSysout(final Collection<String> stars)
    {
        for (final String star : stars)
        {
            System.out.println(star);
        }
    }

    private static <T> void printToSysout(Collection<T> type)
    {
        for (T t : type)
        {
            System.out.println(t);
        }
    }

    @SuppressWarnings("rawtypes")
    private static void printMovies(Collection movies, MoviePrinter printer)
    {
        for (Object movie : movies)
        {
            printer.printMovie((Movie) movie);
        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private static <T> void filter(Collection<Movie> names, Predicate condition)
    {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }

    @SuppressWarnings("unused")
    private static <T, I, O> void print(Collection<T> type, Extractor<I, O> extr)
    {
        for (@SuppressWarnings("unused")
        T t : type)
        {
            // System.out.println(extr.extractValue(I));
        }
    }
}