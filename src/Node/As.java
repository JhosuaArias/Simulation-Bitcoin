package Node;

import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class As extends GeneralNode {


    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private HashMap<String, Miner> connectedMiners;
    private ArrayList<As> adyacentAses;
    private int as_Id;
    private boolean simulationFinished;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param as_Id
     */
    public As( int as_Id ) {
        super();
        this.connectedMiners = new HashMap<String, Miner>();
        this.adyacentAses = new ArrayList<As>();
        this.as_Id = as_Id;
    }

    /**
     * Register a new inner node in the AS's nodes map
     * @param miner
     */
    public void registerNewInnerNode (Miner miner) {
        this.connectedMiners.put( Integer.toString(miner.getMiner_Id()), miner);
    }


    public void registerAdyacentAs(As as) {
        this.adyacentAses.add(as);
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
                    System.out.println("AS id: "+ as_Id+ " Waiting for message...");
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
        System.out.println("AS id: "+ as_Id+ "  Ending AS process...");
    }

    @Override
    public synchronized void receiveMessage (Message message) {
        System.out.println("AS id: "+ as_Id+ "  Message received...");
        super.receiveMessage(message);
    }

    public void handleMessage (Message message) {
        System.out.println("AS id: "+ as_Id+ "   Reading new message...");

        this.simulationFinished = message.isSimulationFinished();
        message.newRead(this);

        /* Both methods need to send the message to everyone that doesn't have it */
        sendMessageToInnerNodes(message);
        sendMessageToAdyacentAses(message);
    }

    public boolean sendMessageToInnerNodes( Message message ) {

        return false;
    }

    public void sendMessageToAdyacentAses( Message message ) {
        for (As as : adyacentAses) {
            if(!message.getReadBy_Ases().contains(as)) {
                as.receiveMessage(message);
            }
        }
        //return false;
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public HashMap<String, Miner> getConnectedMiners() {
        return connectedMiners;
    }

    public int getAs_Id() {
        return as_Id;
    }

}