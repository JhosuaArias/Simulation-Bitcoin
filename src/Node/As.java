package Node;

import java.util.ArrayList;

public class As {


    /*Attributes*/
    private ArrayList<Miner> connectedMiners;
    private int id;

    /*Constructor*/

    public As(ArrayList<Miner> connectedMiners, int id) {
        this.connectedMiners = connectedMiners;
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
    public ArrayList<Miner> getConnectedMiners() {
        return connectedMiners;
    }

    public void setConnectedMiners(ArrayList<Miner> connectedMiners) {
        this.connectedMiners = connectedMiners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}