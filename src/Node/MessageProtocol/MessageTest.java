package Node.MessageProtocol;

public class MessageTest {

    public static void main(String[] args) {
        GeneralNode testNode = new GeneralNode();
        testNode.start();
        System.out.println("System: Waiting for nodes to be created...");
      /*  try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Message testMessage = new Message(true, false, null, null);
        System.out.println("System: Message Sent");
        testNode.receiveMessage(testMessage);
    }

}
