
public class Graph {

    private boolean[][] asRelations;
    private final int maximumASes = 10;

    public Graph () {
        this.asRelations = new boolean[maximumASes][maximumASes];
    }

    //------------------------------------------------------------------------------

    public void add_asRelation (int asX_id, int asY_id) {
        this.asRelations[asX_id][asY_id] = true;
    }

    public void remove_asRelation (int asX_id, int asY_id) {
        this.asRelations[asX_id][asY_id] = false;
    }

}
