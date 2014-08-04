package da.tasks.lambda;

public interface Extractor<I, O>
{
    O extractValue(final I input);
}