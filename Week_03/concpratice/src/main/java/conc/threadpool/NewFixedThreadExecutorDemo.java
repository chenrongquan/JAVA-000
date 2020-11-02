package conc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 01时32分
 */
public class NewFixedThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 10; i++) {
            final int no = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("start:" + no);
                        Thread.sleep(100L);
                        System.out.println("end:" + no);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        System.out.println("Main Thread End!");
    }
}