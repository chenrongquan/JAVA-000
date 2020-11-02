package conc.op;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 22时11分
 */
public class WaitAndNotify {
    public static void main(String[] args) {
        MethodClass methodClass = new MethodClass();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    methodClass.product();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    methodClass.customer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    methodClass.customer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class MethodClass {

    private final int MAX_COUNT = 20;

    int productCount = 0;

    public synchronized void product() throws InterruptedException {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
            Thread.sleep(1000);
            if (productCount >= MAX_COUNT) {
                System.out.println("货舱已满,,.不必再生产");
                wait();
            } else {
                productCount++;
            }
            notifyAll();
        }
    }

    public synchronized void customer() throws InterruptedException {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
            Thread.sleep(1000);
            if (productCount <= 0) {
                System.out.println("货舱已无货...无法消费");
                wait();
            } else {
                productCount--;
            }
            notifyAll();
        }
    }
}