package BusinessLogic;

import Model.Server;
import Model.Status;
import Model.StatusQueue;
import Model.Task;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxServiceTime;
    public int minServiceTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private List<Task> task;
    private int avgServTime = 0;
    private double avgWaitTime;
    private int peakH = 0;
    private int noClients = 0; //count the clients in the queue to compute peak hour
    private int maxNoClients = -10000;
    private int currentTime;
    private int totalServedClients = 0;
    private FileWriter file;
    private HelloController controller;

    public void setController(HelloController controller) {
        this.controller = controller;
    }

    public SimulationManager(int timeLimit, int numberOfClients, int numberOfServers, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, FileWriter file) {
        task = new ArrayList<>();
        this.timeLimit = timeLimit;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.file = file;
        scheduler = new Scheduler(numberOfServers, 15);
        generateNRandomTasks();
    }

    public SimulationManager(int timeLimit, int numberOfClients, int numberOfServers, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime) {
        task = new ArrayList<>();
        this.timeLimit = timeLimit;
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        scheduler = new Scheduler(numberOfServers, 15);
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        int aux1, aux2;
        for (int i = 0; i < numberOfClients; i++) {
            aux1 = (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
            aux2 = (int) (Math.random() * (maxServiceTime - minServiceTime + 1) + minServiceTime);
            Task t = new Task(i + 1, aux1, aux2);
            task.add(t);
        }
        Collections.sort(task, new SortTasks());
    }

    @Override
    public void run() {
        currentTime = 0;
        while (currentTime < timeLimit && totalServedClients != numberOfClients) {
            totalServedClients = 0;
            int numberClosedQ = 0, numberTasks = 0;
            avgServTime = 0;
            avgWaitTime = 0;
            noClients = 0;
            System.out.println("Time " + currentTime);
            List<Task> removed = new ArrayList<>();
            for (Task t : task) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    removed.add(t);
                }
            }
            task.removeAll(removed);
            System.out.println("Waiting tasks: ");
            for (Task t : task) {
                System.out.print("(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")");
            }
            System.out.println();
            int index = 0;
            for (Server s : scheduler.getServers()) {
                index++;
                if (s.getStatusQueue() == StatusQueue.ACTIVE) {
                    System.out.println("Queue" + index + " active");
                    for (Task t : s.getTasks()) {
                        if (t.getStatus() == Status.ACTIVE)
                            System.out.println("Active task");
                        else if (t.getStatus() == Status.PENDING)
                            System.out.println("Pending task");
                        System.out.println(t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime());
                    }
                } else if (s.getStatusQueue() == StatusQueue.STAND_BY)
                    System.out.println("Queue" + index + " stand by");
                else
                    System.out.println("Queue" + index + " inactive");
            }
            for (Server s : scheduler.getServers()) {
                Server serv = s;
                for (Task t : s.getClosedQueue()) {
                    totalServedClients++;
                    numberClosedQ++;
                    avgServTime = avgServTime + t.getAuxServTime();
                }
                System.out.println("WAITING TIME");
                for (Task t1 : s.getTasks()) {
                    numberClosedQ++;
                    avgServTime = avgServTime + t1.getAuxServTime();
                    noClients++;
                    if (t1.getStatus() == Status.PENDING) {
                        System.out.println(t1.getWaiting());
                        numberTasks++;
                        avgWaitTime += t1.getWaiting();
                    }
                }

            }
            if (numberTasks != 0) {
                avgWaitTime = avgWaitTime / numberTasks;
            }
            else
                avgWaitTime=0;
            if (numberClosedQ != 0)
                avgServTime = avgServTime / numberClosedQ;
            if (maxNoClients < noClients) {
                maxNoClients = noClients;
                peakH = currentTime;
            }
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      //refresh controller
                                      controller.setComputedTime(currentTime, avgWaitTime, avgServTime, peakH);
                                      controller.setTasks((ArrayList<Task>) task);
                                      controller.setServers((ArrayList<Server>) scheduler.getServers());
                                      controller.setQueues((ArrayList<Server>) scheduler.getServers());
                                      controller.setClosedQueue((ArrayList<Server>) scheduler.getServers());
                                  }
                              }
            );
            try {
                writeFile(currentTime);
            } catch (IOException e) {
                System.out.println("COULD NOT WRITE");
                e.printStackTrace();
            }
            currentTime++;
            System.out.println("AvgWait " + avgWaitTime + " AvgServ " + avgServTime + " peakHour " + peakH);
            try {
                Thread.sleep(1000);  //wait one second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // controller.setClosedQueue((ArrayList<Server>) scheduler.getServers());
        for (Server s : scheduler.getServers()) {
            s.setStopServer(1);
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        public void writeFile(int currTime) throws IOException {
        file.write("Timer: ");
        file.write(String.valueOf(currTime));
        file.write("\n");
        file.write("Average waiting time: ");
        file.write(String.valueOf(avgWaitTime));
        file.write("\n");
        file.write("Average service time: ");
        file.write(String.valueOf(avgServTime));
        file.write("\n");
        file.write("Peak hour: ");
        file.write(String.valueOf(peakH));
        file.write("\n");
        file.write("Waiting tasks: ");
        for(Task t:task){
            file.write("(");
            file.write(String.valueOf(t.getID()));
            file.write(",");
            file.write(String.valueOf(t.getArrivalTime()));
            file.write(",");
            file.write(String.valueOf(t.getServiceTime()));
            file.write(")");
        }
        file.write("\n");
        int count=1;
        for(Server serv: scheduler.getServers()){
            file.write("Queue");
            file.write(String.valueOf(count));
            file.write("\n");
            count++;
            for(Task t:serv.getTasks()){
                file.write("(");
                file.write(String.valueOf(t.getID()));
                file.write(",");
                file.write(String.valueOf(t.getArrivalTime()));
                file.write(",");
                file.write(String.valueOf(t.getServiceTime()));
                file.write(")");
            }
            file.write("\n");
        }
        file.write("\n");
        count=1;
        for(Server s: scheduler.getServers()){
            file.write("Closed queue");
            file.write(String.valueOf(count));
            file.write("\n");
            count++;
            for(Task t:s.getClosedQueue()){
                file.write("(");
                file.write(String.valueOf(t.getID()));
                file.write(",");
                file.write(String.valueOf(t.getArrivalTime()));
                file.write(",");
                file.write(String.valueOf(t.getAuxServTime()));
                file.write(")");
            }
            file.write("\n");
        }
        file.write("\n");
    }

}
