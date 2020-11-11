import java.util.concurrent.*;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月12日 01时03分
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();

        FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
            return sum();
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("异步计算结果为："+futureTask.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
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