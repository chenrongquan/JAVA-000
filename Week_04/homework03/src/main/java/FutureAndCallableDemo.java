import java.util.concurrent.*;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月12日 01时03分
 */
public class FutureAndCallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        };
        Future<Integer> future = service.submit(task);
        service.shutdown();

        System.out.println("异步计算结果为："+future.get());
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