package in.bbms.history;

public class ModelHistory {

    String  date, task, blood, status;

    public ModelHistory(){

    }

    public ModelHistory(String date, String task, String blood, String status) {

        this.date = date;
        this.task = task;
        this.blood = blood;
        this.status = status;

    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}

