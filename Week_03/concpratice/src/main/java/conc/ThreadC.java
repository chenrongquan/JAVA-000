package conc;

import java.util.concurrent.Callable;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月01日 20时42分
 */
public class ThreadC implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(500);
        System.out.println("这是线程C");
        return "线程C";
    }
}