package io.github.router;

import java.util.List;

/**
 * @description:
 * @author: chenrq
 * @date: 2020年11月02日 21时03分
 */
public interface HttpEndpointRouter {
    String route(List<String> endpoints);
}
