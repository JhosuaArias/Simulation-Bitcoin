package Node.MessageProtocol;

import java.util.LinkedList;
import java.util.Queue;

public class GeneralNode extends Thread {

    public Queue<Message> messageQueue;

    public GeneralNode () {
        this.messageQueue = new LinkedList<>();
    }

    @Override
    public void run() {
        boolean simulationFinished = false;
        while (!simulationFinished) {
            synchronized (this) {
                try {
                    System.out.println("AS: Waiting for message...");
                    wait();
                    System.out.println("AS: Message received...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simulationFinished = messageQueue.poll().isSimulationFinished();
            }
        }
        System.out.println("AS: A message to end the simulation has been received");
    }

    public synchronized void receiveMessage (Message message) {
        this.messageQueue.add(message);
        this.notify();
    }

}
