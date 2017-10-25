package Simulation;

import Blockchain.Block;
import Node.As;
import Node.Miner;

import java.util.*;

public class Simulation {
    /*Attributes*/
    private Statistics statistics;

    private ArrayList<Miner> allMiners;
    private ArrayList<As> allASes;

    public static int globalProbability;

    private int numberAses;
    private int numberMiners;
    private boolean thereIsAttack;
    private int victimAS;
    private int simulationTime;
    /*Constructor*/

    public Simulation(int numberAses, int numberMiners, boolean makeAttack, int victimAs, int simulationTime) {
        this.allMiners = new ArrayList<>();
        this.allASes = new ArrayList<>();
        this.statistics = new Statistics(numberAses);

        this.numberAses = numberAses;
        this.numberMiners = numberMiners;
        this.thereIsAttack = makeAttack;
        this.victimAS = victimAs;
        this.simulationTime = simulationTime;

        globalProbability = (numberAses * numberMiners) * 3;
    }


    /*Methods*/
    public void initSimulation() {

        this.createNetWork();

        if(this.thereIsAttack) {
            System.out.println("Initializing attack...");
            this.simulatePartitionAttack(this.allASes.get(victimAS));
        }

        // Start the ASes
        for (As as: this.allASes) {
            as.start();
        }

        System.out.println("System: Waiting for nodes to be created...");
        for (As as: this.allASes) {
            while (!as.isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep((this.simulationTime * 60000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //End everything
        for (Miner miner : this.allMiners) {
            miner.setSimulationFinished(true);
        }

        for (As as : this.allASes) {
            as.setSimulationFinished(true);
            as.wakeUp();
        }

        //Wait for everything to stop
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Calculate the income for every AS
        this.calculateIncome();
        this.getLostIncomesAttackedAs();

    }

    private void getLostIncomesAttackedAs() {
        System.out.println("Lost income from As# " + (victimAS+1) + ": " + this.statistics.getLostIncome()[victimAS] + " bitcoins");
    }

    private void calculateIncome() {

        Stack<Block> biggestBlockChain = this.allMiners.get(0).getBlockChain();

        for (Miner miner : this.allMiners) {

            if (miner.getBlockChain().size() > biggestBlockChain.size()) {
                biggestBlockChain = miner.getBlockChain();
            }

        }
        this.statistics.calculateIncomes(biggestBlockChain);

    }

    private void createNetWork() {

        for (int i = 0; i < numberAses; i++) {

            As as = new As(i);
            for (int j = 0; j < numberMiners; j++) {

                Miner miner = new Miner(this, as, "" + i + "-"+ j);
                as.registerNewInnerNode(miner.getMinerListener());
                this.allMiners.add(miner);

            }

            this.allASes.add(as);
        }

        //Register the ASes in a ring like matter, bidirectionally
        for (int i = 0; i < numberAses; i++) {
            this.allASes.get(i).registerAdjacentAs(this.allASes.get(((i + 1) % numberAses))); // Right relation
            this.allASes.get(i).registerAdjacentAs(this.allASes.get(((i + numberAses - 1) % numberAses))); //Left relation
        }

    }



    private void simulatePartitionAttack(As isolatedAs) {
        for (As indexAs: this.allASes) {

            for (Map.Entry<As, Boolean> adjacentAs : indexAs.getAdjacentAses()) {
                //Disables all ases of the attacked node or Disables the attacked node
                if (indexAs == isolatedAs || adjacentAs.getKey() == isolatedAs) {
                    adjacentAs.setValue(false);
                }
            }

        }
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}