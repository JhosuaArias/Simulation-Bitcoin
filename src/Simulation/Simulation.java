package Simulation;

import Node.As;
import Node.Miner;

import java.util.ArrayList;

public class Simulation {
    /*Attributes*/
    private Graph graph;
    private ArrayList<Miner> allMiners;
    private ArrayList<As> allAses;

    /*Constructor*/
    public Simulation() {
        graph = new Graph();
        allMiners = new ArrayList<Miner>();
        allAses = new ArrayList<As>();
    }

    public Graph getGraph() {
        return graph;
    }


    /*Methods*/
    public void initSimularion() {

    }

    public void createNetWork() {
        for (int i = 0; i < 10 ; i++) {
            As as = new As(i);
            for (int j = 0; j < 50 ; j++) {
                as.registerNewInnerNode(new Miner(as,(100*(i+1))+j));
            }
            allAses.add(as);
            graph.addAs(as);
        }

        for (int i = 0; i < 10 ; i++) {
            graph.add_Bi_AsRelation(i,(i%10)+1);
        }
    }
    /*Getters and Setters*/
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<Miner> getAllMiners() {
        return allMiners;
    }

    public void setAllMiners(ArrayList<Miner> allMiners) {
        this.allMiners = allMiners;
    }

    public ArrayList<As> getAllAses() {
        return allAses;
    }

    public void setAllAses(ArrayList<As> allAses) {
        this.allAses = allAses;
    }

}