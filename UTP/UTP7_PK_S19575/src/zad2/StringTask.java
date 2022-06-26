package zad2;

public class StringTask implements Runnable{
    TaskStatements state;
    boolean isDone;
    int i;
    String toCopy;
    String result;
    Thread task;
    public StringTask(String a, int i) {
        this.i=i;
        toCopy=a;
        result="";
        state = TaskStatements.CREATED;
        isDone = false;
        task = new Thread(this);
        task.setName("Tread "+a);
    }

    public TaskStatements getState() {
        return state;
    }
    public String getName() {
        return task.getName();
    }

    public void start() {
        task.start();
        state = TaskStatements.RUNNING;
    }

    @Override
    public void run() {
        while (result.length()<i){
            result= result + toCopy;
        }
        state = TaskStatements.READY;
        isDone=true;
    }
    public void abort() {
        task.interrupt();
        state = TaskStatements.ABORTED;
        isDone=true;
    }

    public boolean isDone() {
        return isDone;
    }
    public String getResult(){
        return result;
    }
}
