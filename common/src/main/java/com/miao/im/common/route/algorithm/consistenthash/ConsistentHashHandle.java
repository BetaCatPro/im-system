package com.miao.im.common.route.algorithm.consistenthash;

import com.miao.im.common.route.RouteHandle;

import java.util.List;


public class ConsistentHashHandle implements RouteHandle {

    //TreeMap
    private AbstractConsistentHash hash;

    public void setHash(AbstractConsistentHash hash) {
        this.hash = hash;
    }

    @Override
    public String routeServer(List<String> values, String key) {
        return hash.process(values, key);
    }
}
