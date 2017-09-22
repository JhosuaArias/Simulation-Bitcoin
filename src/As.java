import java.util.ArrayList;

public class As {


    /*Attributes*/
    private ArrayList<Node> connectedNodes;
    private int id;

    /*Constructor*/

    public As(ArrayList<Node> connectedNodes, int id) {
        this.connectedNodes = connectedNodes;
        this.id = id;
    }

    /*Methods*/
    public boolean informNodes() {
        return false;
    }

    public boolean informAdyacentAses() {
        return false;
    }

    /*Gets and sets*/
    public ArrayList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(ArrayList<Node> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}