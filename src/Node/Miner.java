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
    private Stack<Block> blockChain;
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


    private void blockMining(int block_id) {
        Random random = new Random();
        if(random.nextInt(124) == Simulation.globalProbability) {
            Block miningBlock = new Block(block_id, this.miner_Id,this.asFather.getAs_Id());

            blockChain.add(miningBlock);
            Message message = new Message(false,false, this.blockChain,this);
            this.informToAs(message);
            System.out.println("Ya sali mama: "+ this.miner_Id);
        }
        else {
            System.out.println("No salí mama: "+ this.miner_Id);
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void receiveMessage (Message message) {
        System.out.println("Miner id: "+ miner_Id + "  Message received...");
        super.receiveMessage(message);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.blockMining(currentBlockId);
                //this.receiveMessage();
            } catch (Exception e) {
                e.printStackTrace();
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