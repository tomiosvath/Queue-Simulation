/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for storing a number of Client objects using a queue
 */
package model;
        import java.util.concurrent.BlockingQueue;
        import java.util.concurrent.LinkedBlockingQueue;
        import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    private BlockingQueue <Client> clients;
    private AtomicInteger waitingPeriod;
    private volatile boolean isRunning;

    /**
     * Constructor
     */
    public Queue(){
        waitingPeriod = new AtomicInteger(0);
        clients = new LinkedBlockingQueue<Client>();
        isRunning = false;
    }

    /**
     * Function for adding a new client to the queue
     * @param client the client to be added
     */
    public void addClient(Client client){
        clients.add(client);
        client.setWaitingPeriod(waitingPeriod.get() + client.getServiceTime());
        client.setFinishTime(client.getArrivalTime() + client.getServiceTime() + waitingPeriod.get());
        waitingPeriod.set(waitingPeriod.get() + client.getServiceTime());
    }

    /**
     * Function for running the thread
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                Client client = clients.peek();
                int serviceTime = client.getServiceTime();

                Thread.sleep(client.getServiceTime() * 1000);
                waitingPeriod.set(waitingPeriod.get() - serviceTime);

                this.clients.take();

                if (clients.isEmpty()){
                    isRunning = false;
                    waitingPeriod.set(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getWaitingPeriod(){
        return waitingPeriod.get();
    }

    /**
     * Function for decrementing the waiting period on the queue
     */
    public void decrementWaitingPeriod(){
        if (!clients.isEmpty())
            waitingPeriod.decrementAndGet();
    }

    /**
     * Function for decrementing the service time for the first element in the queue
     */
    public void decrementHeadServiceTime(){
        clients.peek().decreaseServiceTime();
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Function for checking if the queue is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (clients.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Function for listing the elements in the queue
     */
    public void listQueue(){
        int lastToServe = 0;
        if (!isEmpty()) {
            for (Client c : clients)
                if (c.getServiceTime() != 0) {
                    System.out.print("(" + c.getClientNo() + "," + c.getArrivalTime() + "," + c.getServiceTime()/* + "," + c.getWaitingPeriod()*/ + "); ");
                    FileService.write("(" + c.getClientNo() + "," + c.getArrivalTime() + "," + c.getServiceTime()/* + "," + c.getWaitingPeriod()*/ + "); ");
                    lastToServe = 1;
                }
        }
        if (lastToServe == 0) {
            System.out.print("closed");
            FileService.write("closed");
        }
        System.out.println();
        FileService.writeln("");
    }

}
