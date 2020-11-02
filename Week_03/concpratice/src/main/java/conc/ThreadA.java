package conc;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 20时39分
 */
public class ThreadA extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这是线程A");
    }
}