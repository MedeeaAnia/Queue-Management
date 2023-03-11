package Model;

public class Task {
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int tWaiting;
    private int auxServTime;
    private Status status;

    public Task(int ID,int arrivalTime,int serviceTime){
        this.ID=ID;
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
        this.auxServTime=serviceTime;
        status=Status.INACTIVE;
        tWaiting=0;
    }

    public int getAuxServTime() {
        return auxServTime;
    }

    public void setAuxServTime(int auxServTime) {
        this.auxServTime = auxServTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getWaiting() {
        return tWaiting;
    }

    public void setWaiting(int tWaiting) {
        this.tWaiting = tWaiting;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
