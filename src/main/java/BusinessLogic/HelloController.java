package BusinessLogic;

import Model.Server;
import Model.Status;
import Model.StatusQueue;
import Model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    @FXML
    private TextField noQueues;
    @FXML
    private TextField noClients;
    @FXML
    private TextField simulationTime;
    @FXML
    private TextField minArrTime;
    @FXML
    private TextField maxArrTime;
    @FXML
    private TextField minServTime;
    @FXML
    private TextField maxServTime;
    @FXML
    private TextField timer;
    @FXML
    private TextField avgWaitingTime;
    @FXML
    private TextField avgServiceTime;
    @FXML
    private TextField peakHour;
    @FXML
    private Rectangle server1;
    @FXML
    private Rectangle server2;
    @FXML
    private Rectangle server3;
    @FXML
    private Rectangle server4;
    @FXML
    private Rectangle server5;
    @FXML
    private Rectangle server6;
    @FXML
    private Rectangle server7;
    @FXML
    private Rectangle server8;
    @FXML
    private Rectangle server9;
    @FXML
    private Rectangle server10;
    @FXML
    private Rectangle server11;
    @FXML
    private Rectangle server12;
    @FXML
    private Rectangle server13;
    @FXML
    private Rectangle server14;
    @FXML
    private Rectangle server15;
    @FXML
    private Rectangle server16;
    @FXML
    private Rectangle server17;
    @FXML
    private Rectangle server18;
    @FXML
    private Rectangle server19;
    @FXML
    private Rectangle server20;
    @FXML
    private TextArea genQueues;
    @FXML
    private TextArea q1;
    @FXML
    private TextArea q2;
    @FXML
    private TextArea q3;
    @FXML
    private TextArea q4;
    @FXML
    private TextArea q5;
    @FXML
    private TextArea q6;
    @FXML
    private TextArea q7;
    @FXML
    private TextArea q8;
    @FXML
    private TextArea q9;
    @FXML
    private TextArea q10;
    @FXML
    private TextArea q11;
    @FXML
    private TextArea q12;
    @FXML
    private TextArea q13;
    @FXML
    private TextArea q14;
    @FXML
    private TextArea q15;
    @FXML
    private TextArea q16;
    @FXML
    private TextArea q17;
    @FXML
    private TextArea q18;
    @FXML
    private TextArea q19;
    @FXML
    private TextArea q20;

    @FXML
    private TextArea c1;
    @FXML
    private TextArea c2;
    @FXML
    private TextArea c3;
    @FXML
    private TextArea c4;
    @FXML
    private TextArea c5;
    @FXML
    private TextArea c6;
    @FXML
    private TextArea c7;
    @FXML
    private TextArea c8;
    @FXML
    private TextArea c9;
    @FXML
    private TextArea c10;
    @FXML
    private TextArea c11;
    @FXML
    private TextArea c12;
    @FXML
    private TextArea c13;
    @FXML
    private TextArea c14;
    @FXML
    private TextArea c15;
    @FXML
    private TextArea c16;
    @FXML
    private TextArea c17;
    @FXML
    private TextArea c18;
    @FXML
    private TextArea c19;
    @FXML
    private TextArea c20;

    public int getInfo(String s) {
        final String VERIFY_NUMBERS = "([0-9]+)";
        Pattern p1 = Pattern.compile(VERIFY_NUMBERS);
        Matcher m1 = p1.matcher(s);
        if (m1.find()) {
            return Integer.parseInt(s);
        }
        return -1;
    }

    public String convertToString(int nr) {
        String s = String.valueOf(nr);
        return s;
    }

    public void writeTasksClosed(TextArea text, ArrayList<Task> task) {
        StringBuilder str = new StringBuilder("");
        for (Task t : task) {
            str.append("(");
            str.append(t.getID());
            str.append(",");
            str.append(convertToString(t.getArrivalTime()));
            str.append(",");
            str.append(convertToString(t.getAuxServTime()));
            str.append(")\n");
        }
        text.setText(String.valueOf(str));
    }


    public void chooseClosed(ArrayList<Task> t, int count) {
        switch (count) {
            case 1:
                writeTasksClosed(c1, t);
                break;
            case 2:
                writeTasksClosed(c2, t);
                break;
            case 3:
                writeTasksClosed(c3, t);
                break;
            case 4:
                writeTasksClosed(c4, t);
                break;
            case 5:
                writeTasksClosed(c5, t);
                break;
            case 6:
                writeTasksClosed(c6, t);
                break;
            case 7:
                writeTasksClosed(c7, t);
                break;
            case 8:
                writeTasksClosed(c8, t);
                break;
            case 9:
                writeTasksClosed(c9, t);
                break;
            case 10:
                writeTasksClosed(c10, t);
                break;
            case 11:
                writeTasksClosed(c11, t);
                break;
            case 12:
                writeTasksClosed(c12, t);
                break;
            case 13:
                writeTasksClosed(c13, t);
                break;
            case 14:
                writeTasksClosed(c14, t);
                break;
            case 15:
                writeTasksClosed(c15, t);
                break;
            case 16:
                writeTasksClosed(c16, t);
                break;
            case 17:
                writeTasksClosed(c17, t);
                break;
            case 18:
                writeTasksClosed(c18, t);
                break;
            case 19:
                writeTasksClosed(c19, t);
                break;
            case 20:
                writeTasksClosed(c20, t);
                break;

        }
    }

    public void setClosedQueue(ArrayList<Server> s) {
        int count = 0;
        for (Server serv : s) {
            if (!serv.getClosedQueue().isEmpty()) {
                count++;
                chooseClosed(serv.getClosedQueue(), count);
            }
        }
    }

    public void writeTasks(TextArea q, Server s, int index) {
        StringBuilder str = new StringBuilder("");
        if (index == 0) {
            for (Task t : s.getTasks()) {
                str.append("(");
                str.append(t.getID());
                str.append(",");
                str.append(convertToString(t.getArrivalTime()));
                str.append(",");
                str.append(convertToString(t.getServiceTime()));
                str.append(",");
                if (t.getStatus() == Status.ACTIVE)
                    str.append("a");
                else if (t.getStatus() == Status.PENDING)
                    str.append("p");
                str.append(")\n");
            }
            q.setText(String.valueOf(str));
        } else if (index == 1) {
            q.setText("");
        }
    }

    public void setT(Server s, int count, int index) {
        switch (count) {
            case 1:
                writeTasks(q1, s, index);
                break;
            case 2:
                writeTasks(q2, s, index);
                break;
            case 3:
                writeTasks(q3, s, index);
                break;
            case 4:
                writeTasks(q4, s, index);
                break;
            case 5:
                writeTasks(q5, s, index);
                break;
            case 6:
                writeTasks(q6, s, index);
                break;
            case 7:
                writeTasks(q7, s, index);
                break;
            case 8:
                writeTasks(q8, s, index);
                break;
            case 9:
                writeTasks(q9, s, index);
                break;
            case 10:
                writeTasks(q10, s, index);
                break;
            case 11:
                writeTasks(q11, s, index);
                break;
            case 12:
                writeTasks(q12, s, index);
                break;
            case 13:
                writeTasks(q13, s, index);
                break;
            case 14:
                writeTasks(q14, s, index);
                break;
            case 15:
                writeTasks(q15, s, index);
                break;
            case 16:
                writeTasks(q16, s, index);
                break;
            case 17:
                writeTasks(q17, s, index);
                break;
            case 18:
                writeTasks(q18, s, index);
                break;
            case 19:
                writeTasks(q19, s, index);
                break;
            case 20:
                writeTasks(q20, s, index);
                break;


        }
    }

    public void setQueues(ArrayList<Server> s) {
        int count = 0;
        for (Server serv : s) {
            count++;
            if (serv.getStatusQueue() == StatusQueue.ACTIVE) {
                setT(serv, count, 0);
            } else if (serv.getStatusQueue() == StatusQueue.STAND_BY)
                setT(serv, count, 1);

        }
    }

    public void colorServer(Rectangle r, int index) {
        if (index == 1)
            r.setFill(Color.GREEN);
        else if (index == 2)
            r.setFill(Color.BLUE);
        else if (index == 3)
            r.setFill(Color.GRAY);
    }

    public void chooseServer(int count, int index) {
        switch (count) {
            case 1:
                colorServer(server1, index);
                break;
            case 2:
                colorServer(server2, index);
                break;
            case 3:
                colorServer(server3, index);
                break;
            case 4:
                colorServer(server4, index);
                break;
            case 5:
                colorServer(server5, index);
                break;
            case 6:
                colorServer(server6, index);
                break;
            case 7:
                colorServer(server7, index);
                break;
            case 8:
                colorServer(server8, index);
                break;
            case 9:
                colorServer(server9, index);
                break;
            case 10:
                colorServer(server10, index);
                break;
            case 11:
                colorServer(server11, index);
                break;
            case 12:
                colorServer(server12, index);
                break;
            case 13:
                colorServer(server13, index);
                break;
            case 14:
                colorServer(server14, index);
                break;
            case 15:
                colorServer(server15, index);
                break;
            case 16:
                colorServer(server16, index);
                break;
            case 17:
                colorServer(server17, index);
                break;
            case 18:
                colorServer(server18, index);
                break;
            case 19:
                colorServer(server19, index);
                break;
            case 20:
                colorServer(server20, index);
                break;

        }
    }

    public void setServers(ArrayList<Server> s) {
        int count = 0;
        for (Server serv : s) {
            count++;
            if (serv.getStatusQueue() == StatusQueue.ACTIVE)
                chooseServer(count, 1);
            else if (serv.getStatusQueue() == StatusQueue.STAND_BY)
                chooseServer(count, 2);
            else
                chooseServer(count,3);
        }
    }

    public void setTasks(ArrayList<Task> task) {
        if (!task.isEmpty()) {
            StringBuilder s = new StringBuilder("");
            for (Task t1 : task) {
                s.append("(");
                s.append(t1.getID());
                s.append(",");
                s.append(convertToString(t1.getArrivalTime()));
                s.append(",");
                s.append(convertToString(t1.getServiceTime()));
                s.append(")\n");
            }
            // System.out.println(s);
            genQueues.setText(String.valueOf(s));
        } else
            genQueues.setText("");
    }

    public void setComputedTime(int time, double waitTime, int servTime, int peakH) {
        timer.setText(convertToString(time));
        avgWaitingTime.setText(String.valueOf(waitTime));
        avgServiceTime.setText(convertToString(servTime));
        peakHour.setText(convertToString(peakH));
    }

    public void buttonStart() {
        int clients = 0, queues = 0, minArr = 0, maxArr = 0, minServ = 0, maxServ = 0, simTime = 0;
        if (simulationTime.getText().isEmpty() || noClients.getText().isEmpty() || noQueues.getText().isEmpty() ||
                minArrTime.getText().isEmpty() || maxArrTime.getText().isEmpty() || minServTime.getText().isEmpty() || maxServTime.getText().isEmpty()) {
            invalidData();
        } else {
            simTime = getInfo(simulationTime.getText());
            clients = getInfo(noClients.getText());
            queues = getInfo(noQueues.getText());
            minArr = getInfo(minArrTime.getText());
            maxArr = getInfo(maxArrTime.getText());
            minServ = getInfo(minServTime.getText());
            maxServ = getInfo(maxServTime.getText());
        }
        if (minArr > maxArr || minServ > maxServ || simTime == -1 || clients == -1 || queues == -1 || simTime == 0 || clients == 0 || queues == 0) {
            invalidData();
        } else {
            FileWriter file = null;
            try {
                file = new FileWriter("test3.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            SimulationManager gen = new SimulationManager(simTime, clients, queues, minArr, maxArr, minServ, maxServ,file);
            gen.setController(this);
            Thread t = new Thread(gen);
            t.start();
            //System.out.println(simTime+" "+clients+" "+queues+" "+" "+minArr+" "+maxArr+" "+minServ+" "+maxServ);
        }

    }
    public void invalidData() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR MESSAGE");
        alert.setHeaderText("Some data was introduced incorrectly or not introduced at all");
        alert.setContentText("Please introduce only positive integers and respect the sign-> <");
        alert.showAndWait();
    }
}