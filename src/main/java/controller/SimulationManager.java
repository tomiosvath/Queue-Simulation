/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for simulating the flow of the clients into different queues
 */
package controller;

import model.Client;
import model.FileService;
import model.Queue;
import model.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {

    private int timeLimit = 200;
    private int minProcessingTime = 3;
    private int maxProcessingTime = 9;
    private int minArrivalTime = 10;
    private int maxArrivalTime = 100;
    private int numberOfQueues = 20;
    private int numberOfClients = 1000;
    private int waitingTime = 0;
    private Scheduler.SelectionPolicy selectionPolicy = Scheduler.SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private ArrayList<Client> generatedClients = new ArrayList<Client>();


    /**
     * Constructor for the SimulationManager, with the input data from the input file
     */
    public SimulationManager() {
        FileService.readSimulationData(this);
        scheduler = new Scheduler(numberOfQueues);
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomClients();
    }

    //testing
    public SimulationManager(int test) {
        timeLimit = 11;
        numberOfQueues = 2;
        numberOfClients = 5;
        scheduler = new Scheduler(numberOfQueues);
        scheduler.changeStrategy(selectionPolicy);
        Client c1 = new Client(2, 2, 1);
        Client c2 = new Client(2, 3, 2);
        Client c3 = new Client(2, 3, 3);
        Client c4 = new Client(8, 2, 4);
        Client c5 = new Client(2, 2, 5);
        Client c6 = new Client(2, 2, 6);
        //Client c5 = new Client(2, 2, 5);
        generatedClients.add(c1);
        generatedClients.add(c2);
        generatedClients.add(c3);
        generatedClients.add(c4);
        generatedClients.add(c5);
        generatedClients.add(c6);
    }

    /**
     * Function for generating N random clients
     */
    private void generateNRandomClients(){
        Random rand = new Random();
        for (int i = 1; i <= numberOfClients; i++){
            int pTime = rand.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            int aTime = rand.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            Client c = new Client(aTime, pTime, i);
            generatedClients.add(c);
        }
    }

    /**
     * Function for listing all the clients
     */
    public void listClients(){
        System.out.print("Waiting clients: ");
        FileService.write("Waiting clients: ");
        for (Client c: generatedClients){
            System.out.print("(" + c.getClientNo() + "," + c.getArrivalTime() + "," + c.getServiceTime() + "); ");
            FileService.write("(" + c.getClientNo() + "," + c.getArrivalTime() + "," + c.getServiceTime() + "); ");
        }
        System.out.println();
        FileService.writeln("");
    }

    /**
     * Run method for the thread
     */
    @Override
    public void run() {
        int currentTime = 0;
        int clientsServed = 0;
        List <Client> toRemove = new ArrayList<Client>();

        while (currentTime < timeLimit){
            System.out.println("TIME " + currentTime);
            FileService.writeln("TIME " + currentTime);

            for (Client c: generatedClients){
                if (c.getArrivalTime() == currentTime){
                    scheduler.dispatchClient(c);
                    if (c.getFinishTime() < timeLimit) {
                        waitingTime += c.getWaitingPeriod();
                        clientsServed++;
                    }
                    toRemove.add(c);
                }
            }
            generatedClients.removeAll(toRemove);
            toRemove.clear();
            scheduler.startQueues();
            listClients();

            //listing of queues
            for (Queue q: scheduler.getQueues()){
                System.out.print("Queue " + (scheduler.getQueues().indexOf(q) + 1) + ": ");
                FileService.write("Queue " + (scheduler.getQueues().indexOf(q) + 1) + ": ");
                q.listQueue();
            }

            for (Queue q: scheduler.getQueues()){
                if (!q.isEmpty()) {
                    //q.decrementWaitingPeriod();
                    q.decrementHeadServiceTime();//
                }
            }

            currentTime++;
            System.out.println();
            FileService.writeln("");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Average waiting time: " + waitingTime / (float)clientsServed);
        FileService.write("Average waiting time: " + waitingTime / (float)clientsServed);
        FileService.closeFile();
        System.exit(0);
    }


    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

}
