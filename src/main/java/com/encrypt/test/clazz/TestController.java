package com.encrypt.test.clazz;

import com.encrypt.test.utils.OverClazzLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen
 * @create 2021-02-01 16:12
 */

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/")
    String test() {
        return "hello world!";
    }

    @GetMapping("/service")
    Object service() {
        return OverClazzLoader.callMethod("com.encrypt.test.clazz.TestService", "test");
    }

    @GetMapping("/service1")
    Object service1() {
        return OverClazzLoader.callMethod("com.encrypt.test.clazz.TestService1", "test");
    }


}
