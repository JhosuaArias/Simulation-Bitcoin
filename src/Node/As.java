package Node;

import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class As extends GeneralNode {


    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private ArrayList<Miner> connectedMiners;
    private ArrayList<As> adjacentAses;
    private int as_Id;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param as_Id the as's id
     */
    public As( int as_Id ) {
        super();
        this.connectedMiners = new ArrayList<>();
        this.adjacentAses = new ArrayList<>();
        this.as_Id = as_Id;
    }

    /**
     * Register a new inner node in the AS's nodes map
     * @param miner the new miner
     */
    public void registerNewInnerNode (Miner miner) {
        this.connectedMiners.add(miner);
        miner.start();
    }

    public void registerAdjacentAs(As as) {
        this.adjacentAses.add(as);
        as.getAdjacentAses().add(this);
    }

    public void removeAdjacentAs(As as) {
        this.adjacentAses.remove(as);
    }
    /**
     * Runnable Method
     */
    @Override
    public void run () {
        super.ready = true;

        while (!super.simulationFinished) {

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
        this.notify();
    }

    private void handleMessage (Message message) {
        System.out.println("AS id: "+ as_Id+ "   Reading new message...");

        this.simulationFinished = message.isSimulationFinished();
        message.newRead(this);

        /* Both methods need to send the message to everyone that doesn't have it */
        sendMessageToInnerNodes(message);
        sendMessageToAdyacentAses(message);
    }

    private void sendMessageToInnerNodes( Message message ) {
        //If there is a specified miner
        if (message.getSourceMiner() != null) {
            for (Miner miner : connectedMiners) {
                if (miner.getMiner_Id() != message.getSourceMiner().getMiner_Id()) {
                    miner.receiveMessage(message);
                }
            }
        } else {
            //If there isn't a miner
            for (Miner miner : connectedMiners) {
                miner.receiveMessage(message);
            }

        }
 
    }

    private void sendMessageToAdyacentAses( Message message ) {
        for (As as : adjacentAses) {
            if(!message.isAlreadyReadBy(as)) {
                as.receiveMessage(message);
            }
        }
        //return false;
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public ArrayList<Miner> getConnectedMiners() {
        return connectedMiners;
    }

    public int getAs_Id() {
        return as_Id;
    }

    public ArrayList<As> getAdjacentAses() {
        return adjacentAses;
    }

    public void setAdjacentAses(ArrayList<As> adjacentAses) {
        this.adjacentAses = adjacentAses;
    }
}