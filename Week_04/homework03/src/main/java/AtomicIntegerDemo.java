import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月12日 01时03分
 */
public class AtomicIntegerDemo {
    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        AtomicInteger result = new AtomicInteger(0);
        Thread t = new Thread(() -> {
            result.set(sum());
        });
        t.start();
        t.join();

        System.out.println("异步计算结果为："+result.get());
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