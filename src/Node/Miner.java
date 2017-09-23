package Node;

import java.util.Stack;
import Blockchain.Block;

public class Miner {

    /*Attributes*/
    private As asFather;
    private double actualCurrency;
    private Stack<Block> blockChain;

    /*Constructor*/
    public Miner(As asFather) {
        this.setAsFather(asFather);
    }

    /*Methods*/
    private boolean informToAs() {
        return false;
    }

    public As getAsFather() {
        return asFather;
    }

    /*Getters and Setters*/

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

}