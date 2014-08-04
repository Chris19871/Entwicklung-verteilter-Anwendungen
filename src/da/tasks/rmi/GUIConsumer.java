package da.tasks.rmi;

public interface GUIConsumer<T>
{
    void doGUIManipulation(T arg);
}