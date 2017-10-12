package Simulation;

import Node.As;

public class SimulationController {


    private Simulation simulation;
    private SimulationUI simulationUI;

    public SimulationController() {
        this.simulation = new Simulation();
        this.simulationUI = new SimulationUI();
    }

    public void init() {
        simulation.initSimulation();
    }
    public static void main(String[] args) {
        SimulationController simulationController;
        simulationController = new SimulationController();
        simulationController.init();
    }
}
