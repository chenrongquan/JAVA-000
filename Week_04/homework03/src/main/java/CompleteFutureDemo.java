import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月12日 03时00分
 */
public class CompleteFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();

        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            return sum();
        });
        System.out.println("异步计算结果为："+supplyAsync.get());
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