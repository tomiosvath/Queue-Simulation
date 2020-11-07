/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for implementing the Strategy which chooses the Queue with the smallest waiting time
 */

package model;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    /**
     * Function for adding a client to a queue from the list
     * @param queues list of the queues
     * @param c client
     */
    @Override
    public void addClient(List<Queue> queues, Client c) {
        int smallest = 10000000;
        int indexQueue = -1;
        for (Queue queue: queues) {
            if (queue.getWaitingPeriod() < smallest){
                smallest = queue.getWaitingPeriod();
                indexQueue = queues.indexOf(queue);
            }
        }

        if (indexQueue != -1)
            queues.get(indexQueue).addClient(c);
        else
            queues.get(0).addClient(c);

    }
}
