package Node.MessageProtocol;

import Node.As;

public class MessageTest {

    public static void main(String[] args) {
        As testAS = new As (1);
        testAS.start();
        System.out.println("System: Waiting for nodes to be created...");

        while (!testAS.isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message testMessage = new Message(true, false, null, null);
        System.out.println("System: Message Sent");
        testAS.receiveMessage(testMessage);
    }

}
