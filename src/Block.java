public class Block {
    /*Attributes*/
    private int blockId;
    private int nodeId;
    private int asId;
    /*Constructor*/
    public Block(int blockId, int nodeId, int asId) {
        this.blockId = blockId;
        this.setNodeId(nodeId);
        this.setAsId(asId);
    }
    /*Methods*/

    /*Setters and Getters*/
    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
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