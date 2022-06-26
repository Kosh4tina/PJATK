package zad1;

import java.util.ArrayList;
import java.util.List;

public class Letters {
    List<Thread> threads;

    public Letters(String str) {
        threads = new ArrayList<>();
        for(char c : str.toCharArray()){
            Thread thread = new Thread();
            threads.add(new Thread("Thread " + c){
                public void run() {
                    while (true) {
                        System.out.print(this.getName().replace("Thread ", ""));
                        try {
                            Thread.sleep(1250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void startT(){
        threads.forEach(Thread::start);
    }

    public void stopT(){
        threads.forEach(Thread::stop);
    }

    public List<Thread> getThreads() {
        return threads;
    }
}
