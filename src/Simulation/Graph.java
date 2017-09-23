package Simulation;

import Node.As;

import java.util.ArrayList;

public class Graph {

    private ArrayList<As> allAses;
    private boolean[][] asRelations;
    private final int maximumASes = 10;

    public Graph () {

        this.asRelations = new boolean[maximumASes][maximumASes];
        this.allAses = new ArrayList<As>();
    }

    //------------------------------------------------------------------------------

    public void add_Uni_AsRelation (int asX_id, int asY_id) {

        this.asRelations[asX_id][asY_id] = true;
    }

    public void remove_Uni_AsRelation (int asX_id, int asY_id) {

        this.asRelations[asX_id][asY_id] = false;
    }

    public void add_Bi_AsRelation (int asX_id, int asY_id) {

        this.asRelations[asX_id][asY_id] = true;
        this.asRelations[asY_id][asX_id] = true;
    }

    public void remove_Bi_AsRelation (int asX_id, int asY_id) {

        this.asRelations[asX_id][asY_id] = false;
        this.asRelations[asY_id][asX_id] = true;
    }

    public As getFirstAs(As asFather) {
        As first = null;
        int index = 0;
        boolean repeat = true;
        while (index < this.allAses.size() && repeat) {
            if(this.asRelations[asFather.getAs_Id()][index]) {
                first = this.allAses.get(index);
                repeat = false;
            }
            else {
                index++;
            }
        }
        return first;
    }

    public As getNextAs(As asFather, As asChild) {
        As next = null;
        int index = asChild.getAs_Id();
        boolean repeat = true;
        while (index < this.allAses.size() && repeat) {
            if(this.asRelations[asChild.getAs_Id()][index]) {
                next = this.allAses.get(index);
                repeat = false;
            }
            else {
                index++;
            }
        }
        return next;
    }

    public boolean addAs(As newAs) {
        if(allAses.size()<=10) {
            allAses.add(newAs);
            return true;
        }
        else {
            return false;
        }
    }
}
