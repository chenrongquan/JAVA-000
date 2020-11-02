package conc.sync;

import conc.Runner2;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 00时48分
 */
public class Thread1 implements Runnable {
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);
            }
        }
    }

    public static void main(String[] args) {
        Thread1 t1 = new Thread1();
        Thread ta = new Thread(t1, "A");
        Thread tb = new Thread(t1, "B");
        ta.start();
        tb.start();
    }
}