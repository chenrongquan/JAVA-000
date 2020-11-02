package conc.threadpool;

import java.util.concurrent.*;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 01时24分
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);
        try {
            String str = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "I am a task, which submited by the so called laoda, and run by those anonymous workers";
                }
            }).get();
            System.out.println("str=" + str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}