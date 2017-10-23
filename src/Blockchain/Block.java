package Blockchain;

public class Block {
    /*Attributes*/
    private int blockId;
    private String nodeId;
    private int asId;
    /*Constructor*/
    public Block(int blockId, String nodeId, int asId) {
        this.blockId = blockId;
        this.nodeId = nodeId;
        this.asId = asId;
    }
    /*Methods*/

    /*Setters and Getters*/
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getAsId() {
        return asId;
    }

    public void setAsId(int asId) {
        this.asId = asId;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }
}