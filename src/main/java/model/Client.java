/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for implementing a client's details
 */
package model;
public class Client {
    private int arrivalTime, serviceTime, clientNo, finishTime;
    private int waitingPeriod;

    /**
     * Constructor for a Client
     * @param aT arrivalTime
     * @param sT servingTime
     * @param cN number of the client
     */
    public Client(int aT, int sT, int cN){
        arrivalTime = aT;
        serviceTime = sT;
        clientNo = cN;
        finishTime = 0;
        waitingPeriod = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getClientNo() {
        return clientNo;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setWaitingPeriod(int period){
        waitingPeriod = period;
    }

    public void decreaseServiceTime(){
        serviceTime--;
    }

    public int getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setFinishTime(int time) {
        this.finishTime = time;
    }

}
