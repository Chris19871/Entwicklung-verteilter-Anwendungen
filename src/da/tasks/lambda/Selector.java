package da.tasks.lambda;

import java.util.Collection;
import java.util.function.Predicate;

public interface Selector
{
    <T> void filter(Collection<T> names, Predicate condition);
}
