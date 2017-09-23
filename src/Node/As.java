package Node;

import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;

import java.util.HashMap;

public class As extends GeneralNode {


    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private HashMap<String, Miner> connectedMiners;
    private String as_Id;
    private boolean simulationFinished;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param as_Id
     */
    public As( String as_Id ) {
        super();
        this.as_Id = as_Id;
    }

    /**
     * Register a new inner node in the AS's nodes map
     * @param miner
     */
    public void registerNewInnerNode (Miner miner) {
        this.connectedMiners.put(miner.getMiner_Id(), miner);
    }

    /**
     * Runnable Method
     */
    @Override
    public void run () {
        super.ready = true;

        while (!this.simulationFinished) {

            synchronized (this) {
                try {
                    System.out.println("AS: Waiting for message...");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (!this.messageQueue.isEmpty()) {
                    Message currMessage = this.messageQueue.poll();
                    this.handleMessage(currMessage);
                }

            }

        }
        System.out.println("AS: A message to end the simulation has been received");
    }

    @Override
    public synchronized void receiveMessage (Message message) {
        System.out.println("AS: Message received...");
        super.receiveMessage(message);
    }

    public void handleMessage (Message message) {
        this.simulationFinished = message.isSimulationFinished();

        /* Both methods need to send the message to everyone that doesn't have it */
        sendMessageToInnerNodes(message);
        sendMessageToAdyacentAses(message);
    }

    public boolean sendMessageToInnerNodes( Message message ) {
        return false;
    }

    public boolean sendMessageToAdyacentAses( Message message ) {
        return false;
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public HashMap<String, Miner> getConnectedMiners() {
        return connectedMiners;
    }

    public void setConnectedMiners(HashMap<String, Miner>  connectedMiners) {
        this.connectedMiners = connectedMiners;
    }

    public String getAs_Id() {
        return as_Id;
    }

}