package server;

/**
 * The Command interface defines a contract for classes that represent executable commands
 * within a server application.
 */
public interface Command {

    /**
     * Executes the specific command logic defined by implementing classes.
     */
    void execute();
}
