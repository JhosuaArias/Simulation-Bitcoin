package Node;

import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;


public class MinerListener extends GeneralNode {

    /*Attributes*/
    private String miner_Id;
    private Miner miner;

    MinerListener (String miner_Id, Miner miner) {
        super();
        this.miner_Id = miner_Id;
        this.miner = miner;
    }

    public synchronized void receiveMessage (Message message) {
        System.out.println("-- Miner id: "+ miner_Id + " /  Message received...");
        super.receiveMessage(message);
    }

    @Override
    public void run () {

        super.ready = true;
        this.miner.start();

        while (!super.simulationFinished) {

            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println("-- Miner id: "+ this.miner_Id + " /  Ending Miner Listener process...");
    }

    public String getMiner_Id() {
        return miner_Id;
    }

    synchronized Message getMessage() {
        return this.messageQueue.poll();
    }

}
