package conc.op;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 21时01分
 */
public class Join {
    public static void main(String[] args) {
        Object oo = new Object();
        MyThread thread1 = new MyThread("thread1 -- ");
        thread1.setOo(oo);
        thread1.start();

        synchronized (thread1) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

}

class MyThread extends Thread {
    public String name;
    public Object oo;

    public MyThread(String name) {
        this.name = name;
    }

    public void setOo(Object oo) {
        this.oo = oo;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println(name + i);
            }
        }
    }
}