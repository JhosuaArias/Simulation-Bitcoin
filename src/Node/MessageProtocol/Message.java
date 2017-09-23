package Node.MessageProtocol;

import Blockchain.Block;

public class Message {

    private boolean simulationFinished;
    private boolean simulationStarted;
    private boolean fromInnerNode;

    private Block propagatedBlock;

    public Message(boolean simulationFinished, boolean simulationStarted, Block propagatedBlock) {
        this.simulationFinished = simulationFinished;
        this.simulationStarted = simulationStarted;
        this.propagatedBlock = propagatedBlock;
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
}
