package Node.MessageProtocol;

import Blockchain.Block;
import Node.As;
import Node.Miner;

import java.util.ArrayList;
import java.util.List;

public class Message {

    //------------------------------------------------------------------------------
    //  Attributes
    //------------------------------------------------------------------------------

    private boolean simulationFinished;
    private boolean simulationStarted;
    private boolean fromInnerNode;

    private Miner sourceMiner;
    private List<As> readBy_Ases;

    private Block propagatedBlock;

    //------------------------------------------------------------------------------

    /**
     * Constructor of the class
     * @param simulationFinished
     * @param simulationStarted
     * @param propagatedBlock
     */
    public Message(boolean simulationFinished, boolean simulationStarted, Block propagatedBlock, Miner sourceMiner) {
        this.simulationFinished = simulationFinished;
        this.simulationStarted = simulationStarted;
        this.propagatedBlock = propagatedBlock;
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

    public Block getPropagatedBlock() {
        return propagatedBlock;
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
}
