package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxTaskPerServers;
    private int maxNoServers;
    private Strategy strategy = new Strategy();

    public Scheduler(int maxNoServers,int maxTaskPerServers){
        this.maxNoServers=maxNoServers;
        this.maxTaskPerServers=maxTaskPerServers;
        servers=new ArrayList<>();
        for(int i=0;i<maxNoServers;i++){
            Server server=new Server(maxTaskPerServers);
            servers.add(server);
            Thread t=new Thread(server);
            t.start();
        }

    }

    public void dispatchTask(Task t){
        strategy.addTask(servers,t);
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = servers;
    }

    public int getMaxTaskPerServers() {
        return maxTaskPerServers;
    }

    public void setMaxTaskPerServers(int maxTaskPerServers) {
        this.maxTaskPerServers = maxTaskPerServers;
    }
}
