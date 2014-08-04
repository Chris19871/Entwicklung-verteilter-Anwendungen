package da.tasks.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Movie {
  public final String title;
  public final int releaseYear;
  public final int lengthInMinutes;
  public final Collection<String> stars;

  public Movie(final String title, final int releaseYear, final int lengthInMinutes,
      final String... stars) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.lengthInMinutes = lengthInMinutes;
    this.stars = Collections.unmodifiableList(Arrays.asList(stars));
  }

  @Override public String toString() {
    return String.format("%s (%d)", this.title, this.releaseYear);
  }
}
