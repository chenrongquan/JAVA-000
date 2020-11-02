package conc.sync;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 22时58分
 */
public class Counter {
    private volatile int sum = 0;
    public void incr() {
        sum++;
    }
    public int getSum() {
        return sum;
    }

    public static void main(String[] args) {
        int loop = 1_0000;
        Counter counter = new Counter();
        for (int i = 0; i < loop; i++) {
            counter.incr();
        }

        System.out.println("single thread: " + counter.getSum());

        final Counter counter2 = new Counter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loop/2; i++) {
                    counter2.incr();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loop/2; i++) {
                    counter2.incr();
                }
            }
        });
        t1.start();
        t2.start();
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("multiple threads: " + counter2.getSum());
    }
}