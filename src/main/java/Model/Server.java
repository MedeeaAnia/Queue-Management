package Model;

import BusinessLogic.SimulationManager;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPer; //period to wait for each queue
    private ArrayList<Task> closedQueue;
    private StatusQueue statusQueue;
    private int stopServer=0;


    public Server(int maxNumberOfClients){
        tasks=new LinkedBlockingQueue<>(maxNumberOfClients);
        closedQueue=new ArrayList<>();
        waitingPer=new AtomicInteger(0);
        statusQueue=StatusQueue.INACTIVE; //in the beginning the queue is inactive
    }

    public int getStopServer() {
        return stopServer;
    }

    public void setStopServer(int stopServer) {
        this.stopServer = stopServer;
    }

    public StatusQueue getStatusQueue() {
        return statusQueue;
    }

    public void setStatusQueue(StatusQueue statusQueue) {
        this.statusQueue = statusQueue;
    }

    public void addTask(Task t){
        if(tasks.isEmpty()){
            t.setStatus(Status.ACTIVE);
//            System.out.println("first task in add task server: ");
//            System.out.println(firstTask.getID());
        }
        else{
            t.setStatus(Status.PENDING);
        }
        tasks.add(t);
        int aux=waitingPer.get();
        aux+=t.getServiceTime();
        waitingPer.set(aux);
    }

    @Override
    public void run() {
        while(stopServer==0){
            try {
                if(tasks.isEmpty() && statusQueue==StatusQueue.ACTIVE)
                    statusQueue=StatusQueue.STAND_BY;
                Task firstTask = tasks.peek();
//                if (!tasks.isEmpty()){
//                    for(Task t: tasks){
//                        if(t.getWaiting()==0 && t.getStatus()==Status.PENDING)
//                            t.setWaiting(1);
//                    }
//                }
                if (firstTask != null) {
                    firstTask.setStatus(Status.ACTIVE);
                    int time=firstTask.getServiceTime();
                    for (int i = 0; i < time; i++) {
                        Thread.sleep(1000);
                        waitingPer.getAndDecrement();
                        firstTask.setServiceTime(firstTask.getServiceTime()-1);
                        for(Task t: tasks){
                            if(t.getStatus()==Status.PENDING)
                                t.setWaiting(t.getWaiting()+1);
                        }
                    }
                    firstTask.setStatus(Status.INACTIVE);
                    closedQueue.add(firstTask);
                    tasks.take();
//                    if(!tasks.isEmpty())
//                        firstTask = tasks.take();
//                    firstTask.setStatus(Status.ACTIVE);
//                    System.out.print("first task in server: ");
//                    System.out.println(firstTask.getID()+ " "+firstTask.getArrivalTime()+ " " +firstTask.getServiceTime());
//                    if (tasks.isEmpty()) {
//                        statusQueue = StatusQueue.STAND_BY.STAND_BY; //if we don't have any clients in the queue
//                    }
//                    firstTask.setStatus(Status.ACTIVE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public AtomicInteger getWaitingPer() {
        return waitingPer;
    }

    public void setWaitingPer(AtomicInteger waitingPer) {
        this.waitingPer = waitingPer;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getClosedQueue() {
        return closedQueue;
    }

    public void setClosedQueue(ArrayList<Task> closedQueue) {
        this.closedQueue = closedQueue;
    }
}
