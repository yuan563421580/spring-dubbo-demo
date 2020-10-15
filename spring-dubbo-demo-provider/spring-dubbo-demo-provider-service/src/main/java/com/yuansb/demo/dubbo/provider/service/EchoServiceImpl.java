package com.yuansb.demo.dubbo.provider.service;

import com.yuansb.demo.dubbo.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 实现类
 */
@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String string) {
        return "Hello Apache Dubbo : " + string;
    }

}
