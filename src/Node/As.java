package Node;

import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class As extends GeneralNode {


    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private ArrayList<MinerListener> connectedMiners;
    private ArrayList<Map.Entry<As, Boolean>> adjacentAses; //Manages AS and state
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
     * @param minerListener the new miner
     */
    public void registerNewInnerNode (MinerListener minerListener) {
        this.connectedMiners.add(minerListener);
    }

    public void registerAdjacentAs(As as) {

        Map.Entry<As, Boolean> newAs = new AbstractMap.SimpleEntry<>(as, true);
        this.adjacentAses.add(newAs);

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
                    System.out.println("AS id: " + as_Id + " Waiting for message...");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (!this.messageQueue.isEmpty()) {
                Message currMessage = this.messageQueue.poll();
                this.handleMessage(currMessage);
            }


        }
        System.out.println("AS id: "+ as_Id+ "  Ending AS process...");
        super.ready = false;
    }

    public synchronized void receiveMessage (Message message) {
        message.newRead(this);
        System.out.println("AS id: "+ as_Id+ "  Message received...");
        super.receiveMessage(message);
        this.notify();
    }

    private void handleMessage (Message message) {
        System.out.println("AS id: "+ as_Id+ "   Reading new message...");

        /* Both methods need to send the message to everyone that doesn't have it */
        sendMessageToInnerNodes(message);
        sendMessageToAdjacentAses(message);
    }

    private void sendMessageToInnerNodes( Message message ) {

        //If there is a specified miner
        if (message.getSourceMiner() != null) {
            for (MinerListener miner : connectedMiners) {
                if (!miner.getMiner_Id().equals(message.getSourceMiner().getMiner_Id())) {
                    miner.receiveMessage(message);
                }
            }
        }
        //If there isn't a miner
        else {

            for (MinerListener miner : connectedMiners) {
                miner.receiveMessage(message);
            }

        }
 
    }

    private void sendMessageToAdjacentAses(Message message ) {
        for (Map.Entry<As, Boolean> as : adjacentAses) {
            if(!message.isAlreadyReadBy(as.getKey()) && as.getValue() && as.getKey().isReady() ) {
                System.err.println("Mandando a... " + as.getKey().getAs_Id());
                as.getKey().receiveMessage(message);
            }
        }
        System.err.println("All messages sent to adjacent nodes!");
    }

    public synchronized  void wakeUp () {
        this.notify();
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------


    public int getAs_Id() {
        return as_Id;
    }

    public ArrayList<Map.Entry<As, Boolean>> getAdjacentAses() {
        return adjacentAses;
    }

}