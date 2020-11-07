/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for scheduling the clients who are being sent to different queues
 */
package model;
import java.util.ArrayList;

public class Scheduler {
    public enum SelectionPolicy {
        SHORTEST_QUEUE, SHORTEST_TIME
    }
    private ArrayList<Queue> queues = new ArrayList<Queue>();
    private int maxNoQueues;
    private Strategy strategy;

    /**
     * Constructor
     * @param maxNoQueues the number of queues
     */
    public Scheduler(int maxNoQueues) {
        this.maxNoQueues = maxNoQueues;
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        for (int i = 0; i < maxNoQueues; i++)
            queues.add(new Queue());
    }

    /**
     * Function for starting the thread of the queues which are inactive
     */
    public void startQueues(){
        for (Queue q: getQueues()){
            if (!q.isRunning() && !q.isEmpty()){
                Thread thread = new Thread(q);
                thread.start();
            }
        }
    }

    /**
     * Function for changing the strategy for adding the clients to a queue
     * Only the strategy based on minimum waiting time is implemented
     * @param policy
     */
    public void changeStrategy(SelectionPolicy policy){
        /*if (policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }*/
        strategy = new ConcreteStrategyTime();
    }

    /**
     * Function for dispatching a client to a queue
     * @param c the client
     */
    public void dispatchClient(Client c){
        strategy.addClient(queues, c);
    }

    public ArrayList<Queue> getQueues(){
        return queues;
    }

}
