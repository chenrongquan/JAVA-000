package conc;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 20时29分
 */
public class Runner1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner1运行状态——————————" + i);
        }
    }
}