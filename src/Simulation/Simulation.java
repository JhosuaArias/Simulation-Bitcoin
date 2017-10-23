package Simulation;

import Blockchain.Block;
import Node.As;
import Node.Miner;

import java.util.*;

public class Simulation {
    /*Attributes*/
    private ArrayList<Miner> allMiners;
    private ArrayList<As> allASes;

    private int simulationTime;
    private boolean thereIsAttack;
    private int victimAS;

    private Scanner keyboard;

    public static int globalProbability;
    /*Constructor*/
    public Simulation() {
        this.allMiners = new ArrayList<>();
        this.allASes = new ArrayList<>();
        this.keyboard = new Scanner(System.in);
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

    }

    private void calculateIncome() {

        Stack<Block> biggestBlockChain = this.allMiners.get(0).getBlockChain();

        for (Miner miner : this.allMiners) {

            if (miner.getBlockChain().size() > biggestBlockChain.size()) {
                biggestBlockChain = miner.getBlockChain();
            }

        }

        float[] incomePerAS = new float[this.allASes.size()];

        for (Block block : biggestBlockChain) {
            incomePerAS[block.getAsId()] += 12.5;
        }

        System.out.println("");
        for (int i = 0; i < incomePerAS.length; i++) {
            System.out.println("Income for the area of AS #" + i + " is: " + incomePerAS[i] + " Bitcoins.");
        }

    }

    private void createNetWork() {

        requestSimulationTime();
        int totalASes = requestNumberASes();
        int totalNodes = requestNumberNodes();

        requestAttack();
        if (this.thereIsAttack) {
            requestVictimAS(totalASes);
        }

        globalProbability = (totalASes * totalNodes) * 3;

        for (int i = 0; i < totalASes; i++) {

            As as = new As(i);
            for (int j = 0; j < totalNodes; j++) {

                Miner miner = new Miner(as, "" + i + "-"+ j);
                as.registerNewInnerNode(miner.getMinerListener());
                this.allMiners.add(miner);

            }

            this.allASes.add(as);
        }

        //Register the ASes in a ring like matter, bidirectionally
        for (int i = 0; i < totalASes; i++) {
            this.allASes.get(i).registerAdjacentAs(this.allASes.get(((i + 1) % totalASes))); // Right relation
            this.allASes.get(i).registerAdjacentAs(this.allASes.get(((i + totalASes - 1) % totalASes))); //Left relation
        }

    }

    private void requestVictimAS(int totalASes) {
        do {

            try {
                System.out.print("Please enter the AS number you want to isolate (between 1 - " + totalASes + "): ");
                this.victimAS = Integer.parseInt(keyboard.nextLine());

                if (this.victimAS <= 0 || this.victimAS > totalASes) {
                    System.out.println("Please enter a number, from 1 to "+ totalASes +".");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                this.victimAS = -1;
            }


        } while (this.victimAS <= 0 || this.victimAS > totalASes);

        this.victimAS--; //Because the IDs start at 0

    }

    private void requestAttack() {
        String userChoice;

        do {
            System.out.print("Do you want to make an partition attack [y/n]: ");
            userChoice = keyboard.nextLine();

            if (!userChoice.equals("y") && !userChoice.equals("n")) {
                System.out.println("Please enter a valid option...");
            }

        } while (!userChoice.equals("y") && !userChoice.equals("n"));

        this.thereIsAttack = userChoice.equals("y");
    }

    private int requestNumberNodes() {
        int numberNodes;
        do {

            try {
                System.out.print("Please enter the number of nodes you want: ");
                numberNodes = Integer.parseInt(keyboard.nextLine());

                if (numberNodes <= 0 || numberNodes > 50) {
                    System.out.println("Please enter a number, from 1 to 50.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                numberNodes = -1;
            }


        } while (numberNodes <= 0 || numberNodes > 50);

        return numberNodes;
    }

    private int requestNumberASes() {
        int numberAS;
        do {

            try {
                System.out.print("Please enter the number of ASes you want: ");
                numberAS = Integer.parseInt(keyboard.nextLine());

                if (numberAS <= 0 || numberAS > 10) {
                    System.out.println("Please enter a number, from 1 to 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                numberAS = -1;
            }

        } while (numberAS <= 0 || numberAS > 10);

        return numberAS;
    }

    private void requestSimulationTime() {
        do {

            try {
                System.out.print("Please enter the time (in minutes) for the simulation: ");
                this.simulationTime = Integer.parseInt(keyboard.nextLine());

                if (this.simulationTime <= 0) {
                    System.out.println("Please enter a number bigger than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                this.simulationTime = -1;
            }

        } while (this.simulationTime <= 0);

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

}