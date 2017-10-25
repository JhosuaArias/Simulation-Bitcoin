package Simulation;

import java.util.ArrayList;
import java.util.Scanner;

public class SimulationController {

    private Scanner keyboard;

    private ArrayList<Simulation> allSimulations;

    private int numberAses;
    private int numberMiners;
    private boolean thereIsAttack;
    private int victimAS;
    private int simulationTime;
    private int numberSimulations;

    public SimulationController() {
        this.keyboard = new Scanner(System.in);
        this.allSimulations = new ArrayList<>();

    }

    public void printStatistics() {
        double[] totalIncomesAverage = new double[this.numberAses];
        double[] lostIncomeAverage = new double[this.numberAses];

        for (int i = 0; i < this.allSimulations.size() ; i++) {
            for (int j = 0 ; j < this.numberAses ; j++) {
                totalIncomesAverage[j] = totalIncomesAverage[j] + this.allSimulations.get(i).getStatistics().getAsesIncomeStatistics()[j];
                lostIncomeAverage[j] = lostIncomeAverage[j] + this.allSimulations.get(i).getStatistics().getLostIncome()[j];
            }
        }

        for (int i = 0; i < this.numberAses ; i++) {
            totalIncomesAverage[i] = totalIncomesAverage[i] / this.allSimulations.size() ;
            lostIncomeAverage[i] = lostIncomeAverage[i] / this.allSimulations.size();
        }

        for (int i = 0; i < this.numberAses; i++) {
            System.out.println("Income average for the area of AS #" + (i+1) + " is: " + totalIncomesAverage[i] + " Bitcoins.");
        }

        for (int i = 0; i < this.numberAses; i++) {
            System.out.println("Lost income average for the area of AS #" + (i+1) + " is: " + lostIncomeAverage[i] + " Bitcoins.");
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

    private void requestNumberSimulations() {
        do {

            try {
                System.out.print("Enter the number of simulations you want to execute: ");
                this.numberSimulations = Integer.parseInt(keyboard.nextLine());

                if (this.numberSimulations <= 0) {
                    System.out.println("Please enter a number bigger than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                this.numberSimulations = -1;
            }

        } while (this.numberSimulations <= 0);

    }

    public void init() {

        this.requestSimulationTime();
        this.numberAses = requestNumberASes();
        this.numberMiners = requestNumberNodes();
        this.requestAttack();

        if (this.thereIsAttack) {
            requestVictimAS(numberAses);
        }

        this.requestNumberSimulations();

        for (int i = 0; i < numberSimulations; i++) {
            this.allSimulations.add(new Simulation(this.numberAses,this.numberMiners,this.thereIsAttack,this.victimAS,this.simulationTime));
        }

        for (Simulation simulation: this.allSimulations) {
            simulation.initSimulation();
            System.out.println("Waiting...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.printStatistics();

    }
    public static void main(String[] args) {
        SimulationController simulationController;
        simulationController = new SimulationController();
        simulationController.init();
    }
}
