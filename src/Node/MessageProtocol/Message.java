package Node.MessageProtocol;

import Blockchain.Block;
import Node.As;
import Node.Miner;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Message {

    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private boolean simulationFinished;
    private boolean simulationStarted;
    private boolean fromInnerNode;

    private Miner sourceMiner;
    private List<As> readBy_Ases;

    private Stack<Block> propagatedBlockChain;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param simulationFinished
     * @param simulationStarted
     * @param propagatedBlock
     */
    public Message(boolean simulationFinished, boolean simulationStarted, Stack<Block> propagatedBlock, Miner sourceMiner) {
        this.simulationFinished = simulationFinished;
        this.simulationStarted = simulationStarted;
        this.propagatedBlockChain = propagatedBlock;
        this.sourceMiner = sourceMiner;
        this.readBy_Ases = new ArrayList<>();
    }

    public void newRead(As as) {
        this.readBy_Ases.add(as);
    }

    public boolean isAlreadyReadBy (As as) {
        return this.readBy_Ases.contains(as);
    }

    //------------------------------------------------------------------------------
    //  Standard Setter and Getter section
    //------------------------------------------------------------------------------

    public boolean isSimulationFinished() {
        return simulationFinished;
    }

    public boolean isSimulationStarted() {
        return simulationStarted;
    }

    public Stack<Block> getPropagatedBlock() {
        return propagatedBlockChain;
    }

    public boolean isFromInnerNode() {
        return fromInnerNode;
    }

    public void setFromInnerNode(boolean fromInnerNode) {
        this.fromInnerNode = fromInnerNode;
    }

    public Miner getSourceMiner() {
        return sourceMiner;
    }

    public List<As> getReadBy_Ases() {
        return readBy_Ases;
    }

    public void setPropagatedBlock(Stack<Block> propagatedBlock) {
        this.propagatedBlockChain = propagatedBlock;
    }
}
