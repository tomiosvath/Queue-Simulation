/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Interface for selecting the working strategy
 */
package model;
import java.util.List;

public interface Strategy {
    /**
     * Function for adding a client
     * @param queues list of the queues
     * @param c client
     */
    public void addClient(List<Queue> queues, Client c);
}
