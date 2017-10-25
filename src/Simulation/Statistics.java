package Simulation;


import Blockchain.Block;

import java.util.Arrays;
import java.util.Stack;

public class Statistics {
    private double[] asesIncomeStatistics;
    private int[] asesMinedBlocks;
    private double[] lostIncome;

    public Statistics(int numberAses) {
        this.asesIncomeStatistics = new double[numberAses];
        this.asesMinedBlocks = new int[numberAses];
        this.lostIncome = new double[numberAses];
    }

    public void addBlockMined(int asId) {
        this.getAsesMinedBlocks()[asId]++;
    }

    public void calculateIncomes(Stack<Block> biggestBlockChain) {

        double[] incomePerAS = new double [this.asesIncomeStatistics.length];

        for (Block block : biggestBlockChain) {
            incomePerAS[block.getAsId()] += 12.5;
        }

        System.out.println("");
        for (int i = 0; i < incomePerAS.length; i++) {
            System.out.println("Income for the area of AS #" + (i+1) + " is: " + incomePerAS[i] + " Bitcoins.");
        }
        this.asesIncomeStatistics = incomePerAS;
        this.calculateLostIncome();
    }

    public double[] getAsesIncomeStatistics() {
        return asesIncomeStatistics;
    }

    public void setAsesIncomeStatistics(double[] asesIncomeStatistics) {
        this.asesIncomeStatistics = asesIncomeStatistics;
    }

    public int[] getAsesMinedBlocks() {
        return asesMinedBlocks;
    }

    public void setAsesMinedBlocks(int[] asesMinedBlocks) {
        this.asesMinedBlocks = asesMinedBlocks;
    }

    private void calculateLostIncome() {
        for (int i = 0; i < this.lostIncome.length; i++) {
            this.lostIncome[i] = this.asesIncomeStatistics[i] - ((double)this.asesMinedBlocks[i]*12.5);
        }

    }

    public double[] getLostIncome() {
        return lostIncome;
    }

    public void setLostIncome(double[] lostIncome) {
        this.lostIncome = lostIncome;
    }

    public double getLostIncomeFromAs(int i) {
        return this.lostIncome[i];
    }
}
