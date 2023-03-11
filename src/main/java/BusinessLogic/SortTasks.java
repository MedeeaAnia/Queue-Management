package BusinessLogic;

import Model.Task;

import java.util.Comparator;
import java.util.List;

public class SortTasks implements Comparator<Task> {
    @Override
    public int compare(Task t1, Task t2) {
        return t1.getArrivalTime()- t2.getArrivalTime();
    }
}
