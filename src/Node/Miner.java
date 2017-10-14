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
    private double actualCurrency;
    private Stack<Block> blockChain; /*A lo mejor esto es volatile tambien*/
    private int miner_Id;
    private int currentBlockId;
    /*Constructor*/
    public Miner(As asFather, int miner_Id) {
        super();
        this.blockChain = new Stack<Block>();
        this.setAsFather(asFather);
        this.miner_Id = miner_Id;
        this.currentBlockId = 0;
    }

    /*Methods*/
    private void informToAs(Message message) {
        this.asFather.receiveMessage(message);
    }


    private synchronized void  blockMining(int block_id) {
        Random random = new Random();
        if(random.nextInt(Simulation.globalProbability) == Simulation.globalProbability-1) {
            Block miningBlock = new Block(block_id, this.miner_Id,this.asFather.getAs_Id());

            System.out.println("Block " + this.currentBlockId + " have been mined by "+ this.miner_Id);
            blockChain.add(miningBlock);
            this.currentBlockId++;

            Message message = new Message(false,false, this.blockChain,this);
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
        System.out.println("Miner id: "+ miner_Id + "  Message received...");
        super.receiveMessage(message);
    }

    private void handleMessage(Message message) {

        this.simulationFinished = message.isSimulationFinished();

        if (message.getPropagatedBlockChain() != null) {

            if (message.getPropagatedBlockChain().size() > this.blockChain.size()) {
                this.blockChain = (Stack<Block>) message.getPropagatedBlockChain().clone();
                this.currentBlockId = blockChain.peek().getBlockId() + 1;
            }

        }
    }
    @Override
    public void run() {
        while (!super.simulationFinished) {
            synchronized (this) {
                try {
                    wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (!this.messageQueue.isEmpty()) {
                    Message currMessage = this.messageQueue.poll();
                    this.handleMessage(currMessage);
                }

                this.blockMining(this.currentBlockId);
            }
        }
    }



    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------
    public As getAsFather() {
        return asFather;
    }

    public void setAsFather(As asFather) {
        this.asFather = asFather;
    }

    public double getActualCurrency() {
        return actualCurrency;
    }

    public void setActualCurrency(double actualCurrency) {
        this.actualCurrency = actualCurrency;
    }

    public Stack<Block> getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(Stack<Block> blockChain) {
        this.blockChain = blockChain;
    }

    public int getMiner_Id() {
        return miner_Id;
    }

}