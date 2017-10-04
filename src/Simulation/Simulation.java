package Simulation;

import Node.As;
import Node.Miner;

import java.util.ArrayList;

public class Simulation {
    /*Attributes*/
    private ArrayList<Miner> allMiners;
    private ArrayList<As> allAses;

    public static int globalProbability;
    /*Constructor*/
    public Simulation(int globalProbability) {
        this.allMiners = new ArrayList<Miner>();
        this.allAses = new ArrayList<As>();
        this.globalProbability = globalProbability;
    }



    /*Methods*/
    public void initSimulation() {

    }

    public void createNetWork() {
        for (int i = 0; i < 10 ; i++) {
            As as = new As(i);
            for (int j = 0; j < 50 ; j++) {
                Miner miner = new Miner(as,(100*(i+1))+j);
                as.registerNewInnerNode(miner);
                allMiners.add(miner);
            }
            allAses.add(as);
        }

        for (int i = 0; i < 10 ; i++) {
            allAses.get(i).registerAdyacentAs(allAses.get(((i+1)%10)));
        }
    }

    /*Getters and Setters*/

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