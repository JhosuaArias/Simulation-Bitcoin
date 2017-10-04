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

    /*Constructor*/
    public Miner(As asFather, int miner_Id) {
        this.setAsFather(asFather);
        this.miner_Id = miner_Id;
    }

    /*Methods*/
    private void informToAs(Message message) {
        this.asFather.receiveMessage(message);
    }


    private void blockMining(int block_id) {
        Random random = new Random();
        Block miningBlock = new Block(block_id, this.miner_Id,this.asFather.getAs_Id());
        while(random.nextInt(9999) != Simulation.globalProbability) {

        }
        blockChain.add(miningBlock);
        Message message = new Message(false,false, this.blockChain,this);
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