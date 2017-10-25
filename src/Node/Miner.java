package Node;

import java.util.Random;
import java.util.Stack;
import Blockchain.Block;
import Node.MessageProtocol.GeneralNode;
import Node.MessageProtocol.Message;
import Simulation.Simulation;

public class Miner extends GeneralNode {

    /*Attributes*/
    private As asFather;
    private Stack<Block> blockChain;
    private String miner_Id;
    private int currentBlockId;
    private MinerListener minerListener;
    Simulation simulation;

    /*Constructor*/
    public Miner(Simulation simulation, As asFather, String miner_Id) {
        this.simulation = simulation;
        this.blockChain = new Stack<>();
        this.asFather = asFather;
        this.miner_Id = miner_Id;
        this.currentBlockId = 0;

        this.minerListener= new MinerListener(miner_Id, this);
        this.minerListener.start(); //So the listeners start listening at once

    }

    /*Methods*/
    private void informToAs(Message message) {
        this.asFather.receiveMessage(message);
    }

    private void  blockMining(int block_id) {

        Random random = new Random();

        if(random.nextInt(Simulation.globalProbability) == Simulation.globalProbability-1) {

            Block miningBlock = new Block(block_id, this.miner_Id, this.asFather.getAs_Id());

            System.out.println("Block " + this.currentBlockId + " has been mined by "+ this.miner_Id);
            this.simulation.getStatistics().addBlockMined(this.asFather.getAs_Id());
            this.blockChain.add(miningBlock);
            this.currentBlockId++;

            Message message = new Message(this.blockChain,this);
            this.informToAs(message);

        }
        else {
            System.out.println("Block "+ this.currentBlockId + " is being mined by " + this.miner_Id);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void receiveMessage (Message message) {
        System.err.println("The miner itself does not receive messages, please call the listener!");
        System.exit(1);
    }

    private void handleMessage(Message message) {

        if (message.getPropagatedBlockChain() != null) {

            if (message.getPropagatedBlockChain().size() > this.blockChain.size()) {
                this.blockChain = (Stack<Block>) message.getPropagatedBlockChain().clone();
                this.currentBlockId = blockChain.peek().getBlockId() + 1;
            }

        }

    }

    @Override
    public void run() {

        // Wait a little time before mining
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!super.simulationFinished) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (!this.minerListener.getMessageQueue().isEmpty()) {
                Message currMessage = this.minerListener.getMessage();
                this.handleMessage(currMessage);
            }

            this.blockMining(this.currentBlockId);
        }


        this.minerListener.setSimulationFinished(true);
        System.out.println("-- Miner id: "+ this.miner_Id + " /  Ending Miner process...");

    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------


    public Stack<Block> getBlockChain() {
        return blockChain;
    }

    public String getMiner_Id() {
        return miner_Id;
    }

    public MinerListener getMinerListener() {
        return minerListener;
    }
}