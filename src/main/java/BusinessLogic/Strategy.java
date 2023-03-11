package BusinessLogic;

import Model.Server;
import Model.Status;
import Model.StatusQueue;
import Model.Task;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Strategy {

    public void addTask(ArrayList<Server> s, Task t){
        Server m=null;
        int aux=10000;
        for(Server serv:s){
           // System.out.println("iterate server");
            if(serv.getWaitingPer().get()<aux){
                m=serv;
                aux=serv.getWaitingPer().get();
            }
        }
        if(m != null) {
            System.out.print("Task ");
            System.out.println("(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ") dispatched ->");
            m.addTask(t);
            m.setStatusQueue(StatusQueue.ACTIVE);//we add in the queue a client=>ACTIVE
        }
    }
}
