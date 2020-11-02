package conc;

import java.io.IOException;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 20时32分
 */
public class RunnerMain {
    public static void main(String[] args) throws IOException {
        Runner1 runner1 = new Runner1();
        Thread thread1 = new Thread(runner1);

        Runner2 runner2 = new Runner2();
        Thread thread2 = new Thread(runner2);

        thread1.start();
        thread2.start();

        thread2.interrupt();


//        System.in.read();
//        System.out.println(Thread.activeCount());
//        Thread.currentThread().getThreadGroup().list();
//        System.out.println(Thread.currentThread().getThreadGroup().getParent().activeCount());
//        Thread.currentThread().getThreadGroup().getParent().list();
    }
}