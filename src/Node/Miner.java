package Node;

import java.util.Stack;
import Blockchain.Block;

public class Miner {

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
    private boolean informToAs() {
        return false;
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