package Node.MessageProtocol;

import java.util.LinkedList;
import java.util.Queue;

public class GeneralNode extends Thread {

    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    protected Queue<Message> messageQueue;
    protected boolean ready;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     */
    public GeneralNode () {
        this.messageQueue = new LinkedList<>();
    }

    public synchronized void receiveMessage (Message message) {
        this.messageQueue.add(message);
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public boolean isReady() {
        return ready;
    }
}
