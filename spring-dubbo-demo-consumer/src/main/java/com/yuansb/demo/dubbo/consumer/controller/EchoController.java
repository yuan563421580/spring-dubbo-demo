package com.yuansb.demo.dubbo.consumer.controller;

import com.yuansb.demo.dubbo.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试实现类
 */
@RestController
public class EchoController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable("string") String string) {
        return echoService.echo(string);
    }

}
