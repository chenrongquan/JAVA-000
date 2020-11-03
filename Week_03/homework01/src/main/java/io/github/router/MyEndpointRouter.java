package io.github.router;

import java.util.List;
import java.util.Random;

/**
 * @description: 作业四：封装路由
 * @author: chenrq
 * @date: 2020年11月03日 11时42分
 */
public class MyEndpointRouter implements HttpEndpointRouter{

    @Override
    public String route(List<String> endpoints) {
        Random random = new Random();
        int i = random.nextInt(endpoints.size());
        return endpoints.get(i);
    }
}