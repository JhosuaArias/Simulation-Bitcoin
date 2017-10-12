package Simulation;

import Blockchain.Block;
import Node.As;
import Node.MessageProtocol.Message;
import Node.Miner;
import org.omg.CORBA.INTERNAL;

import java.util.*;

public class Simulation {
    /*Attributes*/
    private ArrayList<Miner> allMiners;
    private ArrayList<As> allAses;

    private int simulationTime;

    public static int globalProbability;
    /*Constructor*/
    public Simulation() {
        this.allMiners = new ArrayList<>();
        this.allAses = new ArrayList<>();
    }



    /*Methods*/
    public void initSimulation() {
        this.createNetWork();
        for (As as: this.getAllAses()) {
            as.start();
        }

        System.out.println("System: Waiting for nodes to be created...");

        for (As as: this.getAllAses()) {
            while (!as.isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(this.simulationTime * 60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Send end simulation message
        this.allAses.get(0).receiveMessage(new Message(true, false, null, null));

        //Wait for everything to stop
        System.out.println("Waiting for everyone to stop...");
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

        float[] incomePerAS = new float[this.allAses.size()];

        for (Block block : biggestBlockChain) {
            incomePerAS[block.getAsId()] += 12.5;
        }

        System.out.println("");
        for (int i = 0; i < incomePerAS.length; i++) {
            System.out.println("Income for the area of AS #" + i + " is: " + incomePerAS[i] + " Bitcoins.");
        }

    }

    public void createNetWork() {
        Scanner keyboard = new Scanner(System.in);

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

        this.globalProbability = (numberAS * numberNodes) * 3;

        for (int i = 0; i < numberAS ; i++) {
            As as = new As(i);
            for (int j = 0; j < numberNodes ; j++) {
                Miner miner = new Miner(as,(100*(i+1))+j);
                as.registerNewInnerNode(miner);
                allMiners.add(miner);
            }
            allAses.add(as);
        }

        for (int i = 0; i < numberAS ; i++) {
            allAses.get(i).registerAdyacentAs(allAses.get(((i+1)%numberAS)));
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