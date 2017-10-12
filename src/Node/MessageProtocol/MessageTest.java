package Node.MessageProtocol;

import Node.As;
import Simulation.Simulation;

public class MessageTest {


    public static void main(String[] args) {
        Simulation sim = new Simulation(123);
        sim.createNetWork();
        for (As as: sim.getAllAses()) {
            as.start();
        }

        System.out.println("System: Waiting for nodes to be created...");

        for (As as: sim.getAllAses()) {
            while (!as.isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
  /**      Message testMessage = new Message(true, false, null, null);
        System.out.println("System: Message Sent");
        sim.getAllAses().get(0).receiveMessage(testMessage);**/
    }

}
