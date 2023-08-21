package server;

/**
 * The Controller class represents a component that interacts with a Command to execute a specific action.
 * It encapsulates the behavior of setting a Command object and executing the associated action when needed.
 */
public class Controller {

    private Command command;

    /**
     * Sets the Command object to be executed by this Controller.
     *
     * @param command The Command object to be set.
     */
    public void setCommand(final Command command) {
        this.command = command;
    }

    /**
     * Executes the command stored in this Controller.
     * This method invokes the execute() method of the stored Command object to perform the associated action.
     */
    public void executeCommand() {
        command.execute();
    }
}
