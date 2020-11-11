import java.util.concurrent.*;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月12日 01时03分
 */
public class FutureAndRunnableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();

        ExecutorService service = Executors.newSingleThreadExecutor();
        MyTask myTask = new MyTask();
        Future<MyTask> future = service.submit(myTask, myTask);
        service.shutdown();

        System.out.println("异步计算结果为："+future.get().getResult());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    static class MyTask implements Runnable {

        private int result;

        @Override
        public void run() {
            result = sum();
        }

        public int getResult() {
            return result;
        }
    }


    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}