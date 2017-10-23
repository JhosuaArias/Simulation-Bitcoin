package Node.MessageProtocol;

import java.util.LinkedList;
import java.util.Queue;

public class GeneralNode extends Thread {

    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    protected volatile Queue<Message> messageQueue;
    protected boolean ready;
    protected boolean simulationFinished;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     */
    public GeneralNode () {
        this.messageQueue = new LinkedList<>();
    }

    public void receiveMessage (Message message) {
        this.messageQueue.add(message);
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public boolean isReady() {
        return ready;
    }

    public synchronized Queue<Message> getMessageQueue() {
        return messageQueue;
    }

    public void setSimulationFinished(boolean simulationFinished) {
        this.simulationFinished = simulationFinished;
    }
}
