package conc;

/**
 * @description: 守护线程示例
 * @author: chenrq
 * @date: 2020年11月01日 20时07分
 */
public class DaemonThread {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread thread = Thread.currentThread();
                System.out.println("当前线程：" + thread.getName());
            }
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        thread.setDaemon(false);
        thread.start();
    }
}