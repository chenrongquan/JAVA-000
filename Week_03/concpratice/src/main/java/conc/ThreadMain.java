package conc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 20时43分
 */
public class ThreadMain {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.start();
        System.out.println("这是主线程：");

        ThreadB threadB = new ThreadB();
        threadB.start();
        System.out.println("这是主线程：");

        ThreadC threadC = new ThreadC();
        FutureTask<String> futureTask = new FutureTask<>(threadC);
        new Thread(futureTask).start();
        System.out.println("这是主线程:begin!");
        try {
            System.out.println("得到的返回结果是:" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}