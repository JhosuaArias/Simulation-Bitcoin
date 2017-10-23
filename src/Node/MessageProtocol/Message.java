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

    private boolean fromInnerNode;

    private Miner sourceMiner;
    private List<As> readBy_Ases;

    private Stack<Block> propagatedBlockChain;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param propagatedBlock
     */
    public Message(Stack<Block> propagatedBlock, Miner sourceMiner) {
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

    public Stack<Block> getPropagatedBlockChain() {
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

    public void setPropagatedBlockChain(Stack<Block> propagatedBlock) {
        this.propagatedBlockChain = propagatedBlock;
    }
}
