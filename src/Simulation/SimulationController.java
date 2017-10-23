package Simulation;

import Node.As;

public class SimulationController {


    private Simulation simulation;

    public SimulationController() {
        this.simulation = new Simulation();
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
